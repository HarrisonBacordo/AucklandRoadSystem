package Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node {
    private int id;
    private Location location;
    private List<Edge> outgoingList;
    private List<Edge> incomingList;

    public Node(int id, double latitude, double longitude) {
        this.id = id;
        this.location = Location.newFromLatLon(latitude, longitude);
        this.incomingList = new ArrayList<>();
        this.outgoingList = new ArrayList<>();
    }

    public int getId() { return this.id; }

    public List<Edge> getOutgoingList() { return this.outgoingList; }

    public List<Edge> getIncomingList() { return this.incomingList; }

    public void addToOutgoingList(Edge edge) {
        this.outgoingList.add(edge);
    }

    public void addToIncomingList(Edge edge) {
        this.incomingList.add(edge);
    }

    public void draw(Graphics g, Location origin, double scale) {
        Point p = this.location.asPoint(origin, scale);
        g.setColor(Color.BLUE);
        g.fillOval(p.x, p.y, 5, 5);
    }

}
