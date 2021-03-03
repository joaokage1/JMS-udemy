package toddyjms.basics;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageExpirationDemo {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
			producer.send(queue, "Toddy é o melhor");
			producer.setTimeToLive(2000);
			Thread.sleep(5000);

			Message received = jmsContext.createConsumer(queue).receive(5000);
			System.out.println("Message received: " + received.toString());
		}
	}
}
