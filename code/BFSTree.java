/**
     * @author Sean Rowe
     * December 15th, 2020
     * MSCI 240 Final Project
     */

import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

/**
 * This class is meant to implement a Breadth-First Search Tree from an 
 * undirected graph so that information (such as the shortest distance to
 * another node) can be found.
 *
 */
public class BFSTree {
    private IntGraphList graph;
    private int source;
    private Map<Integer, Integer> distances;
    private Map<Integer, Integer> parents;

    /**
     * Conducts a Breadth-First search given a source node, and puts
     * together information for the BFS tree
     * 
     * @param graph - an undirected graph representing the connection
     * between actors
     * @param source - the actor ID to start the search from
     */
    public BFSTree(IntGraphList graph, int source) {
        this.graph = graph;
        this.source = source;
        this.distances = new HashMap<Integer, Integer>();
        this.parents = new HashMap<Integer, Integer>();
        
        for (int node : graph.getVertices()) {
            distances.put(node, -1);
            parents.put(node, null);
        }
        
        Queue<Integer> q = new LinkedList<>();
        distances.put(source, 0);
        q.add(source);
        while (!q.isEmpty()) {
            int u = q.remove();
            for (int v : graph.getAdjacencyList(u)) { 
                if (distances.get(v) == -1) {
                    distances.put(v, distances.get(u) + 1);
                    parents.put(v, u);
                    q.add(v);
                }
            }
        }
    }
    
    /**
     * Returns the distances to each actor from the source actor
     * 
     * @return list of the distances to all other actors in the data set
     */
    public List<Integer> getDistances() {
    	List<Integer> distancesList = new ArrayList<>();
    	for(int key : this.distances.keySet()) {
    		distancesList.add(this.distances.get(key));
    	}
    	return distancesList;
    }
    
    /**
     * Returns the distances to each actor connected to the source actor
     * 
     * @return list of the distances to all other actors connected to the
     * source actor in the data set
     */
    public List<Integer> getDistancesToConnected() {
    	List<Integer> distancesList = new ArrayList<>();
    	for(int key : this.distances.keySet()) {
    		if (this.distances.get(key) > 0) {
    			distancesList.add(this.distances.get(key));
    		}
    	}
    	return distancesList;
    }
    
    /**
     * Finds whether or not the source actor has less than six degrees of
     * separation between them and a majority of actors
     * 
     * @return boolean representing whether or not the source actor has less
     * than six degrees of separation between them and a majority of actors
     */
    public boolean kevinBacon() {
    	int lessThanSix = 0;
    	int greaterThanSix = 0;
    	for(int key : this.distances.keySet()) {
    		if (this.distances.get(key) <= 7 && this.distances.get(key) > 0) {
    			lessThanSix += 1;
    		} else if (this.distances.get(key) != 0) {
    			greaterThanSix += 1;
    		}
    	}
    	System.out.println(lessThanSix);
    	System.out.println(greaterThanSix);
    	return (lessThanSix > greaterThanSix);
    }

    public int getDistanceTo(int v) {
    	return distances.get(v);
    }

    public int getParent(int v) {
    	return parents.get(v);
    }

    public IntGraphList getGraph() {
        return graph;
    }

    public int getSource() {
        return source;
    }
}
