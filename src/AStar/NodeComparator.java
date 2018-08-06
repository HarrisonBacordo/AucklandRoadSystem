package AStar;

import java.util.Comparator;

public class NodeComparator  implements Comparator<SearchNode> {


    @Override
    public int compare(SearchNode x, SearchNode y) {
        return Double.compare(x.getCost(), y.getCost());
    }
}
