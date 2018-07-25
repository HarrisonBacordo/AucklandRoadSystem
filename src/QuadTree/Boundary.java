package QuadTree;

import Graph.Location;
import Graph.Node;

import java.awt.*;

public class Boundary {
    private int x;
    private int y;
    private int width;
    private int height;

    public Boundary(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }

    public boolean contains(Point point) {
        return (point.x >= this.x - this.width
                && point.x <= this.x + this.width
                && point.y >= this.y - this.height
                && point.y <= this.y + this.height);
    }
}
