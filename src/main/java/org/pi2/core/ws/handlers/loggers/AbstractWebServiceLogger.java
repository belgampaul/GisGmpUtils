package org.pi2.core.ws.handlers.loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.Set;

/**
 * @author ikka
 * @date: 30.05.2014.
 */
public abstract class AbstractWebServiceLogger implements SOAPHandler<SOAPMessageContext> {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(AbstractWebServiceLogger.class);

  public boolean handleMessage(SOAPMessageContext mc) {
    if (!log.isDebugEnabled()) {
      return true;
    }
    final SOAPMessage message = mc.getMessage();
    Boolean isRequest = !(Boolean) mc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    logHeaderMarker(isRequest);
    logMessage(message, isRequest);
    logFooterMarker(isRequest);
    return true;
  }

  abstract protected void logFooterMarker(Boolean isRequest);

  abstract protected void logMessage(SOAPMessage message, Boolean isRequest);

  abstract protected void logHeaderMarker(Boolean isRequest);

  public Set<QName> getHeaders() {
    return Collections.emptySet();
  }

  public void close(MessageContext mc) {
  }

  public boolean handleFault(SOAPMessageContext mc) {
    return true;
  }

}
