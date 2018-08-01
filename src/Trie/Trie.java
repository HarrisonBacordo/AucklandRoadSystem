package Trie;

import Graph.Road;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Trie data structure used to implement a search box autocomplete
 */
public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /**
     * Adds the given word and road to the Trie
     *
     * @param word - word to add
     * @param road - road to add
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
     * Gets a list of roads that match a given word
     *
     * @param word - word of interest
     * @return - List of roads matching the word
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
     * Gets all the roads that contain the given prefix
     *
     * @param prefix - prefix of interest
     * @return - all roads having a matching prefix
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
        getAllFrom(current, results);
        return results;
    }

    /**
     * Gets all of the nodes below this node
     *
     * @param node    - root node
     * @param results - collection to dump all the roads into
     */
    public void getAllFrom(TrieNode node, List<Road> results) {
        results.addAll(node.getRoads());

        for (TrieNode child : node.getChildren().values()) {
            getAllFrom(child, results);
        }
    }
}
