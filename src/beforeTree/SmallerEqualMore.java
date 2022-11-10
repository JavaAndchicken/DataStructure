package beforeTree;


public class SmallerEqualMore {

    /**
     * 根据给定的值，将链表分为左边小于目标值，中间等于目标值，右边大于目标值的三个部分
     */
    public static class ListNode{
        int val;
        ListNode next;
        public ListNode(int val){
            this.val = val;
        }
    }


    //方法二：不使用额外的空间  仅使用有限几个变量来实现。将整个链表分为三个部分：小于区，等于区，大于区
    //每个部分都有一个头指针，尾指针。然后通过值的比较，来将这三个区形成小链表。最终头尾相接形成一个排列好的大链表
    public static ListNode listPartition(ListNode head,int x){
        //边界条件
        if(head == null || head.next == null){
            return head;
        }
        //使用分区的变量：三个区 各有头尾指针 共6个指针变量
        //小于区的头节点
        ListNode smallHead = null;
        //小于区的尾节点
        ListNode smallTail = null;
        //等于区的头节点
        ListNode equalHead = null;
        //等于区的尾节点
        ListNode equalTail = null;
        //大于区的头节点
        ListNode moreHead = null;
        //大于区的尾节点
        ListNode moreTail = null;

        //遍历链表 形成各自区内的小链表
        //用于记录下一个节点的指针
        ListNode next = null;
        while(head != null){
            //先保存下一个节点的记录
            next = head.next;
            //让head的原指针断连
            head.next = null;
            //如果当前值小于目标值 则挂载到小于区内
            if(head.val < x){
                //如果当前小于区内的头尾节点还没有指向，则让他俩都指向当前节点
                if(smallHead == null){
                    smallHead = head;
                    smallTail = head;
                }else{
                    //如果已经有指向了  则让新来的节点挂到smallTail后面，并把smallTail也后移（保持尾指针的位置）
                    smallTail.next = head;
                    smallTail = head;
                }
            //同理 如果等于的话
            }else if(head.val == x){
                if(equalHead == null){
                    equalHead = head;
                    equalTail = head;
                }else{
                    equalTail.next = head;
                    equalTail = head;
                }
            //同理 如果大于的话
            }else{
                if(moreHead == null){
                    moreHead = head;
                    moreTail = head;
                }else{
                    moreTail.next = head;
                    moreTail = head;
                }
            }
            //移动head指针
            head = next;
        }
        //循环完成之后  如果小于区 等于区 大于区都存在的话 他们各自形成了自己的小链表
        //需要将他们三个链接起来形成结果链表
        //注意：可能会出现小于区不存在、等于区不存在、大于区不存在、或者任意两个区同时不存在的可能
        //所以在连链的过程中要加上严谨的判断
        //先用小于区的尾指针链接等于区的头指针
        //如果小于区存在的话
        if(smallTail != null){
            smallTail.next = equalHead;
            //再用等于区的尾部去链接大于区的头
            //但是万一等于区不存在 就没有尾部 此时尾部就应该设置为小于区的尾部
            equalTail = equalTail == null ? smallTail : equalTail;
        }
        //一定要用等于区的尾巴区链接大于区的头部
        //如果等于区存在
        //小于区或等于区总存在一个的话
        if(equalTail != null){
            equalTail.next = moreHead;
        }
        /* 如果小于区不存在 则等于区直接连接大于区
           如果等于区不存在 则小于区的尾部作为等于区的尾部区连接大于区
         */
        //最终返回：如果小于区不是空 就直接返回小于区的头节点
        //如果小于区是空，则判断等于区是否为空，如果是空就直接返回大于区，否则就返回等于区
        return smallHead == null ? (equalHead == null ? moreHead : equalHead) : smallHead;
    }



    //方法一：先将链表都放入到数组中，然后在数组中做分区操作，最后将数组中的每个节点重新串成一个链表
    /*public ListNode listPartition(ListNode head, int x) {
        //边界条件
        if(head == null || head.next == null){
            return head;
        }

        //循环链表得到链表中节点的个数
        int count = 0;
        ListNode cur = head;
        while(cur != null){
            count++;
            cur = cur.next;
        }
        //指针回到头节点
        cur = head;
        //创建一个节点数组 将所有节点放入
        //数组的长度？  循环链表找到节点的个数
        ListNode[] nodes = new ListNode[count];
        //循环数组 将节点放入
        int i = 0;
        for(i = 0;i < nodes.length;i++){
            //每次将当前节点放入到数组中
            nodes[i] = cur;
            cur = cur.next;
        }
        //对nodes进行partition
        arrPartition(nodes,x);
        //将数组串成链表
        for(i = 1;i < nodes.length;i++){
            nodes[i - 1].next = nodes[i];
        }
        //让最后一个节点指向空
        nodes[i-1].next = null;
        //返回头节点
        return nodes[0];
    }


    //partition数组
    private void arrPartition(ListNode[] arr,int x){
        //数组头部设置一个指针 用来管理小于区
        //数组尾部设置一个指针 用来管理大于区
        int small = -1;
        int bigger = arr.length;
        //用于移动的指针
        int index = 0;
        //循环数组开始比较
        //如果移动的指针一直没有碰到大于区 则一直循环
        while(index != bigger){
            //如果当前值小于目标值 则让小于区的下一个元素和当前值进行交换，当前指针移动，小于区右扩一位
            if(arr[index].val < x){
                swap(arr,index++,++small);
                //如果是等于
            }else if(arr[index].val == x){
                index++;
            }else{
                //大于
                swap(arr,--bigger,index);
            }
        }
    }
*/
    //用于数值交换的方法
    private void swap(ListNode[] arr,int index1,int index2){
        ListNode tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    public static void printLinkedList(ListNode node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode head1 = new ListNode(7);
        head1.next = new ListNode(9);
        head1.next.next = new ListNode(1);
        head1.next.next.next = new ListNode(8);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(2);
        head1.next.next.next.next.next.next = new ListNode(5);
        printLinkedList(head1);
        // head1 = listPartition1(head1, 4);
        head1 = listPartition(head1, 5);
        printLinkedList(head1);

    }

}
