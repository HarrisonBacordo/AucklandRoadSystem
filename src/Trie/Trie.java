package Trie;

import Graph.Road;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /**
     *
     * @param word
     * @param road
     */
    public void add(char[] word, Road road) {
        TrieNode current = root;

        for (char c : word) {
            if (!current.getChildren().containsKey(c)) {
                current.addChild(c, new TrieNode());
            }
            current = current.getChildren().get(c);
        }
        current.addRoad(road);
    }

    /**
     *
     * @param word
     * @return
     */
    public List<Road> get(char[] word) {
        TrieNode current = root;

        for (char c : word) {
            if (!current.getChildren().containsKey(c)) {
                return null;
            }
            current = current.getChildren().get(c);
        }
        return current.getRoads();
    }

    /**
     *
     * @param prefix
     * @return
     */
    public List<Road> getAll(char[] prefix) {
        List<Road> results = new ArrayList<>();
        TrieNode current = this.root;
        for (char c : prefix) {
            if (!current.getChildren().containsKey(c)) {
                return null;
            }
            current = current.getChildren().get(c);
        }
        getAllFrom(root, results);
        return results;
    }

    /**
     *
     * @param node
     * @param results
     */
    public void getAllFrom(TrieNode node, List<Road> results) {
        results.addAll(node.getRoads());

        for (TrieNode child : node.getChildren().values()) {
            getAllFrom(child, results);
        }
    }
}
