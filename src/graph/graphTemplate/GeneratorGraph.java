package graph.graphTemplate;

/**
 * 利用给定的一个矩阵来生成图 ---> 模板
 */
public class GeneratorGraph {
    //假定给的矩阵是以下样式  int[][]
    /* 边的权重值      从哪里来       指向哪里
        {
            [5,             0,          1]
            [6,             1,          2]
            ...
        }
     */
    public void generatorGraph(int[][] matrix){
        //先创建图结构
        Graph graph = new Graph();
        //循环将二维数组中的信息拆分
        for(int i = 0;i < matrix.length;i++){
            //取出第i行的第一个数据   权重值
            int weight = matrix[i][0];
            //取出第i行得第二个数据   从哪里来的指向
            int from = matrix[i][1];
            //取出第i行的第三个数据   要去哪里的指向
            int to = matrix[i][2];
            //判断当前from是否在图中存在  如果没有就创建新结点并存储到集合中
            if(!graph.nodes.containsKey(from)){
                graph.nodes.put(from,new Node(from));
            }
            //判断当前to是否在图中存在  如果没有就创建新结点并存到集合中
            if(!graph.nodes.containsKey(to)){
                graph.nodes.put(to,new Node(to));
            }
            //获取fromNode和toNode
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            //创建边
            Edge edge = new Edge(weight,fromNode,toNode);
            //fromNode指向toNode   所以fromNode的出度加加
            fromNode.out++;
            //toNode是被指的 入度加加
            toNode.in++;
            //fromNode的nexts指向toNode
            fromNode.nexts.add(toNode);
            //fromNode所拥有的边数 加加
            fromNode.edges.add(edge);
            //图新增边
            graph.edges.add(edge);
        }
    }
}
