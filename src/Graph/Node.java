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

    public Location getLocation() { return this.location; }

    public void setLocation(Location location) { this.location = location; }

    public List<Edge> getOutgoingList() { return this.outgoingList; }

    public List<Edge> getIncomingList() { return this.incomingList; }

    public boolean addToOutgoingList(Edge edge) { return this.outgoingList.add(edge); }

    public boolean addToIncomingList(Edge edge) { return this.incomingList.add(edge); }

    public void draw(Graphics g, double origin, double scale) {
        g.setColor(Color.cyan);
        g.fillOval((int)location.x, (int)location.y, 20, 20);
    }

}
