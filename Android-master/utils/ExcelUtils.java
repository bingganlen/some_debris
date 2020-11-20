package com.megain.nfctemp.utils;

import android.content.Context;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.megain.label.driver.label.FirmwareInfo;
import com.megain.label.driver.label.Label;
import com.megain.label.driver.label.LabelSetting;
import com.megain.label.driver.label.ThValues;
import com.megain.label.driver.util.BcdCodeUtil;
import com.megain.nfctemp.R;
import com.megain.nfctemp.main.CurApp;
import com.megain.nfctemp.model.ThSetting;
import com.megain.nfctemp.model.dao.LabelInfo;
import com.megain.nfctemp.model.dao.LabelInfoDao;
import com.megain.nfctemp.model.dao.LabelInfoDatabase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 导出Excel文件的工具类
 */
public class ExcelUtils {
    WritableFont arial14font = null;
    WritableCellFormat arial14format = null;
    WritableFont arial10font = null;
    WritableCellFormat arial10format = null;
    WritableFont arial12font = null;
    WritableCellFormat arial12format = null;

    final String UTF8_ENCODING = "UTF-8";
    final String GBK_ENCODING = "GBK";
    private volatile static ExcelUtils excelUtils;

    private ExcelUtils() {
        format();
    }

    public static ExcelUtils getInstance(Context context) {
        if (excelUtils == null) {
            synchronized (ExcelUtils.class) {
                if (excelUtils == null) {
                    excelUtils = new ExcelUtils();
                }
            }
        }
        return excelUtils;
    }

    public void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);
            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(jxl.format.Colour.LIGHT_BLUE);
            arial12font = new WritableFont(WritableFont.ARIAL, 12);
            arial12format = new WritableCellFormat(arial12font);
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        } catch (WriteException e) {

            e.printStackTrace();
        }
    }

    /**
     * 导出温湿度数据excel表格，包括温度数据、湿度数据（如果有，否则输出N/A）
     *
     * @param label
     * @return
     */
    public boolean saveRecordsExcel(Label label) {
        if (label == null) {
            return false;
        }
        LabelSetting labelSetting = label.getLabelSetting();
        ThValues thValues = labelSetting.getThValues();
        float[] tempRecords = thValues.getTempValues(SettingsSpProxy.get().isCentigrade() ?
                ThValues.Type.CENTIGRADE : ThValues.Type.FAHRENHEIT);
        int[] rhRecords = thValues.getHumidityValues();
        Date startTime = labelSetting.getBootTime().getTime();
        File appDir = FileUtil.INSTANCE.createDir(Environment.getExternalStorageDirectory(), "T&H IoT");
        File cardDir = FileUtil.INSTANCE.createDir(appDir, label.getId());
        String[] a = {"Position", "Time", "Temp", "RH"};
        String fileName = label.getId() + "Records.xls";
        initExcel(cardDir.getPath() + "/" + fileName, a);
        List<ArrayList> excelData = buildExcelData(tempRecords, rhRecords, startTime, labelSetting.getSampleInterval());
        return writeObjListToExcel(excelData, cardDir.getPath() + "/" + fileName);
    }

    private List<ArrayList> buildExcelData(float[] tempValues, int[] rhValues, Date startDate, short interval) {
        List<ArrayList> cardRecords = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        for (int i = 0; i < tempValues.length; i++) {
            ArrayList<String> excelData = new ArrayList<>();
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(startDate.getTime() + i * interval * 60000L);
            excelData.add(String.valueOf(i + 1));//行号
            excelData.add(dateFormat.format(cl.getTime()));//采样时间
            excelData.add(String.format("%.1f", tempValues[i]));//温度数据
            excelData.add(rhValues == null ? "N/A" : String.valueOf((int) rhValues[i]));//适度数据
            cardRecords.add(excelData);
        }
        return cardRecords;
    }

    @Nullable
    public File saveRecordsCsv(Label label) {
        if (label == null) {
            return null;
        }
        String cardId = label.getId();
        LabelSetting labelSetting = label.getLabelSetting();
        ThValues thValues = labelSetting.getThValues();
        ThSetting.Order order = ThSetting.Order.fromJson(labelSetting.getText());
        long startTime = labelSetting.getBootTime().getTime().getTime();
        File appDir = FileUtil.INSTANCE.createDir(Environment.getExternalStorageDirectory(), "Temp&Hum Record Label");
        File cardDir = FileUtil.INSTANCE.createDir(appDir, label.getId());
        String fileName = order != null ? label.getId() + "-" + order.getNumber().trim() + ".csv" : label.getId() + ".csv";
        try {
            File csvFile = FileUtil.INSTANCE.createFile(cardDir, fileName);
            Lg.i("csvFile.lastModified()= " + csvFile.lastModified() + " thValues.lastModified()= " + thValues.lastModified());
            LabelInfoDao labelInfoDao = LabelInfoDatabase.Companion.getInstance(CurApp.getAppContext()).labelInfoDao();
            LabelInfo info = labelInfoDao.getLabelInfoById(label.getId());
            boolean isUpdated = info != null && info.getLastExportCsv() != null
                    && info.getLastExportCsv().getTime() < thValues.lastModified();
            if (csvFile.length() > 0 && isUpdated) {//已经导出过，并且数据没有更新过
                Lg.i("不需要重复导出");
                return csvFile;//温湿度数据已经导出就不必要再次导出了
                //可能漏洞，如果用户手动修改了CSV文件的内容，但是没有重新读取数据，修改的内容会一直保存，再次调用导出方法不会重新导出
            }


            float[] tempRecords = thValues.getTempValues(SettingsSpProxy.get().isCentigrade() ?
                    ThValues.Type.CENTIGRADE : ThValues.Type.FAHRENHEIT);
            int[] rhRecords = thValues.getHumidityValues();
            short interval = labelSetting.getSampleInterval();
            List<String> csvLines = buildCsvData(tempRecords, rhRecords, startTime, interval, order, labelSetting, cardId, label);
            saveCsvLine(csvFile, csvLines);
            if (info == null) {
                info = new LabelInfo(label);
            }
            info.setLastExportCsv(new Date(System.currentTimeMillis()));
            labelInfoDao.insertLabel(info);
            return csvFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> buildCsvData(float[] tempRecords, int[] rhRecords, long startTime, short interval, ThSetting.Order order, LabelSetting labelSetting, String cardId, Label label) {
        List<String> csvLines = new ArrayList<>();
        csvLines.addAll(buildCardSettingInfo(order, labelSetting, cardId, label));
        String separated = ",", enter = "\n";
        // String titleLine = "Position,Time,Temp(℃),RH(%)";
        Context context = CurApp.getAppContext();
        String tempUnit = context.getString(SettingsSpProxy.get().isCentigrade() ? R.string.tempUnit : R.string.tempFahrenheitUnit);
        String titleLine = context.getString(R.string.csvDataNb) + separated
                + context.getString(R.string.csvTime) + "(" + BcdCodeUtil.getCurrentTimeZone() + ")" + separated
                + context.getString(R.string.csvTemp) + "(" + tempUnit + ")" + separated
                + context.getString(R.string.csvHumidity) + separated;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        csvLines.add(titleLine);
        Calendar cl = Calendar.getInstance();
        for (long i = 0; i < tempRecords.length; i++) {
            cl.setTimeInMillis(startTime + i * interval * 60000);//i * interval * 60000在i=35793时会超过int的max value
            String lineString = (i + 1) + separated;//行号
            lineString += dateFormat.format(cl.getTime()) + separated;//采样时间
            lineString += String.format("%.1f", tempRecords[(int) i]) + separated;//温度数据
            lineString += rhRecords == null ? "N/A" : rhRecords[(int) i] + separated;//适度数据
            csvLines.add(lineString);
        }
        return csvLines;
    }

    List<String> buildCardSettingInfo(@NonNull ThSetting.Order order, LabelSetting labelSetting, String cardId, Label label) {
        List<String> csvLines = new ArrayList<>();
        FirmwareInfo firmwareInfo = label.getFirmwareInfo();
        Context context = CurApp.getAppContext();
        csvLines.add(context.getString(R.string.csvCardSettingTitle));
        csvLines.add(context.getString(R.string.CardId) + "," + cardId);
        if (order != null) {
            csvLines.add(context.getString(R.string.waybillNB) + context.getString(R.string.colon) + "," + order.getNumber());
            csvLines.add(context.getString(R.string.waybillTime) + "," + order.getWaybillTime());
            csvLines.add(context.getString(R.string.companyName) + "," + order.getCompanyName());
            csvLines.add(context.getString(R.string.produceName) + "," + order.getProduceName());
            csvLines.add(context.getString(R.string.waybillCreator) + "," + order.getCreator());
            csvLines.add(context.getString(R.string.shipAddress) + "," + order.getSendAddr());
            csvLines.add(context.getString(R.string.receiptAddress) + "," + order.getReceiptAddr());
        } else {
            csvLines.add(context.getString(R.string.waybillNB) + context.getString(R.string.colon) + ",");
            csvLines.add(context.getString(R.string.waybillTime) + ",");
            csvLines.add(context.getString(R.string.companyName) + ",");
            csvLines.add(context.getString(R.string.produceName) + ",");
            csvLines.add(context.getString(R.string.waybillCreator) + ",");
            csvLines.add(context.getString(R.string.shipAddress) + ",");
            csvLines.add(context.getString(R.string.receiptAddress) + ",");
        }
        String tempUnit = context.getString(SettingsSpProxy.get().isCentigrade() ? R.string.tempUnit : R.string.tempFahrenheitUnit);
        float maxTemp = SettingsSpProxy.get().isCentigrade() ? labelSetting.getMaxTemp() : SettingsSpProxy.TempUtil.toFahrenheit(labelSetting.getMaxTemp());
        float minTemp = SettingsSpProxy.get().isCentigrade() ? labelSetting.getMinTemp() : SettingsSpProxy.TempUtil.toFahrenheit(labelSetting.getMinTemp());
        csvLines.add(context.getString(R.string.delayTime) + "," + labelSetting.getDelayTime() + context.getString(R.string.min));
        csvLines.add(context.getString(R.string.intervalSamp) + context.getString(R.string.colon) + "," + labelSetting.getSampleInterval() + context.getString(R.string.min));
        csvLines.add(context.getString(R.string.MaxTemp) + context.getString(R.string.colon) + "," + maxTemp + tempUnit);
        csvLines.add(context.getString(R.string.MinTemp) + context.getString(R.string.colon) + "," + minTemp + tempUnit);
        if (labelSetting.getThAttribute().equals(LabelSetting.THAttribute.TEMP_HUMIDITY)) {
            csvLines.add(context.getString(R.string.maxHumidity) + context.getString(R.string.colon) + "," + labelSetting.getMaxHumidity() + "%RH");
            csvLines.add(context.getString(R.string.minHumidity) + context.getString(R.string.colon) + "," + labelSetting.getMinHumidity() + "%RH");
        }
        csvLines.add(context.getString(R.string.remarkInfo) + "," + (order != null ? order.getRemark() : ""));
        csvLines.add("");
        csvLines.add(context.getString(R.string.firmwareInfo));
        csvLines.add(context.getString(R.string.firmVersion) + "," + firmwareInfo.getRemoteFirmwareVersion());
        csvLines.add(context.getString(R.string.maxRoom) + "," + firmwareInfo.getFlashCapacity() + "KB");
        csvLines.add(context.getString(R.string.transmit) + "," + firmwareInfo.getTransmitVelocity() + "kpbs");
        csvLines.add(context.getString(R.string.hardwareVersion) + "," + firmwareInfo.getHardwareVersion() + " ");
        csvLines.add(context.getString(R.string.electricity) + "," + label.getLastPower() + "V");
        csvLines.add(context.getString(R.string.stcFirmwareVersion) + "," + firmwareInfo.getStcFirmwareVersion() + " ");
        csvLines.add("");
        return csvLines;
    }

    boolean saveCsvLine(File file, List<String> csvLines) {
        FileOutputStream fos;
        OutputStreamWriter osw;
        BufferedWriter bufferedWriter = null;
        try {
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, "GBK");
            bufferedWriter = new BufferedWriter(osw);
            int size = csvLines.size();
            for (int i = 0; i < size; i++) {
                bufferedWriter.write(csvLines.get(i) + "\n");
            }
            bufferedWriter.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private boolean initExcel(String fileName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("温湿度表", 0);
            sheet.addCell(new jxl.write.Label(0, 0, fileName,
                    arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new jxl.write.Label(col, 0, colName[col], arial10format));
            }
            workbook.write();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private <T> boolean writeObjListToExcel(List<T> objList, String fileName) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writeBook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(fileName));
                Workbook workbook = Workbook.getWorkbook(in);
                writeBook = Workbook.createWorkbook(new File(fileName),
                        workbook);
                WritableSheet sheet = writeBook.getSheet(0);
                for (int j = 0; j < objList.size(); j++) {
                    ArrayList<String> list = (ArrayList<String>) objList.get(j);
                    for (int i = 0; i < list.size(); i++) {
                        sheet.addCell(new jxl.write.Label(i, j + 1, list.get(i),
                                arial12format));
                    }
                }
                writeBook.write();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writeBook != null) {
                    try {
                        writeBook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }


    public static Object getValueByRef(Class cls, String fieldName) {
        Object value = null;
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName
                .substring(0, 1).toUpperCase());
        String getMethodName = "get" + fieldName;
        try {
            Method method = cls.getMethod(getMethodName);
            value = method.invoke(cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}