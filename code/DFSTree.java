/**
     * @author Sean Rowe
     * December 15th, 2020
     * MSCI 240 Final Project
     */

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * This class is meant to implement a Depth-First Search Tree from an 
 * undirected graph so that information (such as the number of connected
 * components, maximum component size, etc.) can be found
 *
 */
public class DFSTree {
    private IntGraphList graph;
    private ArrayList<Integer> componentSizes;
    private Map<Integer, Integer> parents;
    private Map<Integer, Boolean> visited;

    /**
     * Puts together information for the DFS tree
     * 
     * @param graph - an undirected graph representing the connection
     * between actors
     */
    public DFSTree(IntGraphList graph) {
        this.graph = graph;
        this.visited = new HashMap<Integer, Boolean>();
        this.parents = new HashMap<Integer, Integer>();
        this.componentSizes = new ArrayList<Integer>();
        
        for (int vertex : this.graph.getVertices()) {
            visited.put(vertex, false);
        }
        
        for (int vertex : this.graph.getVertices()) {
            if (!visited.get(vertex)) {
                int componentSize = dfsVisit(vertex, visited, parents);
                this.componentSizes.add(componentSize);
            }
        }
    }
    
    /**
     * Conducts a Depth-First search given a starting node, and puts
     * together information for the DFS tree
     * 
     * @param u - starting vertex for the current component
     * @param visited - HashMap to keep track of which vertices
     * 					have been visited
     * @param parents - HashMap to keep track of the parent for
     * 					each vertex
     * 
     * @return number of vertices connected to the vertex u
     */
    private int dfsVisit(int u, Map<Integer, Boolean> visited,
                          Map<Integer, Integer> parents) {
        Set<Integer> components = new HashSet<Integer>();
        Stack<Integer> verticesToVisit = new Stack<Integer>();
        verticesToVisit.push(u);
        visited.put(u, true);
        while (!verticesToVisit.empty()) {
            int v = verticesToVisit.pop();
            components.add(v);
            for (int w : graph.getAdjacencyList(v)) {
                if (!visited.get(w)) {
                    verticesToVisit.push(v);
                    verticesToVisit.push(w);
                    visited.put(w, true);
                    parents.put(w, v);
                    break;
                }
            }
        }
        return components.size();
    }
    
    public IntGraphList getGraph() {
        return graph;
    }

    public int getParent(int v) {
    	return parents.get(v);
    }

    /**
     * Returns a list of the component sizes
     * 
     * @return a list of the component sizes
     */
    public List<Integer> getComponentSizes() {
        return this.componentSizes;
    }
}
