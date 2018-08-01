package QuadTree;

import Graph.Location;
import Graph.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a quad tree data structure used to more efficiently query objects within them on a 2d plane
 */
public class QuadTree {
    private Boundary boundary;
    private int capacity;
    private boolean divided = false;
    private List<Node> points;
    private QuadTree northWest;
    private QuadTree northEast;
    private QuadTree southWest;
    private QuadTree southEast;

    public QuadTree(Boundary boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
        this.points = new ArrayList<>();
    }

    /**
     * Inserts the given node into the quad-tree
     *
     * @param node   - node to insert
     * @param origin - current origin of the node
     * @param scale  - current scale of the node
     * @return - success or not
     */
    public boolean insert(Node node, Location origin, double scale) {
//        Ignore if not witthin boundaries
        if (!this.boundary.contains(node.getPoint(origin, scale))) {
            return false;
        }
//        Haven't reached capacity; Insert node into this container
        if (this.points.size() < this.capacity) {
            return points.add(node);
        } else {
            if (!divided) {
                this.split();
            }
//            Try adding it to each child container
            if (this.northWest.insert(node, origin, scale)) {
                return true;
            }
            if (this.northEast.insert(node, origin, scale)) {
                return true;
            }
            if (this.southWest.insert(node, origin, scale)) {
                return true;
            }
            if (this.southEast.insert(node, origin, scale)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Splits this container into four child containers
     */
    public void split() {
//        local variables to keep code clean
        int x = this.boundary.getX();
        int y = this.boundary.getY();
        int w = this.boundary.getWidth();
        int h = this.boundary.getHeight();
//        NW
        Boundary tempBound = new Boundary(x - w / 2, y - h / 2, w / 2, h / 2);
        this.northWest = new QuadTree(tempBound, this.capacity);
//        NE
        tempBound = new Boundary(x + w / 2, y - h / 2, w / 2, h / 2);
        this.northEast = new QuadTree(tempBound, this.capacity);
//        SW
        tempBound = new Boundary(x - w / 2, y + h / 2, w / 2, h / 2);
        this.southWest = new QuadTree(tempBound, this.capacity);
//        SE
        tempBound = new Boundary(x + w / 2, y + h / 2, w / 2, h / 2);
        this.southEast = new QuadTree(tempBound, this.capacity);

        this.divided = true;
    }

    /**
     * Gets a list of the nearest nodes to that point
     *
     * @param point - point of interest
     * @return - list of the closest nodes to that container
     */
    public List<Node> getNearestNode(Point point) {
        List<Node> nodeList = new ArrayList<>();
        if (this.boundary.contains(point)) {
            if (divided) {
                nodeList.addAll(this.northWest.getNearestNode(point));
                nodeList.addAll(this.northEast.getNearestNode(point));
                nodeList.addAll(this.southWest.getNearestNode(point));
                nodeList.addAll(this.southEast.getNearestNode(point));
            } else {
                nodeList.addAll(this.points);
            }
        }
        return nodeList;
    }
}
