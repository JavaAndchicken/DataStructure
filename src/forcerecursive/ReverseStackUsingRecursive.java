package forcerecursive;

import java.util.Stack;

/**
 * 逆序栈
 */
public class ReverseStackUsingRecursive {


    //将一个栈逆序 不能使用额外的存储空间
    public void reverseStack(Stack<Integer> stack){
        //递归出口  如果栈为空  停止
        if(stack.isEmpty()){
            return ;
        }
        //我需要一个方法  每次调用他 给我返回一个栈低元素
        //每次得到一个栈低元素
        int base = returnBase(stack);
        //使用递归 将获取到的栈低元素按照反方向存入
        reverseStack(stack);
        //将这个元素压入栈
        stack.push(base);
    }

    //返回栈低元素 并保持原栈顺序
    public int returnBase(Stack<Integer> stack){
        //先拿到栈顶元素  当最后一个栈顶元素被弹出时  栈为空  进入到if里面  且top不会记录最后一个元素的值 即不会将其压入栈 会直接返回
        int top = stack.pop();
        //拿完之后 判断栈是否为空 如果为空 直接返回这个top
        if(stack.isEmpty()){
            return top;
        }else{
            //如果不是空  继续执行此操作  并记录栈低的值
            int last = returnBase(stack);
            //栈为空后 依次返回  将上面的元素重新压回栈中  保持原栈顺序
            stack.push(top);
            return last;
        }
    }
}
