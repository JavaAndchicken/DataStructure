package hanoi;

/**
 * N层汉诺塔问题
 */
public class Hanoi {

    //暴力递归

    /**
     * 参数：  N层汉诺塔    源头   目标   辅助
     */
    public static void process(int N, String from, String target, String help) {
        //base case  递归出口
        //如果此时仅剩下一个盘了  直接从from移动到target即可
        if (N == 1) {
            System.out.println("从" + from + "移动 1 盘，到" + target + "上");
        } else {
            //如果有多个盘 将 1 -- n-1 个盘从from移动到help上
            process(N - 1, from, help, target);
            //将第N个盘移动到target上
            System.out.println("从" + from + "移动第" + N + "个盘到" + target + "上");
            //将help上的n-1个盘移动到target上  此时target的底部已经有了第N个盘了
            process(N - 1, help, target, from);
        }
    }

    public static void main(String[] args){
        Hanoi.process(3,"左","右","中");
    }

}
