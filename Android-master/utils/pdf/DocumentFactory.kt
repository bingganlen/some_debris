package com.megain.nfctemp.utils.pdf

import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object DocumentFactory {

    fun builder(): Builder {
        return Builder()
    }


    class Builder {

        private var document = Document()

        @Throws(FileNotFoundException::class, DocumentException::class)
        fun dstFile(file: File): Builder {
            PdfWriter.getInstance(document, FileOutputStream(file.path))
            document.open()
            return this
        }

        @Throws(DocumentException::class)
        fun addParagraph24(content: String, font: Font): Paragraph {
            val paragraph = Paragraph(content, font)
            paragraph.firstLineIndent = 24F // 首行缩进
            document.add(paragraph)
            return paragraph
        }

        @Throws(DocumentException::class)
        fun addParagraph(content: String, font: Font): Paragraph {
            val to = Paragraph(content, font)
            document.add(to)
            return to
        }

        @Throws(DocumentException::class)
        fun addParagraphCenter(content: String, font: Font): Paragraph {
            val to = Paragraph(content, font)
            to.alignment = Element.ALIGN_CENTER
            document.add(to)
            return to
        }

        @Throws(DocumentException::class, IOException::class)
        fun addImage(chartImage: File?): Builder {
            if (chartImage != null) {
                val image = Image.getInstance(chartImage.path)
                val documentWidth = document.pageSize.width - document.leftMargin() - document.rightMargin()
                val documentHeight = documentWidth / 580 * 320//重新设置宽高
                image.scaleAbsolute(documentWidth, documentHeight)//重新设置宽高
                document.add(image)
            }
            return this
        }

        fun build(): Document {
            document.close()
            return document
        }

    }
}
