package chess.bots;

import cse332.chess.interfaces.AbstractSearcher;
import cse332.chess.interfaces.Board;
import cse332.chess.interfaces.Evaluator;
import cse332.chess.interfaces.Move;
import cse332.exceptions.NotYetImplementedException;

import java.util.List;

/**
 * This class should implement the minimax algorithm as described in the
 * assignment handouts.
 */
public class SimpleSearcher<M extends Move<M>, B extends Board<M, B>> extends
        AbstractSearcher<M, B> {

    public M getBestMove(B board, int myTime, int opTime) {
        /* Calculate the best move */
        BestMove<M> best = minimax(this.evaluator, board, ply);
        return best.move;
    }

    public static <M extends Move<M>, B extends Board<M, B>> BestMove<M> minimax(Evaluator<B> evaluator, B board, int depth) {
    	
    	// depth cutoff
        if (depth == 0) {
            return new BestMove<M>(evaluator.eval(board));
        }
        
        List<M> moves = board.generateMoves();

        // there is no move - either stalemate or mate
        if (moves.isEmpty()) {
            if (board.inCheck()) {
                return new BestMove<M>(-evaluator.mate() - depth);
            } else {
                return new BestMove<M>(-evaluator.stalemate());
            }
        }

        

        BestMove<M> best = new BestMove<>(-evaluator.infty());
        for (M currMove : moves) {
            board.applyMove(currMove);
            BestMove<M> currBest = minimax(evaluator, board, depth - 1).negate();
            board.undoMove();
            if (currBest.value > best.value) {
                currBest.move = currMove;
                best = currBest;
            }
        }

        return best;
    }
}