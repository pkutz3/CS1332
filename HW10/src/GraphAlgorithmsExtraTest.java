import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
 * Just an extra set of some extra tests, not too complicated but will test some cases. Good luck on this
 * final homework!
 * @author Menelik Gebremariam
 * @version 1.0
 */
public class GraphAlgorithmsExtraTest {
    private static final int TIMEOUT = 250;
    private Graph<Character> directedGraph;
    private Graph<Character> undirectedGraph;

    @Before
    public void setUp() {
        directedGraph = createDirectedGraph();
        undirectedGraph = createUndirectedGraph();
    }

    private Graph<Character> createDirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 71; i <= 77; i++) {
            vertices.add(new Vertex<>((char) i));
        }
        // G H I J K L M
        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('H'), 0));
        edges.add(new Edge<>(new Vertex<>('G'), new Vertex<>('J'), 0));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('I'), 0));
        edges.add(new Edge<>(new Vertex<>('J'), new Vertex<>('K'), 0));
        edges.add(new Edge<>(new Vertex<>('H'), new Vertex<>('L'), 0));
        edges.add(new Edge<>(new Vertex<>('L'), new Vertex<>('I'), 0));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('J'), 0));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('L'), 0));
        edges.add(new Edge<>(new Vertex<>('M'), new Vertex<>('H'), 0));
        edges.add(new Edge<>(new Vertex<>('K'), new Vertex<>('M'), 0));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('H'), 0));
        edges.add(new Edge<>(new Vertex<>('I'), new Vertex<>('G'), 0));

        return new Graph<>(vertices, edges);
    }

    private Graph<Character> createUndirectedGraph() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        vertices.add(new Vertex<>('a'));
        vertices.add(new Vertex<>('b'));
        vertices.add(new Vertex<>('c'));
        vertices.add(new Vertex<>('d'));
        vertices.add(new Vertex<>('e'));
        vertices.add(new Vertex<>('f'));
        vertices.add(new Vertex<>('g'));
        vertices.add(new Vertex<>('h'));
        vertices.add(new Vertex<>('i'));

        Set<Edge<Character>> edges = new LinkedHashSet<>();
        edges.add(new Edge<>(new Vertex<>('a'), new Vertex<>('b'), 4));
        edges.add(new Edge<>(new Vertex<>('a'), new Vertex<>('h'), 8));
        edges.add(new Edge<>(new Vertex<>('b'), new Vertex<>('c'), 8));
        edges.add(new Edge<>(new Vertex<>('b'), new Vertex<>('h'), 11));
        edges.add(new Edge<>(new Vertex<>('c'), new Vertex<>('i'), 2));
        edges.add(new Edge<>(new Vertex<>('c'), new Vertex<>('d'), 7));
        edges.add(new Edge<>(new Vertex<>('c'), new Vertex<>('f'), 4));
        edges.add(new Edge<>(new Vertex<>('d'), new Vertex<>('f'), 14));
        edges.add(new Edge<>(new Vertex<>('d'), new Vertex<>('e'), 9));
        edges.add(new Edge<>(new Vertex<>('f'), new Vertex<>('e'), 10));
        edges.add(new Edge<>(new Vertex<>('g'), new Vertex<>('f'), 2));
        edges.add(new Edge<>(new Vertex<>('h'), new Vertex<>('g'), 1));
        edges.add(new Edge<>(new Vertex<>('g'), new Vertex<>('i'), 6));
        edges.add(new Edge<>(new Vertex<>('h'), new Vertex<>('i'), 7));

        return new Graph<>(vertices, edges);
    }

    @Test(timeout = TIMEOUT)
    public void testDFS1() {
        List<Vertex<Character>> dfsActual =
                GraphAlgorithms.dfs(new Vertex<>('G'), directedGraph);

        List<Vertex<Character>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<>('G'));
        dfsExpected.add(new Vertex<>('H'));
        dfsExpected.add(new Vertex<>('L'));
        dfsExpected.add(new Vertex<>('I'));
        dfsExpected.add(new Vertex<>('J'));
        dfsExpected.add(new Vertex<>('K'));
        dfsExpected.add(new Vertex<>('M'));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras1() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgorithms.dijkstras(new Vertex<>('a'), undirectedGraph);

        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('a'), 0);
        dijkExpected.put(new Vertex<>('b'), 4);
        dijkExpected.put(new Vertex<>('c'), 12);
        dijkExpected.put(new Vertex<>('d'), 19);
        dijkExpected.put(new Vertex<>('e'), 21);
        dijkExpected.put(new Vertex<>('f'), 11);
        dijkExpected.put(new Vertex<>('g'), 9);
        dijkExpected.put(new Vertex<>('h'), 8);
        dijkExpected.put(new Vertex<>('i'), 14);

        assertEquals(dijkExpected, dijkActual);
    }
}
