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
    public int isOneWay;
    public int speed;
    public int roadClass;
    public int isNotForCar;
    public int isNotForPedestrians;
    public int isNotForBicycles;
    private boolean  isHighlighted;
    private List<Edge> edgeList;

    public Road(int roadId, int type, String label, String city, int isOneWay, int speed, int roadClass, int isNotForCar,
                int isNotForPedestrians, int isNotForBicycles) {
        this.roadId = roadId;
        this.type = type;
        this.label = label;
        this.city = city;
        this.isOneWay = isOneWay;
        this.speed = speed;
        this.roadClass = roadClass;
        this.isNotForCar = isNotForCar;
        this.isNotForPedestrians = isNotForPedestrians;
        this.isNotForBicycles = isNotForBicycles;
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
