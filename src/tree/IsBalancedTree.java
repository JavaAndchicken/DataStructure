package tree;

public class IsBalancedTree {

    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val){
            this.val = val;
        }
    }

    /**
     * 判断一个树是否为平衡二叉树
     * 方法一：使用递归的方式 ，分别找到左右子树的高度 然后进行判断
     */

    public boolean isBanlanced1(TreeNode head){
        if(head == null){
            return true;
        }
        //递归处理过程  传递一个结果参数
        boolean ans = true;
        process(head,ans);
        return ans;
    }

    public int process(TreeNode head,boolean ans){
        //递归出口  如果是空 或者 不是平衡树了  直接返回-1
        if(head == null || !ans){
            return -1;
        }
        //先找到最左节点  并计算他的高度
        int leftHeight = process(head.left,ans);
         //最右结点  计算高度
        int rightHeight = process(head.right,ans);
        //计算他俩的高度差
        if(Math.abs(leftHeight - rightHeight) > 1){
            ans = false;
        }
        //如果局部平衡  返回这个子树的高度
        return Math.max(leftHeight,rightHeight) + 1;
    }


    /**
     * 判断一棵树是否为平衡二叉树
     * 方法二：树型DP
     * 收集信息：
     *   需要哪些信息？
     *      1.左右子树是否平衡
     *      2.左右子树的高度
     *   创建实体类对象Info
     */

    public class Info{
        boolean isBalanced;
        int height;
        public Info(boolean isBalanced,int height){
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public boolean isBalanced2(TreeNode head){
        return process2(head).isBalanced;
    }

    public Info process2(TreeNode head){
        //递归出口  如果是空的 返回对象-->平衡树
        if(head == null){
            return new Info(true,0);
        }
        //递归收集信息  先收集最左结点
        Info leftInfo = process2(head.left);
        //收集最左结点的最右结点
        Info rightInfo = process2(head.right);
        //获取当前子树的高度
        int height = Math.max(leftInfo.height,rightInfo.height) + 1;
        //初始化平衡值
        boolean isBalanced = true;
        //判断左右是否平衡
        if(!leftInfo.isBalanced){
            isBalanced = false;
        }
        if(!rightInfo.isBalanced){
            isBalanced = false;
        }
        //判断高度差的绝对值是否大于1
        if(Math.abs(leftInfo.height - rightInfo.height) > 1){
            isBalanced = false;
        }
        //返回收集的信息
        return new Info(isBalanced,height);
    }


}
