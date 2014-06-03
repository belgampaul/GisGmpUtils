package org.pi2.core.jms.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * User: ikka
 * Date: 4/10/14
 * Time: 7:04 AM
 */
abstract public class AbstractRabbitMQ implements Runnable {
  //logger
  private static final Logger log = Logger.getLogger(AbstractRabbitMQ.class);
  public static final String DEFAULT_HOSTNAME = "localhost";


  protected final ConnectionFactory factory;
  protected final String QUEUE_NAME;
  protected final String HOST;
  protected final String EXCHANGE_NAME;
  protected Channel channel;

  protected AbstractRabbitMQ(String queueName, String exchangeName, String host) {
    QUEUE_NAME = queueName == null ? "" : queueName;
    HOST = host == null ? DEFAULT_HOSTNAME : host;
    EXCHANGE_NAME = exchangeName== null ? "" : exchangeName;

    factory = new ConnectionFactory();
    factory.setHost(HOST);
  }

  @Override
  final public void run() {
     try {
      initChannel();
//      channel.queueDeclare(QUEUE_NAME, false, false, false, null);
     processChannel();
    } catch (IOException | InterruptedException e) {
      log.debug(e);
    }
  }

  protected abstract void processChannel() throws IOException, InterruptedException;

  public boolean process(String message) {
    throw new UnsupportedOperationException();
  }

  abstract protected void initChannel() throws IOException;
}
