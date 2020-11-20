import com.megain.junhao.util.DateTimeUtil;

import java.math.BigDecimal;

public class text2 {
//    public static void main(String[] args) {
////        int size = 0;
////        System.out.println(0 < size);
//        String pdfPath = "http://localhost:8080/temp/b0b9200e-92a2-3540-91ab-e21901021018/1901021018.pdf";
//
//        System.out.println(
//                pdfPath.substring(
//                        pdfPath.lastIndexOf("/",pdfPath.lastIndexOf("/")-1)
//                                +1)
//        );
//
//        //index为最后的“/”字符所在的位置
//        int index=pdfPath.lastIndexOf("/");
//        //从最后的“/”字符的前一个位置向前找“/”的位置为此index
//        index=pdfPath.lastIndexOf("/",index-1);
//        //从倒数第二的“/”字符的前一个位置向前找“/”的位置为此index
//        index=pdfPath.lastIndexOf("/",index-1);
//        //得到倒数第三个“/”之后的字符串
//        System.out.println(pdfPath.substring(index+1));
//
//    }





    static int x = 0, y = 0;
    static int a = 0, b = 0;

    //        Thread one = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                a = 1;
//                x = b;
//            }
//        });
//
//        Thread other = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                b = 1;
//                y = a;
//            }
//        });
//
//        one.start();  other.start();
//        one.join();other.join();
//        System.out.println( x + "   " + y);

    public static void main(String[] args) throws InterruptedException {

//        int temp = 0;
//        int dds = 65;
//        try {
//            temp = dds==null || dds<0 ? 0: ;
//        } catch (Exception e) {
//            temp=0;
//        }
//        System.out.println("报警延迟时间" + temp);


//       float data = 3.1f;
        //利用字符串格式化的方式实现四舍五入,保留1位小数
//        String result = String.format("%.1f",data);
//        System.out.println(result);//输出3.0
//
//        String s = "2020-05-06 - 2020-06-07";
//        System.out.println(s); //2020-05-06 - 2020-06-07
//        System.out.println(s.substring(0,10));
//        System.out.println(s.substring(13,s.length())); System.out.println(s.substring(s.length() - 1, s.length()));

        //BigDecimal longitude1 = BigDecimal.valueOf(22.287389);
        BigDecimal longitude1 = BigDecimal.valueOf(0);
        int longitude = longitude1.compareTo(BigDecimal.ZERO);
        if ( longitude ==0){//错误的经纬度   表示longitude 等于0
            System.out.println("等于00");
        }else if (longitude == 1){ //表示longitude 大于0；
            System.out.println("大于0");
        }else {
            System.out.println("longitude既不小于 也不等于0 ——" + longitude);
        }
        System.out.println("longitude既不小于 也不等于0 ——" + longitude);


//        BigDescimal bd = new BigDescimal(str1);//声明BigDescimal:
//        Integer a = bd1.compareTo(bd2);
//        a = -1,表示bd1小于bd2；
//        a = 0,表示bd1等于bd2；
//        a = 1,表示bd1大于bd2；
//        所以判断 BigDecimal判断是否为0：
//        new BigDecimal("0.00").compareTo(BigDecimal.ZERO)  == 0
    }

//    public static void main(String[] args) {
//
//        int time = 67620 + 25020;
//        //return timeutil(time);
//        String s = DateTimeUtil.formatDateTimeSChina(time);
//        System.out.println(s);
//    }

//    public static void main(String[] args) {
//        Map map = new HashMap();
//        map.put("s1",0);
//        map.put("s2",0);
//        map.put("s1",5>4?1:0);
//        map.put("s2",4>4?1:0);
//        map.put("s3",5>4?1:0);
//        System.out.println(map);
//    }

}
