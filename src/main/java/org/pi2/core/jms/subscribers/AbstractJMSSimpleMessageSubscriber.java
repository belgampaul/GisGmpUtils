package org.pi2.core.jms.subscribers;

import org.pi2.core.jms.common.AbstractRabbitMQ;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * User: ikka
 * Date: 4/10/14
 * Time: 4:52 AM
 */
public abstract class AbstractJMSSimpleMessageSubscriber extends AbstractRabbitMQ {
  //logger
  private static final Logger log = Logger.getLogger(AbstractJMSSimpleMessageSubscriber.class);

  public AbstractJMSSimpleMessageSubscriber(String queueName, String exchangeName, String host) throws IOException, InterruptedException {
super(queueName, exchangeName, host);

  }


  protected void initChannel() throws IOException {
    Connection connection = null;
    connection = factory.newConnection();
    channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(queueName, EXCHANGE_NAME, "");
  }

  @Override
  public abstract boolean process(String message);

  @Override
  protected void processChannel() throws IOException, InterruptedException {
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(QUEUE_NAME, true, consumer);

    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      process(message);
    }
  }
}
