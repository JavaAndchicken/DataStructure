package beforeTree;

public class IsPalindromeList {

    public static class ListNode{
        Integer val;
        ListNode next;
        public ListNode(Integer val){
            this.val = val;
        }
    }


    //方法三：先用快慢指针找到链表的中间位置 然后从中间位置将链表分为两部分 让右边的指针反指
    //反指之后，在链表的左边部分的头部设置一个指针，在右边链表的尾部设置一个指针
    //让两个指针同时移动，并在移动的过程中进行value指的比对，如果不是回文，直接返回false 如果是回文，在最后将右边部分的指针指回去

    public boolean isPalindrome(ListNode head){
        //边界条件
        if(head == null || head.next == null){
            return true;
        }
        //先用快慢指针找到链表的中间位置
        ListNode slow = head;
        ListNode fast = head;
        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        //找到中间位置之后  拆分链表，让fast做右边链表的头节点，slow做左边链表的尾节点
        fast = slow.next;
        //断开slow和fast的链接
        slow.next = null;
        //用一个临时节点记录fast后面的节点，然后指针反指
        ListNode tmp = null;
        while(fast != null){
            tmp = fast.next;
            //将fast的指向反过来
            fast.next = slow;
            //移动指针 处理后面的节点
            slow = fast;
            fast = tmp;
        }
        //反转指针完毕后
        /*此时的链表的结构是：
         *            			  slow
         * 1 ——> 2 ——> 3 <—— 2 <—— 1    fast == null ,  tmp == null
         */
        //让fast去到整个链表的头节点
        fast = head;
        //让临时指针记录尾节点
        tmp = slow;
        //记录结果
        boolean result = true;
        //开始比较是否回文
        while(fast != null && slow != null){
            if(fast.val != slow.val){
                result = false;
            }
            fast = fast.next;
            slow = slow.next;
        }
        //在返回结果之前  将指针指回来
        /*此时的链表结构：
         *             fast		   tmp
         * 	1 ——> 2 ——> 3 <—— 2 <—— 1
         * 			   slow
         */
        //让fast 去到tmp的下一个节点（即前面一个节点)
        fast = tmp.next;
        //tmp指向空
        /*此时的链表结构：
         *                  fast   tmp
         * 	1 ——> 2 ——> 3 <—— 2     1
         * 			   slow
         */
        tmp.next = null;
        while(fast != null){
            //记录fast的前一个节点 以便移动指针
            slow = fast.next;
            //回指
            fast.next = tmp;
            //tmp前移
            tmp = fast;
            //fast位置前移
            fast = slow;
        }

        return result;
    }


    //判断给定链表是否为回文链表

    //方法二：快慢指针
    /*public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null){
            return true;
        }
        //快指针
        ListNode fast = head;
        //慢指针
             为什么slow = head.next ?
                            slow
    例1：奇数个链表：             1 ——> 2 ——> 3 ——> 2 ——> 1
    如果fast和slow初始在一个位置: fast
                                        slow
    经过循环完毕之后：             1 ——> 2 ——> 3 ——> 2 ——> 1
                                                        fast
    如果这个时候从slow开始将结点压入栈中，在后面弹栈和链表比对的时候会返回false,但很明显这个链表是回文链表
                                slow
    例2：偶数个链表：              1 ——> 2 ——> 3 ——> 3 ——> 2 ——> 1
                                fast
                                            slow
    进过循环完毕之后：              1 ——> 2 ——> 3 ——> 3 ——> 2 ——> 1
                                                        fast
    如果这个时候从slow开始将结点压入栈中，后面弹栈时同样无法比对成功，且这个链表也是回文链表。
    所以得出结论，slow应该在fast的后一个开始

            ListNode slow = head.next;
            //循环移动快慢指针
            while(fast.next != null && fast.next.next != null){
                slow = slow.next;
                fast = fast.next.next;
            }
            //从slow的位置开始压栈
            Stack<ListNode> input = new Stack<>();
            while(slow != null){
                input.push(slow);
                slow = slow.next;
            }
            //弹栈，以栈的长度为条件，和链表的值作比较 如果是回文链表，则栈中元素全部弹出，刚好比对完成
            ListNode tmp = head;
            while(!input.isEmpty()){
                if(input.pop().val != tmp.val){
                    return false;
                }
                tmp = tmp.next;
            }
            return true;
    }*/


    /* 方法一：暴力破解
public boolean isPalindrome(ListNode head) {
        //如果链表为空或者只有一个结点，直接返回true
        if(head == null || head.next == null){
            return true;
        }
        Stack<ListNode> value = new Stack<>();
        //使用一个临时指针做移动
        ListNode tmp = head;
        //循环将链表中的元素放入到栈中
        while(tmp != null){
            value.push(tmp);
            tmp = tmp.next;
        }
        //依次出栈 再次和链表中的值做对比
        ListNode compare = head;
        while(!value.isEmpty()){
            if(value.pop().val != compare.val){
                return false;
            }
            compare = compare.next;
        }
        return true;
    }
    */
}
