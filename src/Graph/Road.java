package Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Road {
    private int roadId;
    private int type;
    private String label;
    private String city;
    private boolean isOneWay;
    private int speed;
    private int roadClass;
    private boolean isNotForCar;
    private boolean isNotForPedestrians;
    private boolean isNotForBicycles;
    private List<Edge> edgeList;

    public Road(String[] args) {
        this.roadId = Integer.parseInt(args[0]);
        this.type = Integer.parseInt(args[1]);
        this.label = args[2];
        this.city = args[3];
        this.isOneWay =  Boolean.parseBoolean(args[4]);
        this.speed = Integer.parseInt(args[5]);
        this.roadClass = Integer.parseInt(args[6]);
        this.isNotForCar = Boolean.parseBoolean(args[7]);
        this.isNotForPedestrians = Boolean.parseBoolean(args[8]);
        this.isNotForBicycles = Boolean.parseBoolean(args[9]);
        this.edgeList = new ArrayList<>();
    }

    public int getRoadId() { return roadId; }

    public boolean addEdge(Edge edge) { return this.edgeList.add(edge); }

    public void applyXMove(int move) {
        for(Edge edge : this.edgeList) {
            edge.applyXMove(move);
        }
    }

    public void applyYMove(int move) {
        for(Edge edge : this.edgeList) {
            edge.applyYMove(move);
        }
    }

    public void draw(Graphics g, Location origin, double scale) {
        for (Edge edge : edgeList) {
            edge.draw(g, origin, scale);
        }
    }
}
