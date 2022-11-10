package review.sort;

/**
 * 快速排序  三个版本
 */
public class QuickSort {

    /**
     * ============快排 1.0 ==========================================
     * 将数组中最后一个值当做目标值 然后按照这个目标值，将整个大数组进行partition进行分区
     * 小于等于目标值的放到左边 ，大于目标值的放到右边，最终把目标值放到中间，然后继续对左右两边的分区进行partition 中间值保持不动
     */
    //参数：数组
    public void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //调用递归过程进行排序
        process1(arr, 0, arr.length - 1);
    }

    //快排1.0的递归过程
    //参数：数组  起始位置  终止位置
    private void process1(int[] arr, int L, int R) {
        /*
        先将整个大数组进行partition  让其左右两边分区明显  然后在左右两边分区进行partition 最终有序
         */
        //将大数组进行partition 得到一个中间位置
        int M = partition1(arr, L, R);
        //在M的左边和右边分别进行partition  中间位置上的值不动  最终有序
        process1(arr, L, M - 1);
        process1(arr, M + 1, R);
    }

    //partition大数组 返回一个中间位置
    //所谓中间位置 就是将小于等于特定值的元素放到左边  大于等于他的放到右边  而这个特定值就随机选一个 然后将其放到左右分区的中间位置
    //参数：数组  起始位置 终止位置
    private int partition1(int[] arr, int L, int R) {
        //如果起始位置和终止位置一样 不需要partition直接返回该位置即可
        if (L == R) {
            return L;
        }
        //记录中间位置的索引  初始化在越界的位置
        int lessEqual = L - 1;
        //用于移动的指针
        int index = L;
        //假定拿数组的最后一个元素当做特定值 将小于等于他的放到左边 包含在lessEqual里面 大于他的在右边
        while (index < R) {
            //如果当前值小于最后一个值 则将当前值和lessEqual的后一个元素进行交换 然后lessEqual将其包在范围之内
            if (arr[index] <= arr[R]) {
                swap(arr, index, ++lessEqual);
            }
            index++;
        }
        //当index等于R的时候  也就是来到了特定值的位置  将这个特定值放入到中间位置
        swap(arr, R, ++lessEqual);
        //返回中间值所在的位置
        return lessEqual;
    }

    //交换函数：将数组中的两个索引位置上的值进行交换
    private void swap(int[] arr, int index1, int index2) {
        if (index1 != index2) {
            arr[index1] = arr[index1] ^ arr[index2];
            arr[index2] = arr[index1] ^ arr[index2];
            arr[index1] = arr[index1] ^ arr[index2];
        }
    }

    /**
     * ==============快排 2.0 =========================================
     * 类似的：拿数组中最后一个值当做目标值，然后进行左中右的区域划分
     * 左边的是小于目标值的 中间的是等于目标值的 右边的是大于目标值的
     * 然后返回等于目标值的索引范围：在这个范围之外，继续进行这样的划分（荷兰国旗问题）
     * 范围之内的值不动
     */
    public void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //调用递归过程进行区域划分
        process2(arr, 0, arr.length - 1);
    }

    //递归进行区域的划分
    private void process2(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        //通过partition得到一个划分左右区域的位置
        int[] M = partition2(arr, L, R);
        process2(arr, L, M[0] - 1);
        process2(arr, M[1] + 1, R);
    }

    //划分数组 参数：数组  起始位置  终止位置
    //返回等于目标值的索引范围
    private int[] partition2(int[] arr, int L, int R) {
        //如果左边和右边相等 则不需要划分
        if (L == R) {
            return new int[]{L, R};
        }
        //小于区的管辖指针
        int lessPointer = L - 1;
        //大于区的管辖指针 由于是将最后一个值当做划分值，所以可以直接将他放入到大于区内 不需要考虑排序的问题
        int morePointer = R;
        //用于移动的指针
        int index = L;
        while (index < R) {
            //三种情况：
            //情况一：如果当前值小于目标值arr[R] 则将小于区的后一个值和当前值进行交换
            if (arr[index] < arr[R]) {
                swap(arr, index++, ++lessPointer);
                //情况二：如果当前值等于目标值 则指针移动 不进行其他操作
            } else if (arr[index] == arr[R]) {
                index++;
                //情况三：当前值大于目标值 指针不动 仅将大于区的前一个数和当前值进行交换
            } else {
                swap(arr, index, --morePointer);
            }
        }
        //将最后一个值放入到等于区
        swap(arr, R, morePointer);
        return new int[]{lessPointer + 1, morePointer};
    }


    /**
     * ============快排 3.0 ====================================================
     * 随机生成一个起始位置 然后将他和最后一个值进行交换 然后拿这个交换过的最后一个值当做划分值
     * 去做荷兰国旗的区域划分 而后继续在划分后的等于区两边继续去做这样一个划分
     * 最终数组有序
     */
    public void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //调用递归过程进行分区
        process3(arr, 0, arr.length);
    }

    //递归过程 数组分区及排序
    private void process3(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        //随机选取一个数组范围内的索引将其和最后一个值进行交换
        swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
        //拿这个交换过的值去做荷兰国旗的划分  得到一个等于这个值的索引范围
        int[] M = partition2(arr, L, R);
        //在小范围内进行划分
        process3(arr, L, M[0] - 1);
        process3(arr, M[1] + 1, R);
    }



    /**============= 对数器  For test==============================================*/

    /**
     * 方法一：随机生成一个指定范围内长度 每个值为指定范围内  的数组
     */
    private int[] generatedArray(int maxLength,int maxValue){
        int[] arr = new int[(int) Math.random() * (maxLength + 1)];
        for(int i = 0;i < arr.length;i++){
            arr[i] = (int) Math.random() * (maxValue + 1) - (int) Math.random() * maxValue;
        }
        return arr;
    }
    /**
     * 方法二：复制数组
     */
    private int[] copyArray(int[] arr){
        if(arr == null){
            return new int[]{};
        }
        int[] newArr = new int[arr.length];
        for(int i = 0;i < arr.length;i++){
            newArr[i] = arr[i];
        }
        return newArr;
    }
    /**
     * 方法三：比较两个数组是否相等
     */
    private boolean isEquals(int[] arr1,int[] arr2){
        if((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)){
            return false;
        }
        if(arr1.length != arr2.length){
            return false;
        }
        if(arr1 == null && arr2 == null){
            return true;
        }
        for(int i = 0;i < arr1.length;i++){
            if(arr1[i] != arr2[i]){
                return false;
            }
        }
        return true;
    }
    /**
     * 方法四：打印数组
     */
    private void printArray(int[] arr){
        if(arr == null){
            return ;
        }
        for(int i = 0;i < arr.length;i++){
            System.out.print(arr[i]+"\t");
        }
        System.out.println();
    }

    /**
     * 开始测试
     */

    public static void main(String[] args){
        QuickSort sort = new QuickSort();
        int testTime = 10000000;
        int maxLength = 100;
        int maxValue = 100;
        boolean flag = true;
        for (int i = 0;i <= testTime;i++){
            int[] arr1 = sort.generatedArray(maxLength,maxValue);
            int[] arr2 = sort.copyArray(arr1);
            int[] arr3 = sort.copyArray(arr1);
            sort.quickSort1(arr1);
            sort.quickSort2(arr2);
            sort.quickSort3(arr3);
            if(!sort.isEquals(arr1,arr2) || !sort.isEquals(arr2,arr3)){
                flag = false;
                break;
            }
        }
        System.out.println(flag ? "Nice!" : "Fuck!");
    }
}
