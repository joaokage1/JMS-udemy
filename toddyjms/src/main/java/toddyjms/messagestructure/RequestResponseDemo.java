package toddyjms.messagestructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class RequestResponseDemo {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		Queue requestQueue = (Queue) context.lookup("queue/requestQueue");
		Queue responseQueue = (Queue) context.lookup("queue/responseQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			TextMessage message = jmsContext.createTextMessage("Toddy é o melhor");

			JMSProducer producer = jmsContext.createProducer();

			message.setJMSReplyTo(responseQueue);
			producer.send(requestQueue, message);
			System.out.println("ReplyTo :" + message.getJMSReplyTo().toString());

			JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
			TextMessage receive = (TextMessage) consumer.receive();
			System.out.println("Message received: " + receive.getText());
			System.out.println("ReplyTo :" + receive.getJMSReplyTo().toString());

			JMSProducer responseProducer = jmsContext.createProducer();
			responseProducer.send(receive.getJMSReplyTo(), "You are awsome!!");

			JMSConsumer responseConsumer = jmsContext.createConsumer(responseQueue);
			System.out.println("Message received: " + responseConsumer.receiveBody(String.class));
		}
	}
}
