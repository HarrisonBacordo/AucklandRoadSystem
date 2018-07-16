import Graph.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;

public class RoadMap extends GUI {
    private static final File smallNodesPath = new File("Data/small/nodeID-lat-lon.tab");
    private static final File smallRoadsPath = new File("Data/small/roadSeg-roadID-length-nodeID-nodeID-coords.tab");
    private static final File smallSegmentsPath = new File("Data/small/roadID-roadInfo.tab");
    private static final File largeNodesPath = new File("Data/large/nodeID-lat-lon.tab");
    private static final File largeRoadsPath = new File("Data/large/roadSeg-roadID-length-nodeID-nodeID-coords.tab");
    private static final File largeSegmentsPath = new File("Data/large/roadID-roadInfo.tab");
    private static final File polygonsPath = new File("");

    public RoadMap(boolean isTest) {
        if (isTest) {
            onLoad(smallNodesPath, smallRoadsPath, smallSegmentsPath, polygonsPath);
        } else {
            onLoad(largeNodesPath, largeRoadsPath, largeSegmentsPath, polygonsPath);
        }
    }

    /**
     * Is called when the drawing area is redrawn and performs all the logic for
     * the actual drawing, which is done with the passed Graphics object.
     *
     * @param g
     */
    @Override
    protected void redraw(Graphics g) {

    }

    /**
     * Is called when the mouse is clicked (actually, when the mouse is
     * released), and is passed the MouseEvent object for that click.
     *
     * @param e
     */
    @Override
    protected void onClick(MouseEvent e) {

    }

    /**
     * Is called whenever the search box is updated. Use getSearchBox to get the
     * JTextField object that is the search box itself.
     */
    @Override
    protected void onSearch() {

    }

    /**
     * Is called whenever a navigation button is pressed. An instance of the
     * Move enum is passed, representing the button clicked by the user.
     *
     * @param m
     */
    @Override
    protected void onMove(Move m) {

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
        Graph graph = new Graph();
        loadNodes(nodes, graph);
        loadEdges(segments, graph);
        loadRoads(roads, graph);
    }

    /**
     * Parses node file and saves it in the graph as a node object
     * @param nodes - the nodes file
     * @param graph - the graph to store the nodes in
     */
    private void loadNodes(File nodes, Graph graph) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(nodes));
            String line;
            while((line = fileReader.readLine()) != null) {
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
     * @param edges - the segments file
     * @param graph - the graph to store the segments in
     */
    private void loadEdges(File edges, Graph graph) {

    }

    /**
     * Parses roads file and saves it in the graph as a road object
     * @param roads - the roads file
     * @param graph - the graph to store the roads in
     */
    private void loadRoads(File roads, Graph graph) {

    }

    public static void main(String[] args) { new RoadMap(true); }
}
