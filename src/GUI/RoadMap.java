package GUI;

import Graph.*;
import Trie.Trie;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class RoadMap extends GUI {
    private static final File smallNodesPath = new File("Data/small/nodeID-lat-lon.tab");
    private static final File smallSegmentsPath = new File("Data/small/roadSeg-roadID-length-nodeID-nodeID-coords.tab");
    private static final File smallRoadsPath = new File("Data/small/roadID-roadInfo.tab");
    private static final File largeNodesPath = new File("Data/large/nodeID-lat-lon.tab");
    private static final File largeSegmentsPath = new File("Data/large/roadSeg-roadID-length-nodeID-nodeID-coords.tab");
    private static final File largeRoadsPath = new File("Data/large/roadID-roadInfo.tab");
    private static final File polygonsPath = new File("");
    private static final double scaleStep = 1.5;
    private Graph graph = new Graph();
    private Trie trie = new Trie();
    private Location origin = new Location(0, 0);
    private double scale = 1.0;

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
        graph.draw(g, origin, scale);
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
        String text = getSearchBox().getText();
        List<Road> results = this.trie.get(text.toCharArray());
        System.out.println(results.toString());
//        highlight results on map
        for (Road road : results) {
            graph.getRoadOfId(road.getRoadId()).setHighlighted(true);
        }
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
            case ZOOM_IN:
                this.scale *= scaleStep;
                break;
            case ZOOM_OUT:
                this.scale /= scaleStep;
                break;
            default:
                graph.move(m);
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
        loadNodes(nodes, graph);
        loadRoads(roads, graph);
        loadEdges(segments, graph);
        System.out.println();
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
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(edges));
            String line;
            fileReader.readLine();
            while((line = fileReader.readLine()) != null) {
                String[] lineValues = line.split("\t");
                int roadId = Integer.parseInt(lineValues[0]);
                int toId = Integer.parseInt(lineValues[2]);
                int fromId = Integer.parseInt(lineValues[3]);
                Edge edge = new Edge(
                        Integer.parseInt(lineValues[0]),
                        Double.parseDouble(lineValues[1]),
                        graph.getNodeOfId(toId),
                        graph.getNodeOfId(fromId),
                        Arrays.copyOfRange(lineValues, 4, lineValues.length-1)
                );
                graph.getRoadOfId(roadId).addEdge(edge);
                graph.getNodeOfId(toId).addToIncomingList(edge);
                graph.getNodeOfId(fromId).addToOutgoingList(edge);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses roads file and saves it in the graph as a road object
     * @param roads - the roads file
     * @param graph - the graph to store the roads in
     */
    private void loadRoads(File roads, Graph graph) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(roads));
            String line;
            fileReader.readLine();
            while((line = fileReader.readLine()) != null) {
                Road road = new Road(line.split("\t"));
                graph.addRoad(road);
                this.trie.add(road.getLabel().toCharArray(), road);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) { new RoadMap(true); }
}
