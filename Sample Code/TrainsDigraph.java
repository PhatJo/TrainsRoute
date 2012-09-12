/*************************************************************************
 *  Author: Josphat Magutt
 *  Date: 07/08/2012
 *  Compilation:  javac TrainsDigraph.java
 *  Execution:    java TrainsDigraph
 *  Dependencies: DirectedEdge.java 
 *
 *  An edge-weighted directed graph, implemented using hash maps. The graph 
 *  is represented as a hash map where vertices are keys to a list of 
 *  directed edge values - edges that point from and to a particular vertex
 *  
 *************************************************************************/

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class TrainsDigraph
{
   private int num_vertices = 0, num_edges = 0; 
   /* edges pointing away from this vertex, and edges pointing to this vertex */
   private Map<String, LinkedHashSet<DirectedEdge>> edges_from_map, edges_to_map;
   
   /* create an empty graph with N vertices */
   public TrainsDigraph(int N)
   {
      edges_from_map = new HashMap<String, LinkedHashSet<DirectedEdge>>();
      edges_to_map = new HashMap<String, LinkedHashSet<DirectedEdge>>();
      num_vertices = N;
   }
   
   /**
     * Add the directed edge e to this digraph.
     */
   public void addEdge(DirectedEdge e)
   {
      String v = e.from();
      String w = e.to();
      LinkedHashSet<DirectedEdge> adj_edges_from = edges_from_map.get(v); 
      LinkedHashSet<DirectedEdge> adj_edges_to = edges_to_map.get(w);
      if (adj_edges_from == null) // empty set of edges from vertex v
      {
          // create a new edge
          adj_edges_from =  new LinkedHashSet<DirectedEdge>();
          edges_from_map.put(v, adj_edges_from);
      } 
      adj_edges_from.add(e); // add this edge to the already existing set 
      
      if (adj_edges_to == null) // empty set of edges to vertex v
      {
          adj_edges_to =  new LinkedHashSet<DirectedEdge>();
          edges_to_map.put(w, adj_edges_to);
      } 
      adj_edges_to.add(e); // add this edge to the already existing set
      num_edges += 1;
   }
   
   /* return the number of vertices in this graph */
   public int getNumVertices()
   {
       return num_vertices;
   }
      
   /* return the number of edges in this graph */
   public int getNumEdges()
   {
       return num_edges;
   }
   
   /* return an iterator object containing the edges that from vertex v */
   public Iterable<DirectedEdge> adjEdgesFrom(String v)
   {  
       LinkedHashSet<DirectedEdge> adj_edges_from = edges_from_map.get(v);
       if (adj_edges_from == null)
       {
           return new LinkedList<DirectedEdge>();
       }
       return new LinkedList<DirectedEdge>(adj_edges_from); 
   }
   
   /* return an iterator object containing the edges that to vertex v */
   public Iterable<DirectedEdge> adjEdgesTo(String v)
   {  
       LinkedHashSet<DirectedEdge> adj_edges_to = edges_to_map.get(v);
       if (adj_edges_to == null)
       {
           return new LinkedList<DirectedEdge>();
       }
       return new LinkedList<DirectedEdge>(adj_edges_to); 
   }
   
   /* return an iterator object containing the vertices that vertex v points to */
   public LinkedList<String> adjVertices(String v)
   {
       LinkedList<String> adj_vertices = new LinkedList<String>();
       Iterable<DirectedEdge> adj_edges_from = adjEdgesFrom(v);
       for (DirectedEdge e : adj_edges_from)
       {
           adj_vertices.add(e.to());
       }
       return new LinkedList<String> (adj_vertices);
   }
   
   /* return all vertices in this graph */
   public Set<String> getAllVertices()
   {
       Set<String> union = new HashSet<String>(edges_from_map.keySet());
       union.addAll(edges_to_map.keySet());                      
       return union;
   }
      
   /* find the distance of the path represented by the vertices */
   public Integer findDistance (String [] vertices)
    {        
        int total_dist = 0;
        for (int i = 0; i < vertices.length - 1; i++) 
        {
            String v, w;
            // split string into consecute overlapping vertex pairs
            v = vertices[i];
            w = vertices[i+1];
            boolean isMember = false; // w is a member of v's adjacency list
            Iterable<DirectedEdge> adj_edges_from = adjEdgesFrom(v);
                
            for(DirectedEdge e : adj_edges_from){
                if (e.to().equals(w))
                {
                    total_dist += e.weight();
                    isMember = true;
                }
            }
            if (!isMember)
            {
                return -1;
            }                
        }
        return total_dist;
    }
   
   /**
     * Return a string representation of this graph.
     */
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(num_vertices + " " + num_edges + NEWLINE);
        Set<String> vertices =  getAllVertices();
        
        for (String v : vertices) {
            s.append(v + ": ");            
            for (DirectedEdge e : adjEdgesFrom(v)) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
    
    /**
     * Test client.
     */
    public static void main(String[] args) {
        TrainsDigraph G = new TrainsDigraph(4);
        G.addEdge(new DirectedEdge("A","B",3));
        G.addEdge(new DirectedEdge("B","C",1));
        G.addEdge(new DirectedEdge("C","A",2));
        G.addEdge(new DirectedEdge("A","D",3));
        G.addEdge(new DirectedEdge("D","C",4));
        System.out.println(G);
    }
    
}