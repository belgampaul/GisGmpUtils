package com.bftcom.apps.gisgmp.ws.gisgmp.handlers;

import com.google.common.net.MediaType;
import org.pi2.core.utils.XmlUtils;
import org.pi2.core.ws.handlers.loggers.AbstractWebServiceLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebServiceFinalFileLoggerHandler extends AbstractWebServiceLogger {
  @SuppressWarnings("HardCodedStringLiteral")
  public static final String REQUESTS = "requests";

  @SuppressWarnings("HardCodedStringLiteral")
  public static final String RESPONSES = "responses";


  static {
    File requestsDir = new File(REQUESTS);
    //noinspection ResultOfMethodCallIgnored
    requestsDir.mkdirs();

    File responsesDir = new File(RESPONSES);
    //noinspection ResultOfMethodCallIgnored
    responsesDir.mkdirs();
  }

  private static final String FILE_EXTENSION = MediaType.XML_UTF_8.subtype();

  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(WebServiceFinalFileLoggerHandler.class);

  public boolean handleMessage(SOAPMessageContext mc) {
    return super.handleMessage(mc);
  }

  @Override
  protected void logFooterMarker(Boolean isRequest) {
    //noop
  }

  @Override
  protected void logMessage(SOAPMessage message, Boolean isRequest) {
    File file = createAndGetFile(isRequest);
    try (
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      message.writeTo(out);
      String fullString = out.toString(StandardCharsets.UTF_8.name());

      fileOutputStream.write(XmlUtils.prettyFormat(fullString).getBytes(StandardCharsets.UTF_8));
      fileOutputStream.flush();
    } catch (IOException | SOAPException e) {
      log.error(e.getMessage(), e);
    }
  }

  @SuppressWarnings({"StringConcatenation", "SimpleDateFormatWithoutLocale"})
  private File createAndGetFile(Boolean isRequest) {
    String saveDir = isRequest ? REQUESTS : RESPONSES;
    String filename = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_SSS").format(new Date());
    String filenameWithExtension = filename + "." + FILE_EXTENSION;
    String filePath = saveDir + "/" + filenameWithExtension;
    return new File(filePath);
  }

  @Override
  protected void logHeaderMarker(Boolean isRequest) {
    //noop
  }

}
