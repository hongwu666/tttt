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
	 * @功能：同步接收消息实例
	 * @作者：
	 * @日期：2012-10-17
	 */

	private QueueReceiver receiver;
	private TextMessage msg;

	public SyncMesConsumer() throws NamingException, JMSException {
		/* 初始化上下文对象 */
		String url = "t3://localhost:7001";
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		p.put(Context.PROVIDER_URL, url);
		Context ctx = new InitialContext(p);

		/* 创建一个连接工厂 */
		QueueConnectionFactory qConFactory = (QueueConnectionFactory) ctx.lookup("weblogic.jms.ConnectionFactory");
		/* 创建一个队列 */
		Queue messageQueue = (Queue) ctx.lookup("jms/MyMDB");
		/* 创建一个连接 */
		QueueConnection qCon = qConFactory.createQueueConnection();
		/* 创建一个会话 */
		QueueSession session = qCon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

		/* 创建消息接收者 */
		receiver = session.createReceiver(messageQueue);
		/* 在调用此方法之前，消息传递被禁止 */
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
