package AStar;

import Graph.Node;

public class SearchNode {
    private Node node;
    private Node prev;
    private double subCost;
    private double cost;
    private boolean visited;

    public SearchNode(Node node, Node prev, double subCost, double cost) {
        this.node = node;
        this.prev = prev;
        this.subCost = subCost;
        this.cost = cost;
        this.visited = false;
    }

    public Node getNode() {
        return this.node;
    }

    public Node getPrev() {
        return this.prev;
    }

    public double getSubCost() {
        return this.subCost;
    }

    public double getCost() {
        return this.cost;
    }

    public boolean getVisited() { return this.visited; }

    public void setVisited(boolean visited) { this.visited = visited; }
}
