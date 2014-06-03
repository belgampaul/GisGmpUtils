package com.bftcom.apps.gisgmp.gui;

import com.bftcom.apps.gisgmp.ws.gisgmp.events.UnifoTransferMsgReceived;
import com.bftcom.apps.property.resource.bundle.LangResourceBundle;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;

/**
 * @author ikka
 * @date: 30.05.2014.
 */
public class AppTray {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(AppTray.class);
  private TrayIcon trayIcon;
  final SystemTray tray = SystemTray.getSystemTray();



  public AppTray() {
    //Check the SystemTray support
    if (!SystemTray.isSupported()) {
      log.warn("SystemTray is not supported");
      return;
    }


    final PopupMenu popup = new PopupMenu();
    trayIcon = new TrayIcon(createImage("/images/bulb.gif", "tray icon"), LangResourceBundle.getString("interface.window.Title"));

    // Create a popup menu components
    MenuItem aboutItem = new MenuItem("About");
    CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
    CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
    Menu displayMenu = new Menu("Display");
    MenuItem errorItem = new MenuItem("Error");
    MenuItem warningItem = new MenuItem("Warning");
    MenuItem infoItem = new MenuItem("Info");
    MenuItem noneItem = new MenuItem("None");
    MenuItem exitItem = new MenuItem("Exit");

    //Add components to popup menu
//    popup.add(aboutItem);
//    popup.addSeparator();
//    popup.add(cb1);
//    popup.add(cb2);
//    popup.addSeparator();
//    popup.add(displayMenu);
//    displayMenu.add(errorItem);
//    displayMenu.add(warningItem);
//    displayMenu.add(infoItem);
//    displayMenu.add(noneItem);
    popup.add(exitItem);

    trayIcon.setPopupMenu(popup);

    try {
      tray.add(trayIcon);
    } catch (AWTException e) {
      System.out.println("TrayIcon could not be added.");
      return;
    }

    trayIcon.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
            "This dialog box is run from System Tray");
      }
    });

//    aboutItem.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        JOptionPane.showMessageDialog(null,
//            "This dialog box is run from the About menu item");
//      }
//    });

    cb1.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        int cb1Id = e.getStateChange();
        if (cb1Id == ItemEvent.SELECTED) {
          trayIcon.setImageAutoSize(true);
        } else {
          trayIcon.setImageAutoSize(false);
        }
      }
    });

    cb2.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        int cb2Id = e.getStateChange();
        if (cb2Id == ItemEvent.SELECTED) {
          trayIcon.setToolTip("Sun TrayIcon");
        } else {
          trayIcon.setToolTip(null);
        }
      }
    });

    ActionListener listener = new ActionListener() {
      @SuppressWarnings("CallToStringEquals")
      public void actionPerformed(ActionEvent e) {
        MenuItem item = (MenuItem) e.getSource();
        //TrayIcon.MessageType type = null;
        System.out.println(item.getLabel());
        if ("Error".equals(item.getLabel())) {
          //type = TrayIcon.MessageType.ERROR;
          trayIcon.displayMessage("Sun TrayIcon Demo",
              "This is an error message", TrayIcon.MessageType.ERROR);

        } else if ("Warning".equals(item.getLabel())) {
          //type = TrayIcon.MessageType.WARNING;
          trayIcon.displayMessage("Sun TrayIcon Demo",
              "This is a warning message", TrayIcon.MessageType.WARNING);

        } else if ("Info".equals(item.getLabel())) {
          //type = TrayIcon.MessageType.INFO;
          trayIcon.displayMessage("Sun TrayIcon Demo",
              "This is an info message", TrayIcon.MessageType.INFO);

        } else if ("None".equals(item.getLabel())) {
          //type = TrayIcon.MessageType.NONE;
          trayIcon.displayMessage("Sun TrayIcon Demo",
              "This is an ordinary message", TrayIcon.MessageType.NONE);
        }
      }
    };

    errorItem.addActionListener(listener);
    warningItem.addActionListener(listener);
    infoItem.addActionListener(listener);
    noneItem.addActionListener(listener);

    exitItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tray.remove(trayIcon);
      }
    });



  }

  @Subscribe
  public void unifoTransferMsgReceived(UnifoTransferMsgReceived msg) {
    if (msg.isValid()){
      trayIcon.displayMessage(String.format("%s %s", LangResourceBundle.getString("interface.gisgmp.errmsg.ValidXMLDocumentReceived"), msg.getRequestType()), "", TrayIcon.MessageType.INFO);
    }else {
      trayIcon.displayMessage(String.format("%s %s", LangResourceBundle.getString("interface.gisgmp.errmsg.InvalidXMLDocumentReceived"), msg.getRequestType()), "", TrayIcon.MessageType.ERROR);
    }
  }

  public void remove(){
    tray.remove(trayIcon);
  }
  //Obtain the image URL
  @SuppressWarnings("HardCodedStringLiteral")
  protected static Image createImage(String path, String description) {
    URL imageURL = AppTray.class.getResource(path);

    if (imageURL == null) {
      System.err.println("Resource not found: " + path);
      return null;
    } else {
      return (new ImageIcon(imageURL, description)).getImage();
    }
  }
}
