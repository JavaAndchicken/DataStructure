package review.sort;

/**
 * 求小和问题
 * 一个数组中  左边比当前数小的数累加和就叫做小和   既要求小和返回也要将数组排好序
 */
public class SmallSum {

    //参数：数组  返回值：小和
    public int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        //递归计算小和问题
        return process(arr, 0, arr.length - 1);
    }

    private int process(int[] arr, int L, int R) {
        //递归出口  如果数组中只剩下一个数 就返回0
        if (L == R) {
            return 0;
        }
        //获取中间位置
        int M = L + ((R - L) >> 1);
        //左右分区 进行排序
        return process(arr, L, M) + process(arr, M + 1, R) + merge(arr, L, M, R);
    }

    //将分区内数组进行归并排序计算并返回出小和值
    private int merge(int[] arr, int L, int M, int R) {
        //辅助数组  用来临时存储排序的元素
        int[] help = new int[R - L + 1];
        //先使用两个指针来记录两个分区的范围
        int leftPointer = L;
        int rightPointer = M + 1;
        //记录结果
        int result = 0;
        //数组的索引位置
        int i = 0;
        //情况一：在两个索引都在范围内的情况下
        while (leftPointer <= M && rightPointer <= R) {
            //找出这个区间内的小和
            //因为右边的区间是已经排好序的  所以在右边如果有比左边当前数大的数 则直接用他自己乘以大于他的个数就可以了
            result += arr[leftPointer] < arr[rightPointer] ? (R - rightPointer + 1) * arr[leftPointer] : 0;
            //将数组进行排序
            help[i++] = arr[leftPointer] < arr[rightPointer] ? arr[leftPointer++] : arr[rightPointer++];
        }
        //情况二：左边已经跑完 则直接将右边的放入到辅助数组中
        while (leftPointer > M && rightPointer <= R) {
            help[i++] = arr[rightPointer++];
        }
        //情况三：右边已经跑完 则直接将左边的放入到辅助数组中
        while (rightPointer > R && leftPointer <= M) {
            help[i++] = arr[leftPointer++];
        }
        //辅助数组中排完序之后 将里面有序的元素放入到原数组中对应的位置
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return result;
    }


    /**
     * ==============对数器========================================================================
     */

    //暴力方法
    public int lowSmallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int result = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                result += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return result;
    }

    //生成随机数组  参数： 数组长度范围  和 随机值的范围
    public int[] randomArray(int maxLength, int maxValue) {
        int[] arr = new int[(int) ((maxLength + 1) * Math.random())];
        //给数组赋值
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * maxValue);
        }
        return arr;
    }
    //拷贝数组  参数:目标数组
    public int[] copyArray(int[] arr){
        if(arr == null){
            return null;
        }
        int[] newArr = new int[arr.length];
        for(int i = 0;i < newArr.length;i++){
            newArr[i] = arr[i];
        }
        return newArr;
    }
    //判断两个数组是否相等  参数：两个数组
    public boolean isEquals(int[] arr1,int[] arr2){
        //如果一个为空 一个不为空 则不相等
        if((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)){
            return false;
        }
        //如果两个都为空 则相等
        if(arr1 == null && arr2 == null) {
            return true;
        }
        //如果长度不相等 则不相等
        if(arr1.length != arr2.length){
            return false;
        }
        //如果值不相等 则不相等
        for(int i = 0;i < arr1.length;i++){
            if(arr1[i] != arr2[i]){
                return false;
            }
        }
        return true;
    }
    //打印数组 参数：数组
    public void printArr(int[] arr){
        if(arr == null){
            return;
        }
        for(int i = 0;i < arr.length;i++){
            System.out.print(arr[i]+"\t");
        }
        System.out.println();
    }

    public static void main(String[] args){
        SmallSum sum = new SmallSum();
        //测试次数
        int testTime = 2000000;
        //数组长度和大小
        int maxLength = 100;
        int maxValue = 100;
        boolean success = true;
        for(int i = 0;i < testTime;i++){
            //生成随机数组
            int[] arr1 = sum.randomArray(maxLength,maxValue);
            int[] arr2 = sum.copyArray(arr1);
            //如果两个数组排序结果不同 则直接测试失败
            if(sum.smallSum(arr1) != sum.lowSmallSum(arr2)){
                success = false;
                sum.printArr(arr1);
                sum.printArr(arr2);
                break;
            }
        }
        if(!success){
            System.out.println("FUCK！！！");
        }else{
            System.out.println("SUCCESS!!");
        }
    }



}
