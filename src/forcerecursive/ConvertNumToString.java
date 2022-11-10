package forcerecursive;

/**
 * 规定1和A对应、2和B对应、3和C对应...26和Z对应
 * 那么一个数字字符串比如"111”就可以转化为:
 * "AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 */
public class ConvertNumToString {

    public static void main(String[] args) {
        System.out.println(translateNum(1501));
    }

    public static int translateNum(int num) {
        String nums = String.valueOf(num);
        int result = convertNumToString(nums.toCharArray(), 0);
        return result;
    }

    //参数：字符串数组  起始位置
    //返回值：返回N中转化结果
    public static int convertNumToString(char[] str, int i) {
        //如果已经走到头了 则解法加一
        if (i == str.length) {
            return 1;
        }
        //如果碰到0了 则返回0  因为没有和0对应的字符
//        if (str[i] == '0') {
//            return 0;
//        }
        //如果碰到了1 就正常转
        if (str[i] == '1') {
            //将当前的i进行单转
            int res = convertNumToString(str, i + 1);
            //在不越界的条件下  将当前i和其i+1的位置进行组合转
            if (i + 1 < str.length) {
                res += convertNumToString(str, i + 2);
            }
            //返回i在1时的转换结果
            return res;
        }
        //如果碰到了2 单转正常转
        if (str[i] == '2') {
            //单转
            int res = convertNumToString(str, i + 1);
            //如果i+1的位置不越界 并且在0~6的范围内（字母只有26个）
            //将当前i和i+1进行组合转
            if (i + 1 < str.length && (str[i + 1] >= '0' && str[i + 1] <= '5')) {
                res += convertNumToString(str, i + 2);
            }
            //返回i==2时的转换结果
            return res;
        }
        //如果i在 3~9 的范围内 则直接单转
        return convertNumToString(str, i + 1);
    }

}
