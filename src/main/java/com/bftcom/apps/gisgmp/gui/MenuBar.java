package com.bftcom.apps.gisgmp.gui;

import com.bftcom.apps.gisgmp.gui.panels.AppSettingsPanel;
import com.bftcom.apps.gisgmp.gui.panels.GisGmpEmulatorControlPanel;
import com.bftcom.apps.gisgmp.gui.panels.GisGmpPacketValidator;
import com.bftcom.apps.gisgmp.gui.panels.ReceivedImportChargesPanel;
import com.bftcom.apps.gisgmp.gui.panels.generators.ExportPaymentResponseGeneratorPanel;
import com.bftcom.apps.gisgmp.gui.panels.generators.ImportChargeResponseGeneratorPanel;
import com.bftcom.apps.property.resource.bundle.LangResourceBundle;
import org.pi2.core.gui.SystemProperties;
import org.pi2.form.azk.component.WebBrowser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * User: ikka
 * Date: 28.09.13
 * Time: 3:03
 */
public class MenuBar extends JMenuBar {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(MenuBar.class);

  public MenuBar() {
    super();
    fileMenu();
    mockGisGmpWebServiceMenu();
  }


  private void fileMenu() {
    JMenu file = new JMenu(LangResourceBundle.getString("interface.menu.file"));
    file.setMnemonic(KeyEvent.VK_F);

    createMenuItem(file, "", 0, WebBrowser.class.getCanonicalName(), LangResourceBundle.getString("interface.menu.webBrowser"));
//    createMenuItem(file, "", 0, ExamplePanel.class.getCanonicalName(), ExamplePanel.class.getSimpleName());
    createMenuItem(file, "", 0, SystemProperties.class.getCanonicalName(), LangResourceBundle.getString("interface.menu.systemProperties"));

    createMenuItem(
        file,
        LangResourceBundle.getString("interface.menu.exit"),
        LangResourceBundle.getString("interface.menu.exit.tooltip"),
        KeyEvent.VK_E, getExitActionListener()
    );
    add(file);
  }

  private void mockGisGmpWebServiceMenu() {
    JMenu file = new JMenu(LangResourceBundle.getString("interface.menu.webServices"));
    file.setMnemonic(KeyEvent.VK_F);

    createMenuItem(file, "", 0, GisGmpEmulatorControlPanel.class.getCanonicalName(), LangResourceBundle.getString("interface.menu.gisGmpMockWebService"));
    createMenuItem(file, "", 0, ImportChargeResponseGeneratorPanel.class.getCanonicalName(), LangResourceBundle.getString("interface.menu.SuccessfullChargeImportFileGenerator"));
    createMenuItem(file, "", 0, ExportPaymentResponseGeneratorPanel.class.getCanonicalName(), LangResourceBundle.getString("interface.menu.Base64Encoder"));
    createMenuItem(file, "", 0, AppSettingsPanel.class.getCanonicalName(), LangResourceBundle.getString("interface.menu.ResponsesController"));
    createMenuItem(file, "", 0, GisGmpPacketValidator.class.getCanonicalName(), LangResourceBundle.getString("interface.menu.GisGmpPacketValidator"));
    createMenuItem(file, "", 0, ReceivedImportChargesPanel.class.getCanonicalName(), LangResourceBundle.getString("interface.menu.ReceivedImportChargesPanel"));
    add(file);
  }

  private ActionListener getExitActionListener() {
    return new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        System.exit(0);
      }
    };
  }

  private JMenuItem getMenuItem(String caption, int mnemonic, String toolTipText, ActionListener actionListener) {
    JMenuItem eMenuItem = new JMenuItem(caption);
    eMenuItem.setMnemonic(mnemonic);
    eMenuItem.setToolTipText(toolTipText);
    eMenuItem.addActionListener(actionListener);
    return eMenuItem;
  }

  private void createMenuItem(JMenu menu, String toolTipText, int mnemonic, String className, String menuCaption) {
    menu.add(getMenuItem(toolTipText, mnemonic, className, menuCaption));
  }

  private void createMenuItem(JMenu menu, String caption, String toolTipText, int mnemonic, ActionListener actionListener) {
    menu.add(getMenuItem(caption, mnemonic, toolTipText, actionListener));
  }

  private JMenuItem getMenuItem(String toolTipText, int mnemonic, String className, String menuCaption) {
    JMenuItem eMenuItem;
    eMenuItem = new JMenuItem(menuCaption);
    eMenuItem.setMnemonic(mnemonic);

    eMenuItem.setName(className);
    eMenuItem.setToolTipText(toolTipText);
    eMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        firePropertyChange("menu", null, ((JMenuItem) event.getSource()).getName());
      }
    });
    return eMenuItem;
  }

  private class MenuAction extends AbstractAction {
    private MenuAction() {
      super();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
  }
}
