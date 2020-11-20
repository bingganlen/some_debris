import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLocktest {
        /* 共享数据，只能一个线程写数据，可以多个线程读数据 */
        private Object data = null;
        /* 创建一个读写锁 */
        ReadWriteLock rwlock = new ReentrantReadWriteLock();

        /**
         * 读数据，可以多个线程同时读， 所以上读锁即可
         */
        public void get() {
            /* 上读锁 */
            rwlock.readLock().lock();

            try {
                System.out.println(Thread.currentThread().getName() + " 准备读数据!");
                /* 休眠 */
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println(Thread.currentThread().getName() + "读出的数据为 :" + data);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                rwlock.readLock().unlock();
            }

        }


    public void get(Object data) {//读锁中有写锁操作     证明读锁可以写
        /* 上读锁 */
        rwlock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + " **准备读数据!");
            /* 休眠 */
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println(Thread.currentThread().getName() + "**读出的数据为 :" + data);

            this.data = data;
            System.out.println(Thread.currentThread().getName() + " ***写入的数据: " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwlock.readLock().unlock();
        }

    }




        /**
         * 写数据，多个线程不能同时 写 所以必须上写锁
         *
         * @param data
         */
        public void put(Object data) {

            /* 上写锁 */
            rwlock.writeLock().lock();

            try {
                System.out.println(Thread.currentThread().getName() + " 准备写数据!");
                /* 休眠 */
                Thread.sleep((long) (Math.random() * 1000));
                this.data = data;
                System.out.println(Thread.currentThread().getName() + " 写入的数据: " + data);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rwlock.writeLock().unlock();
            }
        }





    /**
     * 测试类
     *
     * @author Liao
     *
     */


    public static void main(String[] args) {

        /* 创建ReadWrite对象 */
        final ReadWriteLocktest readWrite = new ReadWriteLocktest();

        /* 创建并启动3个读线程 */
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    readWrite.get();

                }
            }).start();

            /*创建3个写线程*/
            new Thread(new Runnable() {

                @Override
                public void run() {
                    /*随机写入一个数*/
                    readWrite.put(new Random().nextInt(8));
                    //readWrite.get(new Random().nextInt(8));

                }
            }).start();
        }

    }

    /**\
     * 上了写锁之后写数据必须同完成即从准备写数据到写入数据必须一气呵成！而上了读锁则不同，可以看到有多个线程同时准备读数据，又有多个线程同时读出数据。
     *
     * Thread-0 准备读数据!
     * Thread-0读出的数据为 :null
     * Thread-1 准备写数据!
     * Thread-1 写入的数据: 0
     * Thread-2 准备读数据!
     * Thread-2读出的数据为 :0
     * Thread-3 准备写数据!
     * Thread-3 写入的数据: 4
     * Thread-4 准备读数据!
     * Thread-4读出的数据为 :4
     * Thread-5 准备写数据!
     * Thread-5 写入的数据: 1
     */





}
