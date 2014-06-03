package com.bftcom.apps.gisgmp.gui.panels.generators;

import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.IOUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdesktop.swingx.JXButton;
import org.pi2.core.gui.IkkaJXPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author ikka
 * @date: 29.05.2014.
 */
@SuppressWarnings("InstanceVariableMayNotBeInitialized")
public class ImportChargeResponseGeneratorPanel extends IkkaJXPanel {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(ImportChargeResponseGeneratorPanel.class);

  private JXButton btnCopy;
  RSyntaxTextArea rSyntaxTextArea;
  private String xmlMsg;
  private JPanel pnlScrollPaneHolder;
  private RTextScrollPane rTextScrollPane;

  @Override
  protected void packComponents() {
    rTextScrollPane = new RTextScrollPane(rSyntaxTextArea);
    pnlScrollPaneHolder.add(rTextScrollPane);
    add(btnCopy, "wrap");
    add(pnlScrollPaneHolder, "grow");
  }

  @Override
  protected void configComponents() {
    btnCopy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String text4Cliboard = rSyntaxTextArea.getText();
        StringSelection stringSelection = new StringSelection(text4Cliboard);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
      }
    });

    rSyntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
    rSyntaxTextArea.setCodeFoldingEnabled(true);
    rSyntaxTextArea.setText(xmlMsg);
    rSyntaxTextArea.setCaretPosition(0);
  }

  @Override
  protected void initComponents() {
    btnCopy = new JXButton("Copy");
    rSyntaxTextArea = new RSyntaxTextArea();
    pnlScrollPaneHolder = new JPanel(new BorderLayout());
  }

  @Override
  protected void initModel() {
    StringWriter writer = new StringWriter();
    String name = "/responses/templates/charges/import/success.xml";
    try {
      IOUtils.copy(ImportChargeResponseGeneratorPanel.class.getResource(name).openStream(), writer, StandardCharsets.UTF_8.name());
    } catch (IOException e) {
      log.error("Unable to read file {}", name, e);
    }
    xmlMsg = writer.toString();
  }

  @SuppressWarnings("HardCodedStringLiteral")
  @Override
  protected void setLayout2() {
    setLayout(new MigLayout("gap 2, fill"));
  }
}
