import com.bftcom.apps.property.resource.bundle.LangResourceBundle;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ikka
 * @date: 04.06.2014.
 */
public class TestPanelScroll extends JPanel implements Runnable{
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(TestPanelScroll.class);


  public TestPanelScroll() {
    super();
    setLayout(new MigLayout("fill"));

    for (int i = 0; i < 40; i++) {
      JPanel jPanel = getjPanel(i);

      add(jPanel, "wrap, grow");
    }

  }

  private JPanel getjPanel(int i) {
    JPanel jPanel = new JPanel();
    jPanel.add(new JLabel(String.valueOf(i)));
    jPanel.setBackground(Color.yellow);
    jPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
    jPanel.setSize(new Dimension(50, 100));
    return jPanel;
  }

  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new TestPanelScroll().run();
      }
    });
  }

  @Override
  public void run() {
    JFrame jFrame = new JFrame();
    JPanel jPanel = new JPanel(new MigLayout("fill"));
    JScrollPane jScrollPane = new JScrollPane(this);
    jPanel.add(jScrollPane, "grow");
    jScrollPane.setViewportView(this);
    jFrame.setContentPane(jPanel);
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    jFrame. setSize(600, 600);
    jFrame.setTitle(LangResourceBundle.getString("interface.window.Title"));
    jFrame.pack();
    jFrame.setLocationRelativeTo(null);

    jFrame.setVisible(true);
    jFrame.setExtendedState(jFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    executorService.submit(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 30; i++) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          TestPanelScroll.this.add(getjPanel(i), "wrap, grow");
          JScrollBar vertical = jScrollPane.getVerticalScrollBar();
          vertical.setValue( vertical.getMaximum() );
        }
      }
    });

    log.info("window app launched");
  }
}
