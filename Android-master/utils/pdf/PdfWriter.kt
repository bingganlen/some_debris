package com.megain.nfctemp.utils.pdf

import android.Manifest
import android.os.Environment
import androidx.annotation.RequiresPermission
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfContentByte
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import com.megain.label.driver.label.Label
import com.megain.nfctemp.main.CurApp
import com.megain.nfctemp.model.ThSetting
import com.megain.nfctemp.utils.FileUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object PdfWriter {

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun write(label: Label): File? {
        val labelSetting = label.getLabelSetting() ?: return null
        try {
            val context = CurApp.getAppContext()
            val ttf = initTtf()
            val fontChinese = BaseFont.createFont(ttf.path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)
            val normalFont = Font(fontChinese, 12f)
            val order = ThSetting.Order.fromJson(labelSetting.text)
            val settingInfos = TempRhPdfSource.buildSettingInfo(label, labelSetting, order)
            val recordInfos = TempRhPdfSource.buildRecordInfo(label, labelSetting)
            val builder = DocumentFactory.builder()
            val pdfTempFile = createTempPdfFile(label.getId(), order)
            builder.dstFile(pdfTempFile)
            val titleFont = Font(fontChinese, 18F)
            builder.addParagraphCenter(label.getId() + " " + context.getString(com.megain.nfctemp.R.string.pdf_title), titleFont)
            val count = settingInfos.size
            for (i in 0 until count) {
                builder.addParagraph(settingInfos[i], normalFont)
            }

            builder.addParagraph("\n" + context.getString(com.megain.nfctemp.R.string.pdf_label_record_info), titleFont)
            for (info in recordInfos) {
                builder.addParagraph(info, normalFont)
            }
            builder.build()
            val targetPdfFile = createTargetPdfFile(label.getId(), order)
            addPdfTextMark(pdfTempFile.path, targetPdfFile, "TempAction", fontChinese)
            FileUtil.delete(pdfTempFile)
            return targetPdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun initTtf(): File {
        val fileName = "chinese.stsong.ttf"
        val parent = FileUtil.createDir(CurApp.getAppContext().cacheDir, "ttf")
        val file = FileUtil.createFile(parent, fileName)
        if (file.length() <= 0) {
            FileUtil.save(file, FileUtil.readAssetFile(fileName), false)
        }
        return file
    }

    @Throws(IOException::class)
    fun createTargetPdfFile(labelId: String, order: ThSetting.Order?): File {
        val appDir = FileUtil.createDir(Environment.getExternalStorageDirectory(), "Temp&Hum Record Label")
        val cardDir = FileUtil.createDir(appDir, labelId)
        val fileName = if (order != null) labelId + "-" + order.number.trim { it <= ' ' } + ".pdf" else "$labelId.pdf"
        return FileUtil.createFile(cardDir, fileName)
    }

    @Throws(IOException::class)
    fun createTempPdfFile(labelId: String, order: ThSetting.Order?): File {
        val appDir = FileUtil.createDir(Environment.getExternalStorageDirectory(), "Temp&Hum Record Label")
        val cardDir = FileUtil.createDir(appDir, labelId)
        val fileName = if (order != null) labelId + "-" + order.number.trim { it <= ' ' } + "Temp" + ".pdf" else "{$labelId}Temp.pdf"
        return FileUtil.createFile(cardDir, fileName)
    }

    /**
     *
     * 给pdf文件添加水印
     *
     * @param InPdfFile  要加水印的原pdf文件路径
     * @param outPdfFile 加了水印后要输出的路径
     * @param textMark   水印文字
     * @throws Exception
     */
    @Throws(Exception::class)
    fun addPdfTextMark(InPdfFile: String, outPdfFile: File, textMark: String, font: BaseFont) {
        val reader = PdfReader(InPdfFile, "PDF".toByteArray())
        val stamp = PdfStamper(reader, FileOutputStream(outPdfFile))
        var under: PdfContentByte
        val pageSize = reader.numberOfPages// 原pdf文件的总页数
        val textWidth = 300//文字横坐标
        val textHeight = 400//文字纵坐标
        for (i in 1..pageSize) {
            under = stamp.getUnderContent(i)// 水印在之前文本下
            // under = stamp.getOverContent(i);//水印在之前文本上
            under.beginText()
            under.setColorFill(BaseColor(211, 211, 211))// 文字水印 颜色
            under.setFontAndSize(font, 100f)// 文字水印 字体及字号
            under.setTextMatrix(textWidth.toFloat(), textHeight.toFloat())// 文字水印 起始位置
            under.showTextAligned(Element.ALIGN_CENTER, textMark, textWidth.toFloat(), textHeight.toFloat(), 45f)
            under.endText()
        }
        stamp.close()// 关闭
    }
}