import java.util.regex.*;

public class PathInfo {
    int dist;
    int pred;
    String path;

    PathInfo(String myPath) {
        String[] paths = myPath.split("\\|");
        String[] path1 = paths[0].split(" ");
        this.pred = Integer.parseInt(path1[path1.length - 1]);
        this.path = myPath.replace('|', ' ');
        this.dist = myPath.replace('|', ' ').split(" ").length - 1;
    }

    public String toString() {
        return this.path;
    }
}
