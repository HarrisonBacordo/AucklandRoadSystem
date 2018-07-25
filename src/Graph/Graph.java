package Graph;

import GUI.RoadMap;
import QuadTree.QuadTree;
import QuadTree.Boundary;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Node> nodeList;
    private QuadTree quadTree;
    private List<Road> roadList;

    public Graph() {
        this.nodeList = new ArrayList<>();
        this.roadList = new ArrayList<>();
        resetQuadTree();
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

    public void resetHighlighted() {
        for (Road road : this.roadList) {
            road.setHighlighted(false);
        }
        for (Node node : this.nodeList) {
            node.setHighlighted(false);
        }
    }

    /**
     * @param g
     * @param origin
     * @param scale
     */
    public void draw(Graphics g, Location origin, double scale) {
        this.resetQuadTree();
        for (Node node : nodeList) {
            this.quadTree.insert(node, origin, scale);
            node.draw(g, origin, scale);
        }
        for (Road road : roadList) {
            road.draw(g, origin, scale);
        }
    }

    private void resetQuadTree() {
        this.quadTree = new QuadTree(new Boundary(RoadMap.canvasWidth/2,
                RoadMap.canvasHeight/2,
                RoadMap.canvasWidth/2,
                RoadMap.canvasHeight/2), 1);
    }

    public Node getNearestNode(Point point) {
        List<Node> nodes= this.quadTree.getNearestNode(point);
        for (Node node : nodes){
            highlight(node);
        }
        return nodes.get(0);
    }

    public void highlight(Node node) {
        node.setHighlighted(true);
        for(Edge edge : node.getIncomingList()) {
            this.getRoadOfId(edge.getroadId()).setHighlighted(true);
        }
        for(Edge edge : node.getOutgoingList()) {
            this.getRoadOfId(edge.getroadId()).setHighlighted(true);
        }
    }
}
