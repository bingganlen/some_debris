import com.megain.junhao.pojo.Ldata;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test3newDate耗时统计 {
    public static void main(String[] args) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startDate = df.parse("2019-11-11 11:03");

        long st = System.currentTimeMillis();
        long temp;long count;
        Date CreateTime = new Date();
        Ldata ldata;
//        for (int i = 0; i < 300000; i++) {
//            count = i*6;
//            temp = count*10000 + startDate.getTime();
//            ldata= new Ldata();
//            ldata.setConfid("12345678910111213141516171819");
//            ldata.setCollectionTime(new Date(temp ));//+5min   时间间隔
//            ldata.setCreateTime(CreateTime);
//        }
        List<Date> list = new ArrayList<>();
        for (int i = 0; i < 300000; i++) {
            count = i*6;
            temp = count*10000 + startDate.getTime();
            list.add(new Date(temp ));
        }
        for (int i = 0; i < 300000; i++) {
            ldata= new Ldata();
            ldata.setConfid("12345678910111213141516171819");
            ldata.setCollectionTime(list.get(i));//+5min   时间间隔
            ldata.setCreateTime(CreateTime);
        }
        long result = System.currentTimeMillis() - st;
        System.out.println("一共耗时:" + result + "ms");

        //30万  16MS     Ldata ldata= new Ldata();放循环里面  17MS
        //拆成2个循环  20-22S

    }
}
