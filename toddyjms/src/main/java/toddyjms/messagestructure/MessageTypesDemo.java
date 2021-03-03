package toddyjms.messagestructure;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypesDemo {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
			// TextMessage body = jmsContext.createTextMessage("Toddy é o melhor");
			// BytesMessage bytesMessage = jmsContext.createBytesMessage();
			// bytesMessage.writeUTF("John");
			// bytesMessage.writeLong(123l);

			// producer.send(queue, bytesMessage);

			// BytesMessage received = (BytesMessage)
			// jmsContext.createConsumer(queue).receive(5000);
			// System.out.println("Message received: " + received.readUTF());
			// System.out.println("Message received: " + received.readLong());

			StreamMessage streamMessage = jmsContext.createStreamMessage();
			streamMessage.writeBoolean(true);

			producer.send(queue, streamMessage);

			StreamMessage streamReceived = (StreamMessage) jmsContext.createConsumer(queue).receive(5000);
			System.out.println("Message received: " + streamReceived.readBoolean());
		}
	}
}
