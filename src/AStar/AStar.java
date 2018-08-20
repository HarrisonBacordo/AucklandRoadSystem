//package AStar;
//
//import Graph.Edge;
//import Graph.Graph;
//import Graph.Node;
//
//import java.util.*;
//import java.util.function.Function;
//
//public class AStar {
//    private Graph graph;
//
//    public AStar(Graph graph) {
//        this.graph = graph;
//    }
//
//    public List<Edge> getPath(Node start, Node finish) {
//        resetNodes();
//        return this.getPathHelper(start, finish, node -> {
//            double x =  node.getLocation().x - finish.getLocation().x;
//            double y =  node.getLocation().y - finish.getLocation().y;
//            return Math.hypot(x , y);
//        });
//    }
//
//    public List<Edge> getPathHelper(Node start, Node finish, Function<Node, Double> func) {
//        Queue<SearchNode> fringe = new PriorityQueue<>(new NodeComparator());
//        List<Node> path = new ArrayList<>();
//        Node finishNode = null;
//        fringe.offer(new SearchNode(start, null, 0, func.apply(start)));
//        while (!fringe.isEmpty()) {
//            SearchNode current = fringe.poll();
//            Node currentNode = current.getNode();
//            Node prevNode = currentNode.getPrevious();
//
//            if (!currentNode.getVisited()) {
//                currentNode.setVisited(true);
//                current.getNode().setPrevious(prevNode);
//
//                if (current.getNode() == finish) {
//                    finishNode = current.getNode();
//                    break;
//                }
//
//                for (Node neighbour : current.getNode().getNeighbours()) {
//                    if (!neighbour.getVisited()) {
//                        double g = current.getSubCost() + currentNode.getEdgeBetween(neighbour).getLength();
//                        double f = g + func.apply(currentNode.getEdgeBetween(neighbour).getFrom());
//                        fringe.offer(new SearchNode(currentNode.getEdgeBetween(neighbour).getFrom(), current.getNode(), g, f));
//                    }
//                }
//            }
//        }
//        while (finishNode != null){
//            path.add(finishNode);
//            finishNode = finishNode.getPrevious();
//        }
//        return getEdge
// s(path);
//    }
//
//    private void resetNodes() {
//        for (Node node : graph.getNodes().values()) {
//            node.setVisited(false);
//            node.setCount(Integer.MAX_VALUE);
//            node.setPrevious(null);
//        }
//    }
//
//    private ArrayList<Edge> getEdge
// s(List<Node> nodeOnPath){
//        ArrayList<Edge> path = new ArrayList<>();
//
//        for (int i = 1; i < nodeOnPath.size(); i++) {
//            Node start  = nodeOnPath.get(i - 1);
//            Node end    = nodeOnPath.get(i);
//
//            Edge foundEdge
// = end.getEdgeBetween(start);
//            if (foundEdge
// != null) path.add(foundEdge
// );
//        }
//
//        return path;
//    }
//}
package AStar;

import Graph.Edge;
import Graph.Node;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Admin on 30/03/17.
 */
public class AStar {

    //Nothing needs to be declared in the constructor
    public AStar() {
    }

    /**
     * Uses the A* algorithm to find the shorted path between 2 nodes.
     * Does not re-visit any nodes as there is no node that has a segment going out and then back into it
     *
     * @param start
     * @param goal
     */
    public ArrayList<Edge> getPath(Node start, Node goal) {
        //Creates a new priority queue/fringe that queues SearchNodes based on there
        PriorityQueue<SearchNode> fringe = new PriorityQueue<>(new NodeComparator());

        //Keeps track of the nodes that are on the path
        ArrayList<Node> nodesOnPath = new ArrayList<>();

        fringe.offer(new SearchNode(start, null, 0, estimate(start, goal)));

        while (!fringe.isEmpty()) {
            SearchNode currentNode = fringe.poll();

            Node current = currentNode.getNode();
            Node from = currentNode.getPrev();
            double costFromStart = currentNode.getSubCost();

            if (!currentNode.getVisited()) {
                currentNode.setVisited(true);
                current.setVisited(true);
                current.setPrevious(from);
                current.setCost(costFromStart);

                if (current == goal) {
                    Node goalNode = goal; //Backtrack from goal to find the correct path

                    while (goalNode != null) {
                        nodesOnPath.add(goalNode);
                        goalNode = goalNode.getPrevious();
                    }
                    break;
                }

                for (Node neighbor : current.getNeighbours()) {
                    if (!neighbor.getVisited()) {

                        //If road is oneway and this node is not an entry point, cannot enter from this direction
                        if (current.getEdgeBetween(neighbor).IsOneWay() && !current.isOneWayEntry()) continue;

                        double costToNeighbor = costFromStart + estimate(neighbor, goal);
                        double estTotal = costToNeighbor + current.getEdgeBetween(neighbor).getLength();
                        fringe.offer(new SearchNode(neighbor, current, costToNeighbor, estTotal));
                    }
                }
            }
        }

        return getEdges(nodesOnPath);
    }

    /**
     * Iterates through the found nodes along the path, finding the segments that connnect them. The segment finding function is
     * called on the second/end node in each pair of nodes.
     *
     * @param nodeOnPath
     * @return
     */
    private ArrayList<Edge> getEdges(ArrayList<Node> nodeOnPath) {
        ArrayList<Edge
                > path = new ArrayList<>();

        for (int i = 1; i < nodeOnPath.size(); i++) {
            Node start = nodeOnPath.get(i - 1);
            Node end = nodeOnPath.get(i);

            Edge foundEdge = end.getEdgeBetween(start);
            if (foundEdge != null) path.add(foundEdge);
        }

        return path;
    }

    /**
     * Calculates the estimated heuristic cost for the current node to the goal node
     *
     * @param current
     * @param goal
     * @return
     */
    private double estimate(Node current, Node goal) {
        //Uses location class distance
        double x = current.getLocation().x - goal.getLocation().x;
        double y = current.getLocation().y - goal.getLocation().y;
        return Math.hypot(x, y);
    }

}
