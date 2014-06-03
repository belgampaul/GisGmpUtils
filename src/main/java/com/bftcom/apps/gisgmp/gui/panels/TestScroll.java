package com.bftcom.apps.gisgmp.gui.panels;

import java.awt.*;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXPanel;

public class TestScroll extends JFrame {
  JXPanel goalPanel;
  JXPanel viewPanel;
  JScrollPane jsp;

  public TestScroll() {
    goalPanel = new JXPanel();
    MigLayout ml1 = new MigLayout("insets 0", "[pref!]", "[pref!]");
    goalPanel.setLayout(ml1);
    goalPanel.setScrollableTracksViewportHeight(false);  //JXPanel specific
    goalPanel.setScrollableTracksViewportWidth(false);   //JXPanel specific
    goalPanel.setBorder(new javax.swing.border.LineBorder(Color.BLACK, 5));
    goalPanel.setPreferredSize(new Dimension(600, 600));

    for (int i = 0; i < 200; i++) {
      JLabel aaaaaaaaa1 = new JLabel("aaaaaaaaa");
      goalPanel.add(aaaaaaaaa1, "wrap");
    }

    jsp = new JScrollPane(goalPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    viewPanel = new JXPanel();
    MigLayout ml2 = new MigLayout();
    viewPanel.setLayout(ml2);
    viewPanel.add(jsp);

    this.setSize(new Dimension(400, 400));  //JFrame
    this.getContentPane().add(viewPanel, BorderLayout.CENTER);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);

    this.setVisible(true);
  }

  public static void main(String[] args) {
    new TestScroll();
  }

}
