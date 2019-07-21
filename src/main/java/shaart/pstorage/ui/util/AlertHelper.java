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
