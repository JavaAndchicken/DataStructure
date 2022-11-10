package graph.questions;

import graph.graphTemplate.Edge;
import graph.graphTemplate.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * 迪杰斯特拉算法
 */
public class Dijkstra {

/**
 * ================经典版迪杰斯特拉算法==============================================
 */

    //迪杰斯特拉算法 返回一个集合（存储结点和对应距离信息）
    public HashMap<Node,Integer> dijkstra1(Node head){
        //集合 用来存储结点和距离信息
        HashMap<Node,Integer> distanceMap = new HashMap<>();
        //集合 用来存储已经计算过距离的结点  将其锁住
        HashSet<Node> handledNodes = new HashSet<>();
        //先将头结点放入到集合中
        distanceMap.put(head,0);
        //从distanceMap中获得一个最小的记录
        Node minNode = getMinNode(distanceMap,handledNodes);
        while(minNode != null) {
            //获取起点结点的距离
            int beginDistance = distanceMap.get(minNode);
            //遍历这个最小记录所有指向的结点
            for (Edge edge : minNode.edges) {
                //如果这个结点没有在distanceMap中存在  则放入这个结点及起点结点到这个结点的距离
                if (!distanceMap.containsKey(edge.to)) {
                    distanceMap.put(edge.to, beginDistance + edge.weight);
                } else {
                    //如果已经存在了  就更新他的值  (在他自己原始距离  和  起点结点到这个结点的距离  之间选择一个最小的存入）
                    distanceMap.put(edge.to, Math.min(distanceMap.get(edge.to), beginDistance + edge.weight));
                }
            }
            //处理完毕之后当前最小值锁起来
            handledNodes.add(minNode);
            //继续找下一个最小值
            minNode = getMinNode(distanceMap, handledNodes);
        }
        return distanceMap;
    }

    //设计一个方法  从distanceMap中获得一个最小的记录  且这个记录没有被处理过
    //参数：distanceMap  和  handledNodes
    //返回最小的记录
    private Node getMinNode(HashMap<Node,Integer> distanceMap,HashSet<Node> handledNodes){
        //先默认一个最大值
        int minDistance = Integer.MAX_VALUE;
        //遍历distanceMap中的所有节点  得到一个最小的
        Node minNode = null;
        for(Map.Entry<Node,Integer> entrys : distanceMap.entrySet()){
            Node node = entrys.getKey();
            int distance = entrys.getValue();
            //如果这个结点没有在set中存在  并且 distance 是小于minValue的
            if(!handledNodes.contains(node) && distance < minDistance){
                //返回这个最小值
                minNode = node;
                //赋值最短距离
                minDistance = distance;
            }
        }
        return minNode;
    }



/**
 * =============加速版迪杰斯特拉算法（自己改写堆结构）==================================
 */

    /**
     * 存储结点信息的结构体
     */
    public class RecordNode{
        Node selfNode;
        int weight;

        public RecordNode(Node selfNode,int weight){
            this.selfNode = selfNode;
            this.weight = weight;
        }
    }

    /**
     * 改写版堆结构
     */
    public class MyHeap {
        //存储元素的堆-->使用数组表示
        private Node[] heap;
        //存储结点对应索引的集合
        // key == Node  value == index
        private HashMap<Node, Integer> indexMap;
        //存储结点对应距离的集合
        // key == Node  value == distance
        private HashMap<Node, Integer> distanceMap;
        //记录当前堆上有几个结点  在进行Dijkstra操作是传递
        int size;

        //初始化堆结构
        public MyHeap(int size) {
            heap = new Node[size];
            indexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            //堆中此时有0个结点
            size = 0;
        }

        //1.设计一个方法 往堆中新增一个结点
        //参数：给这个结点    当前结点到堆顶的距离
        //返回值：直接新增 不需要返回值

        /**
         * 在新增时需要考虑一个情况  因为涉及到可能会有重复的结点问题（重复的边，要修改其权重值，使其成为最小的那个）
         * 所以在添加时要分情况讨论
         *     情况一：在堆中已经有了当前结点的存在
         *             需要比较当前结点的距离是否比指向当前结点的结点到他的距离长
         *                  如果自身的距离长的话，就需要修改成为短的 否则不用操作
         *     情况二：没有当前结点的存在
         *             直接将结点加入
         *
         */
        private void addOrUpdateOrIgnore(Node node, int distance) {
            //情况一：堆中已经有了的当前结点的存在 在堆中有效 更新其distance
            if(isValid(node)){
                //判断当前结点的自身的距离和他到堆顶的距离哪个小用哪个
                distanceMap.put(node,Math.min(distance,distanceMap.get(node)));
                //放入之后 向上heapify
                upToHeapify(indexMap.get(node));
            }
            //情况二：堆中没有当前结点的存在
            if(!isEntered(node)) {
                //将node在 indexMap 中 和 heap堆中各存一份
                heap[size] = node;
                indexMap.put(node, size);
                //存入当前结点的distance
                distanceMap.put(node, distance);
                //在存入之后 需要进行堆结构的调整 这个才是改写heap的重点
                //调用方法 调整堆结构  因为结点插入在heap的后面 所以将其进行向上heapify
                upToHeapify(size++);
            }
            //情况三：距离不小  不用处理
        }
        //2.设计一个方法 向上调整堆结构
        // 参数：当前结点的索引位置
        // 返回值： 不需要
        private void upToHeapify(int index){
            //比较当前结点和他的父结点哪个距离小 将距离小的推到上面
            while(distanceMap.get(heap[index]) < distanceMap.get(heap[(index - 1) / 2])){
                //将两者进行交换  调用交换函数
                swap(index,((index - 1) / 2));
                //继续向上heapify
                index = (index - 1) / 2;
                if(index < 0){  //越界了
                    break;
                }
            }
        }

        //3. 设计一个方法 用于两个结点的交换
        //参数：两个结点的索引位置
        //返回值：不需要
        private void swap(int index1,int index2){
            //交换时  在IndexMap中的记录也要进行交换
            //index1的value值变成index2的
            //index2的vlaue值变成index1的
            indexMap.put(heap[index1] , index2);
            indexMap.put(heap[index2] , index1);
            //交换数组中的两个结点位置
            Node tmp = heap[index1];
            heap[index1] = heap[index2];
            heap[index2] = tmp;
        }

        //4.设计一个方法 用来返回并删除堆顶元素
        //参数：不需要  返回值：返回一个装着堆顶结点相关信息的对象 RecordNode
        private RecordNode pop(){
            //获取堆顶结点的信息
            RecordNode popNode = new RecordNode(heap[0],distanceMap.get(heap[0]));
            //将头结点和尾结点进行交换  然后对尾结点进行向下的heapify
            swap(0,size - 1);
            //直接处理掉尾结点即可  将其在indexMap中和distanceMap中的记录抹除
            //indexMap中将其值改为-1  假删除
            indexMap.put(heap[size - 1],-1);
            distanceMap.remove(heap[size - 1]);
            heap[size - 1] = null;
            size --;
            //调整堆结构  从头结点开始  向下heapify
            downToHeapify(0,size);
            return popNode;
        }
        //5.设计一个方法  从头结点开始  向下heapify
        //参数：头结点的索引位置   size
        //返回值：不需要
        private void downToHeapify(int headIndex,int size){
            //获取头结点左孩子
            int left = (headIndex * 2) + 1;
            while(left < size) {
                //保证不能越界  并且 比较左右孩子的distance哪个小 就安排哪个
                int smallest = left + 1 < size && distanceMap.get(heap[left]) < distanceMap.get(heap[left + 1])
                        ? left : left + 1;
                //比较父点的距离和smallest哪个更小
                smallest = distanceMap.get(heap[headIndex]) < distanceMap.get(heap[smallest]) ? headIndex : left;
                //如果孩子没有比父结点小 就还用父结点 不用进行交换
                if (smallest == headIndex) {
                    break;
                }
                //如果需要变化 就交换
                swap(smallest,headIndex);
                //更该父结点的指向
                headIndex = smallest;
                //获取新的左孩子
                left = (headIndex * 2) + 1;
            }
        }

        //6.设计一个方法 用来判断这个结点是否进入到堆中
        //参数：当前结点
        //返回值：判断结果
        private boolean isEntered(Node curNode){
            return indexMap.containsKey(curNode);
        }

        //7.设计一个方法 判断当前结点是否在堆中有效 即indexMap中的value值未被修改为-1
        //参数：当前结点   返回值：判断结果
        private boolean isValid(Node curNode){
            return isEntered(curNode) && indexMap.get(curNode) != -1;
        }
        //8.设计一个方法 判断堆是否为空
        private boolean isEmpty(){
            return size == 0;
        }

    }



    //Dijkstra算法
    // 参数：可以出发遍历的头结点   总共结点数
    // 返回值：返回从头结点出发走到最后一个结点所得到的最短路径 用集合存放
    public Map<Node, Integer> dijkstra2(Node head, int size) {
        //1.初始化Myheap
        MyHeap myHeap = new MyHeap(size);
        //2.往堆中从head开始将结点加入  调用方法 进行结点的加入  addOrUpdateOrIgnore()
        myHeap.addOrUpdateOrIgnore(head, size);
        //记录结果
        HashMap<Node,Integer> results = new HashMap<>();
        //从堆中取值  如果堆不为空 就一直遍历
        while(!myHeap.isEmpty()) {
            //3.从堆顶中取结果  需要将结果装到一个对象中  RecordNode
            //而这个对象需要包含 结点及其权重的信息
            //调用一个方法 返回堆顶的结果 并调整好堆结构
            RecordNode record = myHeap.pop();
            //遍历这个结点的所有边指向  将边指向的结点进行同样的判断  加入到堆中
            /**
             * 因为是遍历边的操作 所以可能会有重复的结点  所以需要在新增方法中加入分情况讨论
             */
            for (Edge edge : record.selfNode.edges) {
                //                                            当前结点到堆顶的距离
                myHeap.addOrUpdateOrIgnore(edge.to, edge.weight + record.weight);
            }
            //将record加入到结果集合中
            results.put(record.selfNode,record.weight);
        }
        return results;
    }
}
