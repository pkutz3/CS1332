import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TawfiqTest {

    private static final int TIMEOUT = 200;
    private Graph<Integer> singleVertex;
    private Graph<Integer> disconnectedGraph;
    private Graph<Character> undirectedGraph;

    @Before
    public void init() {
        singleVertex = createSingleVertex();
        disconnectedGraph = createDisconnected();
        undirectedGraph = createUndirected();
    }

    /**
     * creates disconnected graph
     * @return disconnected graph
     */
    private  Graph<Integer> createDisconnected() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(0));
        vertices.add(new Vertex<>(1));
        vertices.add(new Vertex<>(2));
        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(0), new Vertex<>(0), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 2));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(1), 2));
        return new Graph<>(vertices, edges);
    }

    /**
     * creates graph with single vertex
     * @return graph with single vertex
     */
    private Graph<Integer> createSingleVertex() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        vertices.add(new Vertex<>(0));
        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(0), new Vertex<>(0), 0));
        return new Graph<>(vertices, edges);
    }

    /**
     * Creates an undirected graph.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private Graph<Character> createUndirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 7));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 7));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 5));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('D'), 2));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('E'), 3));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 8));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 8));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('F'), 6));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('E'), 6));

        return new Graph<>(vertices, edges);
    }


    @Test(timeout = TIMEOUT)
    public void testBFSSingleVertex() {
        List<Vertex<Integer>> bfsActual = GraphAlgorithms.bfs(
                new Vertex<>(0), singleVertex);

        List<Vertex<Integer>> bfsExpected = new LinkedList<>();
        bfsExpected.add(new Vertex<>(0));
        assertEquals(bfsExpected, bfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDFSSingleVertex() {
        List<Vertex<Integer>> dfsActual = GraphAlgorithms.dfs(
                new Vertex<>(0), singleVertex);

        List<Vertex<Integer>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<>(0));
        assertEquals(dfsExpected, dfsActual);
    }


    @Test(timeout = TIMEOUT)
    public void testKruskalsDisconnected() {
        Set<Edge<Integer>> mstActual = GraphAlgorithms.kruskals(
                disconnectedGraph);
        assertNull(mstActual);
    }

    /**
     * creates an undirectedGraph
     * @return undirectedGraph
     */
    private Graph<Character> createUndirected() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 72; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 5));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 5)); //AB
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('F'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('C'), 1)); //CF
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('E'), 1));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('C'), 1)); //EC
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('G'), 9));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('B'), 9)); //BG
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('G'), 7));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('E'), 7)); //EG
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('H'), 5));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 5)); //FH

        return new Graph<>(vertices, edges);
    }
    @Test(timeout = TIMEOUT)
    public void testDijkstras() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(
                new Vertex<>('A'), undirectedGraph);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A'), 0);
        dijkExpected.put(new Vertex<>('B'), 5);
        dijkExpected.put(new Vertex<>('C'), 22);
        dijkExpected.put(new Vertex<>('D'), Integer.MAX_VALUE);
        dijkExpected.put(new Vertex<>('E'), 21);
        dijkExpected.put(new Vertex<>('F'), 23);
        dijkExpected.put(new Vertex<>('G'), 14);
        dijkExpected.put(new Vertex<>('H'), 28);

        assertEquals(dijkExpected, dijkActual);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstrasEmpty() {
        GraphAlgorithms.dijkstras(null, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testKruskalsEmpty() {
        GraphAlgorithms.kruskals(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSEmpty() {
        GraphAlgorithms.bfs(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDFSEmpty() {
        GraphAlgorithms.dfs(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDFSStartDontEvenExist() {
        GraphAlgorithms.dfs(new Vertex<>('Z'), undirectedGraph);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBFSStartDontEvenExist() {
        GraphAlgorithms.bfs(new Vertex<>('Z'), undirectedGraph);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDijkstrasStartDontEvenExist() {
        GraphAlgorithms.dijkstras(new Vertex<>('Z'), undirectedGraph);
    }
}