package com.bftcom.apps.gisgmp.ws.gisgmp.handlers;

import org.apache.log4j.Logger;
import org.pi2.core.utils.XmlUtils;
import org.pi2.core.ws.handlers.loggers.AbstractWebServiceLogger;

import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class WebServicePrettyPrintLoggerHandler extends AbstractWebServiceLogger {
  private static Logger log = Logger.getLogger(WebServicePrettyPrintLoggerHandler.class);

  public boolean handleMessage(SOAPMessageContext mc) {
    return super.handleMessage(mc);
  }

  protected void logMessage(SOAPMessage message, Boolean isRequest) {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    try {
      message.writeTo(stream);
      log.debug(XmlUtils.prettyFormat(stream.toString(StandardCharsets.UTF_8.name())));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  protected void logFooterMarker(Boolean isRequest) {
    if (isRequest) {
      log.debug("========= END GISGMP REQUEST =========");
    } else {
      log.debug(">>>>>>>>> END GISGMP RESPONSE <<<<<<<<<");
    }
  }

  protected void logHeaderMarker(Boolean isRequest) {
    if (isRequest) {
      log.debug("========= GISGMP REQUEST =========");
    } else {
      log.debug(">>>>>>>>> GISGMP RESPONSE <<<<<<<<<");
    }
  }
}
