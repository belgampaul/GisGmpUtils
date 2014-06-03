package com.bftcom.apps.gisgmp.gui.panels;

import com.bftcom.apps.gisgmp.config.Config;
import com.bftcom.apps.gisgmp.config.Constants;
import com.bftcom.apps.property.resource.bundle.LangResourceBundle;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXButton;
import org.pi2.core.gui.IkkaJXPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ikka
 * @date: 30.05.2014.
 */
public class AppSettingsPanel extends IkkaJXPanel {
  private JPanel exportResponsesContainer;
  private JPanel importResponsesContainer;
  private JXButton jxButton;
  private JXButton jxButton2;
  private JCheckBox cbSendSuccessResponseOnChargeImport;
  private JCheckBox cbAutoStartGisGmpWebService;

  @Override
  protected void packComponents() {

    exportResponsesContainer.add(cbSendSuccessResponseOnChargeImport, "wrap");
    exportResponsesContainer.add(cbAutoStartGisGmpWebService, "wrap");
    add(exportResponsesContainer, "width 50%");

  }

  @SuppressWarnings({"CallToStringEquals", "CallToNumericToString"})
  @Override
  protected void configComponents() {
    cbSendSuccessResponseOnChargeImport.setSelected(Config.read(Constants.APPKEY_C_IMP_CH_POSITIVE).equals(Boolean.TRUE.toString()));
    cbSendSuccessResponseOnChargeImport.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean selected = cbSendSuccessResponseOnChargeImport.isSelected();
        Config.write(Constants.APPKEY_C_IMP_CH_POSITIVE, selected + "");
      }
    });

    cbAutoStartGisGmpWebService.setSelected(Config.read(Constants.APPKEY_AUTO_START_WEB_SERVICE).equals(Boolean.TRUE.toString()));
    cbAutoStartGisGmpWebService.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean selected = cbAutoStartGisGmpWebService.isSelected();
        Config.write(Constants.APPKEY_AUTO_START_WEB_SERVICE, selected + "");
      }
    });
  }

  @SuppressWarnings("DuplicateStringLiteralInspection")
  @Override
  protected void initComponents() {
    exportResponsesContainer = new ExportResponseContainer();
    importResponsesContainer = new ImportResponseContainer();

    jxButton = new JXButton("Button");
    jxButton2 = new JXButton("Button2");

    cbSendSuccessResponseOnChargeImport = new JCheckBox(LangResourceBundle.getString("interface.gisgmp.responses.config.Send"));
    cbAutoStartGisGmpWebService = new JCheckBox(LangResourceBundle.getString("interface.settings.auto.start.gisgmp"));
  }

  @Override
  protected void initModel() {

  }

  @Override
  protected void setLayout2() {
    setLayout(new MigLayout("gap 2, fillx"));
  }
}
