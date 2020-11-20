package com.megain.nfctemp.utils;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

public class ExcelPoiAddPassword {
    /**\
     * 加密
     * poi 的加密  只能加密xlsx
     * 此方法不能加密 -- 打开excel输入密码
     */
    public void addPassWordPOI(String password,File file,String path) throws Exception{

        Lg.e("进来了");
        //poi 测试2

        try {
            //File file = new File(path);
            String fileName = file.getName();
//            String suffix = ".xlsx";
//            if (!file.getName().endsWith(suffix)) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    file = Paths.get(file.getAbsolutePath() + suffix).toFile();
//                }
//            }

            FileInputStream inp = new FileInputStream(file);
            Workbook workbook=new XSSFWorkbook();
            if(fileName.endsWith("xls")){
                //2003
                workbook= new HSSFWorkbook(inp);
            }else if(fileName.endsWith("xlsx")){
                //2007
                workbook = new XSSFWorkbook(inp);
            }
//            Workbook workbook=new XSSFWorkbook();
//            fos = new FileOutputStream(path);
            //workbook.write(fos);
//            if (workbook instanceof HSSFWorkbook) {
//                throw new IllegalArgumentException("Document encryption for.xls is not supported");
//            }
            //保存此XSSFWorkbook对象为xlsx文件
            workbook.write(new FileOutputStream(path));//new FileOutputStream(path)

            //You are reading and writing to the same file at the same time
            POIFSFileSystem fs = new POIFSFileSystem();
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.standard);
            Encryptor enc = info.getEncryptor();
            //设置密码
            enc.confirmPassword(password);
            //加密文件
            //org.apache.poi.openxml4j.exceptions。OLE2NotOfficeXmlFileException:提供的数据显示为OLE2格式。您正在调用POI中处理OOXML (Office Open XML)文档的部分。您需要调用POI的不同部分来处理该数据(如HSSF而不是XSSF)
            OPCPackage opc = OPCPackage.open(new File(path), PackageAccess.READ_WRITE);
            OutputStream os = enc.getDataStream(fs);
            opc.save(os);
            opc.close();

            FileOutputStream fos = null;
            //把加密后的文件写回到流
            fos = new FileOutputStream(new File(path));
            fs.writeFilesystem(fos);
            fos.flush();
            fos.close();
            workbook.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**\
     * 打开excel输入密码  // poi 加密2  Biff8EncryptionKey
     * Biff8EncryptionKey.setCurrentUserPassword(password); 要执行两次  https://poi.apache.org/encryption.html
     * @param password
     * @param path cardDir.getPath() + "/" + fileName
     * @throws IOException
     * @throws GeneralSecurityException
     * @throws InvalidFormatException
     */
    public void addPassWordPOI2(String password,File file,String path) throws IOException, GeneralSecurityException, InvalidFormatException {
        Lg.e("进来了");
        InputStream in = new FileInputStream(file);
        Biff8EncryptionKey.setCurrentUserPassword(password);
        Workbook wb = WorkbookFactory.create(in);
        Biff8EncryptionKey.setCurrentUserPassword(password);
        in.close();
        FileOutputStream fos = new FileOutputStream(new File(path));
        wb.write(fos);
        wb.close();
        fos.close();
    }

    /**\
     * jxcell
     * @param password
     * @param path
     */
    public static void addPassWordJxcel(String password,String path){
        //加密  cardDir.getPath() + "/" + fileName
//        View m_view = new View();
//        try {//File.getAbsolutePath()
//            m_view.read("D:\\Users\\Desktop\\excel加密\\Formula\\FormulaSample.xls");
//            m_view.editCopyRight();
//            m_view.write("D:\\Users\\Desktop\\excel加密\\Formula\\FormulaSample.xls", "123456");
//        } catch (IOException | CellException e) {
//            e.printStackTrace();
//        }
    }
}






/**\
 * 加密
 * https://www.e-iceblue.cn/spirexls_java_protect/java-protect-excel-files.html
 */
//加载示例文档
//com.spire.xls.Workbook book = new com.spire.xls.Workbook();
//java.awt.print.Printable    找不到java.awt.print.Printable的类文件   com.android.tools.r8.errors.b: Space characters in SimpleName 'spr  ' are not allowed prior to DEX version 040
//book.loadFromFile(path);
//使用密码加密保护
//book.protect(password);
//保存文档
//book.saveToFile(path, ExcelVersion.Version2010);



    /**
     * 加密导出
     *
     * @param workbook workbook
     * @param file     file
     * @param password password
     * @throws Exception Exception
     */
    /*public static void encryptExport(final Workbook workbook, File file, final String password) throws Exception {
        if (workbook instanceof HSSFWorkbook) {
            throw new IllegalArgumentException("Document encryption for.xls is not supported");
        }
        String suffix = Constants.XLSX;
        if (!file.getName().endsWith(suffix)) {
            file = Paths.get(file.getAbsolutePath() + suffix).toFile();
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
            if (workbook instanceof SXSSFWorkbook) {
                ((SXSSFWorkbook) workbook).dispose();
            }

            final POIFSFileSystem fs = new POIFSFileSystem();
            final EncryptionInfo info = new EncryptionInfo(EncryptionMode.standard);
            final Encryptor enc = info.getEncryptor();
            enc.confirmPassword(password);

            try (OPCPackage opc = OPCPackage.open(file, PackageAccess.READ_WRITE);
                 OutputStream os = enc.getDataStream(fs)) {
                opc.save(os);
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fs.writeFilesystem(fileOutputStream);
            }
        } finally {
            workbook.close();
        }
    }*/