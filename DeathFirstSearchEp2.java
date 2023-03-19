import java.util.*;
import java.io.*;
import java.math.*;
class Graph {
    private int numVertices;
    private List<List<Integer>> adjList;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjList = new ArrayList<>(numVertices);

        for (int i = 0; i < numVertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
        adjList.get(dest).add(src);
    }
    public List<Integer> getAdjacentVertices(int v) {
        return adjList.get(v);
    }

    public int getNumVertices() {
        return numVertices;
    }
}
/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        Graph graph = new Graph(N);
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            graph.addEdge(N1, N2);

        }
        Queue<Integer> exitNodes = new LinkedList<>();

        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            exitNodes.add(EI);
        }

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Bobnet agent is positioned this turn
            int minLength = Integer.MAX_VALUE;
            Integer startNode = exitNodes.peek();
            List<Integer> currentPath = new ArrayList<>();
            for(Integer i : exitNodes) {
                int currLength = bfsShortestPath(graph, i, SI).size();
                if (currLength < minLength) {
                    minLength = currLength;
                    startNode = i;
                }
                currentPath = bfsShortestPath(graph, startNode, SI);
            }
            System.out.println(currentPath.get(0) + " " + currentPath.get(1));
        }
    }
    public static List<Integer> bfsShortestPath(Graph graph, int startNode, int endNode) {
        List<Integer> shortestPath = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[graph.getNumVertices()];
        int[] parent = new int[graph.getNumVertices()];

        queue.offer(startNode);
        visited[startNode] = true;
        parent[startNode] = -1;

        while(!queue.isEmpty()) {
            int currentNode = queue.poll();
            List<Integer> adjacentNodes = graph.getAdjacentVertices(currentNode);
            for(int adjacentNode : adjacentNodes) {
                if(!visited[adjacentNode]) {
                    visited[adjacentNode] = true;
                    parent[adjacentNode] = currentNode;
                    queue.add(adjacentNode);
                }
                if(adjacentNode == endNode) {
                    int node = adjacentNode;
                    while(node != -1) {
                        shortestPath.add(0, node);
                        node = parent[node];
                    }
                    return shortestPath;
                }
            }
        }
        return shortestPath;
    }
}
