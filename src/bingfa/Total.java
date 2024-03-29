package bingfa;

import java.util.concurrent.CyclicBarrier;

public class Total {  
	  
    // private ConcurrentHashMap result = new ConcurrentHashMap();  
  
    public static void main(String[] args) {  
        CyclicBarrier barrier = new CyclicBarrier(5,  
                new TotalTask());  
  
        // 实际系统是查出所有省编码code的列表，然后循环，每个code生成一个线程。  
        new BillTask(null, barrier, "北京").start();  
        new BillTask(null, barrier, "上海").start();  
        new BillTask(null, barrier, "广西").start();  
        new BillTask(null, barrier, "四川").start();  
        new BillTask(null, barrier, "黑龙江").start();  
  
    }  
}  
  
/** 
 * 主任务：汇总任务 
 */  
class TotalTask implements Runnable {  
    private TotalService totalService;  
  
    TotalTask(TotalService totalService) {  
        this.totalService = totalService;  
    }  
  
    public void run() {  
        // 读取内存中各省的数据汇总，过程略。  
        totalService.count();  
        System.out.println("=======================================");  
        System.out.println("开始全国汇总");  
    }  
}  
  
/** 
 * 子任务：计费任务 
 */  
class BillTask extends Thread {  
    // 计费服务  
    private BillService billService;  
    private CyclicBarrier barrier;  
    // 代码，按省代码分类，各省数据库独立。  
    private String code;  
  
    BillTask(BillService billService, CyclicBarrier barrier, String code) {  
        this.billService = billService;  
        this.barrier = barrier;  
        this.code = code;  
    }  
  
    public void run() {  
        System.out.println("开始计算--" + code + "省--数据！");  
        billService.bill(code);  
        // 把bill方法结果存入内存，如ConcurrentHashMap,vector等,代码略  
        System.out.println(code + "省已经计算完成,并通知汇总Service！");  
        try {  
            // 通知barrier已经完成  
            barrier.await();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (BrokenBarrierException e) {  
            e.printStackTrace();  
        }  
    }  
  
}  