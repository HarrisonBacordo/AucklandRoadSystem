package AStar;

import Graph.Edge;
import Graph.Graph;
import Graph.Node;

import java.util.*;
import java.util.function.Function;

public class AStar {
    private Graph graph;

    public AStar(Graph graph) {
        this.graph = graph;
    }

    public Set<Node> getPath(Node start, Node finish) {
        return this.getPathHelper(start, finish, node -> {
            double x =  node.getLocation().x - finish.getLocation().x;
            double y =  node.getLocation().y - finish.getLocation().y;
            return Math.hypot(x , y);
        });
    }

    public Set<Node> getPathHelper(Node start, Node finish, Function<Node, Double> func) {
        Map<Integer, Node> unvisited = graph.getNodes();
        List<Node> visited = new ArrayList<>();
        Queue<SearchNode> fringe = new PriorityQueue<>(new NodeComparator());
        fringe.offer(new SearchNode(start, null, 0, func.apply(start)));
        while (!fringe.isEmpty()) {
            SearchNode current = fringe.poll();
            if (unvisited.containsKey(current.getNode().getId())) {
                unvisited.remove(current.getNode());
                visited.add(current.getNode());
                current.getNode().setPrevious(current.getPrev());
                if (current.getNode() == finish) {
                    break;
                }
                for (Edge e : current.getNode().getOutgoingList()) {
                    if (unvisited.containsKey(e.getTo().getId())) {
                        double g = current.getSubCost() + e.getLength();
                        double f = g + func.apply(e.getTo());
                        fringe.offer(new SearchNode(e.getTo(), current.getNode(), g, f));
                    }
                }
            }
        }
        return null;
    }
}
