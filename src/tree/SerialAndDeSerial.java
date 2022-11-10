package tree;

import java.util.LinkedList;
import java.util.Queue;

public class SerialAndDeSerial {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 序列化和反序列化二叉树
     */

    //前序 序列化和反序列化
    public String serialize(TreeNode root) {
        StringBuilder builder = new StringBuilder();
        //使用一个队列存储所有的结点
        Queue<String> values = new LinkedList<>();
        if (root == null) {
            builder.append("null ,");
        }else{
            //values.add(String.valueOf(root.val));
            builder.append(root.val+" ,");
            builder.append(serialize(root.left)+" ,");
            builder.append(serialize(root.right)+" ,");
        }
        String result = builder.substring(0,builder.length()-1);
        return result;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data == null){
            return null;
        }
        String[] datas = data.split(",");
        Queue<String> queue = new LinkedList<>();
        for(int i = 0;i < datas.length;i++){
            queue.offer(datas[i]);
        }
        return process(queue);

        /*if(data == null){
            return null;
        }
        //data 是一个字符串
        String[] datas = data.split(",");
        String s = datas[0];
        TreeNode node = new TreeNode(Integer.valueOf(s));
        return process(node,datas,1);*/
    }

    public TreeNode process(Queue<String> queue){
        String value = queue.poll();
        if(value.equals("null")){
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(value));
        node.left = process(queue);
        node.right = process(queue);
        return node;
    }

    /*public TreeNode process(TreeNode node,String[] datas,int i){
        while(i < datas.length) {

            //获取第一个
            node = new TreeNode(Integer.parseInt(datas[i]));
            if (datas[i] == null) {
                return null;
            }
            //递归获取每一个值
            node.left = process(node,datas, ++i);
            node.right = process(node,datas, ++i);
        }
        return node;
    }*/
}
