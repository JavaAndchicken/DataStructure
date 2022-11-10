package beforeTree;

import java.util.Arrays;

public class RadixSort {


    /**
     * 基数排序
     */

    public static void main(String[] args) {
        int[] arr = new int[]{1313, 22414, 2213124, 12234, 43234, 13253, 98234, 5321};
        radixSort(arr);
        System.out.println(Arrays.toString(arr));

    }

    //设计一个方法做基数排序
    /* 思路：
        1.先获取数组中最大的一个值，并获取其位数，以此来作为循环的轮次（即入桶出桶的次数）
        2.将count中的出现次数做一个合并
        3.再从前往后计算每一个数的每一位值出现的次数，用一个专门记录出现次数的数组来存放
        3.在从后往前遍历这个数组，再次获取每个数的每一位值，并根据其出现的次数来存放到一个新的数组的位置-1的位置上（类似出桶）
            从后往前是因为这样拿出来元素之后和出桶的顺序是一致的
        5.将出桶后的元素重新放入到原数组中，继续下一次的计算次数操作
     */

    //基数排序  只需要传递一个数组
    public static void radixSort(int[] arr) {
        //如果数组为空或者只有一个元素就不需要排序
        if (arr == null || arr.length < 2) {
            return;
        }
        //调用方法 获取数组中的最大值的位数
        int radix = getRadix(arr);
        //做排序操作
    }

    //设计一个方法 做数组的排序操作
    //参数：需要排序的数组  需要在哪个范围内排序  起始位置，终止位置   最大值的位数
    private static void doRadixSort(int[] arr, int begin, int end, int radix) {
        //记录每一次出桶的数组
        int[] help = new int[arr.length];
        //记录个位数/十位数/百位数出现次数的数组
        //因为是基低值  所以只可能是从0-9的长度
        int[] count = new int[10];
        int eachNum = 0;
        int k = 0;
        int j = 0;
        //用最大值位数当做循环次数
        for (int i = 1; i <= radix; i++) {
            //1.获得每个数的个位数/十位数/百位数
            for (k = begin; k <= end; k++) {
                //调用方法计算每个数的个位数
                eachNum = getEachNum(arr[k], radix);
                //找到对应的count数组中的位置 将eachNum位置的值加加
                count[eachNum]++;
            }
            //2.合并count中的出现次数
            for (k = 1; k <= count.length; k++) {
                count[k] = count[k - 1] + count[k];
            }
            //3.从后往前再次查找每个数的个位/十位/百位数，然后存放到help中对应-1的位置
            for (k = end; k >= begin; k--) {
                eachNum = getEachNum(arr[end],radix);
                help[count[eachNum] - 1] = arr[end];
                count[eachNum] --;
            }
            //4.将help数组中的值存放到arr里面
            for(j = begin,k = 0;k < help.length;k++){
                arr[j] = help[k];
                j++;
            }
        }
    }

    //设计一个方法 计算每个数的当前radix位的单个值
    private static int getEachNum(int curNum, int radix) {
        /* 例：109
                 1                      0                  9
           curNum/10^2%10      curNum/10^1%10      curNum/10^0%10
         */
        return (curNum / (int) (Math.pow(10, radix - 1))) % 10;
    }


    //设计一个方法  获取数组中的最大值的位数
    private static int getRadix(int[] arr) {
        //先生成一个最小值 作为比较的依据
        int max = Integer.MIN_VALUE;
        //循环数组获取最大值
        for (int a : arr) {
            max = a > max ? a : max;
        }
        int count = 0;
        //计算位数
        while (max != 0) {
            count++;
            max /= 10;
        }
        return count;
    }


//=======版本一：====================================================================================================
    /*思路；
        1.先获取数组中的最大值，计算出最大值的位数
        2.然后大循环需要按照位数来计算，有多少位，就大循环多少次
        3.在循环的过程中
            大循环（最大数的位数的次数当做条件）
                a. 使用一个数组count 记录数组中的每一位数出现的次数
                b. 将count数组中的出现次数合并起来
                c. 从后往前遍历数组，再次获取数组中每一位数出现的次数，然后将其放入help数组中
                    怎么放？
                        根据count中的当前数的出现次数，放入到-1的位置，这样放好之后，和出桶的顺序是一致的
                d. 遍历help数组，将每一次的值放入到arr数组中

     */
    /*
    //参数；传递一个数组
    public static void radixSort(int[] arr) {
        //如果数组为空 或者数组内只要一个值 则不需要排序
        if (arr == null || arr.length < 2) {
            return;
        }
        //调用方法获取数组中的最大值
        int radix = getRadix(arr);
        //获取到最大值后，调用方法 进行排序
        doRadixSort(arr, 0, arr.length - 1, radix);
    }


    //设计一个方法 在给定的区间范围内(begin -- end)做基数排序
    //参数：需要排序的数组   起始位置  终止位置  最大值的位数
    private static void doRadixSort(int[] arr, int begin, int end, int radix) {
        //存储每一次出桶的结果  长度为数组中元素的个数长度
        int[] help = new int[end - begin + 1];
        //记录每一位的数
        int eachSingleNum = 0;
        //大循环  控制轮次 以最大值的位数作为循环次数 循环radix次
        for (int i = 1; i <= radix; i++) {
            //存放每个数出现次数的数组 因为每一位数只能是从0-9  所以数组只需要10个长度
            int[] count = new int[10];
            //首先：循环将每个数的每一位数都记录下来，存放到一个额外的数组中 count[]
            //循环条件：从给定的起始位置和终止位置来做一个排序
            int j = 0;
            for (j = begin; j <= end; j++) {
                //将每一个数进行计算 求出其每一位数
                eachSingleNum = getSingleNum(arr[j], i);
                //将对应的位数的位置上的值加1
                count[eachSingleNum]++;
            }

            int k = 0;
            //其次：在每个数的位数出现次数都找出来之后，将count上的次数值合并起来
            //即： 小于等于x的出现次数都合并到x身上
            for (k = 1; k < count.length; k++) {
                count[k] = count[k - 1] + count[k];
            }
            //再者：从后往前遍历数组，再次获取每个数的每一位数，然后在count中找到他的位置再-1，放到help中
            for (k = end; k >= begin; k--) {
                eachSingleNum = getSingleNum(arr[k], i);
                help[count[eachSingleNum] - 1] = arr[k];
                //对应的count中的出现次数在存入值之后也要减1
                count[eachSingleNum]--;
            }
            //最后：遍历help数组  将出桶的值放入到arr数组中
            //因为help需要从0开始放，而arr是从给定的起始范围开始放，所以需要两个条件
            //k = 0 从help中取值的索引
            //j = L 放入arr中的索引
            for (k = 0, j = begin; k < help.length; k++) {
                arr[j] = help[k];
                j++;
            }
        }
    }


    //设计一个方法  求给定数在当前radix上的单个位数
    private static int getSingleNum(int curNum, int radix) {
        //用radix来控制第几位
        *//* 设curNum = 129
            radix   1                               2               3
            需求：  /10^0%10                       /10^1%10        /10^2%10
            规律：(curNum / 10^radix-1) % 10;
         *//*
        //按照以上规律 可得公式
        return (   (curNum / ((int) Math.pow(10, radix - 1))  )     % 10);
    }

    //设计一个方法 获取数组中的最大值，并返回他的位数
    private static int getRadix(int[] arr) {
        //先初始化一个最小值
        int max = Integer.MIN_VALUE;
        //循环找出数组中的最大值
        for (int i = 0; i < arr.length; i++) {
            max = arr[i] > max ? arr[i] : max;
        }
        //用一个变量来记录最大值的位数
        int count = 0;
        //最大值找出来后 计算出这个最大值的位数
        while (max != 0) {
            //第一次进来直接加上一位，然后如果除以10不等于0的话，再加上一位，依次循环
            count++;
            max /= 10;
        }
        return count;
    }*/
}
