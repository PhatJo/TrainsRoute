/*************************************************************************
 *  Author: Josphat Magutt
 *  Date: 07/08/2012
 *  Compilation:  javac DirectedEdge.java
 *  Execution:    java DirectedEdge
 *
 *  Immutable weighted directed edge.
 *
 *************************************************************************/

public class DirectedEdge
{
   private final String v, w;
   private final int weight; //assume all distances are whole numbers
   
   /**
     * Create a directed edge from v to w with given weight.
     */
   public DirectedEdge(String v, String w, int weight)
   {
      this.v = v;
      this.w = w;
      this.weight = weight;
   }
   
   /**
     * Return the vertex where this edge begins.
     */   
   public String from()
   {  return v;  }
   
   /**
     * Return the vertex where this edge ends.
     */
   public String to()
   {  return w;  }
   
   /**
     * Return the weight of this edge.
     */
   public int weight()
   {  return weight; }
   
   /**
     * Return a string representation of this edge.
     */
    public String toString() {
        return v + "->" + w + " " + String.format("%d", weight);
    }
    
    /**
     * Test client.
     */
    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge("A", "B", 5);
        System.out.println(e);
    }
}