package com.sort;

/**
 * @author ymm
 * @create 2020--11--20:28
 */
public class SortArgritm {
    public static void main(String[] args) {
        //int array[] = {53,3,542,748,14,214,194,232,91};
        int array[]={6,1,2,7,9,3,4,5,10,8};
//        System.out.println("排序前\n");
//        Date data1=new Date();
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
//        String datestr1=simpleDateFormat.format(data1);
//        System.out.println("排序前时间为="+datestr1);
        //bubbleSort(array);
        //selectsort(array);
        //Insertsort(array);
        //shellsort(array);
        //quicksort(array, 0, 9);
//        int temp[]=new int[10];
//        mrege(array,0,9,temp);
          //RedixSort(array);

        //quiksort(array,0, array.length-1);
        for (int x : array) {
            System.out.print(x+" ");
        }


    }

    //冒泡排序算法
    public static void bubbleSort(int array[]) {
        for (int i=0;i< array.length-1;i++)
        {
            for(int j=0;j< array.length-1-i;j++){
                if(array[j]>array[j+1]){
                    int temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
            System.out.println("第"+(i+1)+"趟排序结果");
            for (int x : array) {
                System.out.print(x+" ");
            }
            System.out.println();
        }
    }


    //选择排序算法
    public static void selectsort(int array[]) {
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }   //找到最小位置的下标
            //优化,如果初始下标不变时，那就是不需要交换的，节省时间
            if (min != i) {
                int temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            } else
                continue;
        }  //排序结束
//        System.out.println("选择排序排序后数组为");
//        for(int x:array){
//            System.out.print(x+" ");
//        }
    }

public static void quiksort(int array[],int left,int right){
        int i,j,t,temp;
        if(left>right) return;
        temp=array[left];  //选择最左边的作为基准线
        i=left;
        j=right;
        while(i!=j){   //i,j是动态变化的lreft和right只是一个参考
            //从右向左找到第一个比temp小的数,少一个等于号差别太大
            while(array[j]>=temp && i<j){
                j--;
            }
            //从做向右找到第一个比temp大的数
            while (array[i]<=temp && i<j){
                i++;
            }
            //交换两者的位置
            if(i<j){
                t=array[i];
                array[i]=array[j];
                array[j]=t;
            }
        }
        //最后将基准线归位
        array[left]=array[i];
        array[i]=temp;
        //再次对基准左边的递归排序
        quiksort(array,left,i-1);
        quiksort(array,i+1,right);

}



    //插入排序算法,将数组分为两块，一块是有序表，一块无序表，将无序表逐渐插入到有序表中进行的排序
    //第一个数默认看为有序表,从第二个数卡开始
    //{2,3,4,5,6,1}这种情况非常耗时
    public static void Insertsort(int array[]) {
        for (int i = 1; i < array.length; i++) {
            int insertvalue = array[i];   //待插入数据,这里已经是从第二个数进行排序了，所以是i
            int inserindex = i - 1;         //待插入位置肯定是当前数字之前的位置
            while (inserindex >= 0 && insertvalue < array[inserindex])   //加循环后移来腾出位置
            {
                array[inserindex + 1] = array[inserindex];
                inserindex--;
            }   //end while ，找到插入位置或者数组越界，移动数组位置巧妙
            if (inserindex == i - 1)    //说明i就不需要移动，不需要执行赋值,这一步的优化有必要吗，判断不也是需要时间吗
                ;
            else
                array[inserindex + 1] = insertvalue;
            System.out.printf("第%d次排序结果为:", i);
            for (int x : array) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }

    //解决插入排序小数靠后而多次移动的问题，分组来进行处理
    public static void shellsort(int array[]) {
//        //第一轮排序，增量为5进行排序
//        for(int i=5;i< array.length;i++)
//        {
//            for(int j=i-5;j>=0;j=j-5)
//            {
//                if(array[j+5]< array[j])
//                {
//                    int temp=array[j+5];
//                    array[j+5]=array[j];
//                    array[j]=temp;
//                }
//            }
//        }
//        System.out.print("第一趟希尔排序结果为：");
//        for(int x:array)
//            System.out.print(x+" ");
//        System.out.println();
//
//        //第二轮排序，增量为2进行排序
//        for(int i=2;i< array.length;i++)
//        {
//            for(int j=i-2;j>=0;j=j-2)     //新来的数字要和之前的组里数字都比较一下来确定顺序，比如i=4，就要和j=2和j=0相比
//            {
//                if(array[j+2]< array[j])
//                {
//                    int temp=array[j+2];
//                    array[j+2]=array[j];
//                    array[j]=temp;
//                }
//            }
//        }
//        System.out.print("第二趟希尔排序结果为：");
//        for(int x:array)
//            System.out.print(x+" ");
//        System.out.println();
//
//        //第三轮排序，增量为1进行排序
//        for(int i=1;i< array.length;i++)
//        {
//            for(int j=i-1;j>=0;j=j-1)     //新来的数字要和之前的组里数字都比较一下来确定顺序，比如i=4，就要和j=2和j=0相比
//            {
//                if(array[j+1]< array[j])
//                {
//                    int temp=array[j+1];
//                    array[j+1]=array[j];
//                    array[j]=temp;
//                }
//            }
//        }
//        System.out.print("第二趟希尔排序结果为：");
//        for(int x:array)
//            System.out.print(x+" ");
//        System.out.println();

        //总结希尔排序,交换元素顺序来排序，比较慢
        for (int gap = array.length / 2; gap > 0; gap = gap / 2) {
            for (int i = gap; i < array.length; i++) {
                for (int j = i - gap; j >= 0; j = j - gap) {
                    if (array[j + gap] < array[j]) {
                        int temp = array[j];
                        array[j] = array[j + gap];
                        array[j + gap] = temp;
                    }
                }
            }
        }
    }

    //希尔排序2：移位交换，没有多次交换，速度比较快
    public static void shellsort1(int array[]) {
        for (int gap = array.length / 2; gap > 0; gap = gap / 2) {
            //从gap个元素开始逐个插入
            for (int i = gap; i < array.length; i++) {
                int j = i;
                int temp = array[j];
                if (array[j] < array[j - gap]) {
                    while (j - gap >= 0 && temp < array[j - gap])  //移动找插入位置
                    {
                        //移动
                        array[j] = array[j - gap]; //向后移动数据腾出位置
                        j = j - gap;
                    } //end while(循环结束，说明找到位置)
                    array[j] = temp;
                }
            }
        }
    }

//    public static void quicksort(int array[], int left, int right) {
//        int l = left;
//        int r = right;
//        int temp;
//        int middle = (left + right) / 2;
//        while (l < r) {
//            while (array[l] < array[middle]) {
//                l++;     //向左移动找到比中值大的元素，停止
//            }
//            while (array[r] > array[middle]) {
//                r--;
//            }
//            if (l >= r) {   //这个条件成立时，左右两边都是符合小于中间数，右边大于中间数
//                break;
//            }
//            //交换顺序
//            temp = array[l];
//            array[l] = array[r];
//            array[r] = temp;
//            if (l == r) {  //处理特殊情况，相等会发生死循环,这里必须也要写好，否则死循环，这里l=r处理结束了
//                l++;
//                r--;
//            }
//        }
//    }

   //类似单链表合并排序
    public static void mergesort(int array[],int left,int mid,int right,int temp[])
   {
       int i=left;   //i左边有序序列
       int j=mid+1;  //j表示右边有序序列
       int t=0;      //i指向temp
       //先把两边有序序列填充temp，结束后将剩余数组直接copy过去
       while (i<=mid && j<=right)
       {
           if(array[i]<=array[j])  //将较小值填入temp数组
           {
               temp[t]=array[i];
               t++;
               i++;
           }
           else{
               temp[t]=array[j];
               t++;
               j++;
           }
       }
       while(i<=mid){
           temp[t]=array[i];
           t++;
           i++;
       }
       while(j<=right){
           temp[t]=array[j];
           t++;
           j++;
       }
       //将temp拷贝到array数组
       t=0;
       int templeft=left;
       while (templeft<=right){
           array[templeft++]=temp[t++];
       }

   }

   //分解算法,归并排序分而治之
   public static void mrege(int arr[],int left,int right,int temp[] )
   {
       if(left<right)
       {
           int mid=(left+right)/2;
           //向左递归分解
           mrege(arr,left,mid,temp);
           //向右递归分解
           mrege(arr,mid+1,right,temp);
           //合并
           mergesort(arr,left,mid,right,temp);
       }
   }

   //基数排序,速度很快，就是耗内存，数据量太大时会爆内存，稳定排序，暂时不支持负数，负数可以加绝对值再反转
   public static void RedixSort(int array[])
   {
       int max=0;
       for(int i=1;i< array.length;i++)
       {
           if(array[max]<array[i])
               max=i;
       }
       //取最大值下标后怎么找到最大值的位数？tql(将数字转换位字符串判断字符长度，太巧妙了)
       int maxlen=(array[max]+"").length();
       //第一轮对数的个位进行排序,10个基数
       int [][] bucket= new int[10][array.length];
       //用一维数组记录每个桶存放的元素数量
       int []count =new int[10];

       //怎么整合呢，卡在取个位数，十位数，百位数上面了，怎么知道排序的轮数呢
       //取数组中的最大数个数
       for(int index=0,n=1;index<maxlen;index++,n*=10) {   //遍历几次
           for (int i = 0; i < array.length; i++)//将数据送入桶中
           {
               int diget = array[i] / n % 10;   //取个位数,/1/10/100 /10^i次方.用n来控制变量，本来惯性思维是用power函数的，这里用n=n*10来代替了
               bucket[diget][count[diget]] = array[i];   //相通，不行画图
               count[diget]++;
           }
           int k = -1;  //循环原数组下标
           //遍历桶中数据放回原来的数组
           for (int i = 0; i < 10; i++) {
               for (int j = 0; j < count[i]; j++) {
                   k++;
                   array[k] = bucket[i][j];
               }
               count[i] = 0;     //洗数据置0
           }
       }





//       //第一轮，对数据进行处理，遍历数字进行处理
//       for(int i=0;i< array.length;i++)//将数据送入桶中
//       {
//           int diget=array[i]%10;   //取个位数
//           bucket[diget][count[diget]]=array[i];   //相通，不行画图
//           count[diget]++;
//       }
//       int k=-1;  //循环原数组下标
//       //遍历桶中数据放回原来的数组
//       for(int i=0;i<10;i++) {
//           for (int j = 0; j < count[i]; j++) {
//               k++;
//               array[k] = bucket[i][j];
//           }
//           count[i]=0;     //洗数据置0
//       }
//       k=-1;
//
//       //第二轮,如果有个位数存在怎么写
//       for(int i=0;i< array.length;i++)//将数据送入桶中
//       {
//           int diget;
//           if(array[i]/10==0){   //如果只是各位数，那么直接置零
//               diget=array[i]/10;
//           }
//           else if(array[i]/100==0){//如果是两位数，那么处理
//               diget=array[i]/10;
//           }
//           else  //高位数将数字后两位抽出来
//           {
//               diget=(array[i]%100)/10;
//           }
//
//           bucket[diget][count[diget]]=array[i];   //相通，不行画图
//           count[diget]++;
//       }
//         //循环原数组下标
//       //遍历桶中数据放回原来的数组
//       for(int i=0;i<10;i++){
//           for(int j=0;j< count[i];j++)
//           {
//               k++;
//               array[k]=bucket[i][j];
//           }
//           count[i]=0;
//       }
//       k=-1;
//
//       //第三轮排序
//       for(int i=0;i< array.length;i++)//将数据送入桶中
//       {
//           int diget;
//           if(array[i]/10==0){   //如果只是各位数，那么直接置零
//               diget=0;
//           }
//           else if(array[i]/100==0){//如果是两位数，那么处理
//               diget=array[i]/100;
//           }
//           else  //将第3位抽出来
//           {
//               diget=(array[i]/100);
//           }
//
//           bucket[diget][count[diget]]=array[i];   //相通，不行画图
//           count[diget]++;
//       }
//       //循环原数组下标
//       //遍历桶中数据放回原来的数组
//       for(int i=0;i<10;i++){
//           for(int j=0;j< count[i];j++)
//           {
//               k++;
//               array[k]=bucket[i][j];
//           }
//           count[i]=0;
//       }
//



   }







}



