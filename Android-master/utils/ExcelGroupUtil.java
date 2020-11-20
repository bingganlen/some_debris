package com.megain.nfctemp.utils;

import android.content.Context;
import android.os.Environment;
import com.megain.label.driver.label.Label;
import com.megain.label.driver.label.LabelSetting;
import com.megain.label.driver.label.ThValues;
import com.megain.nfctemp.model.ThSetting;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


/**\
 * 生成XSL文件工具类 (使用jxl)
 * 用于分组主页上的导出数据
 */
public class ExcelGroupUtil {
    WritableFont arial14font = null;
    WritableCellFormat arial14format = null;
    WritableFont arial10font = null;
    WritableCellFormat arial10format = null;
    WritableFont arial12font = null;
    WritableCellFormat arial12format = null;

    WritableFont arial10normalFont = null;
    WritableFont arial10normalFontBold = null;
    WritableCellFormat arial10normalFormat = null;
    WritableCellFormat arial10normalFormatBold = null;

    //NumberFormat decimalNo = null;
    //NumberFormat decimalTwo = null;
    WritableCellFormat numberFormatNo = null;//new WritableCellFormat(NumberFormats.INTEGER);
    WritableCellFormat numberFormatTwo = null;//new WritableCellFormat(new NumberFormat("#.00"));

    final String UTF8_ENCODING = "UTF-8";
    final String GBK_ENCODING = "GBK";
    private volatile static ExcelGroupUtil excelGroupUtil;

    private ExcelGroupUtil(){ format(); }

    public static ExcelGroupUtil getInstance(Context context) {
        if (excelGroupUtil == null) {
            synchronized (ExcelUtils.class) {
                if (excelGroupUtil == null) {
                    excelGroupUtil = new ExcelGroupUtil();
                }
            }
        }
        return excelGroupUtil;
    }


    public void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(Alignment.CENTRE);
            arial14format.setBorder(Border.ALL, BorderLineStyle.THIN);
            arial14format.setBackground(Colour.VERY_LIGHT_YELLOW);
            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(Alignment.CENTRE);
            arial10format.setBorder(Border.ALL, BorderLineStyle.THIN);
            arial10format.setBackground(Colour.LIGHT_BLUE);
            arial12font = new WritableFont(WritableFont.ARIAL, 12);
            arial12format = new WritableCellFormat(arial12font);
            arial12format.setBorder(Border.ALL, BorderLineStyle.THIN);

            arial10normalFont = new WritableFont(WritableFont.ARIAL, 10);
            arial10normalFormat = new WritableCellFormat(arial10normalFont);
            arial10normalFormat.setAlignment(Alignment.CENTRE);//设置文字居中对齐方式;//设置垂直居中;
            arial10normalFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

            arial10normalFontBold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10normalFormatBold = new WritableCellFormat(arial10normalFontBold);
            arial10normalFormatBold.setAlignment(Alignment.CENTRE);//设置文字居中对齐方式;//设置垂直居中;
            arial10normalFormatBold.setVerticalAlignment(VerticalAlignment.CENTRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //decimalNo = new NumberFormat("#");  //#：没有则为空    0：没有则补0
            numberFormatNo = new WritableCellFormat(NumberFormats.INTEGER);
            numberFormatTwo = new WritableCellFormat(new NumberFormat("#.00")); //NumberFormats.FLOAT 会出现多个小数点后的数字
            numberFormatNo.setAlignment(Alignment.CENTRE);//设置文字居中对齐方式;//设置垂直居中;
            numberFormatNo.setVerticalAlignment(VerticalAlignment.CENTRE);
            //不能设置居中
            numberFormatTwo.setAlignment(Alignment.CENTRE);
            numberFormatTwo.setVerticalAlignment(VerticalAlignment.CENTRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    /**\
     * 生成Excel
     * 要求: 分组内标签采样时间 启动时间一致  若否, 提示"
     * List<label>  每个标签都要检查有没有更新?
     * 温湿度数据不为null  必有一条数据
     */
    public File saveRecordsExcel(List<Label> labels,String groupName,short sampleInterval) {
        if (labels.size()==0) {
            return null;
        }
        format();
        File file = null;
        Lg.e("Label有" + labels.size()+ "个");
        Date minStartTime = new Date();//时间 找出最小的启动时间进行排序
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String password = dateFormat.format(minStartTime.getTime()); //密码就是创建日期
        List<Date> startTimes = new ArrayList<>();//开始时间 (启动时间, 通过for循环)
        List<float[]> AllTempRecords = new ArrayList<>();
        List<int[]> AllRhRecords = new ArrayList<>();

        List<String> labelIds = new ArrayList<>();
        List<String> cardTypes = new ArrayList<>();
        List<Integer> isTempOnly = new ArrayList<>();
        for (Label label:labels) {
            LabelSetting labelSetting = label.getLabelSetting();
            ThValues thValues = labelSetting.getThValues();
            float[] tempRecords = thValues.getTempValues(SettingsSpProxy.get().isCentigrade() ?
                    ThValues.Type.CENTIGRADE : ThValues.Type.FAHRENHEIT); // 温度集合
            Date time = labelSetting.getBootTime().getTime();
            if (minStartTime.after(time)){ // minStartTime大于time 那就time赋给minStartTime
                minStartTime = time;
            }
            labelIds.add(label.getId());
            isTempOnly.add(thValues.isTempOnly()? 0 :1);// true 表示只有温度数据   1代表温湿度数据
            ThSetting.Order order = ThSetting.Order.fromJson(labelSetting.getText());
            cardTypes.add(order.getCardType()==null ? "": order.getCardType());

            startTimes.add(labelSetting.getBootTime().getTime());//采样时间 1分钟??
            AllTempRecords.add(tempRecords);
            AllRhRecords.add(thValues.getHumidityValues());// 湿度集合
        }

        //todo //循环条件  --  写多少行的time  找出最大的 float[] tempRecords  对比最小的启动时间
        int size = getTableSize(minStartTime,sampleInterval,AllTempRecords,startTimes);
        Lg.e("size = " + size);
        File appDir = FileUtil.INSTANCE.createDir(Environment.getExternalStorageDirectory(), "Temp&Hum Record Label");
        File cardDir = FileUtil.INSTANCE.createDir(appDir, groupName);
        String fileName = groupName + ".xlsx"; //表名要不要加 标签数量
        WritableWorkbook workbook = null;
        try {
            file = new File(cardDir.getPath() + "/" + fileName);
            Lg.i("csvFile.lastModified()= " + file.lastModified() );
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("海岳温湿度", 0);
            sheet.getSettings().setDefaultColumnWidth(12);
            sheet.setColumnView( 0 , 18 );
            //CellView cellView = new CellView();
            //cellView.setAutosize(true); //设置自动大小
            //sheet.setColumnView(1, cellView);//根据内容自动设置列宽


            //sheet.addCell(new jxl.write.Label(0, 0, fileName, arial14format));
            int labelIdSize = labelIds.size();
            jxl.write.Label writeCell = null;
            String[] a = {"卡号", "类型", "时间"};
            for (int i = 0; i < 3; i++) {
                sheet.addCell(new jxl.write.Label(0,i, a[i],arial10normalFormatBold));//column row
            }
            Lg.e("labelIdSize = " + labelIdSize);
            for (int i = 1,j=0; j < labelIdSize; i++,j++) {
                sheet.addCell(new jxl.write.Label(i,0, labelIds.get(j),arial10normalFormat));
                sheet.addCell(new jxl.write.Label(i,1, cardTypes.get(j),arial10normalFormat));// 第二行温度部分 cardType
                sheet.addCell(new jxl.write.Label(i,2, "温度",arial10normalFormat));
                Lg.e(" column= "+i +"          1:  "+ labelIds.get(j) + "  2:  " + cardTypes.get(j) + "   3:  "+ "温度");
            }

            for (int i = 0,j=0,k=0; i < labelIdSize; i++,j++) {
                if (isTempOnly.get(i) ==0){//单温
                    j++;
                    Lg.e("isTempOnly.get(i) ==0 //单温   " + isTempOnly.get(i));
                }
                k=i+1 + labelIdSize;
                sheet.addCell(addCell(k,0,labelIds.get(j)));
                sheet.addCell(addCell(k,1,cardTypes.get(j)));
                sheet.addCell(addCell(k,2,"湿度"));
                Lg.e(" column= "+k +"          1:  "+ labelIds.get(j) + "  2:  " + cardTypes.get(j) + "   3:  "+ "湿度");
            }

            //追加温湿度数据   时间  温  湿
            buildExcelData(size,sampleInterval,minStartTime,sheet,AllTempRecords,AllRhRecords,startTimes);


            //只读加密
            //SheetSettings settings  = sheet.getSettings();
            //workbook.setProtected(true);
            //settings.setProtected(true); // 只是只读模式   it only makes sheet read only.  只读却允许复制  这是个bug
            //settings.setPassword(password); //设置的密码
            workbook.write();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                    if (file != null) {
                        new ExcelPoiAddPassword().addPassWordPOI2(password,file,cardDir.getPath() + "/" + fileName);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    //追加温湿度数据   时间  温  湿
    private void buildExcelData(int size,int sampleInterval,Date minStartTime,WritableSheet sheet,
                                List<float[]> AllTempRecords,List<int[]> AllRhRecords,List<Date> startTimes) throws WriteException {

        int allTempSize = AllTempRecords.size();
        int allRHSize = AllRhRecords.size();
        int column = 0;
        int row = 3;//第四行开始
        for (int i = 0; i < size; i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(minStartTime.getTime() + i * sampleInterval * 60000L); // interval就是采集间隔
            //采样时间 第四行开始 0列
            sheet.addCell(new jxl.write.Label(column,i+3, dateFormat.format(cl.getTime()),arial10normalFormat));
        }

        for (int i = 0; i < allTempSize; i++) { // 温度列
            column++;
            float[] tempRecords = AllTempRecords.get(i);
            row = 3;
            Date date = startTimes.get(i);//当前标签的开始启动时间
            row = row + getDatePoor(minStartTime, date, sampleInterval);//相差多少个时间间隔
            Lg.e("温度数据长度: " + tempRecords.length);
            for (float tempRecord : tempRecords) {
                sheet.addCell(new jxl.write.Number(column,row, tempRecord, numberFormatTwo));//todo 这里提示numberFormatTwo为null
                //Lg.e("row= " + row + "  column="+ column + "   "+String.format("%.2f", tempRecord));
                row++;
            }
//            if (allRHSize > i){
//                int[] rhRecords= AllRhRecords.get(i);
//                int rhColumn = column + allTempSize;
//                row = 3;
//                row = row + getDatePoor(minStartTime, date, sampleInterval);//相差多少个时间间隔
//                for(int rh : rhRecords){//int rh : rhRecords
//                    sheet.addCell(new jxl.write.Label(rhColumn,row, rh+"", arial10normalFormat));
//                    Lg.e("row= " + row + "  column="+ rhColumn + "   "+rh);
//                    row++;
//                }
//            }

        }

        for (int i = 0; i < allRHSize; i++) { // 湿度列
            column++;
            row = 3;
            int[] rhRecords = AllRhRecords.get(i);
            Date date = startTimes.get(i);//当前标签的开始启动时间
            row = row + getDatePoor(minStartTime, date, sampleInterval);//相差多少个时间间隔

            Lg.e("shi度数据长度: " + rhRecords.length);
            for(int rh : rhRecords){
                sheet.addCell(new jxl.write.Number(column,row, rh, numberFormatNo));
                //Lg.e("row= " + row + "  column="+ column + "   "+rh);
                row++;
            }
        }
    }


//    private void buildData(List<Label> labels){//,List<Date> startTimes,List<float[]> AllTempRecords,List<int[]> AllRhRecords
//    }


    private jxl.write.Label addCell(int c, int r, String cont){
        return new jxl.write.Label(c,r,cont,arial10normalFormat);
    }



    /**\
     * 查找数组中不重复的元素
     */
    public void findNoRepeat(Short[] arr) {

        Set set = new HashSet<>();
        List list = new ArrayList <> ();
        for (int i = 0; i < arr.length; i++) {
            if (!set.contains(arr[i])) {
                set.add(arr[i]);
            } else {
                list.add(arr[i]);
            }
        }
        //list里面是重复的元素，不在list里面的arr元素就是唯一的
        for (int i = 0; i < arr.length; i++) {
            if (!list.contains(arr[i])) {
                System.out.println(arr[i]);
            }
        }

    }

    /**\
     * 两时间相差多少个时间间隔   有余数就进一位
     * nowDate永远大于endDate  nowDate.getTime() - endDate.getTime() 永远是正数
     * @param endDate
     * @param nowDate
     * @param sampleInterval
     * @return
     */
    public static int getDatePoor(Date endDate, Date nowDate,int sampleInterval) {

        //long nd = 1000 * 24 * 60 * 60;
        //long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff =  nowDate.getTime() - endDate.getTime();
        // 计算差多少天
        //long day = diff / nd;
        // 计算差多少小时
        //long hour = diff % nd / nh;
        // 计算差多少分钟
        long min =  diff/nm; //diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        //return day + "天" + hour + "小时" + min + "分钟";
        Lg.e("endDate :" + endDate.getTime() +"   nowDate :" + nowDate.getTime() + "    相差diff = " + diff    + "   diff/1000/60 =" + min + "分钟" + "        (min/sampleInterval)="+ (min/sampleInterval));
        if (diff==0){
            return 0;
        }
        int result = (int) (min/sampleInterval);
        int check = (int) (min%sampleInterval);

        return check==0? result : (result+1);
    }

    /**\
     * 温湿度数据表格的行数
     * @param minStartTime
     * @param sampleInterval
     * @return
     */
    private int getTableSize(Date minStartTime,int sampleInterval,List<float[]> AllTempRecords,List<Date> startTimes){
        List<Integer> temp = new ArrayList<>();
        int i=0;
        for(Date date : startTimes){
            temp.add(AllTempRecords.get(i).length + getDatePoor(minStartTime, date, sampleInterval));
            Lg.e("length = " + AllTempRecords.get(i).length  +"   前面 : " + getDatePoor(minStartTime, date, sampleInterval));
            i++;
        }
        return Collections.max(temp);
    }






    /**\
     * 是否需要对表格标签  卡片类型进行排序  原本是按添加标签到分组的默认顺序
     * 方法一:  太慢  遍bai历你的集合list1，找出ID字段值相等的对象，并将相等的字段值存入一个新的集合list2中。新集合就是你要的所有名字相同的字段值。嵌套for循环，遍历list2找出list1中ID值与其值相等的所有对象并保存到新集合list3中
     * 这里报错  先别用
     */
    private void toCardTypeSort(List<Label> labels){
        //Collections.sort(list); 自小到大排序
        int size = labels.size();
        List<String> result = new ArrayList<>();
        List<Label> lists = new ArrayList<>();
        lists.add(labels.get(0));
        result.add(ThSetting.Order.fromJson(labels.get(0).getLabelSetting().getText()).getCardType());
        if (size>1) {
            //选择排序
            for (int i=0;i<size-1;i++) {
                ThSetting.Order order = ThSetting.Order.fromJson(labels.get(i).getLabelSetting().getText());
                String cardType = order.getCardType();
                for (int j = i+1; j < size; j++) {
                    ThSetting.Order order2 = ThSetting.Order.fromJson(labels.get(j).getLabelSetting().getText());
                    if (cardType.equals(order2.getCardType())){
                        lists.add(labels.get(j));
                        result.add(order2.getCardType());
                    }
                }
            }

        }

        for (String s:result) {
            Lg.e("----------  result" + s);
        }
        labels.clear();
        for (Label l : lists) {
            labels.add(l);
        }
    }
}