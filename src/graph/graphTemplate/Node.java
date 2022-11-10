package graph.graphTemplate;

import java.util.HashSet;

/**
 * 图的结构之： 结点
 */
public class Node {
    //结点值
    public int value;
    //结点的入度
    public int in;
    //结点的出度
    public int out;
    //下一个指向的集合
    public HashSet<Node> nexts;
    //当前结点所拥有的边集合
    public HashSet<Edge> edges;
    public Node(int value){
        this.value = value;
        this.in = 0;
        this.out = 0;
        nexts = new HashSet<>();
        edges = new HashSet<>();
    }
}
