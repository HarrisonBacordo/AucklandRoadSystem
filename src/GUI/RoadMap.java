package GUI;

import AStar.AStar;
import ArticulationPoints.ArticulationPoints;
import Graph.*;
import Trie.Trie;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents the overall mapping program, handles all queries by delegating it to lower-level components
 */
public class RoadMap extends GUI {
    private static final int MOVE_EAST_VALUE = 100;
    private static final int MOVE_WEST_VALUE = -100;
    private static final int MOVE_NORTH_VALUE = 100;
    private static final int MOVE_SOUTH_VALUE = -100;
    private static final double SCALE_STEP = 1.5;
    public static final double MAX_CLICKED_DISTANCE = 0.15;
    public static int canvasWidth;
    public static int canvasHeight;
    private Location origin = new Location(0, 0);
    private double scale = 1;
    private Graph graph;
    private Trie trie;
    private AStar aStar;
    private ArticulationPoints artPts;
    private boolean showArtPts = false;
    private Node current;
    private Node previous;
    private Node[] clickHistory = new Node[2];

    /**
     * Is called when the drawing area is redrawn and performs all the logic for
     * the actual drawing, which is done with the passed Graphics object.
     *
     * @param g
     */
    @Override
    protected void redraw(Graphics g) {
        if (graph != null) {
            this.canvasWidth = this.getDrawingAreaDimension().width;
            this.canvasHeight = this.getDrawingAreaDimension().height;
            if (this.showArtPts) {
                List<Node> articPts = this.artPts.findArticulationPoints(this.graph);
                for (Node node : articPts) {
                    this.graph.highlight(node);
                }
            } else {
            }
            graph.draw(g, origin, scale);
        }

    }

    /**
     * Is called when the mouse is clicked (actually, when the mouse is
     * released), and is passed the MouseEvent object for that click.
     *
     * @param e
     */
    @Override
    protected void onClick(MouseEvent e) {
        this.graph.resetHighlighted();
        Location clicked = Location.newFromPoint(e.getPoint(), origin, scale);
        // find the closest node.
        double bestDist = Double.MAX_VALUE;
        Node closest = null;

        for (Node node : graph.getNodes().values()) {
            double distance = clicked.distance(node.getLocation());
            if (distance < bestDist) {
                bestDist = distance;
                closest = node;
            }
        }

        // if it's close enough, highlight it and show some information.
        if (clicked.distance(closest.getLocation()) < MAX_CLICKED_DISTANCE) {
            this.current = closest;
            if (clickHistory[0] == null) {
                clickHistory[0] = current;
            }
            clickHistory[1] = current;
            this.previous = clickHistory[0];
            clickHistory[0] = clickHistory[1];
            graph.highlight(closest);
            graph.highlight(previous);
            getTextOutputArea().setText(closest.toString());
            this.appendTextOutputArea(closest);
        }
    }

    @Override
    protected void onScroll(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            onMove(Move.ZOOM_IN);
        } else {
            onMove(Move.ZOOM_OUT);
        }
    }

    /**
     * Is called whenever the search box is updated. Use getSearchBox to get the
     * JTextField object that is the search box itself.
     */
    @Override
    protected void onSearch() {
        String text = getSearchBox().getText();
        List<Road> results = this.trie.getAll(text.toCharArray());
//        highlight results on map
        this.graph.updateHighlighted(results);
    }

    /**
     * Is called whenever a navigation button is pressed. An instance of the
     * Move enum is passed, representing the button clicked by the user.
     *
     * @param m
     */
    @Override
    protected void onMove(Move m) {
        switch (m) {
            case NORTH:
                this.origin = origin.moveBy(0, MOVE_NORTH_VALUE / this.scale);
                break;
            case SOUTH:
                this.origin = origin.moveBy(0, MOVE_SOUTH_VALUE / this.scale);
                break;
            case EAST:
                this.origin = origin.moveBy(MOVE_EAST_VALUE / this.scale, 0);
                break;
            case WEST:
                this.origin = origin.moveBy(MOVE_WEST_VALUE / this.scale, 0);
                break;
            case ZOOM_IN:
                this.scale *= SCALE_STEP;
                break;
            case ZOOM_OUT:
                this.scale /= SCALE_STEP;
                break;
        }
    }

    /**
     * Is called when the user has successfully selected a directory to load the
     * data files from. File objects representing the four files of interested
     * are passed to the method. The fourth File, polygons, might be null if it
     * isn't present in the directory.
     *
     * @param nodes    a File for nodeID-lat-lon.tab
     * @param roads    a File for roadID-roadInfo.tab
     * @param segments a File for roadSeg-roadID-length-nodeID-nodeID-coords.tab
     * @param polygons
     */
    @Override
    protected void onLoad(File nodes, File roads, File segments, File polygons) {
//        ORDER MATTERS HERE
        this.graph = new Graph(nodes, roads, segments);
        this.graph.setPopulated(true);
        trie = graph.populateTrie();
        this.aStar = new AStar();
        this.artPts = new ArticulationPoints();
    }

    /**
     * Triggers A* search
     */
    @Override
    protected void aStarSearch() {
        List<Edge> path = this.aStar.getPath(this.current, this.previous);
        for (Edge edge : path) {
            edge.setHighlight(true);
        }
        this.redraw();
    }

    /**
     * Toggles articulation points
     */
    @Override
    protected void toggleArticulationPoints() {
        this.showArtPts = !this.showArtPts;
        this.redraw();
    }

    /**
     * Appends information about the given node to the text area
     *
     * @param node - node to print information about
     */
    private void appendTextOutputArea(Node node) {
        if (node == null) {
            return;
        }
        List<Road> roads = new ArrayList<>();
        for (Edge edge : node.getIncomingList()) {
            roads.add(graph.getRoadOfId(edge.getroadId()));
        }
        for (Edge edge : node.getOutgoingList()) {
            roads.add(graph.getRoadOfId(edge.getroadId()));
        }
        for (Road road : roads) {
            getTextOutputArea().append("Road name: " + road.label + "\n" +
                    "City: " + road.city + "\n" +
                    "One way: " + road.isOneWay + "\n" +
                    "Cars prohibited: " + road.isNotForCar + "\n" +
                    "Pedestrians prohibited: " + road.isNotForPedestrians + "\n" +
                    "Bicycles prohibited: " + road.isNotForBicycles + "\n\n");
        }

    }

    public static void main(String[] args) {
        new RoadMap();
    }
}
