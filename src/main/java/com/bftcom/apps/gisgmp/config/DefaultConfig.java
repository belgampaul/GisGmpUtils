package com.bftcom.apps.gisgmp.config;

import org.pi2.core.windows.WinRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author ikka
 * @date: 30.05.2014.
 */
public class DefaultConfig  {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(DefaultConfig.class);

  static {
    for (Map.Entry<String, String> key4DefaultValue : Config.key4DefaultValue.entrySet()) {
      String key = key4DefaultValue.getKey();
      String winRegValue = Config.read(key);
      if (winRegValue == null || winRegValue.isEmpty()){
        String value = key4DefaultValue.getValue();
        Config.write(key, value);
      }
    }

  }

  private static DefaultConfig instance = new DefaultConfig();

  private DefaultConfig() {
  }

  public static void init() {
    log.info("default configuration is inited");
  }

}
