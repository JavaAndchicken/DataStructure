package tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelTraversal {

    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(){}
        TreeNode(int val){
            this.val = val;
        }

    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> results = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if(root == null){
            return results;
        }
        queue.offer(root);
        //双重循环给大List赋值
        while(!queue.isEmpty()){
            List<Integer> result = new LinkedList<>();
            int count = queue.size();
            //循环每一层的结点个数的次数
            while(count > 0){
                TreeNode curNode = queue.poll();
                result.add(curNode.val);
                /**
                 * 要用从队头取出来的当前元素来当做条件去找他的左右孩子而不是继续找root,root根本就没有移动
                 */
                if(curNode.left != null){
                    queue.offer(curNode.left);
                }
                if(curNode.right != null){
                    queue.offer(curNode.right);
                }
                count --;
            }
            results.add(result);
        }
        return results;
    }
}
