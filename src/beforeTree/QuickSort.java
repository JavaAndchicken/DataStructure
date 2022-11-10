package beforeTree;

import java.util.Arrays;

/**
 * 快速排序，递归实现
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = new int[]{3, 5, 6, 7, 4, 3, 5, 8};
        QuickSort sort = new QuickSort();
        sort.quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }


    public void quickSort(int[] arr) {
        //如果数组为空 直接返回当前数组
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    //进行快排处理的递归过程方法
    //参数：数组，起始位置，终止位置
    //要起始位置和终止位置的原因：
    //  因为需要进行递归，每一次的要处理的小分区都不同，所以要传入不同的起始和终止参数，进行迭代处理
    private void process(int[] arr, int begin, int end) {
        //判断给定的起始和终止位置是否合法
        //如果范围不合理或者只有一个元素 直接退出
        if (begin >= end) {
            return;
        }
        //随机生成一个数组范围内的索引值，将这个索引对应的元素和数组最后一个元素进行交换（即概率性索引）
        //使用概率性索引的快排时间复杂度可以达到 O(N * logN)
        //Math.random()  随机生成一个0 - 1的浮点数
        //end - begin + 1   数组的长度
        //begin + ...   每一次传入的数组起始位置可能不同，可能数组的左边已经不需要处理了，所以需要去除
        //Math.random() * (end - begin + 1)  随机生成一个在数组内的任意索引位置
        swap(arr, begin + (int) Math.random() * (end - begin + 1), end);
        //交换后 开始使用荷兰国旗问题 来进行分类
        //调用方法 得到等于目标值的索引范围
        int[] targetRange = netherlandsFlag(arr, begin, end);
        //拿着目标值索引范围的左边小区和右边小区再去做迭代
        /*下面两个递归相当于是：
            想象等于目标值的元素索引范围在中间
            然后拿出这个范围左边的小区间
            拿出这个范围右边的小区间
            将这个两个小区间再分别进行排序
         */
        process(arr, begin, targetRange[0] - 1);
        process(arr, targetRange[1] + 1, end);
    }

    //将数组进行分类的方法（荷兰国旗问题）
    //不同的是：这里不需要传入目标值，因为是拿数组的最后一个值来进行比较
    //这个荷兰国旗是为quickSort服务的，也就是这个里面的目标值是使用quickSort里面随机生成的
    //一个索引位置，然后将其和最后一个元素交换，然后拿这个最后一个元素当做目标值来进行比较
    private int[] netherlandsFlag(int[] arr, int begin, int end) {
        //判断范围是否越界  越界直接返回无效索引
        if (begin > end) {
            return new int[]{-1, -1};
        }
        //只有一个元素  直接返回当前索引
        if (begin == end) {
            return new int[]{begin, end};
        }
        int less = begin - 1;
        //不同：因为最后一个数是只需要拿来做比较的，会在循环结束后直接换到前面  所以不需要做额外的交换
        int more = end;
        int index = begin;
        while (index < more) {
            //情况一：如果当前值小于最后一个目标值  则将less的下一个值和当前值进行交换，less右扩一位，index加加
            if (arr[index] < arr[end]) {
                swap(arr, ++less, index++);
            } else if (arr[index] == arr[end]) {
                //情况二：如果两者相等  直接index后移一位
                index++;
            } else {
                //情况三：当前值大于目标值  让more的前一个元素和当前值进行交换  index不变
                swap(arr, --more, index);
            }
        }
        //循环结束后，将最后一个目标值换到小于区和大于区之间
        //当经历了第一轮排序之后，最左边的，一定是小于或等于我自己的，大于我的都在中间的位置
        //如下示意图：再进行一次交换，让目标值处在小于区和大于区之间
        /*
             小于区              大于区
           __小-->大__ 目标值  __小-->大__
         */
        swap(arr, more, end);
        //返回等于目标值的索引
        return new int[]{less + 1, more};
    }

    //交换元素的函数
    private void swap(int[] arr, int num1, int num2) {
        if (num1 != num2) {
            arr[num1] = arr[num1] ^ arr[num2];
            arr[num2] = arr[num1] ^ arr[num2];
            arr[num1] = arr[num1] ^ arr[num2];
        }
    }
}
