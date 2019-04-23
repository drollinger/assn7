/********************************
 * Provided class to track nodes
 ********************************/
import java.util.LinkedList;

public class GraphNode {
    public int nodeID;
    public LinkedList<EdgeInfo> succ;

    public GraphNode(int nodeID) {
        this.nodeID = nodeID;
        this.succ = new LinkedList<>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeID);
        for (EdgeInfo e:succ){
            sb.append(e.toString());
        }
        sb.append("\n");
        return sb.toString();
    }

    public void addEdge(int v1, int v2) {
        succ.addFirst(new EdgeInfo(v1,v2));
    }
}
