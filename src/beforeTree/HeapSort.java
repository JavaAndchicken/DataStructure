package beforeTree;

import java.util.Arrays;

public class HeapSort {

    /**
     * 堆排序
     */
    public static void main(String[] args){
        HeapSort sort = new HeapSort();
        int[] arr = new int[]{3,4,6,23,56,9,0,1};
        sort.heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }


    /*
    思路：先将传进来的数组构造成大根堆。
        1.将大根堆的最后一个值和根结点进行交换（因为根结点是最大的一个值）
        2.将根结点的索引在heapSize上去除
        3.对新的根结点做heapify
        4.循环1,2,3，步骤的操作 直至heapSize为0 数组即有序
     */
    public void heapSort(int[] arr) {
        //判断数组是否需要进行排序
        if (arr == null || arr.length < 2) {
            return;
        }
        //数组有序，将参数传进来的数组构造成大根堆
        for (int i = 0; i < arr.length; i++) {
            //调用方法进行大根堆的构造
            //边取值边构造
            createHeap(arr, i);
        }
        //大根堆构造完毕
        //获得有效元素的长度
        int heapSize = arr.length;
        //将大根堆的最后一个值和根结点进行交换
        swap(arr,--heapSize,0);
        //下面两部操作是一个循环的过程，循环直至堆上的索引记录为0 即heapSize=0
        while(heapSize > 0) {
            //对新的根结点做heapify
            heapify(arr, 0, heapSize);
            //heapify之后，最大的值又来到根结点，继续交换最后一个值和根结点
            swap(arr, --heapSize, 0);
        }

    }


    //调用方法构造大根堆
    //参数：数组，当前元素的索引位置
    private void createHeap(int[] arr, int cur) {
        // 如果当前结点大于自己的父结点  就一直循环  进行交换
        while (arr[cur] > arr[(cur - 1) / 2]) {
            swap(arr, cur, (cur - 1) / 2);
            //继续获取当前结点的父结点
            cur = (cur - 1) / 2;
        }
    }

    //将根结点进行heapify
    //参数：  数组  起始位置(根结点的位置)  有效元素的长度
    private void heapify(int[] arr,int index,int heapSize){
        //先获取当前根结点的左孩子
        int left = (index * 2) + 1;
        //循环
        //条件：有左孩子  或者孩子存在
        while(left < heapSize){
            //判断右孩子是否存在，如果存在是否大于左孩子
            //如果右孩子存在且大于左孩子  则让largest等于右孩子的索引  否则就是左孩子的索引
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            //判断更大的孩子和父结点谁大
            //如果更大的孩子大于父结点，则赋值更大的孩子给largest  否则赋值index
            largest = arr[largest] > arr[index] ? largest : index;
            //如果孩子不比父亲大 直接退出
            if(largest == index){
                break;
            }
            //如果孩子比父亲大 则进行调换
            swap(arr,largest,index);
            //新的根结点改变
            index = largest;
            //继续获取新的左孩子进行比较
            left = (index * 2) + 1;
        }

    }

    //用于交换两个值的方法
    private void swap(int[] arr, int num1, int num2) {
        if(num1 != num2){
            arr[num1] = arr[num1] ^ arr[num2];
            arr[num2] = arr[num1] ^ arr[num2];
            arr[num1] = arr[num1] ^ arr[num2];
        }
    }
}
