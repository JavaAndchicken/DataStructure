package graph.questions;

import graph.graphTemplate.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 宽度优先遍历图
 */
public class BFSGraph {
//    方法设计分析：
//    参数：从图中的某一个结点开始遍历，所以只需要传入图中的某一个结点
//    返回值：看具体需求，当前方法仅进行打印操作 所以不需要返回值
    public void BFS(Node start){
        if(start == null){
            return ;
        }
        //使用队列来存储遍历到的结点
        Queue<Node> queue = new LinkedList<>();
        //使用一个集合来判断是否有重复的结点
        Set<Node> set = new HashSet<>();
        //将头结点放入到队列和集合中
        queue.offer(start);
        set.add(start);
        //遍历队列
        while(!queue.isEmpty()){
            //取出队头元素
            start = queue.poll();
            //为什么要在取出元素的时候打印？？
            //因为此队列中的同一个元素只存储一次，不会出现重复  与DFS不同
            System.out.println(start.value);
            //遍历当前结点指向的所有节点
            for(Node cur : start.nexts){
                //如果当前结点指向的每个结点不在集合中，则将其放入集合和队列中
                if(!set.contains(cur)){
                    set.add(cur);
                    queue.add(cur);
                }
            }
        }
    }

}
