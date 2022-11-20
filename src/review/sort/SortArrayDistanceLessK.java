package review.sort;


import javafx.scene.layout.Priority;

import javax.swing.*;
import java.util.*;

/**
 * 给定一个几乎有序的数组  几乎有序是指 如果将数组排好序的话  每个元素移动的距离是不超过K的
 * 并且K对于数组来说比较小
 */
public class SortArrayDistanceLessK {


    /* 思路：先将前K个数放入到一个小根堆中 然后将后K个数放入到小根堆中 并且在后K个数放入的时候从小根堆中
    取出一个值，放入到数组中，当所有的值取出完毕 数组有序
     */
    public void sortArray(int[] arr,int K){
        int[] result = new int[arr.length];
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        //循环将前K个值放入到小根堆中
        //注意K越界的问题
        int preIndex ;
        for(preIndex = 0;preIndex < Math.min(K - 1,arr.length - 1);preIndex ++){
            //将前K个值放入到小根堆中
            queue.offer(arr[preIndex]);
        }
        //将后K个值放入到小根堆中 并在放入的同时取出
        //因为前面已经取过值了 所以这次要从preIndex开始取
        int lastIndex;
        for(lastIndex = 0;preIndex < arr.length;preIndex++){
            //将K后面的值存入到小根堆中
            queue.offer(arr[preIndex]);
            //从小根堆中取出每一个最小值放入到数组中
            result[lastIndex] = queue.poll();
            lastIndex++;
        }
        //当K个值全都已经放入完毕后 小根堆中可能还有值 将其全部取出放入到数组中
        while(!queue.isEmpty()){
            result[lastIndex] = queue.poll();
            lastIndex++;
        }
    }


    /**============== For Test ====================================================*/

    public void comparator(int[] arr, int k) {
        Arrays.sort(arr);
    }


    //随机生成几乎有序的数组并保证每个数的移动位置不超过K
    public int[] randomArrayMoveLessK(int maxLength,int maxValue,int K){
        int[] newArr = new int[(int) Math.random() * (maxLength+1)];
        for(int i = 0;i < newArr.length;i++){
            newArr[i] = (int) Math.random() * (maxValue + 1) - (int) Math.random() * maxValue;
        }
        //将数组排好序
        //交换i和j
        //但如果这两个数已经交换过了 就不再交换
        //用一个标志来记录
        boolean[] flag = new boolean[newArr.length];
        Arrays.sort(newArr);
        //将排好序的数组打乱顺序  但是保证每两个数移动的位置不能超过K
        for(int i = 0;i < newArr.length;i++){
            //随机生成一个要交换的值的索引
            //要保证在K之前    并且每一次i移动 都要随着他往后去   并且不能超过数组的长度 所以得出 ：
            int j = Math.min(i + (int) Math.random() * (K + 1),newArr.length - 1);
            //Boolean数组初始化都为false  所以!falg==flase代表还没有交换  falg为true代表已经交换过了 不再交换
            if(!flag[i] && !flag[j]){
                //改变标识  后面不再进行交换
                flag[i] = true;
                flag[j] = true;
                int tmp = newArr[i];
                newArr[i] = newArr[j];
                newArr[j] = tmp;
            }
        }
        return newArr;
    }

    public int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args){
//        SortArrayDistanceLessK sort = new SortArrayDistanceLessK();
//        int maxLength = 100;
//        int maxValue = 100;
//        int testTime = 20000000;
//        boolean flag = true;
//        for(int i = 1;i <= testTime;i++){
//            int K = (int) (Math.random() * maxLength) + 1;
//            int[] arr = sort.randomArrayMoveLessK(maxLength,maxValue,K);
//            int[] arr1 = sort.copyArray(arr);
//            sort.sortArray(arr,K);
//            sort.comparator(arr1,K);
//            if(!sort.isEqual(arr,arr1)){
//                flag = false;
//                break;
//            }
//        }
//        System.out.println(flag ? "NICE" : "FUCK!!!");

    }



}
