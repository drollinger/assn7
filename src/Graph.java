/***********************************************************************
 * Name: Dallin Drollinger
 * Description: Graph.java is our main function that runs and tests our
 *      functions for shortest ancestral path and outcast.
 *********************************************************************/
import java.io.File;
import java.util.*;

public class Graph {
    private int numVertex;  // Number of vertices in the graph.
    private GraphNode[] G;  // Adjacency list for graph.
    private String graphName;  //The file from which the graph was created.

    // Constructor to initialize a few variables
    public Graph() {
        this.numVertex = 0;
        this.graphName = "";
    }

    /***********************************************
     * Function given to create graph from .txt file
     * @param filename .txt file
     **********************************************/
    public void makeGraph(String filename) {
        try {
            graphName = filename;
            Scanner reader = new Scanner( new File( filename ) );
            System.out.println( "\n" + filename );
            numVertex = reader.nextInt();
            G = new GraphNode[numVertex];
            for (int i = 0; i < numVertex; i++) {
                G[i] = new GraphNode( i );
            }
            while (reader.hasNextInt()) {
                int v1 = reader.nextInt();
                int v2 = reader.nextInt();
                G[v1].addEdge( v1, v2 );
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***************************************************************************
     * Prints the length of the path, the ancestor, and the path itself.
     * @param v1: first vertex
     * @param v2: second vertex
     * @return returns the length of the shortest ancestral path.
     **************************************************************************/
    public int lca(int v1, int v2) {
        PathInfo best = getPath(v1, v2);
        System.out.println( graphName + " Best lca " + v1 + " " + v2 + " Distance: " + best.dist + " Ancestor " + best.pred + " Path: " + best );
        return best.dist;
    }

    /**********************************************************************************
     * Private function to calculate the path. It tracks all the ancestors of
     * v1 and then it finds the least common ancestor shared with v2
     * @param v1: first vertex
     * @param v2: second vertex
     * @return a String with the path separated with spaces and a "|" char after the lca
     ***********************************************************************************/
    private PathInfo getPath(int v1, int v2) {
        int[] nodeGraph1 = new int[numVertex];
        for(int i = 0; i < this.numVertex; i++) {
            nodeGraph1[i] = -1;
        }
        Queue<Integer> nodeQueue1 = new LinkedList<>();
        nodeQueue1.add(v1);
        nodeGraph1[v1] = numVertex + 1;
        int cur;
        while(!nodeQueue1.isEmpty()) {
            cur = nodeQueue1.remove();
            for(EdgeInfo E : this.G[cur].succ) {
                nodeGraph1[E.to] = E.from;
                nodeQueue1.add(E.to);
            }
        }
        int[] nodeGraph2 = new int[numVertex];
        for(int i = 0; i < this.numVertex; i++) {
            nodeGraph2[i] = -1;
        }
        Queue<Integer> nodeQueue2 = new LinkedList<>();
        nodeQueue2.add(v2);
        cur = v2;
        while(!nodeQueue2.isEmpty() && nodeGraph1[cur] < 0) {
            cur = nodeQueue2.remove();

            for(EdgeInfo E : this.G[cur].succ) {
                nodeGraph2[E.to] = E.from;
                nodeQueue2.add(E.to);
            }
        }
        return new PathInfo(nodeGraph1, nodeGraph2, cur);
    }

    /*******************************************************************************
     * function is provided a list of nodes in a graph and returns the node with
     * the largest sum of distances from other nodes.
     * @param nodes: list of nodes to work on
     * @return returns the node with that is considered the outcast
     ******************************************************************************/
    public int outcast(int[] nodes) {
        int[] sums = new int[nodes.length];
        int maxSum = -1;
        int outcast = -1;
        int pathLength;
        System.out.println("\n--Outcast on " + this.graphName + "--");
        for(int i = 0; i < sums.length; i++) {
            sums[i] = 0;
        }
        for(int curNode = 0; curNode < nodes.length - 1; curNode++) {
            for (int otherNode = curNode + 1; otherNode < nodes.length; otherNode++) {
                pathLength = lca(nodes[curNode], nodes[otherNode]);
                sums[curNode] += pathLength;
                sums[otherNode] += pathLength;
            }
        }
        for(int i = 0; i < sums.length; i++) {
            if(sums[i] > maxSum) {
                maxSum = sums[i];
                outcast = nodes[i];
            }
        }
        System.out.println( "The outcast of " + Arrays.toString( nodes ) + " is " + outcast + " with distance sum of " + maxSum);
        return outcast;
    }

    //main function for testing
    public static void main(String[] args) {
        Graph graph1 = new Graph();
        graph1.makeGraph( "digraph1.txt" );
        //System.out.println(graph.toString());
        int[] set1 = {7, 10, 2};
        int[] set2 = {7, 17, 5, 11, 4, 23};
        int[] set3 = {10, 17, 13};

        graph1.lca( 3, 3 );
        graph1.lca( 3, 7 );
        graph1.lca( 5, 6 );
        graph1.lca( 9, 1 );
        graph1.outcast( set1 );

        Graph graph2 = new Graph();
        graph2.makeGraph( "digraph2.txt" );
        //System.out.println(graph2.toString());
        graph2.lca( 3, 24 );

        graph2.outcast( set3 );
        graph2.outcast( set2 );
        graph2.outcast( set1 );
    }
}