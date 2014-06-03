package com.bftcom.apps.gisgmp.config;

import org.pi2.core.windows.WinRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ikka
 * @date: 30.05.2014.
 */
@SuppressWarnings("CallToNumericToString")
public class Config {
  //slf4j logger
  public static final HashMap<String , String> key4DefaultValue = new HashMap<>();
  static {
    try {
      WinRegistry.createKey(WinRegistry.HKEY_CURRENT_USER, Constants.APPKEY);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    key4DefaultValue.put(Constants.APPKEY_C_IMP_CH_POSITIVE, Boolean.TRUE.toString());
    key4DefaultValue.put(Constants.APPKEY_PORT, "2000");
    key4DefaultValue.put(Constants.APPKEY_AUTO_START_WEB_SERVICE, Boolean.FALSE.toString());
  }

  private static Logger log = LoggerFactory.getLogger(Config.class);

  public static String read(final String value) {
    try {
      return WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, Constants.APPKEY, value);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void write(final String key, final String value) {
    try {
      WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, Constants.APPKEY, key, value);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }


  public static void main(String[] args) {
    String test = read("test");
    log.info(test);
  }
}
