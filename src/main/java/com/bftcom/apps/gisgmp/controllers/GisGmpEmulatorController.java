package com.bftcom.apps.gisgmp.controllers;

import com.bftcom.apps.gisgmp.ws.gisgmp.GisGmpMockWebService;
import org.pi2.core.utils.NetworkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Endpoint;

/**
 * @author ikka
 * @date: 29.05.2014.
 */
public class GisGmpEmulatorController {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(GisGmpEmulatorController.class);

  private static Endpoint endpoint;

  private static String address;
  private static String runningAddress;
  private static String runningPort;

  private static final String host;

  static {
    endpoint = Endpoint.create(new GisGmpMockWebService());
    address = "http://0.0.0.0:%s/SmevUnifoService";
    host = NetworkUtils.getLocalhostName();
  }


  public static void start(String port) {

    synchronized (endpoint) {
      runningPort = port;
      if (runningPort == null || runningPort.isEmpty()) {
        runningPort = "2000";
      }
      runningAddress = String.format(address, runningPort);
      if (endpoint.isPublished()) {
        log.warn("web service is already running");
        return;
      }

      log.debug("publishing web service");
      try {

        endpoint.publish(runningAddress);
      } catch (IllegalStateException e) {
        //log.warn("", e);
        endpoint = Endpoint.create(new GisGmpMockWebService());
        endpoint.publish(getAddress());
      }

      log.debug("web service has been published at address " + runningAddress);
    }
  }


  public static void stop() {
    synchronized (endpoint) {
      if (!endpoint.isPublished()) {
        log.warn("can not stop web service as it is not running");
        return;
      }
      log.debug("stopping web service");
      endpoint.stop();
      log.debug("web service has been stopped at address " + getAddress());
    }
  }


  public static String getAddress() {
    return String.format("http://%s:%s/SmevUnifoService" + "?WSDL", host, runningPort);
  }



  public static void main(String[] args) {
    new GisGmpEmulatorController();
  }


  public static boolean isStarted() {
    return endpoint.isPublished();
  }
}
