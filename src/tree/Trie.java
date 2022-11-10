package tree;

/**
 * 实现前缀树
 */
public class Trie {
    //结点
    class Node{
        //当前结点被经过的次数
        private int pass;
        //当前结点是字符串中最后一个字符时，记录他的次数（当了几次最后一个字符）
        private int end;
        //记录对于下一个结点的指向（路）
        private Node[] nexts;
        //结尾结点标识
        private boolean isEnd;

        //初始化Node
        public Node(){
            pass = 0;
            end = 0;
            isEnd = false;
            //初始化26和字母的空间大小
            nexts = new Node[26];
        }
    }

    //提供一个根结点属性
    private Node root;
    //初始化前缀树
    public Trie(){
        //新建一个根结点
        root = new Node();
    }

    //将字符串挂载到前缀树上
    //参数：字符串  返回值：不需要
    public void insert(String word){
        if("".equals(word) || word == null){
            return ;
        }
        //将字符串变成字符数组  单个操作
        char[] chars = word.toCharArray();
        Node cur = root;
        //将根结点的pass值加加
        cur.pass++;
        //记录当前字符应该在nexts中存储的位置
        int index = 0;
        //循环这个字符数组  将每个字符挂载到前缀树上
        for(int i = 0;i < chars.length;i++){
            //获取存储到nexts中的位置
            index = chars[i] - 'a';
            //判断nexts中是否有当前字符的存在(是否有通往当前字符的路)  如果没有  新建
            if(cur.nexts[index] == null){
                cur.nexts[index] = new Node();
            }
            //移动cur指针
            cur = cur.nexts[index];
            //移动后
            //将当前字符的pass值加加
            cur.pass++;
        }
        //将最后一个结点的end值加加
        cur.end++;
        //当前结点的结尾标识设置为true
        cur.isEnd = true;
    }

    //删除前缀树上给定的字符串
    public void erase(String word){
        //首先  要保证word在前缀树上存在
        if(search(word) != 0){
            //从头结点开始找 一次经过的结点的pass  和  最后一个的end值减去1
            //如果当前结点的pass是0了 则直接将这个结点的空间释放掉
            //将word转成字符串
            char[] chars = word.toCharArray();
            Node cur = root;
            cur.pass--;
            int index = 0;
            for(int i = 0;i < chars.length;i++){
                //获取位置
                index = chars[i] - 'a';
                //减去他的pass
                if (--cur.nexts[index].pass == 0) {
                    //如果当前结点被消耗完毕 直接释放掉
                    cur.nexts[index] = null;
                    return ;
                }
                cur = cur.nexts[index];
            }
            //走到头后 将end减减
            cur.end --;
        }

    }

    //判断字符串是否在前缀树中存在  如果有  返回其出现次数
    //同样的 按照字符来找，如果word找完了 则返回后面一个结点的end值即可
    public int search(String word){
        if("".equals(word) || word == null){
            return 0;
        }
        //word转成字符数组
        char[] chars = word.toCharArray();
        //从根结点开始
        Node cur = root;
        int index = 0;
        for(int i = 0;i < chars.length;i++){
            //获取位置
            index = chars[i] - 'a';
            //判断当前位置上是否有当前字符的存在
            if(cur.nexts[index] == null){
                //如果没有  直接返回
                return 0;
            }
            //如果有  移动指针
            cur = cur.nexts[index];
        }
        //返回word后面一个结点的end值 就是出现次数了
        return cur.end;
    }


    //找出前缀树中 有几个是以prefix作为前缀出现的
    //同样的  直接找出他后面一个结点的出现次数  pass值  即可得到
    public int findCountOfPrefix(String prefix){
        if("".equals(prefix) || prefix == null){
            return 0;
        }
        //将prefix变成字符数组
        char[] chars = prefix.toCharArray();
        //从头结点开始循环遍历数组
        Node cur = root;
        int index = 0;
        for(int i = 0;i < chars.length;i++){
            index = chars[i] - 'a';
            //判断是否存在
            if(cur.nexts[index] == null){
                return 0;
            }
            cur = cur.nexts[index];
        }
        return cur.pass;
    }
}
