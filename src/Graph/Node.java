package Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node {
    private int id;
    private double latitude;
    private double longitude;
    private List<Edge> outgoingList;
    private List<Edge> incomingList;

    public Node(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.incomingList = new ArrayList<>();
        this.outgoingList = new ArrayList<>();
    }

    public int getId() { return this.id; }

    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

    public List<Edge> getOutgoingList() { return this.outgoingList; }

    public List<Edge> getIncomingList() { return this.incomingList; }

    public boolean addToOutgoingList(Edge edge) { return this.outgoingList.add(edge); }

    public boolean addToIncomingList(Edge edge) { return this.incomingList.add(edge); }

    public void draw(Graphics g) {
//        TODO implement this
    }

}
