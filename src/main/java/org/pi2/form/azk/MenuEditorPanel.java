package org.pi2.form.azk;

import net.miginfocom.swing.MigLayout;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashMap;

/**
 * User: ikka
 * Date: 27.09.13
 * Time: 5:38
 */
public class MenuEditorPanel extends JPanel {
  private HashMap<String, Component> components = new HashMap<>();
  private HashMap<String, String> attributes = new HashMap<>();
  private Element element;


  public MenuEditorPanel(Element element) {
    this();

    if (element != null) {
      this.element = element;

      NamedNodeMap attributes = element.getAttributes();
      int length = attributes.getLength();
      setLayout(new MigLayout("top, left"));
      for (int i = 0; i < length; i++) {
        Node item = attributes.item(i);
        String nodeName = item.getNodeName();
        String nodeValue = item.getNodeValue();

        JLabel jLabel = new JLabel(nodeName);
        JTextField jTextField = new JTextField(nodeValue);
        jTextField.setName(nodeName);
        jTextField.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            System.out.println("log");
          }
        });

        jTextField.addFocusListener(new FocusAdapter() {
          @Override
          public void focusLost(FocusEvent e) {
            super.focusLost(e);
            JTextField component = (JTextField) e.getComponent();
            String text = component.getText();
            String name = component.getName();
            MenuEditorPanel.this.element.setAttribute(name, text);
            MenuEditorPanel.this.firePropertyChange("element", 0, 1);
          }
        });

        this.attributes.put(nodeName, nodeValue);
        components.put("lbl" + nodeName, jLabel);
        components.put("edt" + nodeName, jTextField);
        add(jLabel, "left");
        add(jTextField, "wrap, grow, width 300");
      }
    }
  }

  public MenuEditorPanel() {
    super();
  }
}
