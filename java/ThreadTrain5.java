class ThreadTrain5 implements Runnable {
    // 这是货票总票数,多个线程会同时共享资源
    private int trainCount = 100;
    public boolean flag = true;
    private Object mutex = new Object();

    @Override
    public void run() {
        if (flag) {
            while (true) {
                synchronized (mutex) {
                    //静态同步函数  方法上加上static关键字，使用synchronized 关键字修饰 或者使用类.class文件
                    //静态的同步函数使用的锁是  该函数所属字节码文件对象
                    //可以用 getClass方法获取，也可以用当前  类名.class 表示
                    if (trainCount > 0) {
                        try {
                            Thread.sleep(40);
                        } catch (Exception e) {

                        }
                        System.out.println(Thread.currentThread().getName() + ",出售 第" + (100 - trainCount + 1) + "张票.");
                        trainCount--;
                    }
                }
            }
        } else {
            while (true) {
                sale();
            }
        }
    }


    public synchronized void sale() {
        if (trainCount > 0) {
            try {
                Thread.sleep(40);
            } catch (Exception e) {

            }
            System.out.println(Thread.currentThread().getName() + ",出售 第" + (100 - trainCount + 1) + "张票.");
            trainCount--;
        }
    }


    public static void main(String[] args) throws InterruptedException {

        ThreadTrain5 threadTrain = new ThreadTrain5(); // 定义 一个实例
        Thread thread1 = new Thread(threadTrain, "一号窗口");
        Thread thread2 = new Thread(threadTrain, "二号窗口");
        thread1.start();
        Thread.sleep(40);
        threadTrain.flag = false;
        thread2.start();
    }
}



