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

    public void draw(Graphics g, double origin, double scale) {
//        TODO implement this
        g.setColor(Color.BLUE);
        for (Location location : coords) {
            g.drawOval((int) location.x, (int) location.y, 10, 10);
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