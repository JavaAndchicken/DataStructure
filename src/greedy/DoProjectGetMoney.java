package greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * title:输入正数数组costs、正数数组profits、正数K和正数M
 * costs[i]表示i号项目的花费
 * profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 * K表示你只能串行的最多做k个项目
 * M表示你初始的资金
 * 说明：每做完一个项目，马上获得的收益，可以支持你去做下一个项目，不能并行的做项目。
 * 输出：最后获得的最大钱数
 *
 * target:返回做项目获得的最大受益
 */
public class DoProjectGetMoney {
    //结构
    public class Program{
        //项目受益
        private int profit;
        //项目支出
        private int capital;

        public Program(int profit,int capital){
            this.profit = profit;
            this.capital = capital;
        }
    }

    //返回获取做项目的最大受益  参数： 最多做K个项目  当前现有资金  项目受益的数组  项目支出的数组
    //思路：将所有的项目生成到一个小根堆中“锁”起来 然后在小根堆中找出当前资金可以做的项目
    //放到一个大根堆里面  挑受益多的先做。
    public int doMoreWork(int K,int M,int[] profits,int[] capitals){
        //按照花费从小到大排序的小根堆  比较器
        PriorityQueue<Program> costQueue = new PriorityQueue<>(new costComparator());
        //按照受益从大到小排序的大根堆 比较器
        PriorityQueue<Program> profitQueue = new PriorityQueue<>(new profitComparator());
        //将所有的项目“锁”到小根堆中  profits 和capitals肯定是等长的
        for(int i = 0 ;i < profits.length;i++){
            costQueue.offer(new Program(profits[i],capitals[i]));
        }
        //找出花费队列中每一个花费小于等于现有资金M的项目 解锁到大根堆中
        //但是最多只能做K个项目
        for(int i = 0;i < K;i++){
            while (!costQueue.isEmpty() && costQueue.peek().capital <= M) {
                profitQueue.offer(costQueue.poll());
            }
            //如果小根堆为空了  证明不能做项目了 直接返回M
            if(costQueue.isEmpty()){
                return M;
            }
            //结算M
            M += profitQueue.poll().profit;
        }

        return M;
    }

    //花费比较器  小根堆
    public class costComparator implements Comparator<Program>{
        @Override
        public int compare(Program o1, Program o2) {
            return o1.capital - o2.capital;
        }
    }

    //受益比较器  大根堆
    public class profitComparator implements Comparator<Program>{
        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }



    public int maxValue(int[][] events, int k) {
        int result = 0;
        //将会议按照结束时间从小到大存入到小根堆中
        PriorityQueue<int[]> eventQueue = new PriorityQueue<>(new eventComparator());
        //将events的值生成到小根堆中
        for(int[] event : events){
            eventQueue.offer(event);
        }
        int timeLine = 0;
        //弹出一个会议 记录他的结束时间
        if(!eventQueue.isEmpty()){
            int[] tmp = eventQueue.poll();
            timeLine = tmp[1];
            //这个会议已经参加 获得value
            result += tmp[2];
        }
        //弹出后续会议  如果后续会议的开始时间大于我当前结束时间  就参加这个会议 并获得会议值
        for(int i = 0;i < k;i++){
            while(!eventQueue.isEmpty() && timeLine <= eventQueue.peek()[0]){
                int[] tmp = eventQueue.poll();
                //参加这个会议
                timeLine = tmp[1];
                result += tmp[2];
            }
        }
        return result;
    }
    public class eventComparator implements Comparator<int[]>{
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[1] - o2[1];
        }
    }
}
