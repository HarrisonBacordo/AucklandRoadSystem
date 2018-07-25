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

    /**
     * @param node
     */
    public void addNode(Node node) {
        this.nodeList.add(node);
    }

    /**
     * @param road
     */
    public void addRoad(Road road) {
        this.roadList.add(road);
    }

    /**
     * @param Id
     * @return
     */
    public Node getNodeOfId(int Id) {
        for (Node node : nodeList) {
            if (node.getId() == Id) {
                return node;
            }
        }
        return null;
    }

    /**
     * @param Id
     * @return
     */
    public Road getRoadOfId(int Id) {
        for (Road road : roadList) {
            if (road.getRoadId() == Id) {
                return road;
            }
        }
        return null;
    }

    /**
     * @param roads
     */
    public void updateHighlighted(List<Road> roads) {
        for (Road road : this.roadList) {
            if (road.isHighlighted() && !roads.contains(road)) {
                road.setHighlighted(false);
            } else if (roads.contains(road)) {
                road.setHighlighted(true);
            }
        }
    }

    /**
     * @param g
     * @param origin
     * @param scale
     */
    public void draw(Graphics g, Location origin, double scale) {
        for (Node node : nodeList) {
            node.draw(g, origin, scale);
        }
        for (Road road : roadList) {
            road.draw(g, origin, scale);
        }
    }
}
