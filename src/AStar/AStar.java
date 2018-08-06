package AStar;

import Graph.*;

import java.util.*;
import java.util.function.Function;

public class AStar {
    private Graph graph;
    public AStar(Graph graph) {
        this.graph = graph;
    }

    public Set<Node> getPath(Node start, Node finish, Function<Node, Double> func) {
        List<Node> unvisited = graph.getNodeList();
        List<Node> visited = new ArrayList<>();
        Queue<SearchNode> fringe = new PriorityQueue<>(new NodeComparator());
        fringe.add(new SearchNode(start, null, 0, func.apply(start)));
        while (!fringe.isEmpty()) {
            SearchNode current = fringe.poll();
            if (unvisited.contains(current.getNode())) {
                unvisited.remove(current.getNode());
                visited.add(current.getNode());
//                set node*.prev = prev*????
                if (current.getNode() == finish) {
                    break;
                }
                for (Edge e : current.getNode().getOutgoingList()) {
                    if(unvisited.contains(e.getTo())) {
                        double g = current.getSubCost() + e.getLength();
                        double f = g + func.apply(e.getTo());
                        fringe.add(new SearchNode(e.getTo(), current.getNode(), g, f));
                    }
                }
            }
        }
        return null;
    }
}
