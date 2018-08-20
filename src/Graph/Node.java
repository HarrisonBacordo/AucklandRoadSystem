package Graph;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Queue;

/**
 * Represents a node/intersection on the map
 */
public class Node {
    private int id;
    private Location location;
    private List<Edge> outgoingList;
    private List<Edge> incomingList;
    private List<Edge> allEdges;
    private boolean isHighlighted;
    private Node previous;
    private boolean visited;
    private int count;
    private int reachBack;
    private double cost;
    private Queue<Node> children;
    private boolean oneWayEntry;

    public Node(int id, double latitude, double longitude) {
        this.id = id;
        this.location = Location.newFromLatLon(latitude, longitude);
        this.incomingList = new ArrayList<>();
        this.outgoingList = new ArrayList<>();
        this.allEdges = new ArrayList<>();
        this.isHighlighted = false;
        this.previous = null;
        this.visited = false;
        this.children = new LinkedList<>();
    }

    public int getId() {
        return this.id;
    }

    public List<Edge> getOutgoingList() {
        return this.outgoingList;
    }

    public List<Edge> getIncomingList() {
        return this.incomingList;
    }

    public Location getLocation() {
        return this.location;
    }

    public Point getPoint(Location origin, double scale) {
        return this.location.asPoint(origin, scale);
    }

    public Node getPrevious() {
        return this.previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getReachBack() {
        return this.reachBack;
    }

    public void setReachBack(int reachBack) {
        this.reachBack = reachBack;
    }

    public Queue<Node> getChildren() {
        return this.children;
    }

    public void setChildren(Queue<Node> children) {
        this.children = children;
    }

    public boolean isHighlighted() {
        return this.isHighlighted;
    }

    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public List<Edge> getAllEdges() {
        return this.allEdges;
    }

    public void setCost(double cost) { this.cost = cost; }

    public void setOneWayEntry(boolean oneWayEntry) { this.oneWayEntry = oneWayEntry; }

    public boolean isOneWayEntry() { return this.oneWayEntry; }

    /**
     * Adds the given edge to the outgoing list
     *
     * @param edge - edge to add
     */
    public void addToOutgoingList(Edge edge) {
        this.outgoingList.add(edge);
        this.allEdges.add(edge);
    }

    /**
     * Adds the given edge to the incoming list
     *
     * @param edge - edge to add
     */
    public void addToIncomingList(Edge edge) {
        this.incomingList.add(edge);
        this.allEdges.add(edge);
    }

    public Set<Node> getNeighbours() {
        Set<Node> neighbours = new HashSet<>();
        for (Edge edge : this.allEdges) {
            neighbours.add(edge.getConnectingNode(this));
        }
        return neighbours;
    }

    public Edge getEdgeBetween(Node neighborNode){
        for (Edge edge : this.allEdges){
            if(edge.getTo() == neighborNode || edge.getFrom() == neighborNode) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Draws the node onto the GUI
     *
     * @param g      - graphics object
     * @param origin - current origin of the map
     * @param scale  - current scale of the map
     */
    public void draw(Graphics g, Location origin, double scale) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        Color color = isHighlighted ? Color.CYAN : Color.BLUE;
        g2d.setColor(color);
        Point p = this.location.asPoint(origin, scale);
        g.fillOval(p.x, p.y, 5, 5);
    }

}
