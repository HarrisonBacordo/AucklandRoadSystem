package Graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Node> nodeList;

    public Graph() {
        nodeList = new ArrayList<>();
    }

    public boolean addNode(Node node) {
        return this.nodeList.add(node);
    }
}
