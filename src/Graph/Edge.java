package Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Edge {
    private int roadId;
    private double length;
    private Node from;
    private Node to;
    private List<Double> coords;

    public Edge(int roadId, double length, Node from, Node to, List<Double> coords) {
        this.roadId = roadId;
        this.length = length;
        this.from = from;
        this.to = to;
        this.coords = coords;
//        this.coords = convertCoordinates(coords);
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

    /**
     *
     * @param g
     * @param origin
     * @param scale
     * @param highlight
     */
    public void draw(Graphics g, Location origin, double scale, boolean highlight) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        Color color = highlight ? Color.RED : Color.BLACK;
        g2d.setColor(color);
//        for (int i = 0; i < this.coords.size() - 1; i++) {
//            Point point1 = this.coords.get(i).asPoint(origin, scale);
//            Point point2 = this.coords.get(i + 1).asPoint(origin, scale);
//            g2d.drawLine(point1.x, point1.y, point2.x, point2.y);
//        }
//
        Point p1 = Location.newFromLatLon(this.coords.get(0), this.coords.get(1)).asPoint(origin, scale);
        for (int i = 2; i < coords.size(); i = i + 2) {
            Point p2 = Location.newFromLatLon(this.coords.get(i), this.coords.get(i+1)).asPoint(origin, scale);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            p1 = p2;
        }
    }

    /**
     * @param coords
     * @return
     */
    private List<Location> convertCoordinates(String[] coords) {
        List<Location> newCoords = new ArrayList<>();
        for (int i = 0; i < coords.length - 1; i+= 2) {
            double lat = Double.parseDouble(coords[i]);
            double lon = Double.parseDouble(coords[i+1]);
            newCoords.add(Location.newFromLatLon(lat, lon));
        }
        return newCoords;
    }
}