package test6;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
//        String key = "a";
//
//        LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
//        multiValueMap.add(key, "a");
//        multiValueMap.add(key, "b");
//        multiValueMap.add(key, "c");
//        multiValueMap.add(key, "d");

//        List<String> values = multiValueMap.getValues(key);
//        for (String v : values) {
//            System.out.println(v); //结果 a  b  c  d
//        }

//        MultiValueMap<String, String> stringMultiValueMap = new LinkedMultiValueMap<>();
//
//        stringMultiValueMap.add("早班 9:00-11:00", "周一");
//        stringMultiValueMap.add("早班 9:00-11:00", "周二");
//        stringMultiValueMap.add("中班 13:00-16:00", "周三");
//        stringMultiValueMap.add("早班 9:00-11:00", "周四");
//        stringMultiValueMap.add("测试1天2次 09:00 - 12:00", "周五");
//        stringMultiValueMap.add("测试1天2次 09:00 - 12:00", "周六");
//        stringMultiValueMap.add("中班 13:00-16:00", "周日");
//        //打印所有值
//        Set<String> keySet = stringMultiValueMap.keySet();
//        for (String key : keySet) {
//            List<String> values = stringMultiValueMap.get(key);
//            System.out.println(StringUtils.join(values.toArray()," ")+":"+key);
//
//        }
        /**\
         * 周一 周二 周四:早班 9:00-11:00
         * 周三 周日:中班 13:00-16:00
         * 周五 周六:测试1天2次 09:00 - 12:00
         */



        try
        {
            aMethod(0);
        }
        catch (Exception ex)
        {
            System.out.printf("exception in main");
        }
        System.out.printf("finished");
    }




        public static int aMethod(int i)throws Exception
        {
            try{
                return i / 10;
            }
            catch (Exception ex)
            {
                throw new Exception("exception in a Method");
            } finally{
                System.out.printf("finally");
            }
        }















}
