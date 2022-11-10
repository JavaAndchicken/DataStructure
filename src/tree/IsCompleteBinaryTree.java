package tree;

import java.util.LinkedList;
import java.util.Queue;

public class IsCompleteBinaryTree {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 判断一棵树是否为完全二叉树
     * 方法一：非递归实现 采用宽度优先遍历的思想  从左到右依次检查是否满了
     * 如果遇到有节点的左右孩子不完全  则要判断他后面遇到的结点是否都是叶子结点
     * 如果不是叶子结点 就不是完全树
     */
    public static boolean isCBT1(TreeNode head) {
        if (head == null) {
            return true;
        }
        //使用一个队列来存储所有的结点
        Queue<TreeNode> nodes = new LinkedList<>();
        //左右孩子的指针 用于判断是否存在  代表的是当前结点的左右孩子而不是当前结点
        TreeNode leftPointer = null;
        TreeNode rightPointer = null;
        nodes.offer(head);
        //标志 用于判断是否遇到了不双全的结点  默认没有遇到
        boolean isNotComplete = false;
        while (!nodes.isEmpty()) {
            //取出头结点
            head = nodes.poll();
            leftPointer = head.left;
            rightPointer = head.right;
            //如果遇到了孩子不双全的结点 判断他后面的所有节点是否孩子结点 如果不是  就返回false
            //遇到左右孩子不双全结点，但是他的孩子还有左孩子或者右孩子    或者    他的孩子的左孩子为空 但是 右孩子却存在
            if ( (isNotComplete && (leftPointer != null || rightPointer != null) ) || (leftPointer == null && rightPointer != null) ) {
                return false;
            }
            //如果左右孩子存在 则存到队列中
            if (leftPointer != null) {
                nodes.offer(leftPointer);
            }
            if (rightPointer != null) {
                nodes.offer(rightPointer);
            }
            //如果遇到结点的左右孩子不双全 设置标志
            if (leftPointer == null || rightPointer == null) {
                isNotComplete = true;
            }
        }
        return true;
    }


    /**
     * 判断一颗树是否为完全二叉树
     * 方法二：树型DP 收集数据
     * 需要哪些数据？？
     * 1.左右两边是否是满二叉树
     * 2.左右两边是否为完全二叉树
     * 3.左右两边的高度
     */

    //存储信息的实体内部类
    public static class Info {
        boolean isFull;
        boolean isCBT;
        int height;

        public Info(boolean isFUll, boolean isCBT, int height) {
            this.isFull = isFUll;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    public static boolean isCBT2(TreeNode head) {
        if (head == null) {
            return true;
        }
        //调用树型DP处理方法  收集信息
        return process(head).isCBT;
    }

    public static Info process(TreeNode head) {
        //递归出口
        if (head == null) {
            return new Info(true, true, 0);
        }
        //找到最左结点
        Info leftInfo = process(head.left);
        //找到最左结点的最右结点
        Info rightInfo = process(head.right);
        //获取每一颗子树的高度 左右结点的最大高度 加上自身（1）
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        //得到当前子树是否为满树
        //如果 左边是满的 并且 右边是满的  并且左右高度相等  则当前子树是满的
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        //初始化 isCBT为false   然后在判断结果里面更改是否为完全二叉树
        boolean isCBT = false;
        //分析是否为完全二叉树
        //情况一：左边为满树 右边为满树 且左右两边高度相等  是完全树
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isCBT = true;
            //情况二：左边是完全树  右边是满树  但是要求右边的树的高度要比左边的少1
        } else if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
            //情况三：左边要是满的 右边也是满的  但是！  右边的高度必须比左边的少1  因为：左边可能是一个很高的满树 而 右边可能是一颗很低的树
        } else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            isCBT = true;
            //情况四： 左边是满的  右边是完全的  两边高度相等的话 也是完全树
        } else if (leftInfo.isFull && rightInfo.isCBT && rightInfo.height == leftInfo.height) {
            isCBT = true;
        }
        return new Info(isFull, isCBT, height);
    }




    // for test
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }



}
