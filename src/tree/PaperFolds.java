package tree;

public class PaperFolds {


    public static void main(String[] args) {
        PaperFolds.printFolds(4);
    }

    /**
     * 折纸条  将纸条折叠N次  从上到下打印纸条折痕顺序（凹或者凸）
     */
    //思路：通过折纸条可以发现  折痕的顺序是二叉树的中序遍历  左树为凹的  右树为凸的
    public static void printFolds(int N) {
        process(1, N, true);
        System.out.println();
    }

    //折叠： i 代表当前是第几层  N代表总层数 用于控制停止   down代表当前是凹还是凸  true->凹 false->凸
    public static void process(int i, int N, boolean down) {
        //递归出口
        if (i > N) {
            return;
        }
        process(i + 1, N, true);
        System.out.print(down ? "凹\t" : "凸\t");
        process(i + 1, N, false);
    }
}
