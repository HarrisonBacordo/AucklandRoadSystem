package Graph;

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

    public int getId() { return id; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public List<Edge> getOutgoingList() { return outgoingList; }

    public List<Edge> getIncomingList() { return incomingList; }
}
