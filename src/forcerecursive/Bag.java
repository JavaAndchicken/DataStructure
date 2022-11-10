package forcerecursive;

/**
 * 背包问题
 * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表 i号物品的重量和价值
 * 给定一个正数bag，表示一个载重bag的袋子，装的物品不能超过这个重量
 * 返回能装下的最大价值
 */
public class Bag {

    //参数：背包数组  价值数组  起始位置（i号物品） 载重袋子  已经装了多少货物
    //返回值：能装下的最大价值
    public int bagMaxCapacity(int[] weights,int[] values,int i,int bag,int alreadyWeight){
        //递归出口
        //1. 如果背包中的物品已经大于最大载重了 返回0
        if(alreadyWeight > bag){
            return 0;
        }
        //2. 如果物品数组已经空了 什么也拿不到 返回0
        if(i == weights.length){
            return 0;
        }
        //将要当前物品的和不要当前物品的都跑一遍 然后返回其最大值
        //                 不要当前物品的
        return Math.max(bagMaxCapacity(weights,values,i + 1,bag,alreadyWeight),
        //                 要当前物品的  当前物品的价值 加上下一个要的物品的价值
                        values[i] + bagMaxCapacity(weights,values,i + 1,bag,alreadyWeight + weights[i]));
    }

}
