package test6;

public class test7 {
//    public static void main(String[] args) {
//        String xx = "sdhdsha\"hhgdfsh\"fasg";
//        System.out.println("原本长度："+xx.length());
//        xx = xx.replaceAll("\"","");//双引号
//        System.out.println(xx);
//        System.out.println("长度1："+xx.length());
//    }

    public static void main(String[] args) {
        final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
//        double x = 113.493433, y = 22.287109;
        double x = 113.518023, y = 22.231822;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        System.out.println(bd_lon + "," + bd_lat);
    }
}
