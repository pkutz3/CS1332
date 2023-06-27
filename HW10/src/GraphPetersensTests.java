import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.regex.*;

/**
 * A limited series of tests using a spinoff of a Petersen graph that also features
 * self-loops and parallel edges to test algorithmic performance with these
 * constructs. Also tests algorithms on a single vertex to ensure this fringe
 * case is covered.
 *
 * Also contains some regex-enabled functions to speed up the process of
 * graph creation, should you choose to modify the graphs here.
 *
 * A good tester for regex and whether your expressions pass checks: https://regexr.com
 *
 * This will NOT agree with Checkstyle.
 *
 * @author Peter Lais
 * @version 1.0
 */
public class GraphPetersensTests {
    private Graph<String> graph;
    private Graph<String> graphSingle;

    private final int TIMEOUT = 200;

    // String defining the modified Petersen graph.
    // It's too complicated to provide a visualization here, but
    // you may use this adjacency-list-like string to parse it out.
    private static final String PETERSENS_STRING =
            "O1:O1[00],I1[00],I1[10],O2[01],O5[02]\n"
                    + "O2:O2[00],I2[03],I2[13],O3[04],O1[05]\n"
                    + "O3:O3[00],I3[06],I3[16],O2[07],O4[08]\n"
                    + "O4:O4[00],I4[09],I4[19],O3[10],O5[11]\n"
                    + "O5:O5[00],I5[12],I5[12],O4[13],O1[14]\n"
                    + "I1:O1[15],I3[16],I4[17]\n"
                    + "I2:O2[18],I4[19],I5[20]\n"
                    + "I3:O3[21],I5[22],I1[23]\n"
                    + "I4:O4[24],I1[25],I2[26]\n"
                    + "I5:O5[28],I2[28],I3[29]";

    // Helper function for other utility functions.
    // Essentially add to the map if it doesn't exist or return an existing entry.
    // Disallows duplicate data.
    private static <T> Vertex<T> preferExistingVertex(Map<T, Vertex<T>> map, T key) {
        map.putIfAbsent(key, new Vertex<T>(key));
        return map.get(key);
    }

    // Utility function for making a list of vertices with given string data.
    private static List<Vertex<String>> regexVertexListBuilder(String specs) {
        Pattern p = Pattern.compile("(\\S[^,]*)(?:,|$)");
        List<Vertex<String>> output = new ArrayList<>();
        Matcher m = p.matcher(specs);
        while (m.find()) {
            output.add(new Vertex<>(m.group(1)));
        }
        return output;
    }

    // Utility function for making a map of vertices with given string data and
    // distances.
    private static Map<Vertex<String>, Integer> regexVertexMapBuilder(String specs) {
        Pattern p = Pattern.compile("(\\w+)\\[((?:\\d|-|\\+|\\.)+)\\]");
        Map<Vertex<String>, Integer> output = new HashMap<>();
        Matcher m = p.matcher(specs);
        while (m.find()) {
            output.put(new Vertex<>(m.group(1)), Integer.parseInt(m.group(2)));
        }
        return output;
    }

    // Utility function for making a set of vertices with given string data and edges with
    // specified weights that connect the vertices.
    // Note that a LinkedHashSet is used to predictably iterate through the HashSet in order of insertion
    // when defining the graph.
    private static SimpleEntry<Set<Vertex<String>>, Set<Edge<String>>> regexVertexEdgeBuilder(String specs) {
        // Regex pattern for matching on a single line.
        Pattern p = Pattern.compile("(\\w+)\\[((?:\\d|-|\\+|\\.)+)\\]");
        Map<String, Vertex<String>> addedMap = new HashMap<>();
        Set<Edge<String>> edgeSet = new LinkedHashSet<>();
        for (String line : specs.split("\n")) {
            if (line.contains(":")) {
                // Each line is <VERTEX>: <EDGE SPECIFIERS>
                String[] info = line.split(":");
                Vertex<String> currentVertex = preferExistingVertex(addedMap, info[0]);
                Matcher m = p.matcher(info[1]);
                while (m.find()) {
                    edgeSet.add(
                            new Edge<>(
                                    currentVertex,
                                    preferExistingVertex(addedMap, m.group(1)),
                                    Integer.parseInt(m.group(2))
                            )
                    );
                }
            }
        }
        Set<Vertex<String>> vertexSet = new HashSet<>(addedMap.values());
        return new SimpleEntry<>(vertexSet, edgeSet);
    }

    // Utility function for building a graph with given string data and edges with
    // specified weights that connect the vertices.
    private static Graph<String> regexGraphBuilder(String specs) {
        SimpleEntry<Set<Vertex<String>>, Set<Edge<String>>> results = regexVertexEdgeBuilder(specs);
        return new Graph<>(results.getKey(), results.getValue());
    }

    // Utility function for finding the symmetric difference between two sets.
    // Not used by any other function, but good for debugging.
    @SuppressWarnings("unused")
    private static <T> Set<T> symmetricDifference(Set<T> A, Set<T> B) {
        Set<T> forward = new HashSet<>(A);
        forward.removeAll(new HashSet<>(B));
        Set<T> backward = new HashSet<>(B);
        backward.removeAll(new HashSet<>(A));
        forward.addAll(backward);
        return forward;
    }

    @Before
    public void setUpGraphs() {
        // Build the Petersen graph.
        graph = regexGraphBuilder(PETERSENS_STRING);

        // Build a single vertex with a single edge loop.
        graphSingle = regexGraphBuilder("SINGLE:SINGLE[-1]");
    }

    @Test(timeout = TIMEOUT)
    public void testDFS() {
        // Performance with a single vertex.
        assertEquals(
                regexVertexListBuilder("SINGLE"),
                GraphAlgorithms.dfs(new Vertex<>("SINGLE"), graphSingle)
        );

        // Performance with Petersen's.
        graph.getVertices().add(new Vertex<>("DISCONNECTED"));
        assertEquals(
                regexVertexListBuilder("O1,I1,I3,O3,O2,I2,I4,O4,O5,I5"),
                GraphAlgorithms.dfs(new Vertex<>("O1"), graph)
        );
    }

    @Test(timeout = TIMEOUT)
    public void testDijkstras() {
        // Performance with a single vertex.
        // Even though this one has a negative edge of weight -1, this should still
        // perform correctly since the edge is a self-loop and should be ignored.
        assertEquals(
                regexVertexMapBuilder("SINGLE[0]"),
                GraphAlgorithms.dijkstras(new Vertex<>("SINGLE"), graphSingle)
        );

        // Performance with Petersen's and an additional disconnected
        // vertex.
        graph.getVertices().add(new Vertex<>("DISCONNECTED"));
        assertEquals(
                regexVertexMapBuilder(
                        "O1[0],I1[0],O2[1],O5[2],I2[4],O3[5],I3[11],"
                                + "O4[13],I5[14],I4[17],"
                                + "DISCONNECTED[2147483647]"
                ),
                GraphAlgorithms.dijkstras(new Vertex<>("O1"), graph)
        );
    }
}