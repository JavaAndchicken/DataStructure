package greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 贪心算法：
 * title:一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲，给你每一个项目开始的时间和结束的时间
 * 你来安排宣讲的日程，要求会议室进行的宣讲的场次最多，返回最多的宣讲场次
 * target:在一个时间段中 安排最多的会议
 */
public class BestArrangeInMeeting {
    //会议对象
    public static class Program{
        //会议起始时间
        private int beginTime;
        //会议结束时间
        private int endTime;

        public Program(int beginTime,int endTime){
            this.beginTime = beginTime;
            this.endTime = endTime;
        }
    }

    //设计一个方法  在同一时间内使得安排的会议数达到最大值  返回安排的会议数目
    //参数：会议数组
    public int arrange(Program[] programs){
        //先将会议全部按照从小到大排序  比较器
        Arrays.sort(programs,new programComparator());
        //时间线 来控制哪个能安排哪个不能安排
        int timeLine = 0;
        //结果数组
        int result = 0;
        //遍历会议 每次拿出结束最早的会议进行安排
        for(Program program : programs){
            //当前时间线等于当前会议的结束时间  如果下一个会议的开始时间  大于等于时间线  就安排他
            if(timeLine <= program.beginTime){
                result ++;
                timeLine = program.endTime;
            }
        }
        return result;
    }

    //比较器  将数组中的结束按照从小到大排序
    public class programComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.endTime - o2.endTime ;
        }
    }

    public int maxEvents(int[][] events) {
        if(events == null){
            return 0;
        }
        Arrays.sort(events,new eventsComparator());
        int result = 0;
        int timeLine = 0;
        for(int[] event : events){
            if(event[0] >= timeLine){
                result ++;
                timeLine = event[1];
            }
        }
        return result;
    }
    public class eventsComparator implements Comparator<int[]>{
        //将二维数组升序排列
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[0] - o2[0];
        }

    }
}
