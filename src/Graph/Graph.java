package Graph;

import GUI.RoadMap;
import QuadTree.Boundary;
import QuadTree.QuadTree;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a graph of the given map.
 */
public class Graph {

    private List<Node> nodeList;
    private QuadTree quadTree;
    private List<Road> roadList;

    public Graph() {
        this.nodeList = new ArrayList<>();
        this.roadList = new ArrayList<>();
        resetQuadTree();
    }

    public List<Node> getNodeList() {
        return this.nodeList;
    }

    /**
     * Adds node to the graph
     *
     * @param node - node to add
     */
    public void addNode(Node node) {
        this.nodeList.add(node);
    }

    /**
     * Adds road to the graph
     *
     * @param road - road to add
     */
    public void addRoad(Road road) {
        this.roadList.add(road);
    }

    /**
     * Finds the node of the given id
     *
     * @param Id - id of interest
     * @return - the node that matches this id
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
     * Finds the road of the given id
     *
     * @param Id - id of interest
     * @return - the road that matches this id
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
     * Updates the graph's road list in such a way that only the roads that are contained within
     * the passed in roads list will be highlighted
     *
     * @param roads - roads to highlight
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
     * Sets all nodes and roads to not highlight
     */
    public void resetHighlighted() {
        for (Road road : this.roadList) {
            road.setHighlighted(false);
        }
        for (Node node : this.nodeList) {
            node.setHighlighted(false);
        }
    }

    /**
     * Draws the graph
     *
     * @param g      - graphics object
     * @param origin - current origin of the map
     * @param scale  - current scale of the map
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

    /**
     * Resets the quad-tree to match the updated map
     */
    private void resetQuadTree() {
        this.quadTree = new QuadTree(new Boundary(RoadMap.canvasWidth / 2,
                RoadMap.canvasHeight / 2,
                RoadMap.canvasWidth / 2,
                RoadMap.canvasHeight / 2), 1);
    }

    /**
     * Grabs the nearest node to the given point using a quad-tree
     *
     * @param point - point of interest
     * @return - the nearest node
     */
    public Node getNearestNode(Point point) {
        List<Node> nodes = this.quadTree.getNearestNode(point);
        for (Node node : nodes) {
            highlight(node);
        }
        if(nodes.isEmpty()) { return null; }
        return nodes.get(0);
    }

    public List<Node> getAdjacentNodes(Node node) {
        return node.getAdjacentNodes();
    }

    /**
     * Highlights the selected node, along with the edges connected to the node
     *
     * @param node - node to highlight
     */
    public void highlight(Node node) {
        node.setHighlighted(true);
        for (Edge edge : node.getIncomingList()) {
            this.getRoadOfId(edge.getroadId()).setHighlighted(true);
        }
        for (Edge edge : node.getOutgoingList()) {
            this.getRoadOfId(edge.getroadId()).setHighlighted(true);
        }
    }
}
