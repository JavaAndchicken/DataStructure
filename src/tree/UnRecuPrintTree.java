package tree;

import java.util.Stack;

/**
 * 非递归实现二叉树的先序，中序，后序遍历
 */
public class UnRecuPrintTree {

    public class Node{
        int val;
        Node left;
        Node right;
        public Node(int val){
            this.val = val;
        }
    }

    //先序打印二叉树
    //方法：使用一个额外空间来存储每一个结点，然后弹出打印，再存入这个结点的左孩子或右孩子
    public void preOrderTree(Node head){
        if(head == null){
            return ;
        }
        //使用栈来存储每一个结点
        Stack<Node> nodes = new Stack<>();
        //先把头结点放进去
        nodes.push(head);
        //如果栈不为空，就一直遍历栈中的结点
        while(!nodes.isEmpty()){
            Node getNode = nodes.pop();
            System.out.println(getNode.val);
            //存入头结点的左孩子和右孩子  如果左孩子或右孩子存在的话
            //注意：在存入时要先存入右孩子，因为栈中出来的顺序是左孩子先与右孩子出来 否则打印顺序会出错
            if(getNode.right != null){
                nodes.push(getNode.right);
            }
            if(getNode.left != null){
                nodes.push(getNode.left);
            }

        }
    }

    //中序打印二叉树
    //方法：使用一个栈来存储所有的结点值，先处理左子树的结点，将左子树的结点都存入到栈中
    //如果左边为空了，打印栈中的结点值并将右边子树的结点也存储到栈中
    public void inOrderTree(Node head){
        if(head == null){
            return ;
        }
        Stack<Node> nodes = new Stack<>();
        //用于移动的结点指针
        Node cur = head;
        //当栈为空 并且当前结点也是空的时候再停止循环
        //因为如果单纯的栈是空的话可能值左边子树处理完毕  还没有处理右边子树
        //如果单纯是当前结点为空的话，可能是没有孩子结点或者单边子树暂时处理完毕
        while(!nodes.isEmpty() || cur != null){
            //如果当前结点不为空  就把当前结点的存放到栈中  先处理左孩子，然后指针去到当前结点的左孩子结点
            if(cur != null){
                nodes.push(cur);
                cur = cur.left;
            }else{
                //如果cur是空了，代表局部左子树已经处理完毕 开始处理右子树
                //打印当前结点  并将指针去到下一个右子树的结点
                cur = nodes.pop();
                System.out.println(cur.val);
                cur = cur.right;
            }

        }
    }

    //后序打印二叉树
    //方法一：使用两个栈空间  s1 和 s2
    //遍历树：将数中的结点按照  头 左 右的顺序存入到s1中
    //遍历s1:将s1中的值取出放入到s2中  从s1中取出的顺序就是：头 右 左
    //最后遍历s2将其结果一一输出：其顺序就是左，右，头  和后序遍历顺序一致
    public void postOrderTree1(Node head){
        if(head == null){
            return ;
        }
        Stack<Node> s1 = new Stack<>();
        Stack<Node> s2 = new Stack<>();
        //先将头结点存入
        s1.push(head);
        //从s1中pop  同时存入到s2中，并存入当前结点的左右孩子
        while(!s1.isEmpty()){
            head = s1.pop();
            s2.push(head);
            if(head.left != null){
                s1.push(head.left);
            }
            if(head.right != null){
                s1.push(head.right);
            }
        }
        //s2中存储完毕之后  一一弹出就是后序遍历结果
        while(!s2.isEmpty()){
            head = s2.pop();
            System.out.println(head.val);
        }
    }

    //后序打印二叉树
    //方法二：使用一个栈空间，单纯地将左子树上的左孩子结点全部先放入到栈中，然后再判断其有没有孩子，如果没有直接打印，再去找有没有右孩子，循环此操作
    public void postOrderTree2(Node head){
        if(head == null){
            return ;
        }
        Stack<Node> nodes = new Stack<>();
        //先把根结点放入到栈中
        nodes.push(head);
        //记录栈顶元素  并判断其是否有左孩子
        Node cur = null;
        while(!nodes.isEmpty()){
            //记录栈顶元素
            cur = nodes.peek();
            //存储的规则是：如果结点存储到了尽头，会将最后一个结点打印并做上记号，然后cur上来，再去做判断，存储结点
            //记号就是会让尽头结点变成head  cur会去到head的父结点上，如果cur的左右孩子节点有一个等于head，就代表
            //当前父结点的左孩子已经存储过了，但是只有当右孩子等于head的时候，才能代表当前父节点的右孩子已经存储过了
            //所以要分情况讨论
            if(cur.left != null && cur.left != head && cur.right != head){
                //左孩子还没有存
                nodes.push(cur.left);
            }else if(cur.right != null && cur.right != head){
                //右孩子还没有存
                nodes.push(cur.right);
            }else{
                //左孩子右孩子均已经存过，可以打印并取出当前结点  继续处理下一个结点
                System.out.println(nodes.pop().val);
                //给当前结点做上标记
                head = cur;
            }
        }
    }
}
