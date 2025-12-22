package src;

/**
 * Weighted undirected edge
 */
public class Edge {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        if (vertex == w) return v;
        throw new IllegalArgumentException("Invalid endpoint");
    }

    public String toString() {
        return v + "-" + w + " " + weight;
    }
}
