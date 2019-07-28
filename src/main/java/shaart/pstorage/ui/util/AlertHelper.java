package shaart.pstorage.ui.util;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class AlertHelper {

  private AlertHelper() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static void showAlert(Alert.AlertType alertType, String title, String message) {
    showAlert(alertType, null, title, message);
  }

  /**
   * Show new alert window in center of 'owner' with title and message.
   *
   * @param alertType icon in the window
   * @param owner parent window
   * @param title title of the new window
   * @param message message for body in the new window
   */
  public static void showAlert(Alert.AlertType alertType, Window owner, String title,
      String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.initOwner(owner);
    alert.show();
  }
}
