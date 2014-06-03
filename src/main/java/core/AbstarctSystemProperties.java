package core;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * User: ikka
 * Date: 10/27/13
 * Time: 12:09 AM
 */
public class AbstarctSystemProperties {
  //logger
  private static final Logger log = Logger.getLogger(AbstarctSystemProperties.class);
  private static final String PROJECT_PROPERTIES = "project.properties";
  private static AbstarctSystemProperties instance;


  public Properties getProperties() {
    return properties;
  }

  private Properties properties;

  private AbstarctSystemProperties() {
    properties = new Properties();
    File file = new File(PROJECT_PROPERTIES);
    try(InputStream is = new FileInputStream(file)) {
      properties.load(is);
    } catch (IOException e) {
      log.debug(e);
    }
  }

  private static void init(){
    instance = new AbstarctSystemProperties();
  }
  public static String getSystemProperty(String key) {
    if (instance == null) {
      log.debug("Loading system properties ...");
      init();
    }
    return instance.getProperties().getProperty(key);
  }


  public static void saveProperties(){
    if (instance == null) {
      init();
    }
    //save properties to project root folder
    if (instance.getProperties() != null){
      try {

        instance.getProperties().store(new FileOutputStream(""+PROJECT_PROPERTIES), null);
      } catch (IOException e) {
        log.debug("",e);
        throw new RuntimeException(e);
      }
    }
  }
}
