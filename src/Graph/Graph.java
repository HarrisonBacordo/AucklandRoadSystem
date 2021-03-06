package Graph;

import GUI.RoadMap;
import QuadTree.Boundary;
import QuadTree.QuadTree;
import Trie.Trie;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Represents a graph of the given map.
 */
public class Graph {

    private Map<Integer, Node> nodeList;
    private QuadTree quadTree;
    private Map<Integer, Road> roadList;
    private boolean populated;
    private Collection<Edge> edges;

    public Graph(File nodes, File roads, File segments) {
        this.nodeList = Parser.parseNodes(nodes, this);
        this.roadList = Parser.parseRoads(roads, this);
        this.edges = Parser.parseSegments(segments, this);
        this.populated = false;
        resetQuadTree();
    }

    public Map<Integer, Node> getNodes() {
        return this.nodeList;
    }

    public boolean isPopulated() { return this.populated; }

    public void setPopulated(boolean populated) { this.populated = populated; }


    /**
     * Adds node to the graph
     *
     * @param node - node to add
     */
    public void addNode(Node node) {
        this.nodeList.put(node.getId(), node);
    }

    /**
     * Adds road to the graph
     *
     * @param road - road to add
     */
    public void addRoad(Road road) {
        this.roadList.put(road.getRoadId(), road);
    }

    /**
     * Finds the node of the given id
     *
     * @param Id - id of interest
     * @return - the node that matches this id
     */
    public Node getNodeOfId(int Id) {
        return this.nodeList.get(Id);
    }

    /**
     * Finds the road of the given id
     *
     * @param Id - id of interest
     * @return - the road that matches this id
     */
    public Road getRoadOfId(int Id) {
        return this.roadList.get(Id);
    }

    /**
     * Updates the graph's road list in such a way that only the roads that are contained within
     * the passed in roads list will be highlighted
     *
     * @param roads - roads to highlight
     */
    public void updateHighlighted(List<Road> roads) {
        for (Road road : this.roadList.values()) {
            if (road.isHighlighted() && !roads.contains(road)) {
                road.setHighlighted(false);
            } else if (roads.contains(road)) {
                road.setHighlighted(true);
            }
        }
    }

    public Trie populateTrie() {
        Trie trie = new Trie();
        for (Road road : this.roadList.values()) {
            trie.add(road.label.toCharArray(), road);
        }
        return trie;
    }

    /**
     * Sets all nodes and roads to not highlight
     */
    public void resetHighlighted() {
        for (Road road : this.roadList.values()) {
            road.setHighlighted(false);
        }
        for (Node node : this.nodeList.values()) {
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
        for (Node node : nodeList.values()) {
            this.quadTree.insert(node, origin, scale);
            node.draw(g, origin, scale);
        }
        for (Edge edge : edges) {
            edge.draw(g, origin, scale, this.getRoadOfId(edge.getroadId()).isHighlighted());
        }
    }

    /**
     * Resets the quad-tree to match the updated map
     */
    private void resetQuadTree() {
        this.quadTree = new QuadTree(new Boundary(RoadMap.canvasWidth / 2,
                RoadMap.canvasHeight / 2,
                RoadMap.canvasWidth / 2,
                RoadMap.canvasHeight / 2), 5);
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

    public Set<Node> getAdjacentNodes(Node node) {
        return node.getNeighbours();
    }

    /**
     * Highlights the selected node, along with the edges connected to the node
     *
     * @param node - node to highlight
     */
    public void highlight(Node node) {
        node.setHighlighted(true);
//        for (Edge edge : node.getIncomingList()) {
//            this.getRoadOfId(edge.getroadId()).setHighlighted(true);
//        }
//        for (Edge edge : node.getOutgoingList()) {
//            this.getRoadOfId(edge.getroadId()).setHighlighted(true);
//        }
    }
}
