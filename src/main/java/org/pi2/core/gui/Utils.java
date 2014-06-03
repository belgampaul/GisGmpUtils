package org.pi2.core.gui;


import com.jgoodies.looks.common.RGBGrayFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Utils extends javax.swing.JFrame {

  private static final Icon CLOSE_TAB_ICON = new ImageIcon(Utils.class.getResource("/images/x.png"));
  private static final Icon PAGE_ICON = new ImageIcon(Utils.class.getResource("/images/closeTabButton.png"));
  private int tabCount = 0;

  /**
   * Adds a component to a JTabbedPane with a little "close tab" button on the
   * right side of the tab.
   *
   * @param tabbedPane the JTabbedPane
   * @param c any JComponent
   * @param title the title for the tab
   * @param icon the icon for the tab, if desired
   */
  public static void addClosableTab(final JTabbedPane tabbedPane, final JComponent c, final String title,
                                    final Icon icon) {
    // Add the tab to the pane without any label
    tabbedPane.addTab(null, c);
    int pos = tabbedPane.indexOfComponent(c);

    // Create a FlowLayout that will space things 5px apart
    FlowLayout f = new FlowLayout(FlowLayout.CENTER, 5, 0);

    // Make a small JPanel with the layout and make it non-opaque
    JPanel pnlTab = new JPanel(f);
    pnlTab.setOpaque(false);

    // Add a JLabel with title and the left-side tab icon
    JLabel lblTitle = new JLabel(title);
    lblTitle.setIcon(PAGE_ICON);

    // Create a JButton for the close tab button
    JButton btnClose = new JButton();
    btnClose.setOpaque(false);
    btnClose.setContentAreaFilled(false);
    btnClose.setBorderPainted(false);

    // Configure icon and rollover icon for button
    btnClose.setRolloverIcon(CLOSE_TAB_ICON);
    btnClose.setRolloverEnabled(true);
    btnClose.setIcon(RGBGrayFilter.getDisabledIcon(btnClose, CLOSE_TAB_ICON));

    // Set border null so the button doesn't make the tab too big
    btnClose.setBorder(null);

    // Make sure the button can't get focus, otherwise it looks funny
    btnClose.setFocusable(false);

    // Put the panel together
    pnlTab.add(lblTitle);
    pnlTab.add(btnClose);

    // Add a thin border to keep the image below the top edge of the tab
    // when the tab is selected
    pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

    // Now assign the component for the tab
    tabbedPane.setTabComponentAt(pos, pnlTab);

    // Add the listener that removes the tab
    ActionListener listener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // The component parameter must be declared "final" so that it can be
        // referenced in the anonymous listener class like this.
        tabbedPane.remove(c);
      }
    };
    btnClose.addActionListener(listener);

    // Optionally bring the new tab to the front
    tabbedPane.setSelectedComponent(c);

    //-------------------------------------------------------------
    // Bonus: Adding a <Ctrl-W> keystroke binding to close the tab
    //-------------------------------------------------------------
    AbstractAction closeTabAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        tabbedPane.remove(c);
      }
    };

    // Create a keystroke
    KeyStroke controlW = KeyStroke.getKeyStroke("control W");

    // Get the appropriate input map using the JComponent constants.
    // This one works well when the component is a container. 
    InputMap inputMap = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    // Add the key binding for the keystroke to the action name
    inputMap.put(controlW, "closeTab");

    // Now add a single binding for the action name to the anonymous action
    c.getActionMap().put("closeTab", closeTabAction);
  }
}
