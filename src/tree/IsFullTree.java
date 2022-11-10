package tree;

public class IsFullTree {

    /**
     * 判断一棵树是不是满二叉树
     * 方法一：树型DP  收集树的高度和结点数信息   当此树满足： (2 ^ 高度) - 1 = 节点数  就是满树
     */
    public static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        public TreeNode(int val){
            this.val =val;
        }
    }

    //对象信息
    public static class Info1{
        int height;
        int nodes;
        public Info1(int height,int nodes){
            this.height = height;
            this.nodes = nodes;
        }
    }
    public static boolean isFull1(TreeNode head){
        if(head == null){
            return true;
        }
        Info1 info1 = isFull1Process(head);
        // 1 << N == 2 ^ N   把1转换成2进制  向左移动N位，再把移动后的结果转成10进制 就是2 ^ N的结果了
        return (1 << info1.height) - 1 == info1.nodes;
    }

    public static Info1 isFull1Process(TreeNode head){
        //递归出口  如果是空的 返回对象，数据为0
        if(head == null){
            return new Info1(0,0);
        }
        Info1 leftInfo = isFull1Process(head.left);
        Info1 rightInfo = isFull1Process(head.right);
        //获得高度
        int height = Math.max(leftInfo.height,rightInfo.height) + 1;
        //获得结点数 左子树结点数加上右子树结点数再加上父结点等于这棵树的节点数
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new Info1(height,nodes);
    }


    /**
     * 判断一棵树是否为满二叉树
     * 树型DP 方法二：左边是满树 右边也是满树 且左右子树的高度相等 则整个树就是满树
     */

    //信息结构
    public static class Info2{
        boolean isFull;
        int height;
        public Info2(boolean isFull,int height){
            this.isFull = isFull;
            this.height = height;
        }
    }

    public static boolean isFull2(TreeNode head){
        if(head == null){
            return true;
        }
        return isFull2Process(head).isFull;
    }

    public static Info2 isFull2Process(TreeNode head){
        //如果是空 默认是满的
        if(head == null){
            return new Info2(true,0);
        }
        //找左右结点的信息
        Info2 leftInfo = isFull2Process(head.left);
        Info2 rightInfo = isFull2Process(head.right);
        //找高度
        int height = Math.max(leftInfo.height,rightInfo.height) + 1;
        //找是否满树 左右都满 并且左右高度相等
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        return new Info2(isFull,height);
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
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (isFull1(head) != isFull2(head)) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }
}
