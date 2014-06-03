package org.pi2.core.schedulers;

import be.belgampaul.core.eventbus.EventBusService;
import org.pi2.core.jms.subscribers.AbstractJMSSimpleMessageSubscriber;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * User: ikka
 * Date: 4/10/14
 * Time: 4:52 AM
 */
public class JMSSimpleMessageSubscriber extends AbstractJMSSimpleMessageSubscriber {
  //logger
  private static final Logger log = Logger.getLogger(JMSSimpleMessageSubscriber.class);

  public JMSSimpleMessageSubscriber(String queueName, String exchangeName, String host) throws IOException, InterruptedException {
    super(queueName, exchangeName, host);
  }


  public boolean process(String message) {
    log.debug("received message: " + message);
    EventBusService.getInstance().postEvent(new NewXbetTennisJsonDataArrivedEvent(message));
    return true;
  }


  public static void main(String[] args) {
    try {
      JMSSimpleMessageSubscriber jmsSimpleMessageSubscriber = new JMSSimpleMessageSubscriber("", "be.belgampaul.livefeed.xbet.json.fanout", "localhost");
      FutureTask<?> f = new FutureTask<>(jmsSimpleMessageSubscriber, null);
      ExecutorService executor = Executors.newFixedThreadPool(2);
      executor.execute(f);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
