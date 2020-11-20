package com.megain.nfctemp.utils

import android.content.Context
import com.megain.nfctemp.main.CurApp
import java.io.*
import java.nio.charset.Charset

/**
 * 文件操作工具类
 * Created by apex on 2017/3/23.
 */
object FileUtil {
    /**
     * Default Charset,UTF-8
     */
    val CHARSET: Charset = Charset.defaultCharset()
    const val CACHE_DIR = "/NFCTempHumidity"

    /**
     * 创建一个文件
     *
     * @param parent   父目录
     * @param fileName 文件名
     * @return 新建的文件
     * @throws IOException 父目录非法 or 创建文件失败
     */
    @Throws(IOException::class)
    fun createFile(parent: File, fileName: String): File {
        if (!parent.exists() || !parent.isDirectory) {
            throw IOException("Parent directory illegal")
        }
        val filePath = parent.path + "/" + fileName
        val file = File(filePath)
        if (!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    /**
     * 创建一个目录
     *
     * @param parent  父目录
     * @param dirName 子目录名字
     * @return null，如果父目录非法，否则返回父目录文件
     */
    fun createDir(parent: File, dirName: String): File {
        val dirPath = parent.path + "/" + dirName
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir
    }

    /**
     * 保存文件数据
     *
     * @param file   目标文件
     * @param data   文件数据
     * @param append true，追加方式保存，false，覆盖写入数据
     */
    fun save(file: File, data: ByteArray?, append: Boolean): Boolean {
        if (data != null) {
            var fos: FileOutputStream? = null
            try {
                if (!file.exists()) {
                    file.createNewFile()
                }
                if (!file.exists()) {//文件创建失败
                    Lg.d("文件不存在")
                }
                fos = FileOutputStream(file, append)
                fos.write(data)
                fos.flush()
                fos.close()
                return true
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    fos?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return false
    }

    /**
     * 删除文件
     *
     * @param file destination file
     * @return if and only if the file or directory is
     * successfully deleted; `false` otherwise
     */
    fun delete(file: File): Boolean {
        return if (IsUtils.isNull(file) || !file.exists() || file.isDirectory) {
            false
        } else file.delete()
    }

    /**
     * 删除文件
     *
     * @param file destination file
     * @return if and only if the file or directory is
     * successfully deleted; `false` otherwise
     */
    fun deleteDir(file: File): Boolean {
        return if (IsUtils.isNull(file) || !file.exists() || !file.isDirectory) {
            false
        } else file.delete()
    }

    /**
     * 读取整个文件的数据并返回
     *
     * @param file ,目标文件
     * @return byte[]文件数据
     * @throws IOException
     * @author Jack
     */
    fun read(file: File): ByteArray? {
        var inputStream: InputStream? = null
        try {
            inputStream = FileInputStream(file)
            val fileData = ByteArray(inputStream.available())
            inputStream.read(fileData)
            inputStream.close()
            return fileData
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * 获取缓存APP包目录
     *
     * @return
     */
    fun getPackExternalCacheDir(context: Context): File {
        val cacheDir = File(context.externalCacheDir!!.path)
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        return cacheDir
    }

    fun getPackFilesDir(context: Context): File {
        val cacheDir = File(context.filesDir.path)
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        return cacheDir
    }

    fun readAssetFile(fileName: String): ByteArray? {
        try {
            val stream = CurApp.getAppContext().assets.open(fileName)
            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            return buffer
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}
