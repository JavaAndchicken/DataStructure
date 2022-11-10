package beforeTree;


/**
 * 求小和问题
 * 题目：在一个数组中，每一个数左边比当前数小的数累加起来，求出一个数组的小和值并将数组排序。
 * 在一个数组中，一个数左边比它小的数的总和，叫该数的小和
 * 所有数的小和累加起来，叫数组小和
 * 例子： [1,3,4,2,5]
 * 1左边比1小的数：没有
 * 3左边比3小的数：1
 * 4左边比4小的数：1、3
 * 2左边比2小的数：1
 * 5左边比5小的数：1、3、4、 2
 * 所以数组的小和为1+1+3+1+1+3+4+2=16
 * 给定一个数组arr，求数组小和
 */



public class SmallSum {

    public static void main(String[] args){
        int[] arr = new int[]{1,3,4,2,5,3,54,6546};
        System.out.println(smallSum(arr));
    }

    //注：递归的计算顺序也就是二叉树的后序遍历
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    // arr[L..R]既要排好序，也要求小和返回
    // 所有merge时，产生的小和，累加
    // 左 排序   merge
    // 右 排序  merge
    // merge
    public static int process(int[] arr, int L, int R) {
        //递归出口
        if(L == R){
            return 0;
        }
        //找出中点位置
        int mid = L + ((R - L) >> 1);
        //在进行右边分区的处理时，发现当前分区只有一个值，即L == R时，是不会去调用merge来处理的
        //将左右分区进行排序并找出小和 然后相加
        return process(arr,L,mid)
        + process(arr,mid + 1,R)
        + merge(arr,L,mid,R);
    }


    //递归进行排序并找出小和
    public static int merge(int[] arr, int L, int M, int R) {
        //使用一个额外数组来存储小分区排序的结果（大小为当前分区的大小）
        int[] help = new int[R - L + 1];
        //额外数组的索引位置
        int i = 0;
        //使用两个临时指针来进行位置移动
        int p1 = L;
        int p2 = M + 1;
        //记录结果
        int result = 0;
        //循环进行排序并找出每个小区间的小和
        //情况一：两个索引都不越界
        while(p1 <= M && p2 <= R){
            //先找出小和 在排序
            /*
            arr[p1]位子的值比arr[p2]位置的值小吗？
                如果小：因为比arr[p1]大的这个小区间是有序的，所以找出从arr[p2]开始一直到最后的元素个数，这些元素都比arr[p1]大
                       所以用这些元素个数来乘以arr[p1]得出小和结果
                如果大：
                      不计算  直接等于0
             */
            result += arr[p1] < arr[p2] ? (R - p2 + 1) * arr[p1] : 0;
            //区间内排序
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        //情况二：p1越界了
        while(p1 > M && p2 <= R){
            help[i++] = arr[p2++];
        }
        //情况三：p2越界了
        while(p1 <=M && p2 > R){
            help[i++] = arr[p1++];
        }
        //循环结束  区间内有序
        //将help中的值存入到原数组中
        for(i = 0;i < help.length;i++){
            arr[L + i] = help[i];
        }
        return result;
    }





}
