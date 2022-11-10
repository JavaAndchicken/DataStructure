package tree;

public class GetSuccessorNode {

    /**给定一个结点 找出其后继结点
     * 多一个指向父结点的指针  并且保证正确的指向父结点
     */

    public class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode parent;
        public TreeNode(int val){
            this.val = val;
        }
    }

    public TreeNode findSuccessorNode(TreeNode target){
        if(target == null){
            return target;
        }
        //两种情况  如果当前节点有右子树  则其后继节点就是右子树的最左结点
        //  如果当前结点没有右子树 并且其还是其父结点的右孩子  则后继结点就是根结点
        if(target.right != null){
            return findLeftNode(target.right);
        }else{
            //没有右子树 则沿着父结点指针一直往上找 直至找到根结点
            //先获取第一个父结点
            TreeNode parent = target.parent;
            while(parent != null && target == parent.right){
                target = parent;
                parent = target.parent;
            }
            return parent;
        }
    }

    //找出当前结点的最左结点
    public TreeNode findLeftNode(TreeNode target){
        if(target == null){
            return target;
        }
        while(target.left != null){
            target = target.left;
        }
        return target;
    }

}
