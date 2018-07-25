package QuadTree;

import Graph.Location;
import Graph.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean insert(Node node, Location origin, double scale) {
        if(!this.boundary.contains(node.getPoint(origin, scale))) {
            return false;
        }
        if(this.points.size() < this.capacity) {
            return points.add(node);
        }
        else {
            if (!divided) {
                this.split();
            }
            if (this.northWest.insert(node, origin, scale)) { return true; }
            if (this.northEast.insert(node, origin, scale)) { return true; }
            if (this.southWest.insert(node, origin, scale)) { return true; }
            if (this.southEast.insert(node, origin, scale)) { return true; }

        }
        return false;
    }

    public void split() {
        int x = this.boundary.getX();
        int y = this.boundary.getY();
        int w = this.boundary.getWidth();
        int h = this.boundary.getHeight();
        Boundary tempBound = new Boundary(x - w / 2, y - h / 2, w / 2, h / 2);
        this.northWest = new QuadTree(tempBound, this.capacity);
        tempBound = new Boundary(x + w / 2, y - h / 2, w / 2, h / 2);
        this.northEast = new QuadTree(tempBound, this.capacity);
        tempBound = new Boundary(x - w / 2, y + h / 2, w / 2, h / 2);
        this.southWest = new QuadTree(tempBound, this.capacity);
        tempBound = new Boundary(x + w / 2, y + h / 2, w / 2, h / 2);
        this.southEast = new QuadTree(tempBound, this.capacity);
        this.divided = true;
    }

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
