package toddyjms.messagestructure;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePropertiesDemo {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
			TextMessage body = jmsContext.createTextMessage("Toddy é o melhor");
			body.setBooleanProperty("loggedIn", true);
			body.setStringProperty("userToken", "abc123");
			producer.send(queue, body);

			Message received = jmsContext.createConsumer(queue).receive(5000);
			System.out.println("Message received: " + received.toString());
			System.out.println(received.getBooleanProperty("loggedIn"));
			System.out.println(received.getStringProperty("userToken"));
		}
	}
}
