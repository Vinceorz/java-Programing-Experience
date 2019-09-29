package marvel;


import graph.Edge;
import graph.Graph;

import java.util.*;

/** This is a mutable object that allows to find the path which is the comic book between heroes */
public class MarvelPaths {
    private Graph<String, String> marvelGraph;

    /** The file path of the marvel heroes and  */
    private static final String TSV_FILE_PATH = "src/main/resources/marvel/data/marvel.tsv";

    public static void main(String[] args) {
        MarvelPaths result = new MarvelPaths(TSV_FILE_PATH);
        System.out.println("This is a program that can return the shortest path between two characters");
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Start Hero");
        String startHero = scan.nextLine();
        while (!result.containHero(startHero)) {
            System.out.println("Unknown character");
            System.out.println("Enter Start Hero");
            startHero = scan.nextLine();
        }
        System.out.println("Enter End Hero");
        String endHero = scan.nextLine();
        while (!result.containHero(endHero)) {
            System.out.println("Unknown character");
            System.out.println("Enter End Hero");
            endHero = scan.nextLine();
        }
        System.out.println(result.findPath(startHero,endHero));
    }

    /** Construct an empty MarvelPath */
    public MarvelPaths() {
        marvelGraph = new Graph<String, String>();
    }

    /**
     * Construct a MarvelPath with the given file
     *
     * @param filename the file that need to be read from;
     */
    public MarvelPaths(String filename) {
        marvelGraph = new Graph<String, String>();
        Map<String, List<String>> data = MarvelParser.parseData(filename);
        Set<String> books = data.keySet();
        for (String book : books) {
            List<String> characters = data.get(book);
            for (int i = 0; i < characters.size(); i++) {
                if (!marvelGraph.containsNode(characters.get(i))) {
                    marvelGraph.addNode(characters.get(i));
                }
                for (int j = 0; j < characters.size(); j++) {
                    if (!marvelGraph.containsNode(characters.get(j))) {
                        marvelGraph.addNode(characters.get(j));
                    }
                    if (i != j) {
                        marvelGraph.addEdge(characters.get(i), book, characters.get(j));
                    }
                }
            }
        }
    }

    /**
     * @param startHero the start character of the path
     * @param endHero the destination character of the path
     * @return null if no path found or a list of edges which represents the path
     *
     * */
    public List<Edge<String, String>> findPath(String startHero, String endHero) {
        if (!marvelGraph.containsNode(startHero) || !marvelGraph.containsNode(endHero)) {
            return null;
        }
        Queue<String> workList = new LinkedList<String>();
        Map<String, List<Edge<String, String>>> result = new TreeMap<String, List<Edge<String, String>>>();
        result.put(startHero, new ArrayList<Edge<String, String>>());
        workList.add(startHero);
        while (!workList.isEmpty()) {
            String hero = workList.remove();
            if (hero.equals(endHero)) {
                return new ArrayList<Edge<String, String>>(result.get(hero));
            }
            List<Edge<String, String>> books = this.marvelGraph.listChildren(hero);
            Collections.sort(books, new Comparator<Edge<String, String>>() {
                @Override
                public int compare(Edge<String, String> o1, Edge<String, String> o2) {
                    if (o1.getChild().equals(o2.getChild())) {
                        return o1.getLabel().compareTo(o2.getLabel());
                    }
                    return o1.getChild().compareTo(o2.getChild());
                }
            });
            for (Edge<String, String> book : books) {
                if (!result.containsKey(book.getChild())) {
                    List<Edge<String, String>> path = result.get(hero);
                    List<Edge<String, String>> newPath = new ArrayList<Edge<String, String>>(path);
                    newPath.add(book);
                    result.put(book.getChild(), newPath);
                    workList.add(book.getChild());
                }
            }
        }
        return null;
    }

    /**
     * list all the characters read from the file
     *
     * @return a set of characters read from the file
     * */
    public Set<String> listNodes() {
        return marvelGraph.listNodes();
    }

    /**
     * list all the characters that appear in the same comic book
     *
     * @param  hero the given character that need to check the related characters
     * @return a list of characters and comic books read from the file
     * */
    public List<Edge<String, String>> listChildren(String hero) {
        return marvelGraph.listChildren(hero);
    }

    /**
     * Add a hero to the list of characters
     *
     * @param hero the given character that need to add in the list
     * @return true if successfully add the given character to the list
     * */
    public boolean addNode(String hero) {
        return marvelGraph.addNode(hero);
    }

    /**
     * Add a comic book label between two characters
     *
     * @param hero the given character that appear in the given comic book
     * @param book the comic book that two characters appear in
     * @param otherHero the given character that appear in the given comic book
     * @return True if successfully add the given comic book label between two characters
     * */
    public boolean addEdge(String hero, String book, String otherHero) {
        return marvelGraph.addEdge(hero, book, otherHero);
    }

    /**
     * Check if the graph contains the given character
     *
     * @param hero the given character that need to check if inside the MarvelPath
     * @return true if the graph contains the character
     * */
    public boolean containHero(String hero) {
        return marvelGraph.containsNode(hero);
    }
}
