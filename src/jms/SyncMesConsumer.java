package jms;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SyncMesConsumer {

	/**
	 * @���ܣ�ͬ��������Ϣʵ��
	 * @���ߣ�
	 * @���ڣ�2012-10-17
	 */

	private QueueReceiver receiver;
	private TextMessage msg;

	public SyncMesConsumer() throws NamingException, JMSException {
		/* ��ʼ�������Ķ��� */
		String url = "t3://localhost:7001";
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		p.put(Context.PROVIDER_URL, url);
		Context ctx = new InitialContext(p);

		/* ����һ�����ӹ��� */
		QueueConnectionFactory qConFactory = (QueueConnectionFactory) ctx.lookup("weblogic.jms.ConnectionFactory");
		/* ����һ������ */
		Queue messageQueue = (Queue) ctx.lookup("jms/MyMDB");
		/* ����һ������ */
		QueueConnection qCon = qConFactory.createQueueConnection();
		/* ����һ���Ự */
		QueueSession session = qCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

		/* ������Ϣ������ */
		receiver = session.createReceiver(messageQueue);
		/* �ڵ��ô˷���֮ǰ����Ϣ���ݱ���ֹ */
		qCon.start();

	}

	public void runClient() throws JMSException {
		msg = (TextMessage) receiver.receive();
		System.err.println("Reciverd:" + msg.getText());
		msg = (TextMessage) receiver.receive();
		System.err.println("Reciverd:" + msg.getText());
		msg = (TextMessage) receiver.receive();
		System.err.println("Reciverd:" + msg.getText());
	}

	public static void main(String[] args) throws Exception {
		SyncMesConsumer consumer = new SyncMesConsumer();
		consumer.runClient();

	}

}
