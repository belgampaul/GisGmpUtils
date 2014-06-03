package org.pi2.core.gui;

import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;

/**
 * User: ikka
 * Date: 10/26/13
 * Time: 12:21 AM
 */
public abstract class IkkaJXPanel<T> extends JXPanel {


//  public IkkaJXPanel(Object o) {
//  }
protected T param;

  public IkkaJXPanel() {
    this(null);

  }

  public IkkaJXPanel(T param) {
    this.param = param;
    setName(this.getClass().getSimpleName());
    setBackground(UIManager.getColor("Panel.background"));
    setLayout2();

    initModel();
    initComponents();
    configComponents();
    packComponents();
    setBorder(BorderFactory.createTitledBorder(this.getClass().getSimpleName()));

  }

  protected void setLayout2() {
    setLayout(new MigLayout("fill"));
  }

  protected abstract void packComponents();

  protected abstract void configComponents();

  protected abstract void initComponents();

  protected abstract void initModel();


}
