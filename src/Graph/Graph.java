package Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Node> nodeList;
    private List<Road> roadList;

    public Graph() {
        nodeList = new ArrayList<>();
        roadList = new ArrayList<>();
    }

    public boolean addNode(Node node) {
        return this.nodeList.add(node);
    }

    public boolean addRoad(Road road) { return this.roadList.add(road); }

    public Node getNodeOfId(int Id) {
        for (Node node : nodeList) {
            if (node.getId() == Id) {
                return node;
            }
        }
        return null;
    }

    public Road getRoadOfId(int Id) {
        for (Road road : roadList) {
            if (road.getRoadId() == Id) {
                return road;
            }
        }
        return null;
    }
    
    public void draw(Graphics g){
        for (Node node : nodeList) {
            node.draw(g);
        }
        for (Road road : roadList) {
            road.draw(g);
        }
    }
}
