import java.io.File;
import java.util.*;

public class Graph {
    int numVertex;  // Number of vertices in the graph.
    GraphNode[] G;  // Adjacency list for graph.
    String graphName;  //The file from which the graph was created.


    public Graph() {
        this.numVertex = 0;
        this.graphName = "";
    }

    public Graph(int numVertex) {
        this.numVertex = numVertex;
        G = new GraphNode[numVertex];
        for (int i = 0; i < numVertex; i++) {
            G[i] = new GraphNode( i );
        }
    }

    public boolean addEdge(int source, int destination) {
        if (source < 0 || source >= numVertex) return false;
        if (destination < 0 || destination >= numVertex) return false;
        //add edge
        G[source].addEdge( source, destination );
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "The Graph " + graphName + " \n" );

        for (int i = 0; i < numVertex; i++) {
            sb.append( G[i].toString() );
        }
        return sb.toString();
    }

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

    /**                                                                    //TODO: THIS
     * Find the path from v1 to v2 going through anc.
     *
     * @param v1:  first vertex
     * @param v2:  second vertex
     * @param anc: ancestor of v1 and v2
     * @return the path
     */
    public String reportPath(int v1, int v2, int anc) {
        StringBuilder sb = new StringBuilder();
        sb.append( "Wish I knew the path from " + v1 + " " + anc + " " + v2 );

        return sb.toString();
    }

    /**
     * Computes the least common ancestor of v1 and v2, prints the length of the path, the ancestor, and the path itself.    //TODO: THIS
     *
     * @param v1: first vertex
     * @param v2: second vertex
     * @return returns the length of the shortest ancestral path.
     */
    public int lca(int v1, int v2) {
        // Compute lca
        PathInfo best = new PathInfo(getPath(v1, v2));

        System.out.println( graphName + " Best lca " + v1 + " " + v2 + " Distance: " + best.dist + " Ancestor " + best.pred + " Path: " + best );

        return best.dist;
    }

    private String getPath(int v1, int v2) {
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

        return path1(nodeGraph1, cur) + "|" + path2(nodeGraph2, nodeGraph2[cur]);
    }

    private String path1(int[] l1, int node) {
        if(l1[node] > this.numVertex) {
            return Integer.toString(node);
        }
        else {
            return path1(l1, l1[node]) +  " " + node;
        }
    }

    private String path2(int[] l2, int node) {
        if(node < 0) {
            return "";
        }
        if(l2[node] < 0) {
            return Integer.toString(node);
        }
        else {
            return node + " " + path2(l2, l2[node]);
        }
    }

    public int outcast(int[] v) {
        int[] sums = new int[v.length];
        int maxSum = -1;
        int outcast = -1;
        int pathLength;

        System.out.println("\n--Outcast on " + this.graphName + "--");
        for(int i = 0; i < sums.length; i++) {
            sums[i] = 0;
        }

        for(int curNode = 0; curNode < v.length - 1; curNode++) {
            for (int otherNode = curNode + 1; otherNode < v.length; otherNode++) {
                pathLength = lca(v[curNode], v[otherNode]);
                sums[curNode] += pathLength;
                sums[otherNode] += pathLength;
            }
        }

        for(int i = 0; i < sums.length; i++) {
            if(sums[i] > maxSum) {
                maxSum = sums[i];
                outcast = v[i];
            }
        }

        System.out.println( "The outcast of " + Arrays.toString( v ) + " is " + outcast + " with distance sum of " + maxSum);
        return outcast;

    }

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