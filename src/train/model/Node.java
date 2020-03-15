package train.model;

public class Node {
    String name;
    public Node(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        Node node;
        if (obj instanceof Node) {
            node = (Node) obj;
        } else {
            return false;
        }
        return this.name.equals(node.name);
    }

    @Override
    public String toString() {
        return "Node[" + name + "]";
    }
}
