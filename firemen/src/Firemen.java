import java.util.*;

public class Firemen {
    private final int fireDepartmentCorner = 0;
    private final HashMap<Integer, LinkedList<Integer>> adj = new HashMap<>();

    /**
     * add edge to adjacency matrix
     *
     * @param src  src
     * @param dest dest
     */
    public void addEdge(int src, int dest) {
        LinkedList<Integer> edges = adj.computeIfAbsent(src, k -> new LinkedList<>());
        edges.add(dest);
    }

    /**
     * utility function for finding all routes between two node
     * using backtrack algorithm
     *
     * @param destinationCorner dest node
     * @param currentCorner     current node
     * @param path              visited corners
     * @param answers           answers list
     */
    private void findRoutesUtil(
            int destinationCorner,
            int currentCorner,
            LinkedList<Integer> path,
            LinkedList<int[]> answers) {
        if (destinationCorner == currentCorner) {
            answers.add(listToArray(path));
        } else {
            LinkedList<Integer> destCorners = adj.get(currentCorner);

            for (int corner : destCorners) {
                if (isPromising(path, corner)) {
                    path.add(corner);
                    findRoutesUtil(destinationCorner, corner, path, answers);
                    path.removeLast();
                }
            }
        }
    }

    /**
     * finds all routes between two node
     * first node is 0
     *
     * @param streetCorner destination node
     * @return all routes between two node
     */
    public int[][] findRoutes(int streetCorner) {
        LinkedList<int[]> answers = new LinkedList<>();
        LinkedList<Integer> path = new LinkedList<>();
        path.add(0);
        findRoutesUtil(streetCorner, fireDepartmentCorner, path, answers);

        int[][] routes = new int[answers.size()][];
        answers.toArray(routes);

        return routes;
    }

    /**
     * converts a list to array
     *
     * @param list list
     * @return array consists of list elements
     */
    private int[] listToArray(LinkedList<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * promising function for backtrack algorithm
     * if node hasn't been visited before it is promising
     *
     * @param vertices vertices list
     * @param dest     dest node
     * @return whether the node is promising or note
     */
    private boolean isPromising(LinkedList<Integer> vertices, int dest) {
        for (int vertex : vertices) {
            if (vertex == dest) return false;
        }
        return true;
    }

    private LinkedList<Integer> getPromisingChildren(int lastNode, LinkedList<Integer> path) {
        LinkedList<Integer> edges = adj.get(lastNode), children = new LinkedList<>();
        boolean isPromising;
        int count = 0;
        for (int node : edges) {
            isPromising = true;
            for (int vertex : path) {
                if (node == vertex) {
                    isPromising = false;
                    break;
                }

            }
            if (isPromising) {
                children.add(node);
            }
        }
        return children;
    }

    private int countChildren(int node) {
        return adj.get(node).size();
    }

    public int countCalls() {
        int countOfNodes = 1, countOfChildren = 0, node;
        LinkedList<Integer> path = new LinkedList<>(), promisingChildren;
        path.add(0);
        while (true) {
            promisingChildren = getPromisingChildren(path.getLast(), path);
            if (promisingChildren.isEmpty()) break;
            node = promisingChildren.get((int) (Math.random() * promisingChildren.size()));
            path.add(node);
            countOfChildren = countChildren(node);
            countOfNodes += countOfChildren * promisingChildren.size();
        }

        return countOfNodes;
    }

    public double estimateTimeComplexity() {
        int size = 200;
        double time = 0;
        for (int i = 0; i < size; i++) {
            time += countCalls();
        }

        return time / size;
    }

    public static void main(String[] args) {
        Firemen firemen = new Firemen();
        Scanner in = new Scanner(System.in);
        int destCorner = in.nextInt(), src, dest;
        while (true) {
            src = in.nextInt() - 1;
            dest = in.nextInt() - 1;
            if (src == -1 && dest == -1) break;
            firemen.addEdge(src, dest);
            firemen.addEdge(dest, src);
        }

        int[][] routes = firemen.findRoutes(destCorner - 1);
        for (int[] route : routes) {
            for (int vertex : route) {
                System.out.print((vertex + 1) + " ");
            }
            System.out.println();
        }

        System.out.println("Time Complexity : " + firemen.estimateTimeComplexity());
    }
}