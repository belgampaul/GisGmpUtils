package com.bftcom.apps.property.resource.bundle;

import org.pi2.core.i18n.UTF8Control;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author ikka
 * date: 29.05.2014.
 */
public class LangResourceBundle {
  private static ResourceBundle instance;
  static {
    //noinspection HardCodedStringLiteral
    instance = PropertyResourceBundle.getBundle("gui.lang", new UTF8Control());
  }

  public static String getString(final String key) {
    return instance.getString(key);
  }
}
