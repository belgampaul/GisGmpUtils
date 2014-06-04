package com.bftcom.apps.gisgmp.gui.panels;

import be.belgampaul.core.eventbus.EventBusService;
import com.bftcom.apps.gisgmp.repositories.ReceivedImportCharges;
import com.google.common.eventbus.Subscribe;
import net.miginfocom.swing.MigLayout;
import org.pi2.core.gui.IkkaJXPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ikka
 *         date: 01.06.2014.
 */
public class ReceivedImportChargesPanel extends IkkaJXPanel<UnifoTransferMsg> {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(ReceivedImportChargesPanel.class);
  private List<ReceivedImportChargePanel> importChargePanelList;
  private JScrollPane jScrollPane;
  private JPanel jPanel;


  {
    EventBusService.getInstance().registerSubscriber(this);
  }

  @Override
  protected void packComponents() {



    for (ReceivedImportChargePanel receivedImportChargePanel : importChargePanelList) {
     jPanel.add(receivedImportChargePanel, "wrap, grow");
    }

    JScrollBar vertical = jScrollPane.getVerticalScrollBar();
    vertical.setValue(vertical.getMaximum());

    add(jScrollPane, "grow");
  }

  private JPanel getjPanel(int i) {
    JPanel jPanel = new JPanel();
    jPanel.add(new JLabel(String.valueOf(i)));
    jPanel.setBackground(Color.yellow);
    jPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
    jPanel.setSize(new Dimension(50, 100));
    return jPanel;
  }

  @Override
  protected void configComponents() {
    jScrollPane.setViewportView(jPanel);
  }

  @Override
  protected void initComponents() {
    jScrollPane = new JScrollPane();
    jPanel = new JPanel(new MigLayout("fill"));
  }

  @Override
  protected void initModel() {
    importChargePanelList = new ArrayList<>();
    List<UnifoTransferMsg> receivedImportCharges = ReceivedImportCharges.getReceivedImportCharges();
    for (UnifoTransferMsg receivedImportCharge : receivedImportCharges) {
      ReceivedImportChargePanel receivedImportChargePanel = new ReceivedImportChargePanel(receivedImportCharge);
      importChargePanelList.add(receivedImportChargePanel);
    }

  }

  @Override
  protected void setLayout2() {
    setLayout(new MigLayout("fill"));
  }

  @Subscribe
  public void newImportChargeArrived(ImportChargeArrivedEvent event) {
    UnifoTransferMsg unifoTransferMsg = event.getUnifoTransferMsg();
    ReceivedImportChargePanel receivedImportChargePanel = new ReceivedImportChargePanel(unifoTransferMsg);
    importChargePanelList.add(receivedImportChargePanel);
    jPanel.add(receivedImportChargePanel, "wrap, grow");
    JScrollBar vertical = jScrollPane.getVerticalScrollBar();
    vertical.setValue(vertical.getMaximum());
    log.debug("add new panel");
    this.updateUI();
  }
}
