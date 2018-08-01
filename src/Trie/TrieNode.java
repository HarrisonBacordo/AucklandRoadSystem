package Trie;

import Graph.Road;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a node within the Trie data structure
 */
public class TrieNode {
    private List<Road> roads;
    private HashMap<Character, TrieNode> children;

    public TrieNode() {
        this.roads = new ArrayList<>();
        this.children = new HashMap<>();
    }

    public List<Road> getRoads() {
        return this.roads;
    }

    public HashMap<Character, TrieNode> getChildren() {
        return this.children;
    }

    /**
     * Adds a child node to this node
     *
     * @param ch   - name of child
     * @param node - child node
     */
    public void addChild(Character ch, TrieNode node) {
        children.put(ch, node);
    }

    /**
     * Adds a road to this node
     *
     * @param road - road to add
     */
    public void addRoad(Road road) {
        this.roads.add(road);
    }
}


