package org.pi2.core.rabbitmq.events;

import be.belgampaul.core.eventbus.AbstractEvent2;

/**
 * User: ikka
 * Date: 4/11/14
 * Time: 2:41 AM
 */
public class ExchangeMessageReceivedEvent extends AbstractEvent2<String> {

  private ExchangeMessageReceivedEvent() {
  }

  public ExchangeMessageReceivedEvent(String exchangeId, String messageBody) {
    super(exchangeId, messageBody);
  }

  public String getExchangeId() {
    return super.getSubscriberId();
  }

}
