package beforeTree;

/**
 * leetcode :https://leetcode.cn/problems/fu-za-lian-biao-de-fu-zhi-lcof/
 */
public class CopyListWithRandom {

    public static class Node{
        int val;
        Node next;
        Node random;

        public Node(int val){
            this.val = val;
        }
    }


    /**
     * 复杂链表的复制（链表中不仅包含next指针，还包含一个随机指向的random指针）
     */
    /*方法二：直接在原链表上进行操作
        1.将每个结点的克隆结点直接挂在原结点上，然后克隆结点去连接原结点的下一个原结点
        2.通过原结点的random指针找到克隆结点的random指向
        3.将原链表和克隆链表相分离
     */
    public Node copyRandomList(Node head){
        //边界条件
        if(head == null){
            return null;
        }
        //克隆结点并挂载
        Node cur = head;
        Node next = null;
        while(cur != null){
            //记录原结点的下一个结点
            next = cur.next;
            //cur.next指向克隆结点
            Node clone = new Node(cur.val);
            cur.next = clone;
            //克隆结点指向next结点
            clone.next = next;
            //移动cur
            cur = next;
        }
        //通过原结点的random找到克隆结点的random
        //用三个指针来记录   原结点的位置   原结点的克隆结点  原结点的下一个原结点(克隆结点的next)
        //cur回到头部
        cur = head;
        //记录克隆结点
        Node copy = null;
        while(cur != null){
            //克隆结点的指向
            copy = cur.next;
            //原结点的下一个原结点的指向
            next = cur.next.next;
            //需要判断原结点是否有random指针 如果不为空，则cur.random.next就是当前结点的克隆结点，也就是random需要指向的结点
            copy.random = cur.random == null ? null : cur.random.next;
            //移动cur
            cur = next;
        }
        //分离链表
        //记录结果  原链表的头结点的下一个就是克隆链表的头结点
        Node result = head.next;
        //同样使用三个指针记录位置
        cur = head;
        while(cur != null){
            //克隆结点的指向
            copy = cur.next;
            //原结点的下一个原结点的指向
            next = cur.next.next;
            //让cur直接连接next
            cur.next = next;
            //如果next的next不是空 那就让copy的next连接next的next  否则就指向空
            copy.next = next == null ? null : next.next;
            cur = next;
        }
        return result;
    }





    //方法一：使用集合来存储每个结点及其对应的克隆结点
    /*public Node copyRandomList(Node head) {
        if(head == null){
            return null;
        }
        Map<Node,Node> nodes = new HashMap<>();
        //循环将每个结点存入
        Node cur = head;
        //原结点当做key  克隆结点当做value
        while(cur != null){
            nodes.put(cur,new Node(cur.val));
            cur = cur.next;
        }
        //让cur回到起点 再次遍历 找到对应的指向
        cur = head;
        while(cur != null){
            //cur的克隆结点的next指向cur.next的克隆结点
            nodes.get(cur).next = nodes.get(cur.next);
            //cur的克隆结点的random指向 cur的random的克隆结点
            nodes.get(cur).random =  nodes.get(cur.random);
            //移动指针
            cur = cur.next;
        }
        return nodes.get(head);
    }
*/
}
