package GUI;

import AStar.AStar;
import ArticulationPoints.ArticulationPoints;
import Graph.*;
import Trie.Trie;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the overall mapping program, handles all queries by delegating it to lower-level components
 */
public class RoadMap extends GUI {
    private static final int MOVE_EAST_VALUE = 100;
    private static final int MOVE_WEST_VALUE = -100;
    private static final int MOVE_NORTH_VALUE = 100;
    private static final int MOVE_SOUTH_VALUE = -100;
    private static final double SCALE_STEP = 1.5;
    public static int canvasWidth;
    public static int canvasHeight;
    private Location origin = new Location(-193.5, 163.9);
    private double scale = 6.58;
    private Graph graph = new Graph();
    private Trie trie = new Trie();
    private AStar aStar;
    private ArticulationPoints artPts;

    /**
     * Is called when the drawing area is redrawn and performs all the logic for
     * the actual drawing, which is done with the passed Graphics object.
     *
     * @param g
     */
    @Override
    protected void redraw(Graphics g) {
        this.canvasWidth = this.getDrawingAreaDimension().width;
        this.canvasHeight = this.getDrawingAreaDimension().height;
        if(this.graph.isPopulated()) {
            List<Node> articPts = this.artPts.findArticulationPoints();
            for (Node node : articPts) {
                this.graph.highlight(node);
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
        Point point = new Point(e.getX(), e.getY());
        this.appendTextOutputArea(graph.getNearestNode(point));
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
        loadNodes(nodes, graph);
        loadRoads(roads, graph);
        loadEdges(segments, graph);
        this.graph.setPopulated(true);
        this.aStar = new AStar(this.graph);
        this.artPts = new ArticulationPoints(this.graph);
    }

    /**
     * Parses node file and saves it in the graph as a node object
     *
     * @param nodes - the nodes file
     * @param graph - the graph to store the nodes in
     */
    private void loadNodes(File nodes, Graph graph) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(nodes));
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] lineValues = line.split("\t");
                Node node = new Node(
                        Integer.parseInt(lineValues[0]),
                        Double.parseDouble(lineValues[1]),
                        Double.parseDouble(lineValues[2])
                );
                graph.addNode(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses segment file and saves it in the graph as an edge object
     *
     * @param edges - the segments file
     * @param graph - the graph to store the segments in
     */
    private void loadEdges(File edges, Graph graph) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(edges));
            String line;
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                String[] lineValues = line.split("\t");
                int roadId = Integer.parseInt(lineValues[0]);
                int fromId = Integer.parseInt(lineValues[2]);
                int toId = Integer.parseInt(lineValues[3]);
//                Convert coordinates to doubles
                List<Double> coords = new ArrayList<>();
                for (int i = 4; i < lineValues.length; i++) {
                    coords.add(Double.parseDouble(lineValues[i]));
                }
                Edge edge = new Edge(
                        Integer.parseInt(lineValues[0]),
                        Double.parseDouble(lineValues[1]),
                        this.graph.getNodeOfId(fromId),
                        this.graph.getNodeOfId(toId),
                        coords
                );
                this.graph.getRoadOfId(roadId).addEdge(edge);
                this.graph.getNodeOfId(toId).addToIncomingList(edge);
                this.graph.getNodeOfId(fromId).addToOutgoingList(edge);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses roads file and saves it in the graph as a road object
     *
     * @param roads - the roads file
     * @param graph - the graph to store the roads in
     */
    private void loadRoads(File roads, Graph graph) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(roads));
            String line;
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                Road road = new Road(line.split("\t"));
                graph.addRoad(road);
                this.trie.add(road.label.toCharArray(), road);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appends information about the given node to the text area
     *
     * @param node - node to print information about
     */
    private void appendTextOutputArea(Node node) {
        if (node == null) { return; }
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
