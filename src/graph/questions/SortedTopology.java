package graph.questions;

import graph.graphTemplate.Graph;
import graph.graphTemplate.Node;

import java.util.*;

/**
 * 拓扑排序--->无环有向图
 */
public class SortedTopology {
//    参数： 是排序  所以需要整个图
//    返回值： 返回一个存储排好序结果的集合
    /*
    思路：每一次找到入度为0的结点，将其拿出，并消除他的指向等影响
    方法： 记录当前结点和他的入度值，每次找到一个入度为0的结点，就将其拿出放到另一个地方，然后将其影响消除
     */
    public List<Node> topology(Graph graph) {
        //记录结点和入度数
        Map<Node, Integer> map = new HashMap<>();
        //辅助队列 只有入度为0的结点才能放入
        Queue<Node> inQueue = new LinkedList<>();
        //遍历图中的每一个结点 将其存入到map中
        // nodes.values()  是hashMap的获取全部value值的方法
        for (Node eachNode : graph.nodes.values()) {
            map.put(eachNode, eachNode.in);
            //如果遇到入度为0的结点  将其放入到辅助队列中
            if (eachNode.in == 0) {
                inQueue.offer(eachNode);
            }
        }
        //存储结果
        List<Node> result = new ArrayList<>();
        //从队列中取值进行操作
        while (!inQueue.isEmpty()) {
            //队列中有的都是入度为0的结点 不需要指向其他操作  直接取出队头元素  放入到结果集合中
            Node cur = inQueue.poll();
            result.add(cur);
            //遍历当前结点指向的所有节点
            for (Node to : cur.nexts) {
                //注意： 是从map中获得这个to，消除cur对每一个to的影响  入度减1
                //to.in -= 1 ;  错误写法  这样写是没有意义的，因为每个结点都已经存入到map中
                //map中重新存入相同的key以及被减去1的value（会直接覆盖掉之前的）
                map.put(to, map.get(to) - 1);
                //如果遇到入度减为0的结点了（同样从map中获取入度值）  直接放入到队列中
                if (map.get(to) == 0) {
                    inQueue.offer(to);
                }
            }
        }
        return result;
    }
}



















