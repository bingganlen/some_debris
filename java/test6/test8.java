package test6;


//        Integer i01 = 59;
//        int i02 = 59;
//        Integer i03 =Integer.valueOf(59);
//        Integer i04 = new Integer(59);
//        System.out.println(i01 ==i02);
//        System.out.println(i01 ==i03);
//        System.out.println(i03 ==i04); //false
//        System.out.println(i02 ==i04);

//[编程题]字符串最后一个单词的长度
//        输入
//        hello world
//        输出
//        5

//思路 找到空格
//s.next()输入一个不含空格的字符串，// 这里如果是sc.next();那么只会输出一个hello
//        for(String s:arr) {
////            System.out.println(s);
////        }
//        //hello er mao s sha bi
import java.util.Scanner;
public class test8 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();  // 这里如果是sc.next();那么只会输出一个hello
        int n = str.length();
        if (n == 0 || n>5000) {
            return;
        }
        String[] arr = str.split(" ");
        for (int i = arr.length-1; i < arr.length; i++) {
            System.out.println(arr[i].length());
        }
    }
}

//import java.util.*;
//public class Main{
//    public static int lengthOfLast(String str) {
//        String[] s =str.split(" ");
//        return s[s.length-1].length();
//    }
//
//    public static void main(String[] args) {
//        Scanner scan = new Scanner(System.in);
//        while(scan.hasNext()){
//            String str = scan.nextLine();
//            System.out.println(lengthOfLast(str));
//        }
//    }
//}
