import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Peter Kutz
 * @version 1.0
 * @userid pkutz3
 * @GTID 903637824
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start is null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph is null.");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("The start is not in the graph.");
        }

        List<Vertex<T>> bfsVertices = new ArrayList<>();
        Queue<Vertex<T>> q = new LinkedList<>();
        Set<Vertex<T>> vs = new HashSet<>();

        vs.add(start);
        q.add(start);

        while (!q.isEmpty()) {
            Vertex<T> v = q.poll();
            bfsVertices.add(v);
            for (VertexDistance<T> w : adjList.get(v)) {
                if (!vs.contains(w.getVertex())) {
                    vs.add(w.getVertex());
                    q.add(w.getVertex());
                }
            }
        }
        return bfsVertices;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start is null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph is null.");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("The start is not in the graph.");
        }

        List<Vertex<T>> dfsVertices = new ArrayList<>();
        Set<Vertex<T>> vs = new HashSet<>();
        dfsHelper(start, dfsVertices, vs, adjList);

        return dfsVertices;
    }

    /**
     * Private helper method that executes DFS recursively.
     *
     * @param u the vertex currently visited
     * @param dfsVertices the list of vertices in this graph
     * @param vs the set of visited vertices
     * @param adjList the list of adjacent vertices in graph
     * @param <T> the generic typing of this class
     */
    private static <T> void dfsHelper(Vertex<T> u, List<Vertex<T>> dfsVertices, Set<Vertex<T>> vs,
                                      Map<Vertex<T>, List<VertexDistance<T>>> adjList) {
        vs.add(u);
        dfsVertices.add(u);

        for (VertexDistance<T> w : adjList.get(u)) {
            if (!vs.contains(w.getVertex())) {
                dfsHelper(w.getVertex(), dfsVertices, vs, adjList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("The start is null.");
        }
        if (graph == null) {
            throw new IllegalArgumentException("The graph is null.");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("The start is not in the graph.");
        }

        Set<Vertex<T>> vs = new HashSet<>();
        Queue<VertexDistance<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>, Integer> dm = new HashMap<>();

        for (Vertex<T> v : graph.getVertices()) {
            dm.put(v, Integer.MAX_VALUE);
        }
        VertexDistance<T> vdTemp = new VertexDistance<>(start, 0);
        pq.add(vdTemp);

        while (!pq.isEmpty() && vs.size() < adjList.size()) {
            vdTemp = pq.poll();

            if (!vs.contains(vdTemp.getVertex())) {
                vs.add(vdTemp.getVertex());
                dm.put(vdTemp.getVertex(), vdTemp.getDistance());

                for (VertexDistance<T> w : adjList.get(vdTemp.getVertex())) {

                    if (!vs.contains(w.getVertex())) {
                        VertexDistance<T> vdTemp2 = new VertexDistance<>(w.getVertex(),
                                w.getDistance() + vdTemp.getDistance());
                        pq.add(vdTemp2);
                    }
                }
            }
        }
        return dm;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The graph is null.");
        }

        DisjointSet<T> ds = new DisjointSet<>();
        Set<Edge<T>> mst = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        Set<Edge<T>> edges = graph.getEdges();

        pq.addAll(edges);

        while (!pq.isEmpty() && mst.size() < edges.size()) {
            Edge<T> edge = pq.poll();
            Vertex<T> u = edge.getU();
            Vertex<T> v = edge.getV();

            if (ds.find(u) != ds.find(v)) {
                mst.add(edge);
                mst.add(new Edge<>(edge.getV(), edge.getU(), edge.getWeight()));
                ds.union(ds.find(u), ds.find(v));
            }
        }
        if (mst.size() < (2 * (graph.getVertices().size() - 1))) {
            return null;
        }

        return mst;
    }
}