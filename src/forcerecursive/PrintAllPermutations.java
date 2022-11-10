package forcerecursive;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印字符串的全排列
 */
public class PrintAllPermutations {

    public static void main(String[] args){
        String s = "abcde";
        /*List<String> result = per1(s.toCharArray());
        for(String s1 : result){
            System.out.println(s1);
        }*/
        /*  未去重复
            abcde
            abced
            abdce
            abdec
            abedc
            abecd
            acbde
            acbed
            acdbe
            acdeb
            .....
        */

        List<String> result = per2(s.toCharArray());
        for(String re : result){
            System.out.println(re);
        }
        /* 去重复版
            abcde
            abced
            abdce
            abdec
            abedc
            abecd
            acbde
            acbed
            acdbe
            acdeb
            acedb
         */

    }

    public static List<String> per1(char[] arr){
        List<String> result = new ArrayList<>();
        permutation1(arr,result,0);
        return result;
    }

    //未去重复版
    //参数：字符数组   结果集合   每次开始的位置
    public static void permutation1(char[] arr, List<String> result, int index) {
        //递归出口  如果index等于数组长度了 就将其添加到result中
        if(index == arr.length){
            result.add(String.valueOf(arr));
        }else {
            //循环这个字符串  将这个字符串的邻近两个元素交换位置  去处理它  然后再将其位置还原 进行下一次交换并处理
            //注意：i的位置应该是从index开始 进行每一个局部的相邻字符的交换  最终形成整体
            for (int i = index; i < arr.length; i++) {
                swap(arr, i, index);
                //处理它
                permutation1(arr, result, index + 1);
                //将原顺序调整回来
                swap(arr, i, index);
            }
        }
    }


    //去重复版
    public static List<String> per2(char[] arr){
        List<String> result = new ArrayList<>();
        permutation2(arr,result,0);
        return result;
    }
    //参数：字符数组  结果集合  每次开始的位置
    public static void permutation2(char[] arr,List<String> result,int index){
        if(index == arr.length){
            result.add(String.valueOf(arr));
        }else{
            //去重复 给每一个字符做一个标记 如果是true就是已经存在
            boolean[] flag = new boolean[256];
            for(int i = index;i < arr.length;i++){
                if(!flag[i]) {  //如果是默认值false 就进来处理这个字符串  如果是true就不处理
                    flag[i] = true;
                    swap(arr, i, index);
                    permutation2(arr, result, index + 1);
                    swap(arr, i, index);
                }
            }
        }
    }








    //交换两个数的位置
    private static void swap(char[] arr, int index1, int index2) {
        char tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }


}
