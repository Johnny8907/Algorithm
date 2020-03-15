package train.model;

import com.sun.istack.internal.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private Map<Node, Map<Node, Integer>> adjMap;
    private List<Edge> edgeList = new ArrayList<>();
    public static final int NO_SUCH_ROUTE = -1;
    private static final int NODE_NOT_EXIST = -2;

    public Graph(List<Node> nodes, List<Edge> edges) {
        adjMap = new HashMap<>();
        for (Node node : nodes) {
            adjMap.put(node, new LinkedHashMap<>());
        }

        for (Edge edge : edges) {
            this.edgeList.add(new Edge(edge.from, edge.to, edge.weight));
            if (adjMap.containsKey(edge.from)) {
                adjMap.get(edge.from).put(edge.to, edge.weight);
            } else {
                System.out.println(edge.toString() + " node is not included in nodes list");
            }
        }
    }

    public int getPathWeight(String... nodeNames) {
        int ret = 0;
        for (int i = 0; i < nodeNames.length; i++) {
            if (nodeNames[i] == null || !contains(nodeNames[0])) {
                return NODE_NOT_EXIST;
            }

            if (i + 1 < nodeNames.length) {
                Map<Node, Integer> adjacencyNodes = findAdjacentNodes(nodeNames[i]);
                Node nextNode = findNodeByName(nodeNames[i + 1]);
                if (adjacencyNodes != null && !adjacencyNodes.containsKey(nextNode)) {
                    return NO_SUCH_ROUTE;
                } else if (adjacencyNodes != null) {
                    ret += adjacencyNodes.get(nextNode);
                }
            }

        }
        return ret;
    }

    @Nullable
    public List<Route> findPathBetween(String startName, String endName, int nodeCountLimit) {
        Node startNode = findNodeByName(startName);
        Node endNode = findNodeByName(endName);
        if (startNode == null || endNode == null) {
            return null;
        }

        List<Route> resultList = new ArrayList<>();

        //若起点与终点相同，则转化为寻找有向有环图中的环
        if (startName.equals(endName)) {
            resultList = findCycle(startNode);
            return resultList;
        }

        //若起点终点不相同，则转化为寻找有向有环图中两点之间的路径问题.
//        Map<Node, Integer> adjNodes = adjMap.get(startNode);
//        if (adjNodes != null) {
//            //Search first depth adjacent node.
//            Set<Node> nodeSet = adjNodes.keySet();
//            for (Node node : nodeSet) {
//                if (node.equals(endNode)) {
//                    List<Node> nodeList = new ArrayList<>();
//                    nodeList.add(startNode);
//                    nodeList.add(node);
//                    resultList.add(nodeList);
//                }
//            }
//
//            //Search second depth adjacent node.
//            for (Node node : nodeSet) {
//
//            }
//        }

        return resultList;
    }

    public int findShortestDistanceBetween(String start, String target) {
        Node startNode = findNodeByName(start);
        Node targetNode = findNodeByName(target);
        //Initial distance table and parent table.
        Map<Node, Integer> costTable = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        Map<Node, Integer> startNodeNeighbors = adjMap.get(startNode);
        for (Node node : adjMap.keySet()) {
            if (startNodeNeighbors.containsKey(node)) {
                costTable.put(node, startNodeNeighbors.get(node));
            } else if (!node.equals(startNode)) {
                costTable.put(node, Integer.MAX_VALUE);
            }
        }

        Node node = findLowestCostNode(costTable, visited);
        while (node != null) {
            int cost = costTable.get(node);
            Map<Node, Integer> neighbors = adjMap.get(node);
            for (Node n : neighbors.keySet()) {
                int newCost = cost + neighbors.get(n);
                if (costTable.get(n) != null && costTable.get(n) > newCost) {
                    costTable.put(n, newCost);
                }
            }
            visited.add(node);
            node = findLowestCostNode(costTable, visited);
        }

        return costTable.get(targetNode) == Integer.MAX_VALUE ? NO_SUCH_ROUTE : costTable.get(targetNode);
    }

    private Node findLowestCostNode(Map<Node, Integer> costTable, Set<Node> visited) {
        int lowestCost = Integer.MAX_VALUE;
        Node lowestCostNode = null;
        for (Node node : costTable.keySet()) {
            int cost = costTable.get(node);
            if (cost < lowestCost && !visited.contains(node)) {
                lowestCostNode = node;
                lowestCost = cost;
            }
        }
        return lowestCostNode;
    }

    public void printGraph() {
        Set<Node> keySet = adjMap.keySet();
        for (Node node : keySet) {
            StringBuilder sb = new StringBuilder();
            sb.append("Adjacency list of vertex ").append(node.name);
            Map<Node, Integer> weightMap = adjMap.get(node);
            for (Node adjNode : weightMap.keySet()) {
                sb.append(" -> ").append(adjNode.name);
                sb.append(" weight").append(weightMap.get(adjNode));
            }
            System.out.println(sb.append("\n").toString());
        }
    }

    private int getDistanceBetweenNode(Node from, Node to) {
        for (Edge edge : edgeList) {
            if (edge.from.equals(from) && edge.to.equals(to)) {
                return edge.weight;
            }
        }
        return NO_SUCH_ROUTE;
    }

    private List<Route> findCycle(Node startNode) {
        List<Route> result = new ArrayList<>();
        //1. 获取startNode节点一环内的节点
        List<Route> oneEdgePaths = findFirstRing(startNode);

        //2. 获取startNode节点二环上的节点
        List<Route> twoEdgePaths = findSecondRing(startNode);

        result.addAll(oneEdgePaths);
        result.addAll(twoEdgePaths);

        return result;
    }

    private List<Route> findFirstRing(Node startNode) {
        List<Route> result = new ArrayList<>();
        for (Edge edge : edgeList) {
            if (edge.from.equals(startNode)) {
                for (Edge edge1 : edgeList) {
                    if (edge1.from.equals(edge.to) && edge1.to.equals(edge.from)) {
                        Route route = new Route(this);
                        route.addNode(startNode);
                        route.addNode(edge.to);
                        route.addNode(startNode);
                        result.add(route);
                    }
                }
            }
        }
        return result;
    }

    private List<Route> findSecondRing(Node startNode) {
        List<Route> result = new ArrayList<>();
        Set<Node> oneEdgeToStartNode = new HashSet<>();
        for (Edge edge : edgeList) {
            if (edge.to.equals(startNode) && !edge.from.equals(startNode)) {
                oneEdgeToStartNode.add(edge.from);
            }
        }

        //选出二度可达startNode的节点
        Map<Node, List<Node>> twoEdgeToStartNode = new HashMap<>();
        for (Node node : oneEdgeToStartNode) {
            twoEdgeToStartNode.put(node, new ArrayList<>());
            for (Edge edge : edgeList) {
                if (edge.to.equals(node) && !edge.from.equals(node) && !edge.from.equals(startNode)) {
                    twoEdgeToStartNode.get(node).add(edge.from);
                }
            }
        }
        //查找这些二度节点中的是否有从startNode中出发可达的节点，若存在则为二度环
        for (Node oneEdgeNode : twoEdgeToStartNode.keySet()) {
            List<Node> nodes = twoEdgeToStartNode.get(oneEdgeNode);
            for (Node twoEdgeNode : nodes) {
                for (Edge edge : edgeList) {
                    if (edge.from.equals(startNode) && edge.to.equals(twoEdgeNode)) {
                        Route route = new Route(this);
                        route.addNode(startNode);
                        route.addNode(twoEdgeNode);
                        route.addNode(oneEdgeNode);
                        route.addNode(startNode);
                        result.add(route);
                    }
                }
            }
        }
        //过滤重复路径
        result = filter(result);
        return result;
    }

    private List<Route> filter(List<Route> routes) {
        return routes.stream().distinct().collect(Collectors.toList());
    }

    private boolean contains(String name) {
        for (Node keyNode : adjMap.keySet()) {
            if (keyNode.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private Node findNodeByName(String name) {
        for (Node keyNode : adjMap.keySet()) {
            if (keyNode.name.equals(name)) {
                return keyNode;
            }
        }
        return null;
    }

    @Nullable
    private Node BFS(String startName, String targetName) {
        Set<Node> visited = new HashSet<>();
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(findNodeByName(startName));
        while (!nodeQueue.isEmpty()) {
            Node node = nodeQueue.poll();
            if (node.equals(findNodeByName(targetName))) {
                return node;
            }
            Map<Node, Integer> adjNodeAndWeight = adjMap.get(node);
            if (adjNodeAndWeight != null) {
                Set<Node> adjNodeSet = adjNodeAndWeight.keySet();
                for (Node tempNode : adjNodeSet) {
                    if (!visited.contains(tempNode)) {
                        visited.add(tempNode);
                        nodeQueue.add(tempNode);
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    private Map<Node, Integer> findAdjacentNodes(String name) {
        for (Node keyNode : adjMap.keySet()) {
            if (keyNode.name.equals(name)) {
                return adjMap.get(keyNode);
            }
        }
        return null;
    }
}
