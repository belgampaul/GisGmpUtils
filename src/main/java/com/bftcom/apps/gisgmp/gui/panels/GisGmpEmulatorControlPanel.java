package com.bftcom.apps.gisgmp.gui.panels;

import com.bftcom.apps.gisgmp.config.Config;
import com.bftcom.apps.gisgmp.config.Constants;
import com.bftcom.apps.gisgmp.controllers.GisGmpEmulatorController;
import com.bftcom.apps.property.resource.bundle.LangResourceBundle;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.error.ErrorInfo;
import org.pi2.core.gui.IkkaActionListener;
import org.pi2.core.gui.IkkaJXPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

/**
 * @author ikka
 * @date: 29.05.2014.
 */
public class GisGmpEmulatorControlPanel extends IkkaJXPanel {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(GisGmpEmulatorControlPanel.class);
  @SuppressWarnings("InstanceVariableMayNotBeInitialized")
  private JXButton btnStartService;
  @SuppressWarnings("InstanceVariableMayNotBeInitialized")
  private JXButton btnStopService;
  private JXButton linkButton;
  private JXTextField tfServicePort;
  private JXLabel lblServicePort;

  @Override
  protected void packComponents() {
    add(lblServicePort);
    add(tfServicePort, "width 100px, wrap");
    JPanel startStopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

    startStopPanel.add(btnStopService);
    startStopPanel.add(btnStartService);
    add(startStopPanel, "span2, wrap");
//    add(btnStopService, "wrap");

    linkButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          open(new URI(GisGmpEmulatorController.getAddress()));
        } catch (URISyntaxException e1) {
          e1.printStackTrace();
        }
      }
    });

    add(linkButton, "span 2, wrap");
    add(new Label(""), "grow");
    add(new Label(""), "grow, push");
  }

  @Override
  protected void configComponents() {
    configBtnStartService();
    configBtnStopService();


    configLinkButton();
    configTFServicePort();
  }

  private void configBtnStartService() {
    btnStartService.addActionListener(new IkkaActionListener() {
      @Override
      public void actionPerformed2(ActionEvent e) {
        try {
          GisGmpEmulatorController.start(Config.read(Constants.APPKEY_PORT));
          btnStartService.setVisible(false);
          btnStopService.setVisible(true);
          linkButton.setVisible(true);
        } catch (Exception ex) {
          JXErrorPane.showInternalFrame(ex);
        }

      }
    });
    btnStartService.setVisible(!GisGmpEmulatorController.isStarted());
  }

  private void configBtnStopService() {
    btnStopService.addActionListener(new IkkaActionListener() {
      @Override
      public void actionPerformed2(ActionEvent e) {
        GisGmpEmulatorController.stop();
        btnStartService.setVisible(true);
        btnStopService.setVisible(false);
        linkButton.setVisible(false);
      }
    });

    btnStopService.setVisible(GisGmpEmulatorController.isStarted());
  }

  private void configLinkButton() {
    linkButton.setText("<HTML><FONT color=\"#000099\"><U>" + LangResourceBundle.getString("interface.control.OpenWsdlFileInWebBrowser") + "</U></FONT></HTML>");
    linkButton.setHorizontalAlignment(SwingConstants.LEFT);
    linkButton.setBorderPainted(false);
    linkButton.setOpaque(false);
    linkButton.setVisible(GisGmpEmulatorController.isStarted());
  }

  private void configTFServicePort() {
    tfServicePort.setText(Config.read(Constants.APPKEY_PORT));
    tfServicePort.getDocument().addDocumentListener(new DocumentListener() {
      // Listen for changes in the text
      public void changedUpdate(DocumentEvent e) {
        warn();
      }

      public void removeUpdate(DocumentEvent e) {
        warn();
      }

      public void insertUpdate(DocumentEvent e) {
        warn();
      }

      public void warn() {
        Config.write(Constants.APPKEY_PORT, tfServicePort.getText());
      }
    });
  }

  @Override
  protected void initComponents() {
    btnStartService = new JXButton(LangResourceBundle.getString("interface.control.Start"));
    btnStopService = new JXButton(LangResourceBundle.getString("interface.control.Stop"));
    linkButton = new JXButton();
    tfServicePort = new JXTextField();
    lblServicePort = new JXLabel(LangResourceBundle.getString("interface.gisgmp.ws.port"));
  }

  @Override
  protected void initModel() {

  }

  @Override
  protected void setLayout2() {
    setLayout(new MigLayout("gap 5, fill"));
  }

  private static void open(URI uri) {
    if (Desktop.isDesktopSupported()) {
      try {
        Desktop.getDesktop().browse(uri);
      } catch (IOException e) {
        log.error("", e);
      }
    } else {
      log.warn("no default web browser found in the system");
    }
  }
}
