package tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FindLowestAncestor {

    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val){
            this.val = val;
        }
    }

    /**
     * 给定一颗二叉树，找出给定两个结点的最近公共结点
     * 方法二：递归加判断（画图理解会更清晰一些）
     */
    //原理：因为每一轮都会将不含有目标结点的子树返回为空，或者左边为空，或者右边为空 所以到最后如果左右两边都不为空，则这个父结点一定是最近的公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
        //边界条件  如果当前结点是空 返回空  如果当前结点是p或q的任意一个，则最近公共祖先就是当前这个结点
        if(root == null || root == p || root == q){
            return root;
        }
        //递归找到每一个结点
        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);
        //如果左右两边都不是空 则这个结点就是最近公共祖先
        if(left != null && right != null){
            return root;
        }
        //每一轮的返回：左右哪个不空返回哪个
        return left == null ? right : left;
    }



    /**
     * 给定一颗二叉树，找出给定两个结点的最近公共结点
     * 方法一：使用一个集合将所有节点存储起来  然后根据这个集合，生成其中一个o1或o2的链路
     * 再去回溯另一个结点，找到在已生成链路中存在的结点，就是两个结点最近公共祖先
     */
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        //使用一个集合存储所有左右子树的结点  存储形式为：key->左孩子结点 value->父结点
        Map<TreeNode,TreeNode> nodes = new HashMap<>();
        //先存储根结点
        nodes.put(root,null);
        //调用方法 递归存储左右孩子
        fillMap(root,nodes);
        //生成p结点的链路 将链路的结点存到集合中（set）
        Set<TreeNode> ps = new HashSet<>();
        TreeNode cur = p;
        //先将当前结点放入到集合中
        ps.add(cur);
        //循环条件：如果当前结点的父结点不为空 就将当前结点存入  因为map中存储的key就是当前结点 值就是父结点 所以可以直接从map中查询结果进行判断
        while(nodes.get(cur) != null){
            //移动cur到上面
            cur = nodes.get(cur);
            //存储当前的cur
            ps.add(cur);
        }
        //回溯查找q  如果遇到了p链路中的结点 就是最近公共结点
        cur = q;
        while(!ps.contains(cur)){
            cur = nodes.get(cur);
        }
        return cur;
    }

    public void fillMap(TreeNode root,Map<TreeNode,TreeNode> nodes){
        if(root.left != null){
            nodes.put(root.left,root);
            fillMap(root.left,nodes);
        }
        if(root.right != null){
            nodes.put(root.right,root);
            fillMap(root.right,nodes);
        }
    }
}
