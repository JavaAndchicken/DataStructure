package graph.graphTemplate;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图的结构之： 图
 */
public class Graph {
    //结点集合
    public HashMap<Integer,Node> nodes;
    //边集合
    public HashSet<Edge> edges;
    public Graph(){
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
