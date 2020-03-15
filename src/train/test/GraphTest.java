package train.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import train.model.Edge;
import train.model.Graph;
import train.model.Node;
import train.model.Route;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class GraphTest {
    private static Graph graph;
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPathWeight() {
        String[] testCase1 = new String[3];
        testCase1[0] = "A";
        testCase1[1] = "B";
        testCase1[2] = "C";
        assertEquals(graph.getPathWeight(testCase1), 9);

        String[] testCase2 = new String[2];
        testCase2[0] = "A";
        testCase2[1] = "D";
        assertEquals(graph.getPathWeight(testCase2), 5);

        String[] testCase3 = new String[3];
        testCase3[0] = "A";
        testCase3[1] = "D";
        testCase3[2] = "C";
        assertEquals(graph.getPathWeight(testCase3), 13);

        String[] testCase4 = new String[5];
        testCase4[0] = "A";
        testCase4[1] = "E";
        testCase4[2] = "B";
        testCase4[3] = "C";
        testCase4[4] = "D";
        assertEquals(graph.getPathWeight(testCase4), 22);

        String[] testCase5 = new String[3];
        testCase5[0] = "A";
        testCase5[1] = "E";
        testCase5[2] = "D";
        assertEquals(graph.getPathWeight(testCase5), Graph.NO_SUCH_ROUTE);

    }

    @Test
    public void findPathBetween() {
        List<Route> routes = graph.findPathBetween("C", "C", 2);
        Route route1 = new Route(graph);
        route1.addNode("C");
        route1.addNode("D");
        route1.addNode("C");
        assertTrue(routes.contains(route1));

        Route route2 = new Route(graph);
        route2.addNode("C");
        route2.addNode("E");
        route2.addNode("B");
        route2.addNode("C");
        assertTrue(routes.contains(route2));
    }

    @Test
    public void findShortDistanceBetween() {
        assertEquals(graph.findShortestDistanceBetween("A", "C"), 9);
        assertEquals(graph.findShortestDistanceBetween("C", "D"), 8);
        assertEquals(graph.findShortestDistanceBetween("C", "A"), -1);
    }

    @BeforeClass
    public static void initGraph() {
        //init nodes
        List<Node> nodes = new LinkedList<>();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");

        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(nodeC);
        nodes.add(nodeD);
        nodes.add(nodeE);

        //init edges
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(nodeA, nodeB, 5));
        edges.add(new Edge(nodeB, nodeC, 4));
        edges.add(new Edge(nodeC, nodeD, 8));
        edges.add(new Edge(nodeD, nodeC, 8));
        edges.add(new Edge(nodeD, nodeE, 6));
        edges.add(new Edge(nodeA, nodeD, 5));
        edges.add(new Edge(nodeC, nodeE, 2));
        edges.add(new Edge(nodeE, nodeB, 3));
        edges.add(new Edge(nodeA, nodeE, 7));

        graph = new Graph(nodes, edges);
    }
}