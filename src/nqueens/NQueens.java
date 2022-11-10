package nqueens;

/**
 * N皇后问题
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * 给定一个整数n，返回n皇后的摆法有多少种。  n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 */
public class NQueens {
    //给定一个N  返回能找出的有效摆法数
    public static int num(int N) {
        if (N < 1) {
            return 0;
        }
        if (N == 1) {
            return 1;
        }
        //记录列的数组  开辟出N个长度
        int[] record = new int[N];
        int result = process(N, 0, record);
        return result;
    }
    //给定N  当前行  以及记录摆入皇后的列数组（按照深度优先进行遍历，不会出现行重复的情况，所以只需要记录列数组）
    public static int process(int N, int curRow, int[] record) {
        //递归出口
        //当列索引和行索引相等 （满了的时候）停止
        if (N == curRow) {
            return 1;
        }
        int result = 0;
        //循环N次  列索引
        for (int j = 0; j < N; j++) {
            //判断 是否有共列 或者共斜线的情况
            if (isValid(record, curRow, j)) {
                //可以存
                //将对应的列索引值存到当前位置上  即 第0行 存入j=0
                record[curRow] = j;
                //继续找别的皇后 并加入解法中
                result += process(N, curRow + 1, record);
            }
        }
        return result;
    }


    //判断是否有共列 共斜线的情况  只需要看 i - 1 之前的那些行  i及其之后的还没有放入过值
    //参数：当前数组   处理到了第几行  当前列值多少
    public static boolean isValid(int[] record, int curRow, int j) {
        //依次查看每一列的每一个值 是否等于 当前位置（因为存储的时候是将对应的位置索引存入到对应的位置）
        for (int k = 0; k < curRow; k++) {
            //条件一：当前这个K位置上的值是不是等于对应的列索引值
            //例如 处理第2行时， 第一行的0位置上存储的就是0  就代表在第二行的0位置上已经共有共行的数了 不能存
            //条件二：是否是同斜线的  计算方法：如果行-行 == 列-列 就代表是统一条斜线上面的
            //如果当前不是同一条斜线上面的 就缩小范围  k代表了行  record[k]就是列
            if (record[k] == j || Math.abs(curRow - k) == Math.abs(record[k] - j)) {
                //不能存
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 15;
        long start = System.currentTimeMillis();
        System.out.println(num(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

    }
}
