package Graph;

import java.awt.*;
import java.util.List;

/**
 * Represents a portion of a road between two nodes.
 */
public class Edge {
    private int roadId;
    private double length;
    private Node from;
    private Node to;
    private List<Double> coords;
    private boolean highlight;
    private boolean isOneWay;

    public Edge(int roadId, double length, Node from, Node to, List<Double> coords) {
        this.roadId = roadId;
        this.length = length;
        this.from = from;
        this.to = to;
        this.coords = coords;
        this.highlight = false;
    }

    public int getroadId() {
        return this.roadId;
    }

    public double getLength() {
        return this.length;
    }

    public Node getFrom() {
        return this.from;
    }

    public Node getTo() {
        return this.to;
    }

    public void setHighlight(boolean highlight) { this.highlight = highlight; }

    public boolean IsOneWay() { return this.isOneWay; }

    public void setOneWay(boolean isOneWay) { this.isOneWay = isOneWay; }

    //Returns the other node
    public Node getConnectingNode(Node node){
        if(node == this.to) return this.from;
        return this.to;
    }

    /**
     * Draws the edge onto the map
     *
     * @param g         - graphics object
     * @param origin    - current origin of the map
     * @param scale     - current scale of the map
     * @param highlight - whether to highlight this edge or not
     */
    public void draw(Graphics g, Location origin, double scale, boolean highlight) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1));
        Color color = highlight || this.highlight ? Color.RED : Color.BLACK;
        g2d.setColor(color);
//        convert all coordinates to points, and draw the line between the points
        Point p1 = Location.newFromLatLon(this.coords.get(0), this.coords.get(1)).asPoint(origin, scale);
        for (int i = 2; i < coords.size(); i = i + 2) {
            Point p2 = Location.newFromLatLon(this.coords.get(i), this.coords.get(i + 1)).asPoint(origin, scale);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            p1 = p2;
        }
    }
}