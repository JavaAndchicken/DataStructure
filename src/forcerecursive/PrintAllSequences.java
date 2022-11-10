package forcerecursive;

import java.util.HashSet;
import java.util.List;

/**
 * 暴力递归：打印一个字符串的子序列
 */
public class PrintAllSequences {

    //打印字符串的子序列  包含重复的
    //参数：字符串  起始位置  存储结果的集合  结果串
    public void printSequences(char[] arr, int index, List<String> result, String path) {
        //递归出口  如果当前index等于arr的长度了  就将这个path结果存到result集合中
        if (index == arr.length) {
            result.add(path);
            return;
        }
        //将要和不要的字符 都通通跑一遍  然后将需要的字符加到path上面
        //不能去太深的理解  容易乱
        //不要当前字符的  将其深度跑一遍
        printSequences(arr, index + 1, result, path);
        //要当前字符的  将其深度跑一遍 并加上当前字符
        printSequences(arr, index + 1, result, path + arr[index]);
    }

    //打印字符串序列  不包含重复元素  直接使用set去重即可
    public void printSequences(char[] arr, int index, HashSet<String> result, String path) {
        if (index == arr.length) {
            result.add(path);
            return;
        }
        //不要当前位置上的元素
        printSequences(arr, index + 1, result, path);
        //需要当前位置上的元素
        printSequences(arr, index + 1, result, path + arr[index]);
    }

}
