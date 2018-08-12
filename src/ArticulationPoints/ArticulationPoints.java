package ArticulationPoints;

import Graph.Graph;
import Graph.Node;

import java.util.*;

public class ArticulationPoints {
    private Graph graph;
    private Map<Node, Integer> counts = new HashMap<>();
    private List<Node> foundArtPoints = new ArrayList<>();

    public ArticulationPoints(Graph graph) {
        this.graph = graph;
    }

    /**
     *
     * @return
     */
    public List<Node> findArticulationPoints() {
        Random random = new Random();
        int rootIdx = random.nextInt(this.graph.getNodeList().size());
        Node root = this.graph.getNodeList().get(rootIdx);
        this.counts.put(root, 0);
        int numSubTrees = 0;
        for (Node neighbour :
                root.getAdjacentNodes()) {
            if (!this.counts.containsKey(neighbour)) {
                recArtPts(neighbour, 1, root);
                numSubTrees++;
            }
            if (numSubTrees > 1) {
                this.foundArtPoints.add(root);
            }
        }
        return this.foundArtPoints;
    }

    /**
     * Helper method for finding articulation points
     * @param node
     * @param count
     * @param parent
     * @return
     */
    private int recArtPts(Node node, int count, Node parent) {
        this.counts.put(node, count);
        int reachBack = count;
        for (Node neighbour : node.getAdjacentNodes()) {
            if (neighbour == parent) {
                continue;
            }
            if (this.counts.containsKey(neighbour)) {
                reachBack = Math.min(this.counts.get(neighbour), reachBack);
            } else {
                int childReach = recArtPts(neighbour, count++, node);
                reachBack = Math.min(childReach, reachBack);
                if (childReach >= count) {
                    this.foundArtPoints.add(node);
                }
            }
        }
        return reachBack;
    }
}
