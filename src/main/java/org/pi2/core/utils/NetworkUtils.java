package org.pi2.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author ikka
 * @date: 29.05.2014.
 */
public class NetworkUtils {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(NetworkUtils.class);
  public static void main(String[] args) {

  }

  public static String getLocalhostName() {
    String hostname = "Unknown";

    try {
      InetAddress addr;
      addr = InetAddress.getLocalHost();
      hostname = addr.getHostName();
    } catch (UnknownHostException ex) {
      log.warn("Hostname can not be resolved", ex);
    }
    return hostname;
  }
}
