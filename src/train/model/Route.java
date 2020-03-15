package train.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Johnny Zhang
 * @Date: 2020/3/3 21:22
 */
public class Route {
    private List<String> nodes = new ArrayList<>();
    private Graph graph;
    public Route(Graph graph){
        this.graph = graph;
    }

    public void addNode(String name) {
        nodes.add(name);
    }

    public void addNode(Node node) {
        nodes.add(node.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Route) {
            Route route = (Route)obj;
            if (this == route) {
                return true;
            }

            if (nodes.size() != route.nodes.size()) {
                return false;
            }

            for (int i = 0; i < nodes.size(); i ++) {
                if (!nodes.get(i).equals(route.nodes.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
