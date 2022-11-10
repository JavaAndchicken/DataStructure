package graph.graphTemplate;


/**
 * 图的结构之： 边
 */
public class Edge {
    //权重值
    public int weight;
    //从哪里来
    public Node from;
    //指向哪里去
    public Node to;
    public Edge(int weight,Node from,Node to){
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
