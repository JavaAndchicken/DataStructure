package hash;

import java.util.HashMap;

/**
 * 设计一种结构 在该结构中有如下三个功能
 * insert(key)  将某个key放入到该结构 做到不重复加入
 * delete(key)  将原本在该结构中的某个key移除
 * getRandom()  等概率随机返回结构中的任何一个key
 * 要求：三个方法的时间复杂度都是O(1)
 */
public class RandomPool {

    //内部类 定义该结构具有的功能  泛型为K
    public static class Pool<K>{
        //使用两个集合 一个集合存储 key --> value
        //另一个集合存储 value --> key
        //两个集合存储的元素是一样的  既可以通过key找value  也可以通过value找key
        private HashMap<K,Integer> keyMap;
        private HashMap<Integer,K> indexMap;
        //集合内元素的个数
        private Integer size;


        //初始化结构
        public Pool(){
            this.keyMap = new HashMap<>();
            this.indexMap = new HashMap<>();
            this.size = 0;
        }

        //新增方法  参数：要增加的这个元素
        public void insert(K element){
            //先判断这个元素是否在集合中存在  如果不存在再添加
            if(!keyMap.containsKey(element)){
                //在keyMap中和indexMap中都对应的添加
                keyMap.put(element,this.size);
                indexMap.put(this.size,element);
                size++;
            }
        }



        //删除方法  参数：要删除的那个元素
        //将集合中的最后一个元素覆盖到要删除的那个元素 然后将要集合中的最后一个元素删除 长度减一
        public void delete(K element){
            //判断是否在集合中存在  如果存在 再删除
            if(keyMap.containsKey(element)){
                //获得最后一个集合的位置  并将集合长度减一
                int lastIndex = -- size;
                //获得要删除的这个元素的位置
                int targetIndex = keyMap.get(element);
                //获得最后一个元素
                K lastElem = indexMap.get(lastIndex);
                //将最后一个元素添加为要删除的元素的位置（属于新增操作） key不同 无法覆盖之前的元素
                this.keyMap.put(lastElem,targetIndex);
                //在indexMap中同时修改信息  最后一个元素的索引变成targetIndex ———> 在indexMap中修改要删除的元素位置对应的值
                //因为key相同了 所以直接覆盖掉要删除的这个元素了  属于修改操作
                this.indexMap.put(targetIndex,lastElem);
                //在keyMap集合中移除要删除的目标元素
                this.keyMap.remove(element);
                //在indexMap中移除最后一条记录
                this.indexMap.remove(lastIndex);
            }
        }

        //等概率随机返回一个范围内的值
        public K getRandom(){
            //判断集合是否为空
            if(size == 0){
                return null;
            }
            //随机生成一个 [0 ~ size) 范围内的整数索引
            int randomIndex = (int) ( Math.random() * this.size );
            //返回这个索引对应的值
            return indexMap.get(randomIndex);
        }
    }
}
