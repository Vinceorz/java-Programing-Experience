package chess.bots;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


//import chess.bots.ParallelSearcher.MiniMaxTask;
import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Evaluator;
import cse332.chess.interfaces.Move;
import cse332.exceptions.NotYetImplementedException;

import java.util.ArrayList;
import java.util.List;


public class ParallelSearcher<M extends Move<M>, B extends Board<M, B>> extends
        AbstractSearcher<M, B> {
	private static final ForkJoinPool POOL = new ForkJoinPool();
	
	public M getBestMove(B board, int myTime, int opTime) {
        BestMove<M> best = parallel(this.evaluator, board, this.ply, this.cutoff);
        //reportNewBestMove(best.move);
        return best.move;
    }
    
    private static <M extends Move<M>, B extends Board<M, B>> BestMove<M> parallel(Evaluator<B> evaluator, B board, int depth, int cutoff) {
        List<M> moves = board.generateMoves();
        return POOL.invoke(new MiniMaxTask<M, B>(evaluator, board, moves, null, cutoff, depth));
    }

    public static class MiniMaxTask<M extends Move<M>, B extends Board<M, B>> extends RecursiveTask<BestMove<M>> {
        Evaluator<B> evaluator;
        B board;
        List<M> moves;
        M move;
        int depth, cutoff;
        final int DIVIDE_CUTOFF = 25;

        MiniMaxTask(Evaluator<B> evaluator, B board, List<M> moves, M move, int cutoff, int depth) {
            this.evaluator = evaluator;
            this.moves = moves;
            this.move = move;
            this.board = board;
            this.cutoff = cutoff;
            this.depth = depth;
        }

        @Override
        protected BestMove<M> compute() {
        	if (move != null) {
        		this.board = board.copy();
        		board.applyMove(move);
        		this.moves = board.generateMoves();
        		if (moves.isEmpty()) {
	                if (board.inCheck()) {
	                    return new BestMove<M>(-evaluator.mate() - depth);
	                } else {
	                    return new BestMove<M>(-evaluator.stalemate());
	                }
            	}
        	}
        	
        	if (depth <= cutoff) {
        		return SimpleSearcher.minimax(evaluator, board, depth);
        	}
        		
        	// stop divide and conquer and fork sequentially
            if (this.moves.size() <= DIVIDE_CUTOFF) {
                // moves is small enough
            	MiniMaxTask<M, B>[] forkSequential = (MiniMaxTask<M, B>[]) new MiniMaxTask[moves.size()];
                for (int i = 0; i < this.moves.size(); i++) {
                	forkSequential[i] = new MiniMaxTask<M, B>(evaluator, board, null, moves.get(i), cutoff, depth - 1);
                	forkSequential[i].fork();
                }
                BestMove<M> best = new BestMove<>(-evaluator.infty());
                for (int j = 0; j < forkSequential.length; j++) {
                	BestMove<M> currBest = forkSequential[j].join().negate();
                	if (currBest.value > best.value) {
                        currBest.move = moves.get(j);
                        best = currBest;
                    }
                }
                return best;
            } else {
                // split moves
            	List<M> leftMoves = this.moves.subList(0, this.moves.size() / 2);
                List<M> rightMoves = this.moves.subList(this.moves.size() / 2, this.moves.size());

                MiniMaxTask<M, B> left = new MiniMaxTask<M, B>(evaluator, board, leftMoves, null, cutoff, depth);
                MiniMaxTask<M, B> right = new MiniMaxTask<M, B>(evaluator, board, rightMoves, null, cutoff, depth);
                left.fork();
                BestMove<M> rightBest = right.compute();
                BestMove<M> leftBest = left.join();
                if (rightBest.value >= leftBest.value) {
                    return rightBest;
                }
                return leftBest;
            }
        }
    }
}