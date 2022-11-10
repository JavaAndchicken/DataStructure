package beforeTree;

/**
 * 归并排序
 */
public class MergeSort {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //直接将nums2加到nums1后面 然后直接排序
        int mIndex = m;
        for(int i = 0,j = m;i < n && j <= nums1.length;i++){
            nums1[m++] = nums2[i];
        }

    }

    /*思路：将数组分为不越界条件下的无限小段,然后不断迭代每一个小段数组，进行merge排序，
               排序时使用一个辅助数组来存储当前小段数组的有序结果，再讲辅助数组中的结果存入到结果数组中
               不断迭代这个过程最终实现归并排序
         */
    //question: 设计一个方法,将一个数组排序
    //参数：要排序的数组 左边位置   右边位置
    public static void process(int[] arr, int L, int R) {
        //使用递归的方法  先将数组分为很多个有限范围内的小段 然后在小段内进行元素的排序  之后将排完序的结果存入到原数组中
        //递归出口  如果左右相等时 ，直接退出
        //如果左右两边相等 证明只剩下一个数 不需要排序
        if (L == R) {
            return;
        }
        //找到中间值以进行左右分区
        int mid = L + ((R - L) >> 1); // int mid = L + ((R - L)) / 2;
        //递归进行左边的分区  左边区域 是从L到Mid的位置
        process(arr, L, mid);
        //递归进行右边的分区  右边区域是从mid+1 到R的位置
        process(arr, mid + 1, R);
        //左边和右边各自分区完毕之后 将其进行排序
        //进行区内的排序 调用方法进行操作
        merge(arr, L, mid, R);
    }

    //排序
    //设计一个方法，用来给数组进行排序，怎么排？？
    /*
        参数： 数组  左边起始位置  右边起始位置
        需要一个额外的空间来临时存储排序的结果  多大？？ 根据传进来的左边和右边位置就可以计算出来了
        两个指针一起找，通过两个指针所指向的值的大小的比对，来将小的先存入到数组中，从而实现较大的效率
        指针一： P1指向最左边  p1等于谁？？  等于L   因为当前区从L开始
        指针二： P2指向中间位置的后一个元素
                两个指针将这个数组分成了左右两块区域  两个指针的移动来将这两块区域的值进行排序
        开始循环：
            分为三种情况：
               1.p1和p2都不越界
                    循环条件？？   p1 不能超过Mid吧   p2不能超过R吧
                    怎么操作：
                        通过比对arr中当前P1位置的值和P2位置的值谁小，小的先放入help数组中 实现排序
               2.p1越界，p2不越界
                    循环条件？？   p1肯定不用了   直接将p2的值放入到数组中就可以
                    操作：直接将p2的值放入到数组中就可以
               3.p2越界，p1不越界
                    循环条件？？   p2肯定不用了   直接将p1的值放入到数组中就可以
                    操作：直接将p1的值放入到数组中就可以
        循环完成干什么？？
            肯定要将help数组中的有序的值放入到原数组中
            怎么放？索引位置怎么对应？   从L的位置上开始放  使用一个变量控制移动

     */
    //具体进行排序的方法
    //参数： 需要排序的数组  左边位置  中间位置  右边位置
    public static void merge(int[] arr, int L, int M, int R) {
        //辅助数组 容量大小为每个分区的元素个数长度
        int[] help = new int[R - L + 1];
        //help的索引位置
        int i = 0;
        //p1  和  p2两个指针分管两个区域  p1管左边至M的位置  p2管右边至R的位置
        int p1 = L;
        int p2 = M + 1;
        //情况一：都不越界
        while (p1 <= M && p2 <= R) {
            //在两个指针都不越界的条件下  将两个指针所代表的值 更小的那一个放入到辅助数组内 保证辅助数组这个小范围有序
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        //如果左边已经走完了 但是右边还有 则直接给右边的放入到辅助数组即可
        //情况二：p1越界  p2不越界
        while (p2 <= R && p1 > M) {
            help[i++] = arr[p2++];
        }
        //同理 右边走完了 但是左边还没有  直接放入左边即可
        //情况三：p1不越界  p2越界
        while (p1 <= M && p2 > R) {
            help[i++] = arr[p1++];
        }
        //将辅助数组中已经有序的元素放入到结果数组中
        //但是要注意放置的位置：因为左边的是已经放置的排好序的 所以需要越过左边排好序的位置来进行放置
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }


    public int countConsistentStrings(String allowed, String[] words) {
        //因为只有小写字母 所以可以使用26个字节大小的数组来给每一个字符做一个标记
        boolean[] flag = new boolean[26];
        int result = 0;
        //先将allowed在这个数组中做上标记 然后words中的每一个word也进行同样的操作 但是仅仅是查看标记是否为true 如果不是true 证明这个数在allowed中没有 就可以停止了
        for(int i = 0;i < allowed.length();i++){
            //按照顺序将对应位置赋值为true
            int index = allowed.charAt(i) - 'a';
            flag[index] = true;
        }
        //判断是否对result进行加加
        boolean b = true;
        //在words中进行判断
        for(String word : words){
            //遍历每一个字符串的每一个字符
            for(int i = 0;i < word.length();i++){
                //判断当前字符串对应字符的位置的值是否为true
                if(!flag[ word.charAt(i) - 'a' ]){
                    //将判断改为false 不对result加加
                    b = false;
                    //不是true证明这个字符在allowed不存在  直接进行下一个字符串的判断
                    break;
                }
            }
            //如果b没有被改变 证明result需要进行加加
            if(b){
                result++;
            }
            //进行下一个字符串的判断
        }
        return result;
    }

    public static void main(String[] args) {
//        int[] arr = new int[]{5, 2, 3, 1};
//        process(arr, 0, arr.length - 1);
//        System.out.println(Arrays.toString(arr));


    }
}
