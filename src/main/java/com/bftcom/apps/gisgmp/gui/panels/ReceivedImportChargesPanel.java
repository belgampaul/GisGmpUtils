package com.bftcom.apps.gisgmp.gui.panels;

import be.belgampaul.core.eventbus.EventBusService;
import com.bftcom.apps.gisgmp.config.Config;
import com.bftcom.apps.gisgmp.config.Constants;
import com.bftcom.apps.gisgmp.repositories.ReceivedImportCharges;
import com.bftcom.apps.property.resource.bundle.LangResourceBundle;
import com.google.common.eventbus.Subscribe;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.*;
import org.pi2.core.gui.IkkaJXPanel;
import ru.roskazna.smevunifoservice.UnifoTransferMsg;
import ru.roskazna.xsd.charge.ChargeType;
import ru.roskazna.xsd.pgu_importrequest.ImportRequest;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;

/**
 * @author ikka
 *         date: 01.06.2014.
 */
public class ReceivedImportChargesPanel extends IkkaJXPanel<UnifoTransferMsg> {
  private List<ReceivedImportChargePanel> importChargePanelList;


  {
    EventBusService.getInstance().registerSubscriber(this);
  }

  @Override
  protected void packComponents() {



    for (ReceivedImportChargePanel receivedImportChargePanel : importChargePanelList) {
      add(receivedImportChargePanel, "wrap");
    }


  }


  @Override
  protected void configComponents() {

  }

  @Override
  protected void initComponents() {


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
    setLayout(new MigLayout("gap 2, fillx"));
  }

  @Subscribe
  public void newImportChargeArrived(ImportChargeArrivedEvent event) {
    UnifoTransferMsg unifoTransferMsg = event.getUnifoTransferMsg();
    ReceivedImportChargePanel receivedImportChargePanel = new ReceivedImportChargePanel(unifoTransferMsg);
    importChargePanelList.add(receivedImportChargePanel);
    add(receivedImportChargePanel, "wrap");
  }

}
