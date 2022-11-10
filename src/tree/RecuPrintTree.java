package tree;

/**
 * 递归实现二叉树的先序、中序、后序遍历
 */
public class RecuPrintTree {


    public class Node {
        int val;
        Node left;
        Node right;
        public Node(int val){
            this.val = val;
        }
    }

    //先序遍历
    public void preOrderTree(Node head){
        //递归出口
        if(head == null){
            return ;
        }
        System.out.println(head.val);
        preOrderTree(head.left);
        preOrderTree(head.right);
    }

    //中序遍历
    public void inOrderTree(Node head){
        if(head == null){
            return ;
        }
        inOrderTree(head.left);
        System.out.println(head.val);
        inOrderTree(head.right);
    }
    //后序遍历
    public void postOrderTree(Node head){
        if(head == null){
            return ;
        }
        postOrderTree(head.left);
        postOrderTree(head.right);
        System.out.println(head.val);
    }

}
