package Trie;

import Graph.Road;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrieNode {
    private List<Road> roads;
    private HashMap<Character, TrieNode> children;

    public TrieNode() {
        this.roads = new ArrayList<>();
        this.children = new HashMap<>();
    }

    /**
     *
     * @param ch
     * @param node
     */
    public void addChild(Character ch, TrieNode node) {
        children.put(ch, node);
    }

    /**
     *
     * @param road
     */
    public void addRoad(Road road) { this.roads.add(road); }


    public List<Road> getRoads() { return this.roads; }

    public HashMap<Character, TrieNode> getChildren() { return this.children; }
}


