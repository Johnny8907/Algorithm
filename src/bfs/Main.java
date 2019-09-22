package bfs;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Node> nodes = new LinkedList<>();
        Node you = new Node("You", false);
        Node bob = new Node("Bob", false);
        Node claire = new Node("Claire", true);
        Node alice = new Node("Alice", false);
        Node johnny = new Node("Johnny", false);
        Node thom = new Node("Thom", false);
        Node anuj = new Node("Anuj", false);
        Node peggy = new Node("Peggy", false);
        nodes.add(you);
        nodes.add(bob);
        nodes.add(claire);
        nodes.add(alice);
        nodes.add(johnny);
        nodes.add(thom);
        nodes.add(anuj);
        nodes.add(peggy);

        List<Edge> edges = new LinkedList<>();
        Edge youBob = new Edge(you, bob);
        Edge youClaire = new Edge(you, claire);
        Edge youAlice = new Edge(you, alice);
        Edge bobAnuj = new Edge(bob, anuj);
        Edge bobPeggy = new Edge(bob, peggy);
        Edge claireThom = new Edge(claire, thom);
        Edge claireJohnny = new Edge(claire, johnny);
        Edge alicePeggy = new Edge(alice, peggy);
        edges.add(youBob);
        edges.add(youClaire);
        edges.add(youAlice);
        edges.add(bobAnuj);
        edges.add(bobPeggy);
        edges.add(claireThom);
        edges.add(claireJohnny);
        edges.add(alicePeggy);

        Graph graph = new Graph(nodes, edges);
        BFS(graph, you, node -> node.isMangoSeller);
//        BFS(graph, you, null);
    }

    static class Graph {
        Map<Node, List<Node>> adjMap;
        Graph(List<Node> nodes, List<Edge> edges) {
            adjMap = new HashMap<>();

            for (Node node : nodes) {
                adjMap.put(node, new LinkedList<>());
            }

            for (Edge edge : edges) {
                if (adjMap.containsKey(edge.from)) {
                    adjMap.get(edge.from).add(edge.to);
                } else {
                    System.out.println(edge.toString() + " node is not included in nodes list");
                }
            }
        }

        void printGraph() {
            Set<Node> keySet = adjMap.keySet();
            for (Node node : keySet) {
                StringBuilder sb = new StringBuilder();
                sb.append("Adjacency list of vertex ").append(node.name);
                List<Node> adjList = adjMap.get(node);
                for (Node adjNode : adjList) {
                    sb.append(" -> ").append(adjNode.name);
                }
                System.out.println(sb.append("\n").toString());
            }
        }
    }

    static class Node {
        String name;
        boolean isMangoSeller;
        Node(String name, boolean isMangoSeller) {
            this.name = name;
            this.isMangoSeller = isMangoSeller;
        }
    }

    static class Edge {
        Node from;
        Node to;

        Edge(Node from, Node to) {
            this.from = from;
            this.to = to;
        }
        @Override
        public String toString() {
            return "Edge from " + from.name + " to " + to.name;
        }
    }

    private static void BFS(Graph graph, Node startNode, BreakCondition condition) {
        Set<Node> visited = new HashSet<>();
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(startNode);
        while (!queue.isEmpty()) {
            Node s = queue.poll();
            System.out.println("Node " + s.name);
            if (condition != null && condition.isSeller(s)) {
                System.out.println("Find seller! " + s.name);
                break;
            }
            List<Node> adjNodes = graph.adjMap.get(s);
            for (Node adjNode: adjNodes) {
                if (!visited.contains(adjNode)) {
                    queue.add(adjNode);
                    visited.add(adjNode);
                }
            }
        }
    }

    interface BreakCondition {
        boolean isSeller(Node node);
    }
}
