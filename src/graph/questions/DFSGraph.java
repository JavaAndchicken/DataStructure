package graph.questions;

import graph.graphTemplate.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 图的深度优先遍历
 */
public class DFSGraph {
//    参数：一个起始结点
//    返回值：此实现为打印操作  根据具体需求设置返回值
    public void DFS(Node start) {
        if (start == null) {
            return;
        }
        //使用栈来操作
        Stack<Node> stack = new Stack<>();
        //使用集合来检查是否有重复结点
        Set<Node> set = new HashSet<>();
        //先放入起始结点
        stack.push(start);
        set.add(start);
        //打印value值
        System.out.println(start.value);
        while (!stack.isEmpty()) {
            //取出栈顶结点
            start = stack.pop();
            //遍历栈顶结点指向的每一个结点
            for (Node cur : start.nexts) {
                //如果当前被指向的结点不在集合中存在  就将当前被指向的结点 和 栈顶结点重新压回栈中
                //注意： 当前被指向的结点要后压入，因为会在下一轮循环中取出使用
                if (!set.contains(cur)) {
                    stack.push(start);
                    stack.push(cur);
                    //将当前被指向结点放入到集合中
                    set.add(cur);
                    //打印结果 为什么要在set后面打印？？不在弹出之后打印
                    //  因为当元素存入到set之后了，证明就是正确的遍历顺序了（相当于此元素已经稳定）
                    //  栈中会重复压入已经弹出的元素，若在弹出之后打印就会重复打印  与BFS不同
                    System.out.println(cur.value);
                    //结束当前循环，看当前被指向的结点指向的下一个节点（实现深度优先）
                    break;
                }
            }
        }
    }
}
