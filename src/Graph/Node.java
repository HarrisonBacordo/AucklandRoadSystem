package Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node/intersection on the map
 */
public class Node {
    private int id;
    private Location location;
    private List<Edge> outgoingList;
    private List<Edge> incomingList;
    private boolean isHighlighted;

    public Node(int id, double latitude, double longitude) {
        this.id = id;
        this.location = Location.newFromLatLon(latitude, longitude);
        this.incomingList = new ArrayList<>();
        this.outgoingList = new ArrayList<>();
        this.isHighlighted = false;
    }

    public int getId() {
        return this.id;
    }

    public List<Edge> getOutgoingList() {
        return this.outgoingList;
    }

    public List<Edge> getIncomingList() {
        return this.incomingList;
    }

    public Location getLocation() {
        return this.location;
    }

    public Point getPoint(Location origin, double scale) {
        return this.location.asPoint(origin, scale);
    }

    public boolean isHighlighted() {
        return this.isHighlighted;
    }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    /**
     * Adds the given edge to the outgoing list
     *
     * @param edge - edge to add
     */
    public void addToOutgoingList(Edge edge) {
        this.outgoingList.add(edge);
    }

    /**
     * Adds the given edge to the incoming list
     *
     * @param edge - edge to add
     */
    public void addToIncomingList(Edge edge) {
        this.incomingList.add(edge);
    }

    public List<Node> getAdjacentNodes() {
        List<Node> adjacentNodes = new ArrayList<>();
        for (Edge edge : this.outgoingList) {
            adjacentNodes.add(edge.getTo());
        }
        return adjacentNodes;
    }

    /**
     * Draws the node onto the GUI
     *
     * @param g      - graphics object
     * @param origin - current origin of the map
     * @param scale  - current scale of the map
     */
    public void draw(Graphics g, Location origin, double scale) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        Color color = isHighlighted ? Color.CYAN : Color.BLUE;
        g2d.setColor(color);
        Point p = this.location.asPoint(origin, scale);
        g.fillOval(p.x, p.y, 5, 5);
    }

}
