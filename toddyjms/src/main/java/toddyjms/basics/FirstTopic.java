package toddyjms.basics;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class FirstTopic {

	public static void main(String[] args) throws Exception {

		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/myTopic");

		ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection connection = cf.createConnection();

		Session session = connection.createSession();
		MessageProducer producer = session.createProducer(topic);

		MessageConsumer consumer1 = session.createConsumer(topic);
		MessageConsumer consumer2 = session.createConsumer(topic);

		TextMessage message = session.createTextMessage("All the power is with in me");

		producer.send(message);

		connection.start();

		TextMessage msg1 = (TextMessage) consumer1.receive();
		System.out.println("Consumer 1: " + msg1.getText());

		TextMessage msg2 = (TextMessage) consumer2.receive();
		System.out.println("Consumer 2: " + msg2.getText());

		connection.close();
		initialContext.close();
	}

}
