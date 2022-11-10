package beforeTree;

import java.util.Arrays;
import java.util.PriorityQueue;

public class SortArrayDistanceLessK {

    /**
     * 堆排序扩展（小根堆相关）
     * 已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离可以不超过K，
     * 并且k相对于数组来说比较小。请选择一个合适的排序算法针对这个数据进行排序。
     */

    public static void main(String[] args) {
        int[] arr = new int[]{3,5,7,9,4,2,1,9,2};
        sortArrayDistanceLessK(arr,6);
        System.out.println(Arrays.toString(arr));
    }
    /*
    思路：
        使用小根堆的方式，因为每个元素移动的距离不超过K，所以先循环将前K个值放入到小根堆中，
        然后再次循环，依次从小根堆中取出最小值，从0开始放入到数组中，开始排序。在取出的同时，
        将k之后的元素也放入到小根堆中，以此一边取，一边存，保证了移动距离不超过K
     */
    public static void sortArrayDistanceLessK(int[] arr, int k) {
        if(k == 0){
            return ;
        }
        //创建小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        //循环将前k个值放入到小根堆中
        //因为index需要记录k后面元素的位置，所以将其写在外面，延长声明周期
        int index;
        //为了防止给的k值过大以至于超过了数组的长度 用一个判断
        for (index = 0; index <= Math.min(k - 1, arr.length - 1); index++) {
            heap.offer(arr[index]);
        }
        //前k个值放入进去之后 再次循环  取出最小值存入到数组中，并将k后面的值也放入
        //因为还剩下k之后的元素，所以判断条件应由index来做
        //且在存入进了一部分数之后，i的位置也要记录下来 所以需要写在外面
        int i;
        for (i = 0; index < arr.length; i++) {
            //这里需要先存入再取出 因为如果k后面是最小值，则在存入的时候会放到根结点上，然后在后面直接取出，进行排序
            //将k后面的元素放入到小根堆中
            heap.offer(arr[index]);
            //从小根堆中取出值放入到数组中，开始排序
            arr[i] = heap.poll();
            //索引自增
            index++;
        }
        //两次循环结束后，小根堆中可能还有值，都取出放入数组中
        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }
    }
}
