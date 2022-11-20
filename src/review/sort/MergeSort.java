package review.sort;


import java.util.Arrays;

/**
 * 归并排序-->复习版
 */
public class MergeSort {


    static class ListNode {
           int val;
           ListNode next;
           ListNode() {}
           ListNode(int val) { this.val = val; }
           ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public static void main(String[] args){
        int[] arr = new int[]{3,5,6,2,5,7,2,1};
        mergeSort(arr,0);
        System.out.println(Arrays.toString(arr));
    }

    //递归实现
    //参数：要排序的数组  从什么位置开始排
    //返回值：不需要
    public static void mergeSort(int[] arr, int L) {
        //如果数组为空 或者数组内只有一个元素 则不需要排序
        if (arr == null || arr.length < 2) {
            return;
        }
        //调用递归过程进行排序
        process(arr, L, arr.length - 1);
    }



    //递归过程进行排序
    //参数：数组  起始位置
    private static void process(int[] arr, int L, int R) {
        //递归出口
        //如果分区内只有一个元素 也就是R == L  时  是不需要排序的
        if (R == L) {
            return;
        }
        //递归进行左右两边的分区
        //获取中间位置
        int mid = L + ((R - L) >> 1);
        //处理左边分区
        process(arr, L, mid);
        //处理右边分区
        process(arr, mid + 1, R);
        //将各分区内排序
        merge(arr, L, mid, R);

    }



    //设计一个方法 将各分区内进行排序
    //参数：数组  起始位置  中间位置  终止位置
    //返回值：不需要
    private static void merge(int[] arr, int L, int mid, int R) {
        //辅助数组 临时存储有序元素  容量为当前分区元素大小
        int[] help = new int[R - L + 1];
        //在这个分区内使用双指针  使这个小分区有序
        int leftPointer = L;
        int rightPointer = mid + 1;
        //控制help的索引
        int indexForHelp = 0;
        //情况一：两个指针都在有效范围内  则将两个只针对代表的值有序放入到辅助数组中
        while (leftPointer <= mid && rightPointer <= R) {
            help[indexForHelp++] = arr[leftPointer] < arr[rightPointer] ? arr[leftPointer++] : arr[rightPointer++];
        }
        //情况二：左指针已经走完 则直接将右指针的值放入到数组中即可
        while(leftPointer > mid && rightPointer <= R){
            help[indexForHelp++] = arr[rightPointer++];
        }
        //情况三：右指针走完 将左指针的值放入到数组中
        while(leftPointer <= mid && rightPointer > R){
            help[indexForHelp++] = arr[leftPointer++];
        }
        //分区内有序之后 将辅助数组中的值拿出来放入到原数组中对应的位置上
        for(int i = 0;i < help.length;i++){
            //arr每次放入是在左边已经有值的情况下  所以要越过左边的值
            arr[L + i] = help[i];
        }
    }
}
