package com.bftcom.apps.gisgmp.gui.panels;

import com.bftcom.apps.gisgmp.gui.panels.generators.ImportChargeResponseGeneratorPanel;
import com.bftcom.apps.gisgmp.ws.gisgmp.validation.Validators;
import com.bftcom.apps.property.resource.bundle.LangResourceBundle;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.pi2.core.gui.IkkaJXPanel;
import org.pi2.core.utils.JaxbUtils;
import org.pi2.core.utils.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3._2004._08.xop.include.Include;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.roskazna.smevunifoservice.ObjectFactory;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.GregorianCalendar;
import java.util.logging.Level;

/**
 * @author ikka
 * @date: 30.05.2014.
 */
public class GisGmpPacketValidator extends IkkaJXPanel {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(ImportChargeResponseGeneratorPanel.class);

  private JXButton btnValidate;
  RSyntaxTextArea rSyntaxTextArea;
  private String xmlMsg;
  private JPanel cp;
  private RTextScrollPane comp;

  @Override
  protected void packComponents() {
    comp = new RTextScrollPane(rSyntaxTextArea);
    cp.add(comp);
    add(btnValidate, "wrap");
    add(cp, "grow");
  }

  @Override
  protected void configComponents() {
    btnValidate.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String text4Cliboard = rSyntaxTextArea.getText();
        //StringSelection stringSelection = new StringSelection(text4Cliboard);
        //Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        //clpbrd.setContents(stringSelection, null);
        Document document = XmlUtils.convertStringToDocument(text4Cliboard);
        NodeList unifoTransferMsgNodeList = document.getElementsByTagName("ns11:UnifoTransferMsg");
        int length = unifoTransferMsgNodeList.getLength();
        System.out.println(String.format("found %s UnifoTransferMsg elements", length));
        Node item = unifoTransferMsgNodeList.item(0);

        try {
          Document convert = XmlUtils.convertToDocument(item);
          String x = XmlUtils.convertDocumentToString(convert);
          System.out.println(x);

          UnifoTransferMsg unifoTransferMsg = JaxbUtils.inputStreamToObject(x, UnifoTransferMsg.class, new Class[]{Include.class, UnifoTransferMsg.class});
         //todo@ikka bug #1 after conversion timestamp is lost. lazy to find out a real value and set it and have no idea why it is not converted automatically
          unifoTransferMsg.getMessageData().getAppData().getImportDataResponse().getTicket().getPostBlock().setTimeStamp(new XMLGregorianCalendarImpl(new GregorianCalendar()));
          Validators.validate(unifoTransferMsg);
          unifoTransferMsg = null;
        } catch (ParserConfigurationException | JAXBException | SAXException e1) {
          e1.printStackTrace();
          JXErrorPane jxErrorPane = new JXErrorPane();
          jxErrorPane.setErrorInfo(new ErrorInfo(LangResourceBundle.getString("validation.error"), ExceptionUtils.getRootCauseMessage(e1), e1.getLocalizedMessage(), "C", e1, Level.SEVERE, null));
          JXErrorPane.showDialog(null, jxErrorPane);
        }


        String[] split = text4Cliboard.split("\n");
        for (String s : split) {
          //log.debug(s);
        }


      }
    });

    rSyntaxTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
    rSyntaxTextArea.setCodeFoldingEnabled(true);
    rSyntaxTextArea.setText(xmlMsg);
    rSyntaxTextArea.setCaretPosition(0);
  }

  @Override
  protected void initComponents() {
    btnValidate = new JXButton(LangResourceBundle.getString("interface.btn.validateXml"));
    rSyntaxTextArea = new RSyntaxTextArea();
    cp = new JPanel(new BorderLayout());
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

  @Override
  protected void setLayout2() {
    setLayout(new MigLayout("gap 2, fill"));
  }
}
