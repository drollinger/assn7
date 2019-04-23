/****************************************************************************
 * Provided class that was modified to create pathInfo based off of the node
 * graphs given to it.
 ***************************************************************************/
public class PathInfo {
    int dist;
    int pred;
    String path;

    // Main constructor
    PathInfo(int[] nodeGraph1, int[] nodeGraph2, int ancestor) {
        this.pred = ancestor;
        this.path = path1(nodeGraph1, ancestor) + " " + path2(nodeGraph2, nodeGraph2[ancestor]);
        this.dist = this.path.split(" ").length - 1;
    }

    public String toString() {
        return this.path;
    }

    /*******************************************************************************
     * Formats the list path to the least common ancestor into a string
     * @param l1: list of elements in the path
     * @param node: the current node that is being worked on in the list
     * @return returns the string of the path
     *******************************************************************************/
    private String path1(int[] l1, int node) {
        if(l1[node] > l1.length) {
            return Integer.toString(node);
        }
        else {
            return path1(l1, l1[node]) +  " " + node;
        }
    }

    /*******************************************************************************
     * Formats the list path to the least common ancestor into a string
     * @param l2: list of elements in the path
     * @param node: the current node that is being worked on in the list
     * @return returns the string of the path
     *******************************************************************************/
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

}
