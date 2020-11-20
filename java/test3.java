import com.google.common.math.BigIntegerMath;
import com.megain.junhao.util.DateTimeUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class test3 {


        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = dateTimeFormatter.parseDateTime("");
        // dateTime.toDate();


    public static void main(String[] args) throws ParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = dateTimeFormatter.parseDateTime("2019-11-11 11:03:38");
//        System.out.println(dateTime.toDate().getTime());
//        System.out.println(dateTime);


//                         2147483648    int
//                      1573441380000    需要
//                9223372036854775807    long
        Long stat = dateTime.toDate().getTime();//1573441380000
//          new Date(  +  )



        //long 和 int  都出现这种错误
//         2147425408  i==178956    2019-12-06 07:34:03
//        -2147481888  i==178957    2019-10-17 14:32:16




        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = df.parse("2019-11-11 11:03");
        long start = System.currentTimeMillis();



        // test   成员变量
        //long count;long temp;
/*        for (int l = 0; l < 300000; l++) {
            //long count = l*6;
            long temp = (long)l*6*10000 +date.getTime() ;

            Date expireTime1 = new Date(temp);

            System.out.println(df.format(expireTime1) + "     " + (temp-date.getTime())/10000 );
        }
        System.out.println(System.currentTimeMillis() - start); //   4657-4676*/



        //test2  无临时变量
        long count;long temp;
        for (int l = 0; l < 300000; l++) {
            count = l*6;
            temp = count*10000 +date.getTime() ;

            Date expireTime1 = new Date(temp);

            //System.out.println(df.format(expireTime1) + "     " + (temp-date.getTime())/10000 );
        }
        System.out.println(System.currentTimeMillis() - start); // 无临时变量  4490  4450
    }
}
