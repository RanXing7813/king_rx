package com.example.java设计模式.单例;

public class SyncThread implements Runnable {

	 private static int count;

	   public SyncThread() {
	      count = 0;
	   }
	   
	   @Override
	   public  void run() {
	      synchronized(this) {
	    	  System.out.println(this);
	         for (int i = 0; i < 5; i++) {
	            try {
	               System.out.println(Thread.currentThread().getName() + ":" + (count++));
	               Thread.sleep(100);
	            } catch (InterruptedException e) {
	               e.printStackTrace();
	            }
	         }
	      }
	   }

	   public int getCount() {
	      return count;
	   }
	   
	   
//	   /**
//	    * 当两个并发线程(thread1和thread2)访问同一个对象(syncThread)中的synchronized代码块时，
//	    * 在同一时刻只能有一个线程得到执行，另一个线程受阻塞，必须等待当前线程执行完这个代码块以后才能执行该代码块。
//	    * Thread1和thread2是互斥的，因为在执行synchronized代码块时会锁定当前的对象，
//	    * 只有执行完该代码块才能释放该对象锁，下一个线程才能执行并锁定该对象。
//	    * @param args
//	    */
//	   public static void main(String[] args) {
//		   SyncThread syncThread = new SyncThread();
//		   Thread thread1 = new Thread(syncThread, "SyncThread1");
//		   Thread thread2 = new Thread(syncThread, "SyncThread2");
//		   thread1.start();
//		   thread2.start();
////		   SyncThread1:0
////		   SyncThread1:1
////		   SyncThread1:2
////		   SyncThread1:3
////		   SyncThread1:4
////		   SyncThread2:5
////		   SyncThread2:6
////		   SyncThread2:7
////		   SyncThread2:8
////		   SyncThread2:9
//	   }
	   /**
	    * 不是说一个线程执行synchronized代码块时其它的线程受阻塞吗？为什么下面的例子中thread1和thread2同时在执行。
	    * 这是因为synchronized只锁定对象，
	    * 这时会有两把锁分别锁定syncThread1对象和syncThread2对象，而这两把锁是互不干扰的，不形成互斥，所以两个线程可以同时执行。
	    * @param args
	    */
	   public static void main(String[] args) {
		   Thread thread1 = new Thread(new SyncThread(), "SyncThread1");
		   Thread thread2 = new Thread(new SyncThread(), "SyncThread2");
		   thread1.start();
		   thread2.start();
//		   SyncThread1:0
//		   SyncThread2:1
//		   SyncThread2:2
//		   SyncThread1:3
//		   SyncThread1:4
//		   SyncThread2:5
//		   SyncThread1:6
//		   SyncThread2:7
//		   SyncThread1:8
//		   SyncThread2:9
	   }
	   
	   /**
	    * 上面代码中countAdd是一个synchronized的，printCount是非synchronized的。
	    * 从上面的结果中可以看出一个线程访问一个对象的synchronized代码块时，
	    * 别的线程可以访问该对象的非synchronized代码块而不受阻塞。
	    */
}
