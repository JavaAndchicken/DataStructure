package beforeTree;

import java.util.ArrayList;

public class HeapStructure {

    //数组中有效元素的长度
    private int heapSize = -1;

    //存储元素的堆
    private int[] heap;

    /**
     * 堆结构的实现类
     * 大根堆
     */
    //往大根堆中插入一个元素
    public void push(int value) {

        //直接将当前值存入一个数组中，然后用heapInsert()将其挂载在大根堆（完全二叉树）上
        heap[heapSize] = value;
        heapInsert(heap, heapSize);
        //插入一个值后，让有效元素值加一
        heapSize++;
    }

    //参数：数组   有效元素长度（即当前元素的索引位置）
    public void heapInsert(int[] arr, int index) {
        //循环判断  如果新进来的值比自己的父结点要大，让交换他和父结点，
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            //再次获取交换后当前结点的父结点
            index = (index - 1) >> 1;
        }

    }

    //返回堆中的根结点，并将其移除
    /*
    思路：先直接将最后一个元素和根结点进行交换，然后对新的根结点进行heapify,将其和左右孩子进行比较，或不动，或下移
     */
    public int pop() {
        //先获取根结点
        int result = heap[0];
        //heapify 调整大根堆
        heapify(heap, 0, heapSize);
        return result;
    }

    //参数：arr   根结点的位置index   有效元素的长度（最后一个元素的索引位置）
    public void heapify(int[] arr, int index, int size) {
        if (size == -1) {
            //当前堆中没有值
            return;
        }
        //将根结点和最后一个节点进行交换
        swap(arr, index, size);
        //交换完成之后，将size缩减一，即将根结点取消挂载
        size--;
        //获取当前结点的左孩子
        int left = (index << 1) + 1;
        //循环进行比较
        //循环条件：两个
        //  1.当前根结点是否有左孩子（即左孩子索引是否在范围之内）
        //  2.当前根结点是否有孩子
        // 因为右孩子的索引肯定是比左孩子大的，所以如果左孩子存在，则两个条件都满足
        while (left < size) {
            //比较左孩子和右孩子哪个大
            //要确保右孩子存在
            //如果右孩子存在且比左孩子大 则将右孩子的索引给到largest  否则就是左孩子的索引赋值
            int largest = left + 1 < size && arr[left] < arr[left + 1] ? left + 1 : left;
            //比较当前更大的孩子是否比自己的父结点大  如果大 则继续赋值当前更大的孩子 否则就赋值父结点
            largest = arr[largest] > arr[index] ? largest : index;
            //判断largest是否等于根结点索引（即自己的孩子没有比自己大）
            if (largest == index) {
                //如果孩子不比自己大 直接退出
                break;
            }
            //如果孩子比自己大 则直接交换父结点和大的孩子结点
            swap(arr, index, largest);
            //根结点变成新的
            index = largest;
            //获取新的左孩子 进行下一次循环
            left = (index << 1) + 1;
        }

    }


    //交换两个元素的位置
    public void swap(int[] arr, int num1, int num2) {
        if (num1 != num2) {
            arr[num1] = num1 ^ num2;
            arr[num2] = num1 ^ num2;
            arr[num1] = num1 ^ num2;
        }
    }
}
