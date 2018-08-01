package Graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a road on the map, which is made up of one or more segments.
 */
public class Road {
    private int roadId;
    public int type;
    public String label;
    public String city;
    public boolean isOneWay;
    public int speed;
    public int roadClass;
    public boolean isNotForCar;
    public boolean isNotForPedestrians;
    public boolean isNotForBicycles;
    private boolean isHighlighted;
    private List<Edge> edgeList;

    public Road(String[] args) {
        this.roadId = Integer.parseInt(args[0]);
        this.type = Integer.parseInt(args[1]);
        this.label = args[2];
        this.city = args[3];
        this.isOneWay = Boolean.parseBoolean(args[4]);
        this.speed = Integer.parseInt(args[5]);
        this.roadClass = Integer.parseInt(args[6]);
        this.isNotForCar = Boolean.parseBoolean(args[7]);
        this.isNotForPedestrians = Boolean.parseBoolean(args[8]);
        this.isNotForBicycles = Boolean.parseBoolean(args[9]);
        this.isHighlighted = false;
        this.edgeList = new ArrayList<>();
    }

    public int getRoadId() {
        return roadId;
    }

    public List<Edge> getEdgeList() {
        return this.edgeList;
    }

    public boolean isHighlighted() {
        return this.isHighlighted;
    }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    /**
     * adds an edge to this road
     *
     * @param edge - edge to add
     * @return
     */
    public boolean addEdge(Edge edge) {
        return this.edgeList.add(edge);
    }

    /**
     * Draws this road onto the map
     *
     * @param g      - graphics object
     * @param origin - current origin of the map
     * @param scale  - current scale of the map
     */
    public void draw(Graphics g, Location origin, double scale) {
        for (Edge edge : edgeList) {
            edge.draw(g, origin, scale, isHighlighted);
        }
    }
}
