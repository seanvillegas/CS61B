package main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The skeleton of this code was written by Kevin Wayne et al from Princeton
 * Expanded upon by Sean for project
 */

public class DirectedDFS {
    private boolean[] marked;  // marked[v] = true iff v is reachable from source(s)
    private int count;         // number of vertices reachable from source(s)
    private Set<Integer> visited = new HashSet<>();

    /**
     * Finds the reachable IDs with DFS
     */
    public DirectedDFS(DiGraph graph, List<Integer> idHyponyms) {
        marked = new boolean[graph.V()];
        validateVertex(idHyponyms);

        for (Integer i : idHyponyms) {
            dfs(graph, i);
        }
    }

    private void dfs(DiGraph G, int v) {
        count++;
        marked[v] = true;
        visited.add(v);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    /**
     * Is there a directed path from the source vertex (or any
     * of the source vertices) and vertex
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    public Set<Integer> markedVertices() {
        return visited;
    }

    /**
     * Returns the number of vertices reachable from the source vertex
     * (or source vertices).
     */
    public int count() {
        return count;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    private void validateVertex(List<Integer> idList) {
        for (Integer i : idList) {
            validateVertex(i);
        }
    }

}
