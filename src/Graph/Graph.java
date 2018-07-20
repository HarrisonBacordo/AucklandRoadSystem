package Graph;

import GUI.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private static final int MOVE_EAST_VALUE = -10;
    private static final int MOVE_WEST_VALUE = 10;
    private static final int MOVE_NORTH_VALUE = -10;
    private static final int MOVE_SOUTH_VALUE = 10;
    private static final double ZOOM_IN_VALUE = 0.1;
    private static final double ZOOM_OUT_VALUE = -0.1;

    private List<Node> nodeList;
    private List<Road> roadList;

    public Graph() {
        nodeList = new ArrayList<>();
        roadList = new ArrayList<>();
    }

    public boolean addNode(Node node) {
        return this.nodeList.add(node);
    }

    public boolean addRoad(Road road) { return this.roadList.add(road); }

    public Node getNodeOfId(int Id) {
        for (Node node : nodeList) {
            if (node.getId() == Id) {
                return node;
            }
        }
        return null;
    }

    public Road getRoadOfId(int Id) {
        for (Road road : roadList) {
            if (road.getRoadId() == Id) {
                return road;
            }
        }
        return null;
    }

    public void move(GUI.Move m) {
        switch(m) {
            case NORTH:
                this.moveY(MOVE_NORTH_VALUE);
                break;
            case SOUTH:
                this.moveY(MOVE_SOUTH_VALUE);
                break;
            case EAST:
                this.moveX(MOVE_EAST_VALUE);
                break;
            case WEST:
                this.moveX(MOVE_WEST_VALUE);
                break;
        }
    }

    private void moveX(int move) {
        for(Node node : this.nodeList) {
            node.setLocation(new Location(node.getLocation().x + move, node.getLocation().y));
        }
        for(Road road : this.roadList) {
            road.applyXMove(move);
        }
    }

    private void moveY(int move) {
        for(Node node : this.nodeList) {
            node.setLocation(new Location(node.getLocation().x, node.getLocation().y + move));
        }
        for(Road road : this.roadList) {
            road.applyYMove(move);
        }
    }
    
    public void draw(Graphics g, Location origin, double scale){
        for (Node node : nodeList) {
            node.draw(g, origin, scale);
        }
        for (Road road : roadList) {
            road.draw(g, origin, scale);
        }
    }
}
