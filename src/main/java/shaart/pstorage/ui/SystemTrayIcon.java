package shaart.pstorage.ui;

import static java.util.Objects.isNull;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import shaart.pstorage.exception.SystemTrayException;
import shaart.pstorage.ui.tray.PasswordsMenu;
import shaart.pstorage.ui.util.AlertHelper;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum SystemTrayIcon {
  INSTANCE;

  private TrayIcon trayIcon;
  private SystemTray systemTray;

  @Setter
  private Stage currentMainStage;

  /**
   * Initializes an application's icon on the System Tray.
   */
  public void initialize() {
    try {
      initTrayMenu();
    } catch (Exception e) {
      log.error("Can't initialize system tray.", e);
      AlertHelper.showAlert(AlertType.WARNING, "System Tray",
          "Can't initialize system tray. It will be unavailable.");
    }
  }

  private void initTrayMenu() throws IOException {
    log.info("Setting java.awt.headless to false for SystemTray supporting");
    System.setProperty("java.awt.headless", "false");

    if (!SystemTray.isSupported()) {
      log.warn("SystemTray is not supported");
      return;
    }
    systemTray = SystemTray.getSystemTray();

    final URL iconUrl = getClass().getResource("/view/image/icon16.png");
    log.info("Loading application icon from URL {}", iconUrl);
    Image icon = ImageIO.read(iconUrl);
    trayIcon = new TrayIcon(icon);

    final PopupMenu popup = createPopupMenu();
    trayIcon.setPopupMenu(popup);
    trayIcon.addActionListener(e -> Platform.runLater(() -> currentMainStage.show()));

    try {
      log.info("Adding icon to system tray");
      systemTray.add(trayIcon);
    } catch (AWTException e) {
      throw new SystemTrayException("TrayIcon could not be added.");
    }
  }

  private PopupMenu createPopupMenu() {
    final Menu passwordsMenu = PasswordsMenu.getInstance();
    final MenuItem options = new MenuItem("Options");
    options.setEnabled(false);

    MenuItem aboutItem = new MenuItem("About");
    aboutItem.setEnabled(false);

    MenuItem exitItem = new MenuItem("Exit");
    exitItem.addActionListener(CommandFactory.INSTANCE.createExitCommand());

    final PopupMenu popup = new PopupMenu();
    popup.add(passwordsMenu);
    popup.addSeparator();
    popup.add(options);
    popup.addSeparator();
    popup.add(aboutItem);
    popup.add(exitItem);
    return popup;
  }

  /**
   * Removes an application's icon from the system tray if exists.
   */
  public void remove() {
    if (isNull(systemTray) || isNull(trayIcon)) {
      log.info("System Tray or Tray Icon is not initialized, nothing to remove.");
      return;
    }

    log.info("Removing tray icon");
    systemTray.remove(trayIcon);
  }
}
