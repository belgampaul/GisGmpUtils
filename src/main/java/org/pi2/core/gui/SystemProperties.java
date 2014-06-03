package org.pi2.core.gui;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.lf5.viewer.LogTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;
import java.util.Properties;

/**
 * User: ikka
 * Date: 02.10.13
 * Time: 1:45
 */
public class SystemProperties {
  private JPanel panel;
  private JTextArea textArea;
  private JTable table;

  public SystemProperties() {
    //Tools.openURL("www.google.be");
    panel.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
        super.componentShown(e);

        StringBuilder sb = new StringBuilder();
        Properties properties = System.getProperties();
        String str = StringEscapeUtils.escapeJava("\n");
        DefaultTableModel logTableModel = new LogTableModel(new Object[]{"key", "property"}, 0);
        for (Map.Entry<Object, Object> property : properties.entrySet()) {
          String key = StringEscapeUtils.escapeJava((String) property.getKey());
          String value = StringEscapeUtils.escapeJava((String) property.getValue());
          sb.append(key).append("=").append(value).append("\n");
          logTableModel.addRow(new Object[]{key, value});
        }
        textArea.setText(sb.toString());
        table.setModel(logTableModel);
      }


    });
  }

  public JPanel getPanel() {
    return panel;
  }
}
