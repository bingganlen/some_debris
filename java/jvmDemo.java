import java.io.IOException;

public class jvmDemo {
    public static void main(String[] args) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("最大内存(M):"+runtime.maxMemory()/1024/1024);
        System.out.println("总内存(M):"+runtime.totalMemory()/1024/1024);
        System.out.println("可用内存(M):"+runtime.freeMemory()/1024/1024);
        System.out.println("打开计算器:"+runtime.exec("calc"));
        System.out.println("jvm可用的处理器核心的数 量:"+runtime.availableProcessors()/1024/1024);
        //通知JVM进行gc
        runtime.gc();
        System.gc();
    }
}
