package tree;

import java.util.LinkedList;

public class IsSearchBinaryTree {

    public static class TreeNode{
        int val ;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val){
            this.val = val;
        }
    }

    /**
     * 判断一棵树是否为搜索二叉树
     * 方法一：采用中序遍历的思想，先将所有的结点按照中序的顺序放入到集合中，然后将集合里面的元素进行比较，看看是否是一个从小到大的状态，如果是，就是搜索树
     */
    public static boolean isSearchBinaryTree1(TreeNode head){
        if(head == null){
            return true;
        }
        //将所有的结点按照中序的顺序存储到集合中
        LinkedList<TreeNode> nodes = new LinkedList<>();
        inOrder(head,nodes);
        //比较是否为升序  因为list.get()的时候是get对应位置的元素  所以不能再i <= nodes.size()  会越界
        for(int i = 1;i < nodes.size();i++){
            if(nodes.get(i - 1).val >= nodes.get(i).val){
                return false;
            }
        }
        return true;
    }

    //中序遍历所有的结点  并存储到集合中
    public static void inOrder(TreeNode head,LinkedList<TreeNode> nodes){
        //递归出口
        if(head == null){
            return ;
        }
        //先获取左边的
        inOrder(head.left,nodes);
        //将结点添加到集合中
        nodes.add(head);
        //获取右边的结点
        inOrder(head.right,nodes);
    }


    /**
     * 判断一棵树是否为搜索二叉树
     * 方法二：树型DP(Dynamic Programming)
     */
    /**
     * 分析 ：树型DP需要什么？？
     *      需要我能得到结果的条件：
     *          以判断搜索树为例：
     *          我需要：
     *              1.左子树是否为搜索树
     *              2.右子树是否为搜索树
     *              3.左子树的最大值是否大于我X(父结点)  (不应大于)
     *              4.右子树的最小值是否大于我X(父结点)  (应大于)
     *          将此条件合并做一个对象，用于判断
     * @param head
     * @return
     */

    public static boolean isSearchBinaryTree2(TreeNode head){
        if(head == null){
            return true;
        }
        //返回判断结果
        return process(head).isSBT;
    }

    //树型DP条件对象
    public static class Info{
        boolean isSBT;
        int max;
        int min;
        public Info(boolean isSBT,int max,int min){
            this.isSBT = isSBT;
            this.max = max;
            this.min = min;
        }
    }

    //调用方法进行处理

    /**
     * 操作：先一直找左子树  获得左子树的信息
     *      再一直找右子树  获得右子树的信息
     *      最终来做条件的判断
     * @param head
     * @return
     */
    public static Info process(TreeNode head){
        //递归出口
        if(head == null){
            return null;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        //找到最左之后 赋值左右两边各自的最大最小值
        //暂时让最大值为父结点
        int max = head.val;
        //如果左子树信息存在的话 找出父结点和他哪个大
        if(leftInfo != null){
            max = Math.max(max,leftInfo.max);
        }
        //如果右子树信息存在的话  找出最大值
        if(rightInfo != null){
            max = Math.max(max,rightInfo.max);
        }
        //找到最小值  暂时等于父结点
        int min = head.val;
        //同理  如果左子树信息存在的话  找出父结点和它哪个小
        if(leftInfo != null){
            min = Math.min(min,leftInfo.min);
        }
        if(rightInfo != null){
            min = Math.min(min, rightInfo.min);
        }
        //进行比较  判断是否为搜索树
        //判断标识  默认为true(因为第一个判断的肯定是最后一个左子树叶子结点，让其默认为搜索树)
        boolean isSBT = true;
        /**
         * 注意：下面被注释掉的写法是我的错误之处。
         *  就是如果都这样判断的话，结果无论怎样都会是false  因为第一个肯定是叶子结点进来判断
         *  如果这样一判断，肯定会变成false，所以这四个条件应该分开判断  并且每一个判断上面都要加上一个
         *  当前子树的信息是否为空（这样才能合理的避开第一个结点）。望借鉴。
         */
        /*//如果不是搜索树（isSBT被改了）  或者左边子树的最大值不小于父结点,更改isSBT
        if(!isSBT || leftInfo.max >= head.val){
            isSBT = false;
        }
        //同理 如果不是搜索树  或者右边子树的最小值不大于父结点 更改isSBT
        if(!isSBT || rightInfo.min <= head.val){
            isSBT = false;
        }*/
        //判断一：如果leftInfo 不是空 但是  他的isSBT已经变了 那就isSBT改为false（因为最后返回的对象里面存的是isSBT）
        if(leftInfo != null && !leftInfo.isSBT){
            isSBT = false;
        }
        //判断二：如果rightInfo 不是空  但是 isSBT变了 同样的
        if(rightInfo != null && !rightInfo.isSBT){
            isSBT = false;
        }
        //判断三：如果leftInfo 不是空 但是  左子树的最大值大于等于父结点的值了  不可以
        if(leftInfo != null && leftInfo.max >= head.val){
            isSBT = false;
        }
        //判断四：如果rightInfo 不是空 但是 右子树的最小值小于等于父结点的值了 不可以
        if(rightInfo != null && rightInfo.min <= head.val){
            isSBT = false;
        }
        //返回信息
        return new Info(isSBT,max,min);
    }




    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (isSearchBinaryTree1(head) != isSearchBinaryTree2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
