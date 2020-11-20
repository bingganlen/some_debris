import java.io.File;

public class FileMkdirTest {
    public static void main(String[] args) {
        String path = "D:\\temp33\\temp1\\temp2\\temp3";
        File file = new File(path);
        if( !file.getParentFile().exists()) {// 如果目标文件所在的目录不存在，则创建父目录    会创建所有需要的路径上的父目录
            file.getParentFile().mkdirs();
        }

        //结果在D盘上创建了 D:\temp33\temp1\temp2 不存在的temp33、temp1、temp2  3个文件夹
    }
}
