package beforeTree;

import java.util.Arrays;

/**
 * 荷兰国旗问题
 */
public class HeLanFlag {

    public static void main(String[] args) {
        int[] arr = new int[]{3, 5, 6, 7, 4, 3, 5, 8};
        int[] result = netherlandsFlag(arr, 0, 7, 5);
        System.out.println(Arrays.toString(result));
    }

    /*
    荷兰国旗问题：
    问题描述：
        给定一个整数数组，给定一个值K，这个值在原数组中一定存在，要求把数组中小于K的元素放到
        数组的左边，大于K的元素放到数组的右边，等于K的元素放到数组的中间，最终返回一个整数数组，
        其中只有两个值，分别是等于K的数组部分的左右两个下标值。
        思路：
		    使用两个指针
				一个指针指向最左边，代表着小于给定值的范围索引  less
				另一个指针指向最右边，代表着大于给定值的范围索引 more
			通过i索引循环遍历数组
			循环的条件?
			  假设是让左边小于目标值的区间来推着等于区朝着大于区的方向走，则循环条件就是 i != more
                   情况一：
                     如果当前值小于给定的目标值
                        a.让当前值和less的后一个元素互换位置，
                        b.然后less向右移动一位（相当于将这个小于目标值的元素包在自己的区间里面 ）,less ++
                        c.i后移一位
                   情况二：
                     如果当前值大于给定的目标值
                        a.让当前值和more的前一个元素互换位置
                        b.然后more向左移动一位，（相当于将这个大于目标值的元素包在自己的区间里面）,more --
                        c.i不动，继续下一次比较
                   情况三：
                     如果当前值等于目标值
                        a.i++，跳过
	 */
    /*
    方法的设计：
        参数：数组，起始位置，终止位置，目标值
        返回：等于目标值的一个范围（数组）
     */
    public static int[] netherlandsFlag(int[] arr, int begin, int end, int target) {
        //先判断起始位置和终止位置是否越界
        if (begin > end) {
            //返回无效的索引位置
            return new int[]{-1, -1};
        }
        //如果起始位置和终止位置相等 则就一个值
        if (begin == end) {
            //返回自身索引位置
            return new int[]{begin, end};
        }
        //范围合理，循环排序
        //小于区的索引 从越界位开始
        int less = begin - 1;
        //大于区的索引 从越界位开始
        int more = end + 1;
        //用于移动的索引位置
        int todo = begin;
        //循环条件：todo需要在触及大于区之前停下
        while (todo < more) {
            //情况一：如果当前值小于目标值
            if (arr[todo] < target) {
                //将小于区的后一个值和当前值进行交换，小于区右扩一个位置，todo后移一个位置
                swap(arr, ++less, todo++);
            } else if (arr[todo] == target) {
                //情况二：如果当前值等于目标值
                //索引直接跳过
                todo++;
            } else {
                //情况三：当前值大于目标值
                //将大于区的前一个元素和当前值进行交换，todo索引不动
                swap(arr, --more, todo);
            }
        }
        return new int[]{less + 1, more - 1};
    }

    //用于交换的方法
    private static void swap(int[] arr, int num1, int num2) {
        //使用异或交换方法，数组中的两个值不能相等，否则交换结果为0
        if (num1 != num2) {
            arr[num1] = arr[num1] ^ arr[num2];
            arr[num2] = arr[num1] ^ arr[num2];
            arr[num1] = arr[num1] ^ arr[num2];
        }
    }

}
