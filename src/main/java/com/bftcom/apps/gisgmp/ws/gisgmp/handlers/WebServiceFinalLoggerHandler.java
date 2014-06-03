package com.bftcom.apps.gisgmp.ws.gisgmp.handlers;

import org.pi2.core.ws.handlers.loggers.AbstractWebServiceLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class WebServiceFinalLoggerHandler extends AbstractWebServiceLogger {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(WebServiceFinalLoggerHandler.class);

  public boolean handleMessage(SOAPMessageContext mc) {
    return super.handleMessage(mc);
  }

  @Override
  protected void logFooterMarker(Boolean isRequest) {
    if (isRequest) {
      log.debug("========= END FINAL REQUEST =========");
    } else {
      log.debug(">>>>>>>>> END FINAL RESPONSE <<<<<<<<<");
    }
  }

  @Override
  protected void logMessage(SOAPMessage message, Boolean isRequest) {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    try {
      message.writeTo(stream);
      log.debug(stream.toString(StandardCharsets.UTF_8.name()));

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  protected void logHeaderMarker(Boolean isRequest) {
    if (isRequest) {
      log.debug("========= FINAL REQUEST =========");
    } else {
      log.debug(">>>>>>>>> FINAL RESPONSE <<<<<<<<<");
    }
  }

}
