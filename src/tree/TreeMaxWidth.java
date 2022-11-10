package tree;

import java.util.*;

/**
 * 计算二叉树的最大宽度
 */
public class TreeMaxWidth {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }




    /**
     * 找出二叉树的宽度最大值 （空节点也算）
     * leetCode测试链接：https://leetcode.cn/problems/maximum-width-of-binary-tree/
     * @param root
     * @return
     */

    //空节点也要算作宽度
    public int widthOfBinaryTreeHasNullNode(TreeNode root) {
        if(root == null){
            return 0;
        }
        int width = 0;
        //使用两个队列  一个队列用来存储结点  另一个用来存储结点对应的位置
        Deque<TreeNode> nodes = new LinkedList<>();
        Deque<Integer> indexs = new LinkedList<>();
        //先存储根结点
        nodes.addLast(root);
        //存储根结点的位置
        indexs.addLast(1);
        while(!nodes.isEmpty()){
            //每一层结点的左右边界（用于计算宽度）
            int left = -1;
            int right = -1;
            //获取当前层节点个数
            int curLevelNodes = nodes.size();
            //判断是否是第一个结点
            boolean isFirst = true;
            //每一层结点的宽度
            int eachLevelWidth = 0;
            //循环这些nodes  得到宽度
            while(curLevelNodes > 0){
                //获取队列中的结点
                TreeNode cur = nodes.pollFirst();
                //获取当前结点对应的位置
                int curIndex = indexs.pollFirst();
                //如果是第一个结点  让左边界等于他的位置
                if(isFirst){
                    left = curIndex;
                    isFirst = false;
                }
                //如果不是第一个结点  更新右边界
                right = curIndex;
                //更新整体宽度
                eachLevelWidth = Math.max(eachLevelWidth,(right - left) + 1);
                //放入左右孩子及其对应的索引位置  左孩子是父结点*2 右孩子是父结点*2 + 1
                if(cur.left != null){
                    nodes.addLast(cur.left);
                    indexs.addLast(curIndex * 2);
                }
                if(cur.right != null){
                    nodes.addLast(cur.right);
                    indexs.addLast((curIndex * 2) + 1);
                }
                curLevelNodes --;
            }
            //比较每一层宽度最大值
            width = Math.max(width,eachLevelWidth);
        }
        return width;
    }

    //求二叉树的宽度  不包括空节点
    //使用两个索引记录左右两边的位置
    //非哈希法
    public int noHashWidthOfBinaryTreeNoNullNode(TreeNode root){
        if(root == null){
            return 0;
        }
        int width = 0;
        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.offer(root);
        //记录当前层的最后一个结点
        //首先是根结点 默认在root上  后面的所有层都需要进行移动
        TreeNode curEnd = root;
        //记录下一层的最后一个结点（从第一个结点开始往后移动，一直移动到下一层的最后一个右结点）
        TreeNode nextLevelEnd = null;
        //记录结点数
        int nodesCount = 0;
        while(!nodes.isEmpty()){
            TreeNode cur = nodes.poll();
            //判断是否有左右孩子  如果有 设置nextLevelEnd的指向
            if(cur.left != null){
                nodes.offer(cur.left);
                nextLevelEnd = cur.left;
            }
            if(cur.right != null){
                nodes.offer(cur.right);
                nextLevelEnd = cur.right;
            }
            //结点数加加
            nodesCount ++;
            //如果当前的结点来到了这一层的最后一个结点位置  代表这一层要结束了，结算
            //进行结算
            if(curEnd == cur){
                width = Math.max(width,nodesCount);
                //节点数归0
                nodesCount = 0;
                //走到这代表上一层已经结束了，让当前层最后一个结点指针curEnd指向下一层的结点指针
                curEnd = nextLevelEnd;
            }
        }
        return width;
    }

    //求二叉树的宽度  不包括空节点
    //哈希法
    public int hashWidthOfBinaryTreeNoNullNode(TreeNode root) {
        if(root == null){
            return 0;
        }
        //最大宽度
        int max = 0;
        //使用一个队列来临时存储结点值
        Queue<TreeNode> queue = new LinkedList<>();
        //使用一个Map集合来存储对应层级的信息
        // key -- 当前结点   value -- 当前结点所在层级
        Map<TreeNode, Integer> map = new HashMap<>();
        //map中先存储进根结点  key--root value--第一层
        map.put(root, 1);
        queue.offer(root);
        //当前结点所在层级
        int curNodeLevel = 0;
        //当前正在统计的层级
        int curCountLevel = 1;
        //当前层级的结点数
        int curNodeCount = 0;
        while (!queue.isEmpty()) {
            //取出队头元素
            root = queue.poll();
            //获取当前结点所在的层级
            curNodeLevel = map.get(root);
            //判断他是否有左孩子  如果有，连同左孩子和当前孩子所在层级(队头结点的层级 + 1)存入到map中
            if (root.left != null) {
                map.put(root.left, curNodeLevel + 1);
                //队列中也存储左孩子结点
                queue.offer(root.left);
            }
            //判断是否有右孩子  如果有  连同右孩子和所在层级存入到map
            if (root.right != null) {
                map.put(root.right, curNodeLevel + 1);
                queue.offer(root.right);
            }
            //处理当前队头结点
            //如果当前队头结点的所在层级 和我正在统计的层级一致  那么我要将当前层级的结点数加一
            if (curNodeLevel == curCountLevel) {
                curNodeCount++;
            } else {
                //如果不一致，证明我当前正在统计的层级已经没有新结点了，可以结算了
                //获取上一层和当前层结点数的最大值
                max = Math.max(max, curNodeCount);
                //我正在统计的层级也要加一
                curCountLevel ++;
                //当前层级的结点数变成0  但是由于当前结点已经是下一层的了 所以变成1
                curNodeCount = 1;
            }
        }
        //循环结束后，还有一个层级未统计
        max = Math.max(max,curNodeCount);
        return max;
    }


}
