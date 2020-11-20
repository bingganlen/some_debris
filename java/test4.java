import com.megain.junhao.util.DateTimeUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test4 {
    public static void main(String[] args) throws ParseException {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = df.parse("2019-11-11 11:03:00");//1573401780000
////        System.out.println(date.getTime());
//        System.out.println(DateTimeUtil.dateToStr(date));

///long比较大小测试
//        Long start = 1573401780000L;
//        Long end = 1573401780000L;
//        if (start == end) {
//            System.out.println("==");
//        }
//        if (start.longValue() == end.longValue()) {
//            System.out.println("longValue");
//        }
//        if (start.equals(end)) {
//            System.out.println("equals");
//        }

        long s1 = 1592550996000L;
        long s2 = 1592551002000L;
        System.out.println(DateTimeUtil.dateToStr(new Date(s1)));
        System.out.println(DateTimeUtil.dateToStr(new Date(s2)));




        //new Date(0)，这个括号里的0代表的就是自1970年。你定义的就是这个日期。要当前时间还是置空吧。
        //导入包的时候别用import java.sql.Date;换成import java.util.Date;
        //“yyyy-MM-dd HH:mm:ss”  需要年的存在    没有年那么年可能是1970年  MM-dd-HH-mm
        //还与时区有关
        Date date = new Date(0);
        System.out.println(date);
        //java Date类可以显示1970年之前的时间吗   可以！~但是以负数显示
        System.out.println(new Date(-100000000));//这个就变成1969年了

        //时间戳转成日期，解决总是日期总是1970年的问题
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//        return sdf.format(date);
        //改成下面   new Date一下    10位时间戳改为13位   10位时间戳只精确到秒
//        public static String getDateStringByTimeSTamp(Long timeStamp,String pattern){
//            String result = null;
//            Date date = new Date(timeStamp*1000);
//            SimpleDateFormat sd = new SimpleDateFormat(pattern);
//            result = sd.format(date);
//            return result;
//        }
    }
}
