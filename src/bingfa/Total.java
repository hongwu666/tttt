package bingfa;

import java.util.concurrent.CyclicBarrier;

public class Total {  
	  
    // private ConcurrentHashMap result = new ConcurrentHashMap();  
  
    public static void main(String[] args) {  
        CyclicBarrier barrier = new CyclicBarrier(5,  
                new TotalTask());  
  
        // ʵ��ϵͳ�ǲ������ʡ����code���б�Ȼ��ѭ����ÿ��code����һ���̡߳�  
        new BillTask(null, barrier, "����").start();  
        new BillTask(null, barrier, "�Ϻ�").start();  
        new BillTask(null, barrier, "����").start();  
        new BillTask(null, barrier, "�Ĵ�").start();  
        new BillTask(null, barrier, "������").start();  
  
    }  
}  
  
/** 
 * �����񣺻������� 
 */  
class TotalTask implements Runnable {  
    private TotalService totalService;  
  
    TotalTask(TotalService totalService) {  
        this.totalService = totalService;  
    }  
  
    public void run() {  
        // ��ȡ�ڴ��и�ʡ�����ݻ��ܣ������ԡ�  
        totalService.count();  
        System.out.println("=======================================");  
        System.out.println("��ʼȫ������");  
    }  
}  
  
/** 
 * �����񣺼Ʒ����� 
 */  
class BillTask extends Thread {  
    // �Ʒѷ���  
    private BillService billService;  
    private CyclicBarrier barrier;  
    // ���룬��ʡ������࣬��ʡ���ݿ������  
    private String code;  
  
    BillTask(BillService billService, CyclicBarrier barrier, String code) {  
        this.billService = billService;  
        this.barrier = barrier;  
        this.code = code;  
    }  
  
    public void run() {  
        System.out.println("��ʼ����--" + code + "ʡ--���ݣ�");  
        billService.bill(code);  
        // ��bill������������ڴ棬��ConcurrentHashMap,vector��,������  
        System.out.println(code + "ʡ�Ѿ��������,��֪ͨ����Service��");  
        try {  
            // ֪ͨbarrier�Ѿ����  
            barrier.await();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (BrokenBarrierException e) {  
            e.printStackTrace();  
        }  
    }  
  
}  