package src;

import java.util.ArrayList;
import java.util.List;

/**
 * A minimal weighted, undirected graph suitable for Dijkstra practice.
 */
public class EdgeWeightedGraph {

    private final int V;                           // number of vertices
    private int E;                                 // number of edges
    private List<List<Edge>> adj;                  // adjacency lists


    public EdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("V must be >= 0");
        this.V = V;
        this.E = 0;

        adj = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            adj.add(new ArrayList<>());
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    // Adds an undirected weighted edge v-w
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        validateVertex(v);
        validateVertex(w);

        adj.get(v).add(e);
        adj.get(w).add(e);
        E++;
    }

    // Returns all edges incident to vertex v
    public List<Edge> adj(int v) {
        validateVertex(v);
        return adj.get(v);
    }

    // List of all edges (each only once)
    public List<Edge> edges() {
        List<Edge> list = new ArrayList<>();

        for (int v = 0; v < V; v++) {
            for (Edge e : adj.get(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
            }
        }
        return list;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // For printing the graph
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V + " vertices, " + E + " edges\n");
        for (int v = 0; v < V; v++) {
            sb.append(v + ": ");
            for (Edge e : adj.get(v)) {
                sb.append(e + "  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
