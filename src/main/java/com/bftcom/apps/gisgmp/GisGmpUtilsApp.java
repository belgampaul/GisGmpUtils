package com.bftcom.apps.gisgmp;

import be.belgampaul.core.eventbus.DeadEventListener;
import be.belgampaul.core.eventbus.EventBusService;
import com.bftcom.apps.gisgmp.config.Config;
import com.bftcom.apps.gisgmp.config.Constants;
import com.bftcom.apps.gisgmp.config.DefaultConfig;
import com.bftcom.apps.gisgmp.controllers.GisGmpEmulatorController;
import com.bftcom.apps.gisgmp.gui.AppTray;
import com.bftcom.apps.property.resource.bundle.LangResourceBundle;
import org.pi2.core.gui.Utils;
import net.miginfocom.swing.MigLayout;
import oreilly.hcj.reflection.DynamicTypeChecking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;


/**
 * User: ikka
 * Date: 26.09.13
 * Time: 15:34
 */
public class GisGmpUtilsApp extends JFrame implements Runnable {
  //slf4j logger
  private static Logger log = LoggerFactory.getLogger(GisGmpUtilsApp.class);

  private JTabbedPane tabbedPane;
  private JScrollPane spLogPane;
  private JPanel pnlMain;

  public GisGmpUtilsApp() throws HeadlessException {
    super();
    initComponents();
  }

  private void initComponents() {
    pnlMain = new JPanel(new MigLayout("fill"));
    tabbedPane = new JTabbedPane();


    //JTextArea textArea = new JTextArea();
    //spLogPane = new JScrollPane(textArea);
    //MessageConsole mc = new MessageConsole(textArea);
    //mc.redirectOut();
    //mc.redirectErr(Color.RED, null);
    //mc.setMessageLines(100);
    //mc.redirectOut(null, System.out);


    pnlMain.add(tabbedPane, "grow");
    //pnlMain.add(spLogPane, "grow");
  }


  @Override
  public void run() {

    EventBusService.getInstance().registerSubscriber(new DeadEventListener());
    EventBusService.getInstance().registerSubscriber(this);

    setContentPane(pnlMain);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    com.bftcom.apps.gisgmp.gui.MenuBar menubar = new com.bftcom.apps.gisgmp.gui.MenuBar();
    setJMenuBar(menubar);
    menubar.addPropertyChangeListener("menu", handleMenuBarClick());

    setSize(600, 600);
    setTitle(LangResourceBundle.getString("interface.window.Title"));
    pack();
    setLocationRelativeTo(null);


    //splash.close();
    setVisible(true);
    setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

    AppTray appTray = new AppTray();

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowClosingListener(appTray));

    startSchedulers();
    EventBusService.getInstance().registerSubscriber(appTray);
  }

  protected void startSchedulers() {
    //add scheduled tasks
  }


  private PropertyChangeListener handleMenuBarClick() {
    return new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        try {
          String className = (String) evt.getNewValue();
          Class<?> classType = Class.forName(className);

          if (DynamicTypeChecking.isJPanel(classType)) {
            Constructor constructor = classType.getConstructor();
            JPanel jpanel = (JPanel) constructor.newInstance();
            Utils.addClosableTab(tabbedPane, jpanel, jpanel.getName(), null);
          } else {
            try {
              Constructor constructor = classType.getConstructor();
              Object aJpanel = constructor.newInstance();
              Method getPanelMethod = classType.getDeclaredMethod("getPanel");

              JPanel panel = (JPanel) getPanelMethod.invoke(aJpanel);

              Utils.addClosableTab(tabbedPane, panel, panel.getName(), null);
            } catch (NoSuchMethodException ignored) {
              log.warn(ignored.getMessage());
            }
          }
          pnlMain.updateUI();

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
          log.warn("Произошла ошибка при обработке создании панели приложения.", e);
        }
      }
    };
  }

  public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

    setLocale();
    DefaultConfig.init();
    init();

    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new GisGmpUtilsApp().run();
      }
    });
  }

  @SuppressWarnings({"CallToStringEquals", "CallToNumericToString"})
  private static void init() {
    String read = Config.read(Constants.APPKEY_AUTO_START_WEB_SERVICE);
    if (Boolean.TRUE.toString().equals(read)){
      GisGmpEmulatorController.start(Config.read(Constants.APPKEY_PORT));
    }
  }

  private static void setLocale() {
    log.info("инициализирую настройки языка приложения...");
    String s = System.getProperty("java.util.Locale");
    if (s != null) {
      String[] parts = s.split("_");
      Locale locale = null;
      if (parts.length == 1)
        locale = new Locale(parts[0]);
      else if (parts.length == 2)
        locale = new Locale(parts[0], parts[1]);
      else if (parts.length == 3)
        locale = new Locale(parts[0], parts[1], parts[2]);

      if (locale == null)
        log.warn("ignored: " + s);
      else
        Locale.setDefault(locale);
    }
    log.info("Язык и страна системы (Locale): " + Locale.getDefault().toString());
  }

  public class WindowClosingListener extends WindowAdapter {
    private AppTray tray;

    public WindowClosingListener(AppTray tray) {
      this.tray = tray;
    }

    public void windowClosing(WindowEvent e) {
      tray.remove();
      System.exit(0);
    }
  }
}


