package greedy;

import java.util.PriorityQueue;

/**
 * title:一块金条切成两半，是需要花费和长度数值一样的铜板
 * 比如长度为20的金条，不管怎么切都要花费20个铜板，一群人想整分整块金条，怎么分最省铜板?
 * 例如，给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
 * 如果先把长度60的金条分成10和50，花费60；再把长度50的金条分成20和30，花费50；一共花费110铜板
 * 但如果先把长度60的金条分成30和30，花费60；再把长度30金条分成10和20，花费30；一共花费90铜板
 *
 * target:给一个数组  返回分金条花费的最小代价
 */
public class LessMoneySplitMoney {
    //哈夫曼编码问题
    public int splitMoney(int[] arr){
        //先将数组中所有的值存到小根堆中
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for(int i = 0;i < arr.length;i++){
            queue.offer(arr[i]);
        }
        //取出前两个值相加 然后将相加的和再放入到队列中  循环 直至队列中剩下最后一个结合后的值
        //返回sum和
        int sum = 0;
        //临时接收值
        int cur = 0;
        while(queue.size() > 1){
            cur += queue.poll() + queue.poll();
            sum += cur;
            queue.offer(cur);
        }
        return sum;
    }
}
