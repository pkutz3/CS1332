import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JUnit test for HW8-GraphAlgorithms.
 *
 * @author Jun Yeop Kim
 * @version 1.0
 */
public class JunGraphAlgorithmsTest {
    private static final int TIMEOUT = 200;
    private Graph<Integer> directedGraph;
    private Graph<Character> undirectedGraph;
    private Graph<Integer> directedGraph2;
    private Graph<Character> undirectedGraph2;

    @Before
    public void setUp() {
        directedGraph = createDirectedGraph();
        undirectedGraph = createUndirectedGraph();

        directedGraph2 = createDirectedGraph2();
        undirectedGraph2 = createUndirectedGraph2();
    }

    /**
     * Creates a directed graph.
     *
     * @return the completed graph
     */
    private Graph<Integer> createDirectedGraph() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 8; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(1), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(8), 0));

        return new Graph<>(vertices, edges);
    }

    /**
     * Creates a directed graph.
     *
     * @return the completed graph
     */
    private Graph<Integer> createDirectedGraph2() {
        Set<Vertex<Integer>> vertices = new HashSet<>();
        for (int i = 1; i <= 8; i++) {
            vertices.add(new Vertex<>(i));
        }

        Set<Edge<Integer>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(1), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(2), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(1), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(3), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(4), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(5), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(2), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(4), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(7), 0));
        edges.add(new Edge<>(new Vertex<>(6), new Vertex<>(8), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(3), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(5), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(6), 0));
        edges.add(new Edge<>(new Vertex<>(7), new Vertex<>(8), 0));
        return new Graph<>(vertices, edges);
    }

    /**
     * Creates an undirected graph.
     *
     * @return the completed graph
     */
    private Graph<Character> createUndirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 72; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 4));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 4));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('C'), 2));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('A'), 2));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 6));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 6));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 3));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 3));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('F'), 7));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('C'), 7));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('G'), 7));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('C'), 7));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 1));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 1));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('F'), 3));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('D'), 3));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 2));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('H'), 2));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('G'), 7));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('E'), 7));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('H'), 4));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('E'), 4));

        return new Graph<>(vertices, edges);
    }

    /**
     * Creates an undirected graph.
     *
     * @return the completed graph
     */
    private Graph<Character> createUndirectedGraph2() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 72; i++) {
            vertices.add(new Vertex<>((char) i));
        }

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('D'), 1));
        edges.add(new Edge<>(new Vertex<>('D'), new Vertex<>('A'), 1));
        edges.add(new Edge<>(new Vertex<>('A'), new Vertex<>('B'), 6));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('A'), 6));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('C'), 6));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('B'), 6));
        edges.add(new Edge<>(new Vertex<>('B'), new Vertex<>('F'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('B'), 1));
        edges.add(new Edge<>(new Vertex<>('C'), new Vertex<>('F'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('C'), 1));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('G'), 5));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('F'), 5));
        edges.add(new Edge<>(new Vertex<>('F'), new Vertex<>('H'), 2));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('F'), 2));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 4));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('G'), 4));
        edges.add(new Edge<>(new Vertex<>('E'), new Vertex<>('H'), 5));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('E'), 5));

        return new Graph<>(vertices, edges);
    }


    @Test(timeout = TIMEOUT)
    public void dfs() {
        List<Vertex<Integer>> actual =
                GraphAlgorithms.dfs(new Vertex<>(4), directedGraph);

        List<Vertex<Integer>> expected = new LinkedList<>();
        expected.add(new Vertex<>(4));
        expected.add(new Vertex<>(6));
        expected.add(new Vertex<>(7));
        expected.add(new Vertex<>(3));
        expected.add(new Vertex<>(1));
        expected.add(new Vertex<>(2));
        expected.add(new Vertex<>(8));

        assertEquals(expected, actual);

        actual = GraphAlgorithms.dfs(new Vertex<>(2), directedGraph2);

        expected.clear();
        expected.add(new Vertex<>(2));
        expected.add(new Vertex<>(3));
        expected.add(new Vertex<>(1));
        expected.add(new Vertex<>(4));
        expected.add(new Vertex<>(8));
        expected.add(new Vertex<>(7));
        expected.add(new Vertex<>(5));
        expected.add(new Vertex<>(6));

        assertEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void dijkstras() {
        Map<Vertex<Character>, Integer> actual
                = GraphAlgorithms.dijkstras(new Vertex<>('D'), undirectedGraph);

        Map<Vertex<Character>, Integer> expected = new HashMap<>();
        expected.put(new Vertex<>('A'), 10);
        expected.put(new Vertex<>('B'), 6);
        expected.put(new Vertex<>('C'), 10);
        expected.put(new Vertex<>('D'), 0);
        expected.put(new Vertex<>('E'), 9);
        expected.put(new Vertex<>('F'), 3);
        expected.put(new Vertex<>('G'), 4);
        expected.put(new Vertex<>('H'), 5);

        assertEquals(expected, actual);

        actual = GraphAlgorithms.dijkstras(new Vertex<>('B'), undirectedGraph2);

        expected.clear();
        expected.put(new Vertex<>('A'), 6);
        expected.put(new Vertex<>('B'), 0);
        expected.put(new Vertex<>('C'), 2);
        expected.put(new Vertex<>('D'), 7);
        expected.put(new Vertex<>('E'), 8);
        expected.put(new Vertex<>('F'), 1);
        expected.put(new Vertex<>('G'), 6);
        expected.put(new Vertex<>('H'), 3);

        assertEquals(expected, actual);
    }

}