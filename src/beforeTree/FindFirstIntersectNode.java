package beforeTree;

/**
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2
 * 请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交返回null
 * 要求如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度请达到O(1)
 */
public class FindFirstIntersectNode {

    public class ListNode{
        int val;
        ListNode next;
        public ListNode(int val){
            this.val = val;
        }
    }
    //找寻两个链表第一次相交的结点
    /*
    两个链表会有三种情况（有图）
        情况一：两个链表都有环
        情况二：两个链表都没有环
        情况三：一个链表有环，另一个没有
            此时两个链表不可能相交，因为如果有相交结点，两个链表会共享一个环，所以直接返回空
     */
    //参数：两个链表的头结点
    public ListNode findFirstIntersectNode(ListNode head1,ListNode head2){
        if(head1 == null || head2 == null){
            return null;
        }
        //因为不知道链表是否有环 所以先判断链表是否有环 若有环 返回入环结点 否则返回空
        ListNode inCycleNode1 = isCycle(head1);
        ListNode inCycleNode2 = isCycle(head2);
        //分三种情况讨论：
        //情况一：如果两个链表都有环
        if(inCycleNode1 != null && inCycleNode2 != null){
            //调用方法处理
            return isHasIntersectNode(head1,head2,inCycleNode1,inCycleNode2);
        }
        //情况二：两个链表都没有环
        if(inCycleNode1 == null && inCycleNode2 == null){
            return noCycleIntersectNode(head1,head2);
        }
        //情况三：一个链表有环，另一个没环 不可能相交
        return null;
    }

    //设计一个方法 给定两个无环链表 判断两个链表是否相交，如果相交返回第一个相交的结点，否则返回空
    //参数：两个链表的头结点
    //方法：直接让两个链表从同一个长度的位置开始遍历，当两个结点相等的时候就是第一个相交结点
    // 如果结点为空了，还没有相等 就是不相交
    //由于和isHasIntersectNode()方法中的部分操作类似，所以此段代码不做很详细的解释
    private ListNode noCycleIntersectNode(ListNode head1,ListNode head2){
        //边界条件
        if(head1 == null || head2 == null){
            return null;
        }
        //使用两个结点指针来遍历两个链表
        ListNode cur1 = head1;
        ListNode cur2 = head2;
        //计算两个链表的长度差值
        int n = 0;
        //获取cur1的长度
        while(cur1.next != null){
            n ++;
            cur1 = cur1.next;
        }
        //让cur1的长度减去cur2的长度
        while(cur2.next != null){
            n --;
            cur2 = cur2.next;
        }
        /**需要注意的点：
        如果两个链表有相交的结点  则两个指针走到最后一个结点时，肯定会相等的  如果不相等就是没有相交结点
         */
        if(cur1 != cur2){
            return null;
        }
        /**如果两个链表有相交的结点*/
        //让cur1去遍历更长的那个链表  如果相减是负的 则证明cur2更长 否则cur1更长
        cur1 = n < 0 ? head2 : head1;
        cur2 = cur1 == head1 ? head2 : head1;
        //取长度的差值的绝对值让长链表先走完差值的步数
        n = Math.abs(n);
        while(n != 0){
            n --;
            cur1 = cur1.next;
        }
        //此时两个结点在同一长度 开始遍历  如果两个结点相等证明相交了，否则就没有相交的结点
        //当循环结束 如果相交了，返回的cur1就是相交结点 如果没有相交 返回的就是空
        while(cur1 != cur2){
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    //设计一个方法 给定两个有环链表 判断两个链表是否相交，如果相交返回第一个相交的结点，否则返回空
    //参数：两个链表的头结点   两个链表的入环结点
    private ListNode isHasIntersectNode(ListNode head1,ListNode head2,ListNode inCycleNode1,ListNode inCycleNode2){
        //用于移动的指针
        ListNode cur1 = head1;
        ListNode cur2 = head2;
        //记录链表长度差值
        int n = 0;
        //两个链表都有入环结点
        //需要分为两种情况：
        //  1.两个入环结点是同一个
        //      让两个链表从相同长度的位置开始走
        //      需要先判断两个链表长度是否一样 如果不一样让更长的先走完多出的长度，然后两个链表指针一起移动
        if(inCycleNode1 == inCycleNode2){
            //获取两个链表的长度差值  在到达入环结点之前
            //获得cur1的环之前的长度
            while(cur1 != inCycleNode1){
                n++;
                cur1 = cur1.next;
            }
            //用cur1的长度减去cur2的长度
            while(cur2 != inCycleNode2){
                n--;
                cur2 = cur2.next;
            }
            //让cur1来移动多于的那一截链表
            //判断n的值 如果n小于0  证明cur2更长 赋值给cur1让其去移动  否则就是cur2赋值给cur1
            cur1 = n < 0 ? head2 : head1;
            //赋值cur2
            cur2 = cur1 == head1 ? head2 : head1;
            //取n的绝对值 作为循环的条件
            n = Math.abs(n);
            while(n != 0){
                n --;
                cur1 = cur1.next;
            }
            //此时两个指针cur1 和 cur2在相同的长度上
            //同时移动两个指针直至两个指针相等 此时就是第一个相交结点
            while(cur1 != cur2){
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        }
        //  2.不是同一个
        //      使用一个指针从loop1后面开始移动，在到达loop1之前停下，如果中间经过loop2，则停下，并返回loop1
        //      因为loop2代表链表2入环了，而loop1是肯定会经过的点，且是第一个和链表1相交的点
        else{
            cur1 = inCycleNode1.next;
            while(cur1 != inCycleNode1){
                if(cur1 == inCycleNode2){
                    return cur1;
                }
                cur1 = cur1.next;
            }
            //如果没有遇到inCycleNode2 说明没有相交
            return null;
        }
    }


    //设计一个方法 判断链表是否形成了环
    //参数：，返回值：如果有环，返回第一个入环结点  如果没有返回空
    private ListNode isCycle(ListNode head){
        //如果链表为空或者只有一个结点，只有两个结点 不可能构成环
        if(head == null || head.next == null || head.next.next == null){
            return null;
        }
        //使用快慢指针的方法 快指针每次移动两步，慢指针每次移动一步
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while(fast != slow){
            //两个指针移动
            //判断下一个结点是否为空(快指针探路)
            if(fast.next == null || fast.next.next == null){
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        //两个指针第一次相遇
        //定理：当两个指针第一次相遇时，让fast指针回到头结点，然后两个指针同时以每次一步的速度移动
        //当两个指针第二次相遇的时候，该结点就是入环结点
        fast = head;
        while(fast != slow){
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }



}
