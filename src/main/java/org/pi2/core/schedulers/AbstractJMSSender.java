package org.pi2.core.schedulers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;

import java.io.IOException;

abstract public class AbstractJMSSender extends AbstractScheduler implements Runnable {
  //logger
  private static final Logger log = Logger.getLogger(AbstractJMSSender.class);
  private ConnectionFactory factory = new ConnectionFactory();
  public final String QUEUE_NAME;
  Channel channel;

  public AbstractJMSSender(String queueName, String host) throws IOException {
    QUEUE_NAME = queueName;
    factory.setHost(host);
  }


  @Override
  protected Runnable getTask() {
    return new Runnable() {
      @Override
      public void run() {
        String json = null;
        Connection connection = null;
        try {
          connection = factory.newConnection();
          channel = connection.createChannel();
          channel.queueDeclare(QUEUE_NAME, false, false, false, null);

          json = getMessage();
          channel.basicPublish("", QUEUE_NAME, null, json.getBytes());
        } catch (IOException e) {
          log.debug(e);
        } finally {
          try {
            if (connection != null) {
              connection.close();
            }
            channel.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    };
  }

  protected abstract String getMessage();

  public static void main(String[] args) {
    AbstractJMSSender nhlOddsFetcher = null;
//    try {
//      //nhlOddsFetcher = new AbstractJMSSender();
//      //nhlOddsFetcher.start();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }
}
