package shaart.pstorage.ui.util;

import java.util.Optional;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

public class AlertHelper {

  /**
   * The amount of horizontal space between each child in the {@link HBox}.
   */
  public static final int CUSTOM_DIALOG_SPACING = 10;

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
    Platform.runLater(() -> {
      Alert alert = new Alert(alertType);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.initOwner(owner);
      alert.show();
    });
  }

  public static void showPasswordInputDialog(String title, String message,
      Consumer<Optional<String>> callback) {
    showPasswordInputDialog(null, title, message, callback);
  }

  /**
   * Shows dialog with input field (with secret mask) and processes received from user value.
   *
   * @param owner parent window
   * @param title title of the new window
   * @param message message for body in the new window
   * @param callback logic to do with received value or {@link Optional#empty()} if canceled
   */
  public static void showPasswordInputDialog(Window owner, String title,
      String message, Consumer<Optional<String>> callback) {
    Platform.runLater(() -> {
      Dialog<String> dialog = new Dialog<>();
      dialog.setTitle(title);
      dialog.setHeaderText(null);
      dialog.initOwner(owner);

      final ObservableList<ButtonType> buttonTypes = dialog.getDialogPane().getButtonTypes();
      buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL);

      HBox content = new HBox();
      content.setAlignment(Pos.CENTER_LEFT);
      content.setSpacing(CUSTOM_DIALOG_SPACING);

      PasswordField passwordField = new PasswordField();
      content.getChildren().addAll(new Label(message), passwordField);

      dialog.getDialogPane().setContent(content);
      dialog.setResultConverter(dialogButton -> {
        if (dialogButton == ButtonType.OK) {
          return passwordField.getText();
        }
        return null;
      });

      final Optional<String> result = dialog.showAndWait();
      callback.accept(result);
    });
  }
}
