/*************************************************************************
 *  Author: Josphat Magutt
 *  Date: 07/08/2012
 *  Compilation:  javac TrainsSP.java
 *  Execution:    java TrainsSP
 *  Dependencies: TrainsDigraph.java IndexMinPQ.java DirectedEdge.java
 *
 *  Implements Dijkstra's Shortest Path Algorithm, modified to compute the
 *  computes the shortest path tree that gives the shortest distance from
 *  a node to itself. Assumes all weights are nonnegative.
 *
 *************************************************************************/
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;

public class TrainsSP {    
    private static final int INFINITY = Integer.MAX_VALUE;
    private int[] distTo;          // distTo[v] = distance  of shortest source->v path
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest source->v path
    private IndexMinPQ<Integer> pq;    // priority queue of vertices
    private Map<Integer, String> key2vertex_map;
    private Map<String, Integer> vertex2key_map;
    private int num_vertices; 
    private String new_vertex, source, dest;
    
    /* perform Dijstra's shortest path computation method between
     * the source and destination vertices*/
    public TrainsSP(TrainsDigraph G, String source, String dest) {
        key2vertex_map = new HashMap<Integer, String>();
        vertex2key_map = new HashMap<String, Integer>();
        this.source = source;
        this.dest = dest;
        
        if (source.equals(dest))
        {
            num_vertices = G.getNumVertices() + 1; // add an extra/new vertex
            handleSourceIsDest(G, source);
        }        
        else 
        { 
            num_vertices = G.getNumVertices(); 
        }
        initializeMaps(G, key2vertex_map, vertex2key_map);
        distTo = new int[num_vertices];
        edgeTo = new DirectedEdge[num_vertices];
        
        // initialize all distances to infinity
        for (int v = 0; v < num_vertices; v++) {
            distTo[v] = INFINITY;
        }
        int src_index = vertex2key_map.get(source);
        distTo[src_index] = 0;

        // relax vertices in order of distance from source
        pq = new IndexMinPQ<Integer>(num_vertices);
        pq.insert(src_index, distTo[src_index]);
        while (!pq.isEmpty()) {
            int min_index = pq.delMin();
            String curr_vertex = key2vertex_map.get(min_index);
            for (DirectedEdge e : G.adjEdgesFrom(curr_vertex))
                relax(e);
        }
    }
    
    /* handles the special case of the shortest path problem when the
     * source and destination vertices are the same */
    public void handleSourceIsDest(TrainsDigraph G, String source)
    {
        Iterable<DirectedEdge> edges_to = G.adjEdgesTo(source);
        new_vertex = "#"; // create a new, 'fictitious' vertex
        String V = "";
        int new_weight = 0; 
        
        /* point all vertices that point to the destination vertex to
         * the new vertex */        
        for (DirectedEdge e : edges_to)
        {
            V = e.from();
            new_weight = e.weight();
            DirectedEdge new_edge = new DirectedEdge(V, new_vertex, new_weight);
            G.addEdge(new_edge);            
        }
    }
    
    /* initialize the mappings between integer and string representations of
     * the graph's vertices. */
    public void initializeMaps(TrainsDigraph G,
                               Map<Integer, String> key2vertex_map, 
                               Map<String, Integer> vertex2key_map)
    {
        Set vertices = G.getAllVertices();
        int i = 0;
        for (Object v : vertices) {
            key2vertex_map.put(i, (String) v);
            vertex2key_map.put((String) v, i);
            i++;
        }
    }
    
    /* relax edge e and update pq if changed */
    private void relax(DirectedEdge e) {
        String V = e.from(), W = e.to();
        int v = vertex2key_map.get(V);
        int w = vertex2key_map.get(W);
        
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.change(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }
    
    /* find the length of shortest path from s to v */
    public double distTo(String V) {
        if (source.equals(dest))
        {
            V = new_vertex;
        }
        int v = vertex2key_map.get(V);
        return distTo[v];
    }

    // is there a path from s to v?
    public boolean hasPathTo(String V) {
        int v = vertex2key_map.get(V);
        return distTo[v] < INFINITY;
    }

    /* shortest path from s to v as an Iterable, null if no such path */
    public Iterable<DirectedEdge> pathTo(String V) {
        String tmpV = "";
        if (source.equals(dest))
        {
            tmpV = V;
            V = new_vertex;
        }
        int v = vertex2key_map.get(V);
        if (!hasPathTo(V)) return null;
        LinkedList<DirectedEdge> path = new LinkedList<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null;
             e = edgeTo[vertex2key_map.get(e.from())]) {
                 if (e.to().equals(new_vertex))
                 {
                     DirectedEdge ne = new DirectedEdge(e.from(), tmpV, e.weight());
                     path.push(ne);
                 }
                 else {path.push(e); }
        }
        return path;
    }
    
    // test method
    public static void main(String[] args) {
                
        TrainsDigraph G = new TrainsDigraph(4);
        G.addEdge(new DirectedEdge("A","B",3));
        G.addEdge(new DirectedEdge("B","C",1));
        G.addEdge(new DirectedEdge("C","A",2));
        G.addEdge(new DirectedEdge("A","D",3));
        G.addEdge(new DirectedEdge("D","C",4));
        
        String src = "A", dst = "C";
        
        // compute shortest paths
        TrainsSP sp = new TrainsSP(G, src, dst);

        // print shortest path
        Set<String> vertices =  G.getAllVertices();
        for (String v : vertices) {
            if (sp.hasPathTo(v)) {
                System.out.printf("shortest dist from " +src+ " to " +dst+  " : " +sp.distTo(dst));
                if (sp.hasPathTo(v)) {
                    for (DirectedEdge e : sp.pathTo(dst)) {
                        System.out.print(e + "   ");
                    }
                }
                System.out.println();
            }
            else {
                System.out.printf("%s to %s         no path\n", src, dst);
            }
        }
    }
}