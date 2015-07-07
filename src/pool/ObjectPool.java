package pool;

import java.util.Enumeration;
import java.util.Vector;

public class ObjectPool {
	private int numObjects = 10; // ����صĴ�С
	private int maxObjects = 50; // ��������Ĵ�С
	private Vector objects = null; // ��Ŷ�����ж��������( PooledObject����)

	public ObjectPool() {
	}

	/*** ����һ������� ***/
	@SuppressWarnings("unchecked")
	public synchronized void createPool(){     
        // ȷ�������û�д�������������ˣ������������� objects ����Ϊ��     
        if (objects != null) {     
            return; // ��������������򷵻�     
        }     
    
        // ���������������� , ��ʼʱ�� 0 ��Ԫ��     
        objects = new Vector();     
    
        // ���� numObjects �����õ�ֵ��ѭ������ָ����Ŀ�Ķ���     
        for (int x = 0; x < numObjects; x++) {     
           if ((objects.size() == 0)&&this.objects.size() <this.maxObjects) {  
              Object obj = new Object();     
              objects.addElement(new PooledObject(obj));  
           }
������������}
��������}

	public synchronized Object getObject() {
		// ȷ������ؼ�������
		if (objects == null) {
			return null; // ����ػ�û�������򷵻� null
		}

		Object conn = getFreeObject(); // ���һ�����õĶ���

		// ���Ŀǰû�п���ʹ�õĶ��󣬼����еĶ�����ʹ����
		while (conn == null) {
			wait(250);
			conn = getFreeObject(); // �������ԣ�ֱ����ÿ��õĶ������
			// getFreeObject() ���ص�Ϊ null�����������һ�������Ҳ���ɻ�ÿ��ö���
		}

		return conn;// ���ػ�õĿ��õĶ���
	}

	/**
	 * �������Ӷ���ض��� objects �з���һ�����õĵĶ������ ��ǰû�п��õĶ����򴴽��������󣬲����������С�
	 * ������������еĶ�����ʹ���У��򷵻� null
	 */
	private Object getFreeObject() {

		// �Ӷ�����л��һ�����õĶ���
		Object obj = findFreeObject();

		if (obj == null) {
			createObjects(null); // ���Ŀǰ�������û�п��õĶ��󣬴���һЩ����

			// ���´ӳ��в����Ƿ��п��ö���
			obj = findFreeObject();

			// �������������Ի�ò������õĶ����򷵻� null
			if (obj == null) {
				return null;
			}
		}

		return obj;
	}

	/**
	 * ���Ҷ���������еĶ��󣬲���һ�����õĶ��� ���û�п��õĶ��󣬷��� null
	 */
	private Object findFreeObject() {

		Object obj = null;
		PooledObject pObj = null;

		// ��ö�������������еĶ���
		Enumeration enumerate = objects.elements();

		// �������еĶ��󣬿��Ƿ��п��õĶ���
		while (enumerate.hasMoreElements()) {
			pObj = (PooledObject) enumerate.nextElement();

			// ����˶���æ���������Ķ��󲢰�����Ϊæ
			if (!pObj.isBusy()) {
				obj = pObj.getObject();
				pObj.setBusy(true);
			}
		}
		// �����ҵ����Ŀ��ö���
		return obj;
	}

	/**
	 * �˺�������һ�����󵽶�����У����Ѵ˶�����Ϊ���С� ����ʹ�ö���ػ�õĶ����Ӧ�ڲ�ʹ�ô˶���ʱ��������
	 */

	public void returnObject(Object obj) {

		// ȷ������ش��ڣ��������û�д����������ڣ���ֱ�ӷ���
		if (objects == null) {
			return;
		}

		PooledObject pObj = null;

		Enumeration enumerate = objects.elements();

		// ����������е����ж����ҵ����Ҫ���صĶ������
		while (enumerate.hasMoreElements()) {
			pObj = (PooledObject) enumerate.nextElement();

			// ���ҵ�������е�Ҫ���صĶ������
			if (obj == pObj.getObject()) {
				// �ҵ��� , ���ô˶���Ϊ����״̬
				pObj.setBusy(false);
				break;
			}
		}
	}

	/**
	 * �رն���������еĶ��󣬲���ն���ء�
	 */
	public synchronized void closeObjectPool() {

		// ȷ������ش��ڣ���������ڣ�����
		if (objects == null) {
			return;
		}

		PooledObject pObj = null;

		Enumeration enumerate = objects.elements();

		while (enumerate.hasMoreElements()) {

			pObj = (PooledObject) enumerate.nextElement();

			// ���æ���� 5 ��
			if (pObj.isBusy()) {
				wait(5000); // �� 5 ��
			}

			// �Ӷ����������ɾ����
			objects.removeElement(pObj);
		}

		// �ö����Ϊ��
		objects = null;
	}

	/**
	 * ʹ����ȴ������ĺ�����
	 */
	private void wait(int mSeconds) {
		try {
			Thread.sleep(mSeconds);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * �ڲ�ʹ�õ����ڱ��������ж�����ࡣ ��������������Ա��һ���Ƕ�����һ����ָʾ�˶����Ƿ�����ʹ�õı�־ ��
	 */
	class PooledObject {

		Object objection = null;// ����
		boolean busy = false; // �˶����Ƿ�����ʹ�õı�־��Ĭ��û������ʹ��

		// ���캯��������һ�� Object ����һ�� PooledObject ����
		public PooledObject(Object objection) {

			this.objection = objection;

		}

		// ���ش˶����еĶ���
		public Object getObject() {
			return objection;
		}

		// ���ô˶���ģ�����
		public void setObject(Object objection) {
			this.objection = objection;

		}

		// ��ö�������Ƿ�æ
		public boolean isBusy() {
			return busy;
		}

		// ���ö���Ķ�������æ
		public void setBusy(boolean busy) {
			this.busy = busy;
		}
	}
}

class ObjectPoolTest {
	public static void main(String[] args) throws Exception {
		ObjectPool objPool = new ObjectPool();

		objPool.createPool();
		Object obj = objPool.getObject();
		objPool.returnObject(obj);
		objPool.closeObjectPool();
	}
}
