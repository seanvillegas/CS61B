package src;

public class Dijkstra {
    EdgeWeightedGraph g;

    public static void main(String[] args) {
        EdgeWeightedGraph g = new EdgeWeightedGraph(5);
        g.addEdge(new Edge(0, 1, 7));
        g.addEdge(new Edge(0, 2, 3));
        g.addEdge(new Edge(1, 3, 4));
        g.addEdge(new Edge(2, 3, 2));
        g.addEdge(new Edge(3, 4, 1));
        System.out.println(g);
    }

}
