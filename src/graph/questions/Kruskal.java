package graph.questions;

import graph.graphTemplate.Edge;
import graph.graphTemplate.Graph;
import graph.graphTemplate.Node;

import java.util.*;

//K算法实现最小生成树（其中的并查集是性能较慢的替代版本）
public class Kruskal {
    //功能内部类
    public static class MyUnion {
        //存储每个小集合的大集合  使用map来存储  key是node   value是node对应的集合
        private HashMap<Node, Set<Node>> mapSet = new HashMap<Node, Set<Node>>();

        //实现几个功能方法
        //1.初始化 将每个结点单独放入一个集合中，然后将他们放入到一个大集合中
        //参数：给我一个图上面的所有节点集合
        public MyUnion(List<Node> allNodes){
            //将所有节点单独放入到一个集合中，然后将他们整体放入到一个大集合中
            for(Node eachNode : allNodes){
                Set<Node> nodeSet = new HashSet<>();
                nodeSet.add(eachNode);
                mapSet.put(eachNode,nodeSet);
            }
        }
        //2.判断两个结点是否在同一个集合中  直接传过来你获取完结点的集合地址
        public boolean isSameSet(Node from,Node to){
            //先获取from和to所在的集合
            Set<Node> fromNode = mapSet.get(from);
            Set<Node> toNode = mapSet.get(to);
            //只需要获取两个结点在mapSet中的位置，然后比较他们的地址即可
            return from == to;
        }
        //3.合并两个结点
        public void union(Node from,Node to){
            //先获取from和to所在的集合
            Set<Node> fromNode = mapSet.get(from);
            Set<Node> toNode = mapSet.get(to);
            //将to中所有的结点都放入到from中  并改变to在map中指向的地址
            for(Node node : toNode){
                fromNode.add(node);
                mapSet.put(node,fromNode);
            }
        }
        //Kruskal算法实现:
        /*
        思路：将所有的结点都单独形成一个小集合，然后放入到一个大集合中，将所有的边也存储到一个从小到大排序的小根堆中
        ，每次找出一条最小边，看看两边的结点是否在同一个集合中，如果不在同一个集合，就将这两个结点放入到同一个集合中，
        再将这条最小边放入到结果集合中，最终返回的这个结果集合中就存储这最小生成树所需要使用的所有最小值边
         */
        //因为是实现最小生成树  所以从边开始找
        //将所有的边按照从小到大的顺序存到一个容器里面  这个容器就使用小根堆 （自己写一个比较器）
        public static class EdgeCompartor implements Comparator<Edge> {
            //o1 - o2  如果是负的 就是o1小 在前面
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight - o2.weight;
            }
        }
        //参数：给一个图  返回值：返回一个最小生成树的边集合
        public List<Edge> kruskal(Graph graph){
            //初始化并查集
            MyUnion myUnion = new MyUnion((List<Node>) graph.nodes.values());
            //先获取所有的边  按照从小到大的顺序存入到小根堆中
            PriorityQueue<Edge> edges = new PriorityQueue<>();
            for(Edge eachEdge : graph.edges){
                edges.add(eachEdge);
            }
            //结果结合
            List<Edge> results = new LinkedList<>();
            //每次循环取出一条边
            while(!edges.isEmpty()){
                Edge edge = edges.poll();
                //获取这条边上的两个节点
                Node fromNode = edge.from;
                Node toNode = edge.to;
                //判断这条边上的两个结点在不在同一个集合中
                //如果不在  将其合并
                if(!isSameSet(fromNode,toNode)){
                    //将这条边放入到结果集合中
                    results.add(edge);
                    //将两个结点放到一起
                    myUnion.union(fromNode,toNode);
                }
            }
            return results;
        }
    }












    /*public static class MySets {
        public HashMap<Node, List<Node>> setMap;

        public MySets(List<Node> nodes){
            for(Node cur : nodes){
                List<Node> set = new ArrayList<>();
                set.add(cur);
                setMap.put(cur,set);
            }
        }

        public boolean isSameSet(Node from ,Node to){
            List<Node> fromNode = setMap.get(from);
            List<Node> toNode = setMap.get(to);
            return fromNode == toNode;
        }

        //把fromNode所在的集合和toNode所在的集合放到一个集合里面(合并)
        public void union(Node from ,Node to){
            List<Node> fromSet = setMap.get(from);
            List<Node> toSet = setMap.get(to);
            //把to中所有的node全加到from里面
            for(Node toNode : toSet){
                fromSet.add(toNode);
                //把toNode的在map中的value值指向fromSet
                setMap.put(toNode,fromSet);
            }
        }

    }

    //升序排列的小根堆  按照边上的权重值来比较
    public static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }

    }

    public static Set<Edge> kruskalMST(Graph graph) {
        //初始化并查集  将每一个结点都单独的装在一个小集合里面
        //然后整体放入到一个大集合
        MySets sets = new MySets((List<Node>) graph.nodes.values());
        // 从小的边到大的边，依次弹出，小根堆！
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        //将所有的边都加入到小根堆中
        for (Edge edge : graph.edges) { // M 条边
            priorityQueue.add(edge);  // O(logM)
        }
        //存储结果的集合
        Set<Edge> result = new HashSet<>();
        //遍历所有的边
        while (!priorityQueue.isEmpty()) { // M 条边
            Edge edge = priorityQueue.poll(); // O(logM)
            //如果当前边上的两个结点不在同一个集合中
            if (!sets.isSameSet(edge.from, edge.to)) { // O(1)
                //则将这条边放入到结果集中
                result.add(edge);
                //将这条边上的两个结点放入到同一个集合中
                sets.union(edge.from, edge.to);
            }
        }
        return result;
    }*/


}
