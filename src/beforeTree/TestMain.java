package beforeTree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestMain {

    public static void main(String[] args) {
        /*
        生成50道100以内的加减法算式，要求参与运算的数据限制在1-100，结果也在1-100，
        并把算式使用IO流输入到文件中

         */

        write("D:/test/test.txt");
    }

    //生成50道算式
    public static String[] generateSum() {
        String[] result = new String[50];
        StringBuilder builder = new StringBuilder();
        //生成50道100以内的加减法
        int count = 0;
        while(true) {
            if(count > 49){
                break;
            }
            //1.先生成100以内的两个数
            int num1 = (int) (Math.random() * 100);
            int num2 = (int) (Math.random() * 100);
            //如果num1和num2是0 就重新生成
            if (num1 == 0) {
                num1 = (int) (Math.random() * 100);
            }
            if (num2 == 0) {
                num2 = (int) (Math.random() * 100);
            }
            //2.生成两个数的加减法算式 要求结果不能大于100
            if (num1 + num2 <= 100) {
                //只有拼接出式子了才算数
                count++;
                builder.append(num1);
                if(num1 > num2){
                    builder.append(" - ");
                    builder.append(num2);
                    builder.append(" = ");
                    builder.append(num1 - num2+" ; ");
                }else {
                    builder.append(" + ");
                    builder.append(num2);
                    builder.append(" = ");
                    builder.append(num1 + num2+" ; ");
                }
                result[count - 1] = builder.toString();
                //每次拼接完毕之后将builder清空
                builder.delete(0,builder.length());
            }else{
                //如果相加之和大于100了 跳过这次循环 重新生成
                continue;
            }
        }
        for(String r : result){
            System.out.println(r);
        }
        return result;
    }

    public static void write(String path) {
//        OutputStream stream = null;
        BufferedWriter writer = null;
        try {
            //true表示将内容追加到已有的内容后面
            //stream = new FileOutputStream(path,true);
            //使用缓冲流
            writer = new BufferedWriter(new FileWriter(path, true));
            //写入数据
            //获取生成的结果
            String[] results = generateSum();
            //循环遍历results,将每一条结果写入到文件中
            int line = 1;
            for (String s : results) {
                //使用缓冲流实现方式
                writer.write(s+"\t");
                line ++;
                if(line > 5){
                    //每次切换新的一行写入数据
                    writer.newLine();
                    line = 1;
                }
                //将数据从流管道中写入文件
                writer.flush();
                // 普通实现方式 ：将数据读入到字节数组中
                /*byte[] b = s.getBytes();
                stream.write(b);*/
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            //关闭流
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
