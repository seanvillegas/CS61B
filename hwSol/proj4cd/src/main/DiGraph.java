package main;

import edu.princeton.cs.algs4.Bag;

/**
 * The skeleton of this code was written by Kevin Wayne et al from Princeton
 * Expanded upon by Sean for project
 */
public class DiGraph {
    // instance
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;           // number of vertices in this DiGraph
    private int E;                 // number of edges in this DiGraph
    // Bag linked list structure from princeton
    private Bag<Integer>[] adj;    // adj[v] = adjacency list for vertex v
    private int[] indegree;        // indegree[v] = indegree of vertex v


    // constructor
    public DiGraph(int nVert) {
        if (nVert < 0) {
            throw new IllegalArgumentException("Number of vertices in a DiGraph must be non-negative");
        }
        this.V = nVert;
        this.E = 0;
        indegree = new int[V];
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * Returns the number of vertices in this DiGraph.
     *
     * @return the number of vertices in this DiGraph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this DiGraph.
     *
     * @return the number of edges in this DiGraph
     */
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    // this feels redundant, I dont want to break program if it doesnt meet this criteria
    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * Adds the directed edge v→w to this DiGraph.
     * This means it adds w to v's adjacency list, but does not add v to w's list.
     * @param  v the tail vertex
     * @param  w the head vertex
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        // updates the counter for how many edges point into vertex w. I.e. addEdge(0, 3) => indegree[3] = 1
        indegree[w]++;
        E++;
    }

    /**
     * Returns the vertices adjacent from vertex {@code v} in this DiGraph.
     *
     * @param  v the vertex
     * @return the vertices adjacent from vertex {@code v} in this DiGraph, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the outdegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the indegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    /**
     * Returns the reverse of the DiGraph.
     *
     * @return the reverse of the DiGraph
     */
    public DiGraph reverse() {
        DiGraph reverse = new DiGraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
