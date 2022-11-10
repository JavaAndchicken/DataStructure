package review.sort;

/**
 * 给第给一个目标值
 * 荷兰国旗问题：将数组中小于目标值的放到左边  等于目标值的放到中间  大于目标值的放到右边
 * 最后返回等于目标值的一个集合（索引位置） 例：[1,4] 在1，4范围内都是等于目标值的
 */
public class NetherlandsFlag {

    /*
    荷兰国旗问题的调用函数  传递一个数组和一个目标值
     */
    public int[] netherlandsFlag(int[] arr, int target) {
        //如果数组为空或者只有一个元素
        if (arr == null || arr.length < 2) {
            //如果这个元素刚好等于目标值 则就返回这个位置
            if (arr[0] == target) {
                return new int[]{0, 0};
            }
            //如果不等于目标值 直接返回无效索引
            return new int[]{-1, -1};
        }
        return process(arr, 0, arr.length - 1, 5);
    }

    /*
    参数：数组  起始位置  右边位置  目标值
     */
    public int[] process(int[] arr, int L, int R, int target) {
        //如果起始位置和终止位置是一个 并且这个值等于目标值 则直接返回这个范围内的数  否则返回无效索引 没找到
        if (L == R && target == arr[L]) {
            return new int[]{L, R};
        }
        //如果起始位置是大于终止位置的 则索引越界 返回无效索引
        if (L > R) {
            return new int[]{-1, -1};
        }
        //用两个指针来表示大于区和小于区的范围 分管头尾
        int smallest = L - 1; //头部
        int biggest = R + 1;//尾部
        //用一个指针用于移动  初始在第一个元素上面
        int move = 0;
        while(move < R) {
            //情况一：如果当前move代表的值是小于target的 则将这个值和小于区的后一个进行交换 然后小于区右扩一个位置将其包住
            if (arr[move] < target) {
                //交换
                swap(arr, ++smallest, move++);
                //情况二：如果当前move代表的值等于target的 move加加 跳过当前元素
            } else if (arr[move] == target) {
                move++;
                //情况三：当前move代表的值是大于target的 将大于区的前一个值和move值进行交换 move不动 进行下一次比较
            } else {
                swap(arr, move, --biggest);
            }
        }
        return new int[]{smallest + 1, biggest - 1};
    }

    //交换函数
    private void swap(int[] arr, int index1, int index2) {
        if (index1 != index2) {
            arr[index1] = arr[index1] ^ arr[index2];
            arr[index2] = arr[index1] ^ arr[index2];
            arr[index1] = arr[index1] ^ arr[index2];
        }
    }




    /**=================对数器=====================================================*/
    //1.生成一个随机数组   参数：最大长度  值的最大范围
    public int[] generatedRandomArray(int maxLength,int maxValue){
        //按照给定的最大长度生成一个数组的长度  maxLength+1 代表包含这个范围
        int[] arr = new int[ (int)(Math.random() * (maxLength + 1))];
        //给这个数组赋值
        for(int i = 0;i < arr.length;i++){
            //根据给定的maxValue生成对应的value值
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int)(Math.random() * maxValue);
        }
        return arr;
    }
    //2.复制数组
    public int[] copyArray(int[] arr){
        int[] newArray = new int[arr.length];
        for(int i = 0;i < arr.length;i++){
            newArray[i] = arr[i];
        }
        return newArray;
    }
    //3.判断两个数组是否相等
    public boolean isEquals(int[] arr1,int[] arr2){
        //1.如果两个数组长度不相等 直接不相等
        if(arr1.length != arr2.length){
            return false;
        }
        //2.如果两个数组一个为空 一个不为空 也不相等
        if((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)){
            return false;
        }
        //3.如果两个数组都为空 则返回true
        if(arr1 == null && arr2 == null){
            return true;
        }
        //4.如果两个数组的值相等 返回true
        for(int i = 0;i < arr1.length;i++){
            if(arr1[i] != arr2[i]){
                return false;
            }
        }
        return true;
    }
    //4.打印数组
    public void printArr(int[] arr){
        if(arr == null){
            return ;
        }
        for(int i = 0;i < arr.length;i++){
            System.out.print(arr[i] + "\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {


    }

}
