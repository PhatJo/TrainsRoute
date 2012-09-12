 /********************************************************************
 *  Author: Josphat Magutt
 *  Date: 07/08/2012
 *  Compilation:  javac TrainsClient.java
 *  Execution:    java TrainsClient
 *  Dependencies: TrainsDigraph.java, DirectedEdge.java, TrainsSP.java
 *                TrainsBFS.java
 * 
 *  Trains Problem Test client. Finds the distance of a given path,
 *  the number of trips between any two vertices and the shortest 
 *  paths between two vertices
 * 
 * *******************************************************************/
import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.io.File;

public class TrainsClient{    
    
    /* print the distance of this path in the graph, or report if such 
     * a path does not exist */
    public String printDistance(TrainsDigraph graph, String path)
    {
        String [] vertices = path.split("-");        
        int path_dist = graph.findDistance(vertices);
        if (path_dist < 0)
        {
            return "NO SUCH PATH";
        }
        else 
        {
            return Integer.toString(path_dist);
        }
    }
     
    /* print line delimiter between outputs */
    public void formatOutput(int quizno)
    {
        System.out.println("\n----------Question " +quizno+ " Ouptput-----------\n");
    }
 
    /* trains problem test client */ 
    public static void main(String[] args) {
        
        Scanner inputstream = null;
        try {
            inputstream = new Scanner(System.in);
        } 
        catch (Exception e) {
            System.out.println(e);
        }
        
        // parse the input graph from a comma-delimited file 
        inputstream.useDelimiter("\\s*,\\s*"); 
        LinkedList<String> inputlist = new LinkedList<String>();
        
        // prompt user input
        System.out.println("Please enter the text filename containing the graph: ");        
        String filename = inputstream.nextLine();
        Scanner fscanner = null;
        try {
            fscanner = new Scanner(new File(filename));
        } 
        catch (Exception e) 
        {             
            System.out.println(e);
        }
        
        // read all vertices/edges from the graph
        while (fscanner.hasNext())
        {
            inputlist.add(fscanner.next());
        }
        
        int inputsize = inputlist.size(); // size of the graph 
        String [] inputarray = inputlist.toArray(new String[inputsize]);
        TrainsClient client = new TrainsClient ();
        TrainsDigraph graph = new TrainsDigraph(inputsize);        
        TrainsBFS trainsbfs = null;
        
        // create graph 
        for (int i = 0; i < inputsize; i++)
        {
            String vertex1 = Character.toString(inputarray[i].charAt(0));
            String vertex2 = Character.toString(inputarray[i].charAt(1));
            int weight = Integer.parseInt(Character.toString(inputarray[i].charAt(2))); 
            
            DirectedEdge e = new DirectedEdge(vertex1, vertex2, weight);
            graph.addEdge(e);
        }
        
        System.out.println("Enter question number, e.g 2");
        int quizno = Integer.parseInt(inputstream.nextLine());
        String src = "", dest = "";
        
        // handle problem differentially 
        switch (quizno)
        {
            case 1: 
            case 2: 
            case 3:
            case 4:
            case 5:
                System.out.println("Enter path, e.g. A-B-C");
                String path = inputstream.nextLine();
                String path_length = client.printDistance(graph, path);
                client.formatOutput(quizno);
                System.out.println("Length of path " +path+ " = " +path_length);
                break;
            case 6: 
                System.out.println("Enter origin city");
                src = inputstream.nextLine();
                System.out.println("Enter destination city");
                dest = inputstream.nextLine();
                client.formatOutput(quizno);
                trainsbfs= new TrainsBFS (graph, src, dest, quizno);
                System.out.print("Number of routes between " +src+ " and " +dest);
                System.out.println(" = " +trainsbfs.getTripCount());
                break;
                
            case 7: 
                System.out.println("Enter origin city");
                src = inputstream.nextLine();
                System.out.println("Enter destination city");
                dest = inputstream.nextLine();
                client.formatOutput(quizno);
                trainsbfs= new TrainsBFS (graph, src, dest, quizno);
                System.out.print("Number of routes between " +src+ " and " +dest);
                System.out.println(" = " +trainsbfs.getTripCount());
                break;
            case 8:
            case 9: 
                System.out.println("Enter origin, e.g A");
                src = inputstream.nextLine();
                System.out.println("Enter destination, e.g C");
                dest = inputstream.nextLine();
                client.formatOutput(quizno);
                System.out.print("Shortest route between " +src+ " and " +dest+ " : ");
                TrainsSP sp = new TrainsSP(graph, src, dest);
                System.out.println(sp.pathTo(dest)+ "; length = " +sp.distTo(dest));
                break;
            case 10: 
                System.out.println("Enter origin city");
                src = inputstream.nextLine();                
                System.out.println("Enter destination city");
                dest = inputstream.nextLine();
                client.formatOutput(quizno);
                trainsbfs= new TrainsBFS (graph, src, dest, quizno);
                System.out.print("Number of routes with max. distance = 30 " +
                                     "between " +src+ " and " +dest);
                System.out.println(" = " +trainsbfs.getTripCount());
                break;            
        } 

    }
}