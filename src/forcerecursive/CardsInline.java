package forcerecursive;

/**
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
 * 玩家A和玩家B依次拿走每张纸牌
 * 规定玩家A先拿，玩家B后拿
 * 但是每个玩家每次只能拿走最左或最右的纸牌
 * 玩家A和玩家B都绝顶聪明
 * 请返回最后获胜者的分数
 */
public class CardsInline {

    //题目给定一个数组
    public int win(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //先手先拿
        int firstValue = first(arr,0,arr.length - 1);
        int lastValue = last(arr,0,arr.length - 1);
        return Math.max(firstValue,lastValue);
    }

    //参数：起始位置  终止位置  在这个范围内去拿纸牌
    public int first(int[] arr, int left, int right) {
        //如果只剩下一个牌  肯定是我先拿
        if(left == right){
            return arr[left];
        }
        //两种情况：
        //情况一：我先拿左边的  拿完之后我的每次都变成了后手 但是我多了一个arr[left]值
        //范围变成了  left + 1 ~ right
        int leftValue = arr[left] + last(arr, left + 1, right);
        //情况二：先拿右边的 拿完之后我变成了后手  同时我也多了一个arr[right]
        int rightValue = arr[right] + last(arr, left, right - 1);
        //我每次获得两个之中的最大值
        return Math.max(leftValue,rightValue);
    }

    //后手拿牌
    public int last(int[] arr, int left, int right) {
        //如果只剩下一个牌  肯定是属于先手  我什么也得不到
        if(left == right){
            return 0;
        }
        //两种情况：
        //情况一：左边的牌已经被先手拿了  那我只剩下右边的牌 即范围变成了left + 1 ~ right
        int rightValue = first(arr, left + 1, right);
        //情况二：右边的牌被先手拿了  我只剩下左边的牌  范围变成了 left ~ right - 1;
        int leftValue = first(arr, left, right - 1);
        //每次获得最小的那个
        return Math.min(rightValue,leftValue);
    }
}
