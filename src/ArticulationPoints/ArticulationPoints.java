package ArticulationPoints;

import Graph.Graph;
import Graph.Node;

import java.util.*;

public class ArticulationPoints {
    private Graph graph;
    private List<Node> foundArtPoints = new ArrayList<>();

    /**
     * @return
     */
    public List<Node> findArticulationPoints(Graph graph) {
        this.graph = graph;
        this.resetNodes();
        this.foundArtPoints = new ArrayList<>();

        Node root = getNextUnvisited();
        while (root != null) {
            root.setCount(0);
            int numSubTrees = 0;
            for (Node neighbour : root.getNeighbours()) {
                if (neighbour.getCount() == Integer.MAX_VALUE) {
                    recArtPts(neighbour, 1, root);
                    numSubTrees++;
                }
            }
            if (numSubTrees > 1) {
                this.foundArtPoints.add(root);
            }
            root.setVisited(true);
            root = this.getNextUnvisited();
        }
        System.out.println(foundArtPoints.size());
        return this.foundArtPoints;
    }

    /**
     * Helper method for finding articulation points
     *
     * @param node
     * @param count
     * @param parent
     * @return
     */
    private int recArtPts(Node node, int count, Node parent) {
        node.setCount(count);
        int reachBack = count;
        for (Node neighbour : node.getNeighbours()) {
            neighbour.setVisited(true);

            if (neighbour == parent) {
                continue;
            }
            if (neighbour.getCount() < Integer.MAX_VALUE) {
                reachBack = Math.min(neighbour.getCount(), reachBack);
            } else {
                int childReach = recArtPts(neighbour, count + 1, node);
                reachBack = Math.min(childReach, reachBack);
                if (childReach >= count) {
                    this.foundArtPoints.add(node);
                }
            }
        }
        return reachBack;
    }

    /**
     * Iterative version of the articulation point finding algorithm
     *
     * @param firstNode
     * @param count
     * @param root
     */
    private void iterArtPts(Node firstNode, int count, Node root) {
        Stack<Node> nodeStack = new Stack<>();
        firstNode.setPrevious(root);
        firstNode.setCount(count);
        nodeStack.push(firstNode);
        Node current;
        Node child;
        while (!nodeStack.isEmpty()) {
            current = nodeStack.peek();
            current.setVisited(true);
            if (current.getCount() == Integer.MAX_VALUE) {
                current.setCount(count);
                current.setReachBack(count);
                current.setChildren(this.getChildrenOfNode(current));
            } else if (!current.getChildren().isEmpty()) {
                child = current.getChildren().poll();
                if (child.getCount() < Integer.MAX_VALUE) {
                    current.setReachBack(Math.min(child.getCount(), current.getReachBack()));
                } else {
                    child.setCount(count + 1);
                    child.setReachBack(current.getCount());
                    child.setPrevious(current);
                    nodeStack.push(child);
                }
            } else {
                if (current != firstNode) {
                    current.getPrevious().setReachBack(Math.min(current.getReachBack(), current.getPrevious().getReachBack()));
                    if (current.getReachBack() >= current.getPrevious().getCount()) {
                        if (!foundArtPoints.contains(current.getPrevious())) {
                            this.foundArtPoints.add(current.getPrevious());
                        }
                    }
                }
                nodeStack.pop();
            }
        }
    }

    private void resetNodes() {
        for (Node node : graph.getNodes().values()) {
            node.setVisited(false);
            node.setCount(Integer.MAX_VALUE);
            node.setPrevious(null);
        }
    }

    private Node getNextUnvisited() {
        for (Node node : this.graph.getNodes().values()) {
            if (!node.isVisited()) {
                return node;
            }
        }
        return null;
    }

    private Queue<Node> getChildrenOfNode(Node node) {
        Queue<Node> children = new LinkedList<>();
        for (Node neighbour : node.getNeighbours()) {
            if (neighbour != node.getPrevious()) {
                children.offer(neighbour);
            }
        }
        return children;
    }
}
