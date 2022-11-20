package review.sort;


import java.util.Arrays;
import java.util.Stack;

/**
 * 堆排序
 */
public class HeapSort {


    //将数组从小到大排序
    public void heapSort(int[] arr){
        //如果数组不需要排序
        if(arr == null || arr.length < 2){
            return;
        }
        //将数组构造成堆结构
        createHeap(arr);
        //将数组进行排序  向下的heapify
        //得到数组中有效元素的长度
        int validLength = arr.length;
        //因为是将数组从小到大排序 所以需要将大根堆上的根结点上的值都拿到后面去（因为根节点上值都局部最大的）
        //将整个数组中的值都进行heapify一遍  最终保证有序
        //先将根结点和最后一个节点进行交换 然后拿交换后的根结点去进行向下的heapify
        swap(arr,0,--validLength);
        //如果数组被遍历完毕 则停止循环
        while(validLength > 0){
            //将每个数都进行一个heapify
            heapify(arr,0,validLength);
            //将新的最大值放到数组的后面
            swap(arr,0,--validLength);
        }
    }

    //将数组进行向下的heapify排序
    //需要排序的数组目标  从哪个地方开始进行heapify   数组的有效元素的长度
    private void heapify(int[] arr,int index,int validLength){
        //得到根结点的左孩子
        int left = (index << 1) + 1;
        //在左孩子在范围内的条件下 如果他比自己父结点大 进行交换
        while(left < validLength){
            //在右孩子结点在范围内的情况下   先比较左右孩子哪个大 得到一个更大的值的索引
            int leftOrRight = arr[left+1] < validLength && arr[left+1] > arr[left] ? left+1 : left;
            //比较左右孩子是否比父结点大  得到一个更大值的索引
            int biggest = arr[leftOrRight] > arr[index] ? leftOrRight : index;
            //判断父结点是否被比下去了  如果没有 就直接退出
            if(biggest == index){
                break;
            }
            //如果被比下去了 则直接交换两个值
            swap(arr,index,biggest);
            //改变新的根结点
            index = biggest;
            //重新得到left的值
            left = (index << 1) + 1;

        }




    }

    //将数组构造成堆结构
    private void createHeap(int[] arr){
        int cur = 0;
        for(int i = 0;i < arr.length;i++){
            cur = i;
            while(arr[cur] > arr[(cur-1) / 2]) {
                //如果arr[i]是大于父结点的  则让其交换
                swap(arr, cur, (cur - 1) / 2);
                //继续获取当前结点的父结点
                cur = (cur - 1) / 2;
            }
        }
    }


    //交换数组中两个元素
    private void swap(int[] arr,int index1,int index2){
        if(index1 != index2){
            arr[index1] = arr[index1] ^ arr[index2];
            arr[index2] = arr[index1] ^ arr[index2];
            arr[index1] = arr[index1] ^ arr[index2];
        }
    }

    /**==== for test =================================================================*/

    /*暴力法*/
    public void comparator(int[] arr){
        Arrays.sort(arr);
    }

    //生成一个随机数组
    private int[] generatedRandomArray(int maxLength,int maxValue){
        int[] arr = new int[(int) Math.random() * (maxLength + 1)];
        for(int i = 0;i < arr.length;i++){
            arr[i] = (int) Math.random() * (maxValue + 1) - (int) Math.random() * maxValue;
        }
        return arr;
    }
    //复制数组
    private int[] copyArray(int[] arr){
        int[] newArr = new int[arr.length];
        for(int i = 0;i < arr.length;i++){
            newArr[i] = arr[i];
        }
        return newArr;
    }
    //判断两个数组是否相等
    private boolean isEquals(int[] arr1,int[] arr2){
        if(arr1 == null && arr2 == null){
            return true;
        }
        if((arr1 == null && arr2 != null ) || (arr1 != null && arr2 == null)){
            return false;
        }
        if(arr1.length != arr2.length){
            return false;
        }
        for(int i = 0;i < arr1.length;i++){
            if(arr1[i] != arr2[i]){
                return false;
            }
        }
        return true;
    }
    //打印数组
    private void print(int[] arr){
        if(arr == null){
            return ;
        }
        for(int i = 0;i < arr.length;i++){
            System.out.print(arr[i]+"\t");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        HeapSort sort = new HeapSort();
        int maxLength = 100;
        int maxValue = 100;
        int testTime = 200000000;
        int[] arr1 = sort.generatedRandomArray(maxLength,maxValue);
        int[] arr2 = sort.copyArray(arr1);
        boolean flag = true;
        for(int i = 1;i <= testTime;i++){
            sort.heapSort(arr1);
            sort.comparator(arr2);
            if(!sort.isEquals(arr1,arr2)){
                flag = false;
                break;
            }
        }
        System.out.println(flag ? "NICE!!!" : "FUCK!!!");
    }


}
