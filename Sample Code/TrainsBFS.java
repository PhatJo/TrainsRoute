/*************************************************************************
 *  Author: Josphat Magutt
 *  Date: 07/08/2012
 *  Compilation:  javac TrainsBFS.java
 *  Execution:    java TrainsBFS
 *  Dependencies: TrainsDigraph.java, DirectedEdge.java
 *
 *  Finds paths between two vertices, the source and destination. Implements
 *  the breadth-first algorithm, modified to handle different conditions
 *  depending on the problem presented
 *************************************************************************/

import java.util.Queue;
import java.util.LinkedList;

public class TrainsBFS {
    
    private int num_vertices; // number of vertices in the graph
    private int hop_count; // number of hops (or stops) in a path
    private int trip_count; // number of trips between to vertices
    private static final int MAX_DIST = 30; // maximum distance for question 10
    private String source, dest; // source and destination vertices to execute BFS
    int quizno; // number of the problem/question
    
    // execute exhaustive search from a single source
    public TrainsBFS(TrainsDigraph G, String source, String dest, int quizno) {
        num_vertices = G.getNumVertices();
        this.source = source;
        this.dest = dest;
        this.quizno = quizno;
        
        // queue of paths that have been traversed so far
        LinkedList<String> paths = new LinkedList<String>();
        paths.add(source);
        trainsbfs(G, paths);
    }
    
    // BFS from single source    
    public void trainsbfs(TrainsDigraph G, LinkedList<String> paths)
    {        
        int path_dist = 0; // length of the current path
        String curr_path = null; // sequence of vertices in the current path
        
        while (paths.size() > 0)
        {
            curr_path = paths.remove(); // dequeue the current path 
            String [] tmp_array = curr_path.split(""); // convert to an array
            String [] curr_path_array = new String[tmp_array.length-1];
            boolean exit_condition = false, print_condition = false;
            int curr_path_len = curr_path_array.length;
            
            // safe copy all elements
            for (int i = 0; i < curr_path_len; i++)
            {
                curr_path_array[i] = tmp_array[i+1];
            }
            
            // the least recently inserted vertex
            String curr_vertex = curr_path_array[curr_path_len-1]; 
            LinkedList<String> adjvertices = G.adjVertices(curr_vertex);            
            path_dist = G.findDistance(curr_path_array);   
            
            // switch BFS stop conditions depending on the problem being solved
            if (quizno == 6)
            {
                hop_count = 3;
                exit_condition = curr_path_len > hop_count + 1; /* hop_count + 1 
                 * (the number of hops includes the final vertex)*/
                print_condition = (curr_path_len <= 4) && (curr_path_len > 1);
            }
            else if (quizno == 7)
            {
                hop_count = 4;
                exit_condition = curr_path_len > hop_count + 1;
                print_condition = curr_path_len == 5; /* hop_count + 1 
                 * (the number of hops includes the final vertex)*/
            }
            else if (quizno == 10)
            {
                exit_condition = path_dist >= MAX_DIST;
                print_condition = (path_dist < MAX_DIST) && (curr_path_len > 1);
            }
            
            if (curr_vertex.equals(dest) && print_condition)
            {
                System.out.println("path " +trip_count+ ": "+curr_path);
                trip_count++;
            }            
            
            if (!exit_condition)
            {
                // examine neighbours of current vertex        
                for (String nextvertex : adjvertices)
                { 
                    // enqueue examined neighbours of the current vertex
                    paths.add(curr_path + nextvertex); 
                }                
            }
        }
    }
    
    public int getTripCount()
    {
        return trip_count;
    }
    
    private void printPath(LinkedList<String> visited, int path_length) {      
            for (String vertex : visited) {
                System.out.print(vertex);
                System.out.print(" ");
            }
            System.out.print("--> length --> " +path_length);
            System.out.println();
    }
    
    // test method
    public static void main(String[] args) {
                
        TrainsDigraph G = new TrainsDigraph(4);
        G.addEdge(new DirectedEdge("A","B",3));
        G.addEdge(new DirectedEdge("B","C",1));
        G.addEdge(new DirectedEdge("C","A",2));
        G.addEdge(new DirectedEdge("A","D",3));
        G.addEdge(new DirectedEdge("D","C",4));
        
        String src = "C", dst = "C";
        
        // compute shortest paths
        TrainsBFS bfs = new TrainsBFS(G, src, dst, 6);

        // print routes between source and destination        
        System.out.print("Number of routes between " +src+ " and " +dst);
        System.out.println(" = " +bfs.getTripCount());
    }
}
