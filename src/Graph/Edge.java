package Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Edge {
    private int roadId;
    private double length;
    private Node from;
    private Node to;
    private List<Location> coords;

    public Edge(int roadId, double length, Node from, Node to, String[] coords) {
        this.roadId = roadId;
        this.length = length;
        this.from = from;
        this.to = to;
        this.coords = convertCoordinates(coords);
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

    public void draw(Graphics g, Location origin, double scale) {
//        TODO implement this
        g.setColor(Color.BLUE);
        for (int i = 0; i < this.coords.size() - 1; i+=2) {
            Location point1 = this.coords.get(i);
            Location point2 = this.coords.get(i + 1);
            g.drawLine((int)point1.x, (int)point1.y, (int)point2.x, (int)point2.y);
        }
    }

    private List<Location> convertCoordinates(String[] coords) {
        List<Location> newCoords = new ArrayList<>();
        for (int i = 0; i < coords.length - 1; i+=2) {
            double lat = Double.parseDouble(coords[i]);
            double lon = Double.parseDouble(coords[i+1]);
            newCoords.add(Location.newFromLatLon(lat, lon));
        }
        return newCoords;
    }
}