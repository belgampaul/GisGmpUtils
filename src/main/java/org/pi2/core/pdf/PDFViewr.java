package org.pi2.core.pdf;

import org.pi2.core.gui.IkkaJXPanel;
import net.miginfocom.swing.MigLayout;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.jdesktop.swingx.JXButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: ikka
 * Date: 06.05.2014
 * Time: 01:40
 */
public class PDFViewr extends IkkaJXPanel {
  //logger
  private static final Logger log = LoggerFactory.getLogger(PDFViewr.class);

  // build a controller
  SwingController controller;
  private SwingViewBuilder factory;
  private JPanel viewr;
  private JXButton btnOpenFile;

  public PDFViewr() {
    super();
  }

  @Override
  protected void packComponents() {
    add(btnOpenFile, "gapy 0, wrap");
    add(viewr, "grow, push");
    controller.openDocument("D:\\ent\\ebooks\\1617291307_Gradle.pdf");

  }

  @Override
  protected void setLayout2() {
    setLayout(new MigLayout("gap 0, fill"));
  }

  @Override
  protected void configComponents() {
    btnOpenFile.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf files", "pdf");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(PDFViewr.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          String filePath = chooser.getSelectedFile().getAbsolutePath();
          log.trace("You chose to open this file: " + filePath);
          controller.closeDocument();
          controller.openDocument(filePath);

        }
      }
    });

  }

  @Override
  protected void initComponents() {
    controller = new SwingController();
    factory = new SwingViewBuilder(controller);
    viewr = factory.buildViewerPanel();
    btnOpenFile = new JXButton("Open pdf file");
  }

  @Override
  protected void initModel() {

  }
}
