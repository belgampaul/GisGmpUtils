package com.bftcom.apps.gisgmp.gui.panels;

import be.belgampaul.core.eventbus.EventBusService;
import com.bftcom.apps.gisgmp.config.Config;
import com.bftcom.apps.gisgmp.config.Constants;
import com.bftcom.apps.gisgmp.repositories.ReceivedImportCharges;
import com.bftcom.apps.property.resource.bundle.LangResourceBundle;
import com.google.common.eventbus.Subscribe;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXLabel;
import org.pi2.core.gui.IkkaJXPanel;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;
import ru.roskazna.xsd.charge.ChargeType;
import ru.roskazna.xsd.pgu_importrequest.ImportRequest;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ikka
 *         date: 01.06.2014.
 */
public class ReceivedImportChargePanel extends IkkaJXPanel<UnifoTransferMsg> {
  private JPanel exportResponsesContainer;
  private JPanel importResponsesContainer;
  private JXButton jxButton;
  private JXButton jxButton2;
  private JCheckBox cbSendSuccessResponseOnChargeImportR;
  private UnifoTransferMsg unifoTransferMsg;


  public ReceivedImportChargePanel(UnifoTransferMsg param) {
    super(param);
  }

  @Override
  protected void packComponents() {
//    exportResponsesContainer.add(jxButton);
//    exportResponsesContainer.add(cbSendSuccessResponseOnChargeImportR, "wrap");
//    importResponsesContainer.add(jxButton2);


//    add(exportResponsesContainer, "width 50%");
//    add(importResponsesContainer, "width 50%, wrap");
//    add(new JLabel(), "width 50%, wrap");
    ImportRequest importRequest = unifoTransferMsg.getMessageData().getAppData().getImportData().getImportRequest();
    ChargeType charge = importRequest.getCharge();
    String id = importRequest.getPostBlock().getID();


    addRow("ChangeStatus", LangResourceBundle.getString("doc.gisgmp.charge.changeStatus"), charge.getChangeStatus());
    addRow("id", "under construction", id);
    addRow("UnifiedPayerIdentifier", LangResourceBundle.getString("doc.gisgmp.UnifiedPayerIdentifier"), charge.getUnifiedPayerIdentifier());
    addRow("SupplierBillID", LangResourceBundle.getString("doc.gisgmp.SupplierBillID"), charge.getSupplierBillID());
    addRow("BillFor", LangResourceBundle.getString("doc.gisgmp.BillFor"), charge.getBillFor());
    addRow("TotaAmount", LangResourceBundle.getString("doc.gisgmp.TotalAmount"), String.valueOf(charge.getTotalAmount()));

    final JXCollapsiblePane cp = new JXCollapsiblePane();

    // JXCollapsiblePane can be used like any other container
    cp.setLayout(new BorderLayout());

    // the Controls panel with a textfield to filter the tree
    JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
    controls.add(new JLabel("Search:"));
    controls.add(new JTextField(10));
    controls.add(new JButton("Refresh"));
    controls.setBorder(new TitledBorder("Filters"));
    cp.add("Center", controls);
    final JButton toggle = new JButton(cp.getActionMap().get("toggle"));
    toggle.setText("Something");
    toggle.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (cp.isCollapsed()){

          toggle.setText("Hide");
        } else {
          toggle.setText("Open");
        }
      }
    });

    add(toggle, "split 2, wrap");
    add(cp, "split 2, wrap");

    addRow("TotaAmount", LangResourceBundle.getString("doc.gisgmp.TotalAmount"), String.valueOf(charge.getTotalAmount()));


  }

  private void addRow(String lblText, String lblTooltipText, String value) {
    JXLabel lblUnifiedPayerIdentifier = new JXLabel(lblText);
    lblUnifiedPayerIdentifier.setToolTipText(lblTooltipText);
    add(lblUnifiedPayerIdentifier);
    add(new JXLabel(value), "wrap");
  }

  @Override
  protected void configComponents() {
    EventBusService.getInstance().registerSubscriber(this);
    cbSendSuccessResponseOnChargeImportR.setSelected(Config.read(Constants.APPKEY_C_IMP_CH_POSITIVE).equals(Boolean.TRUE.toString()));
    cbSendSuccessResponseOnChargeImportR.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean selected = cbSendSuccessResponseOnChargeImportR.isSelected();
        Config.write(Constants.APPKEY_C_IMP_CH_POSITIVE, selected + "");
      }
    });
  }

  @Override
  protected void initComponents() {
    exportResponsesContainer = new ExportResponseContainer();
    importResponsesContainer = new ImportResponseContainer();
    jxButton = new JXButton("Button");
    jxButton2 = new JXButton("Button2");
    cbSendSuccessResponseOnChargeImportR = new JCheckBox(LangResourceBundle.getString("interface.gisgmp.responses.config.Send"));
  }

  @Override
  protected void initModel() {
    unifoTransferMsg = ReceivedImportCharges.getReceivedImportCharges().get(0);
  }

  @Override
  protected void setLayout2() {
    setLayout(new MigLayout("gap 2"));
  }

  @Subscribe
  public void newImportChargeArrived(ImportChargeArrivedEvent event) {

  }

}
