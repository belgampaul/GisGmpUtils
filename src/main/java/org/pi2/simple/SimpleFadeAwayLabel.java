package org.pi2.simple;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * User: ikka
 * Date: 4/10/14
 * Time: 2:57 AM
 */
public class SimpleFadeAwayLabel extends JFrame implements Runnable {

  private final JPanel pnlMain;
  private JLabel jLabel = new JLabel();
  public SimpleFadeAwayLabel() throws HeadlessException {
    super();
    pnlMain = new JPanel(new MigLayout("fill"));

  }

  public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new SimpleFadeAwayLabel().run();
      }
    });
  }

  @Override
  public void run() {
    setContentPane(pnlMain);
    //setJMenuBar(new MainMenuExample());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
