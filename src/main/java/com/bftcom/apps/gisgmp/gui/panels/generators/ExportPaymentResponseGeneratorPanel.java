package com.bftcom.apps.gisgmp.gui.panels.generators;


import com.bftcom.apps.property.resource.bundle.LangResourceBundle;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdesktop.swingx.JXButton;
import org.pi2.core.gui.IkkaActionListener;
import org.pi2.core.gui.IkkaJXPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author ikka
 * @date: 29.05.2014.
 */
public class ExportPaymentResponseGeneratorPanel extends IkkaJXPanel {
  public static final String RESPONSES_TEMPLATES_PAYMENTS_EXPORT_SUCCESS_XML = "/responses/templates/payments/export/success.xml";
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(ExportPaymentResponseGeneratorPanel.class);

  private JXButton btnConvertToBase64;
  RSyntaxTextArea srcSyntaxTextArea;
  RSyntaxTextArea dstSyntaxTextArea;
  private String xmlMsg;
  private RTextScrollPane spSrc;
  private RTextScrollPane spDst;
  private JPanel pnlSrc;
  private JPanel pnlDst;

  @Override
  protected void packComponents() {


    pnlSrc.add(spSrc);

    pnlDst.add(spDst);
    add(btnConvertToBase64, "height 24, shrink 0");
    add(new JLabel(), "wrap");
    add(pnlSrc, "height 100%, grow");
    add(pnlDst, "width 50%, grow, wrap");
//    add(jTextArea2, "grow");
  }

  @Override
  protected void configComponents() {
    btnConvertToBase64.addActionListener(new IkkaActionListener() {
      @Override
      public void actionPerformed2(ActionEvent e) {
        convert();
      }
    });

    srcSyntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
    srcSyntaxTextArea.setCodeFoldingEnabled(true);
    srcSyntaxTextArea.setLineWrap(true);
    srcSyntaxTextArea.setText(xmlMsg);
    srcSyntaxTextArea.setCaretPosition(0);
    srcSyntaxTextArea.setLineWrap(true);

    dstSyntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
    dstSyntaxTextArea.setCodeFoldingEnabled(true);
    dstSyntaxTextArea.setLineWrap(true);
    dstSyntaxTextArea.setText(xmlMsg);
    dstSyntaxTextArea.setCaretPosition(0);
    dstSyntaxTextArea.setLineWrap(true);
    dstSyntaxTextArea.setEditable(false);

    srcSyntaxTextArea.addKeyListener(onPasteListener());
  }

  private KeyListener onPasteListener() {
    return new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {

      }

      @Override
      public void keyReleased(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
          convert();
        }
      }
    };
  }

  @Override
  protected void initComponents() {
    pnlSrc = new JPanel(new BorderLayout());
    pnlDst = new JPanel(new BorderLayout());

    btnConvertToBase64 = new JXButton(LangResourceBundle.getString("interface.btn.ConvertToBase64FinalPayment"));

    srcSyntaxTextArea = new RSyntaxTextArea();
    dstSyntaxTextArea = new RSyntaxTextArea();

    spSrc = new RTextScrollPane(srcSyntaxTextArea);
    spDst = new RTextScrollPane(dstSyntaxTextArea);
  }

  @Override
  protected void initModel() {
    getSuccessTemplate();

  }

  private String getSuccessTemplate() {
    StringWriter writer = new StringWriter();
    try {
      IOUtils.copy(ExportPaymentResponseGeneratorPanel.class.getResource(RESPONSES_TEMPLATES_PAYMENTS_EXPORT_SUCCESS_XML).openStream(), writer, StandardCharsets.UTF_8.name());
    } catch (IOException e) {

    }
    return writer.toString();
  }

  @SuppressWarnings("HardCodedStringLiteral")
  @Override
  protected void setLayout2() {
    setLayout(new MigLayout("gap 2, fill"));
  }


  private void convert() {
    String text = srcSyntaxTextArea.getText();
    String encodedToString =  Base64.encodeBase64String(text.getBytes(StandardCharsets.UTF_8));
    String successTemplate = getSuccessTemplate();
    dstSyntaxTextArea.setText(successTemplate.replace("{{}}", encodedToString));
    dstSyntaxTextArea.setCaretPosition(0);


    StringSelection stringSelection = new StringSelection(dstSyntaxTextArea.getText());
    Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
    clpbrd.setContents(stringSelection, null);
  }
}
