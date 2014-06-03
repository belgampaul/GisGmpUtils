package org.pi2.core.gui;

import org.jdesktop.swingx.JXButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract public class IkkaActionListener implements ActionListener {
  @Override
  final public void actionPerformed(ActionEvent e) {
    Container parent = null;
    if (e.getSource() instanceof JButton) {
      parent = ((JXButton) e.getSource()).getParent();
      parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    try {
      actionPerformed2(e);
    } catch (RuntimeException ex){
      throw new RuntimeException(ex);
    } finally {
      if (parent != null){
        parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      }
    }
  }

    public abstract void actionPerformed2(ActionEvent e);
}
