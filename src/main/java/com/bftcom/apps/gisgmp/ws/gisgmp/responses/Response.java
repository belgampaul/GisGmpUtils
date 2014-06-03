package com.bftcom.apps.gisgmp.ws.gisgmp.responses;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Date: 26.03.13
 *
 * @author p.shapoval
 */
public class Response {
  static protected Properties properties;

  static {
    properties = new Properties();
    try {
      properties.load(new FileInputStream("c:\\gisgmp.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
