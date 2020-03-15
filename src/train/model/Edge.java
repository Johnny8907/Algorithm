package train.model;

import java.util.Map;

public class Edge {
    Node from;
    Node to;
    int weight;

    public Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge from " + from.name + " to " + to.name + " weight " + weight;
    }
}
