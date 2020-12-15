import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class DFSTree {
    private IntGraphList graph;
    private ArrayList<Integer> componentSizes;
    private Map<Integer, Integer> parents;
    private Map<Integer, Boolean> visited;

    public DFSTree(IntGraphList graph) {
        this.graph = graph;
        this.visited = new HashMap<Integer, Boolean>();
        this.parents = new HashMap<Integer, Integer>();
        
        for (int vertex : this.graph.getVertices()) {
            visited.put(vertex, false);
        }
        
        for (int vertex : this.graph.getVertices()) {
            if (!visited.get(vertex)) {
                int componentSize = dfsVisit(vertex, visited, parents);
                this.componentSizes.add(componentSize);
            }
        }
        
        /*
         * TODO: complete this method with the depth-first
         * search algorithm, modifying it to be able to
         * provide information about parents and component
         * sizes
         */
    }
    
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
    
    
    // TODO: create the DFSTree constructor

    public IntGraphList getGraph() {
        return graph;
    }

    public int getParent(int v) {
        // TODO: complete this method
        return 0;
    }

    public List<Integer> getComponentSizes() {
        return this.componentSizes;
    }
}
