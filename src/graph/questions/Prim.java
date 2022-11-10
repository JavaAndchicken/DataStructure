package graph.questions;

import graph.graphTemplate.Edge;
import graph.graphTemplate.Graph;
import graph.graphTemplate.Node;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 最小生成树——> P算法
 */
public class Prim {


    //参数： 一个图
    //返回值：返回最小生成树的边的集合
    /*
    思路：边遍历边解锁的方式，随机选一个结点开始，然后解锁他所拥有的边，找到最小的那条，然后看当前边的toNode是否已经
    在结点集合中存在 如果不存在，则添加这条结果边，并把当前结点也添加进结点集合中。
    去找其他结点，然后继续解锁其他结点所拥有的边，找最小的那条进行处理
     */
    //边的比较器  从小到大
    public static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public List<Edge> prim(Graph graph) {
        //用一个集合来存储解锁后的边
        PriorityQueue<Edge> edges = new PriorityQueue<>();
        //结果边的集合
        List<Edge> result = new LinkedList<>();
        //结点集合
        List<Node> nodes = new LinkedList<>();
        //随机选取一个结点
        for (Node node : graph.nodes.values()) {
            //将其拥有的边按照从小到大的顺序存入到边集合中
            //看看这条边上的toNode是否在集合中已经存在
            //如果不存在
            if (!nodes.contains(node)) {
                //集合中新增当前结点
                nodes.add(node);
                //将这个结点的边新增进解锁的边集合中
                for (Edge e : node.edges) {
                    edges.add(e);
                }
            }
            //遍历解锁的边
            while (!edges.isEmpty()) {
                //弹出最小的边
                Edge smaller = edges.poll();
                //将其加入到结点集合中
                nodes.add(smaller.to);
                //将这条边也加入到结果边中
                result.add(smaller);
                //继续去存储这个结点所涉及的边
                for (Edge es : smaller.to.edges) {
                    edges.add(es);
                }
            }
        }
        return result;
    }
}
