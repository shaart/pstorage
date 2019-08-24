package shaart.pstorage.handler;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import shaart.pstorage.util.ExceptionUtil;

/**
 * Global exception handler that shows new window with error's stacktrace.
 */
@Slf4j
public enum GlobalExceptionHandler {
  INSTANCE;

  public static GlobalExceptionHandler getInstance() {
    return INSTANCE;
  }

  private ExceptionUtil exceptionUtil = ExceptionUtil.getInstance();

  /**
   * <p>Handles an exception that was not handled by any try-catch construction.</p>
   * <p>Writes a message into log and then shows new window with error to user.</p>
   *
   * @param thread thread where an exception occurred
   * @param exception an exception that was unhandled before
   */
  public void handle(Thread thread, Throwable exception) {
    log.error(String.format("Uncaught exception at thread '%s': %s",
        thread.getName(),
        exception.getMessage()), exception);

    showGlobalErrorWindow(exception);
  }

  private void showGlobalErrorWindow(Throwable exception) {
    Stage errorStage = new Stage();
    errorStage.setTitle("PStorage: unknown error");

    BorderPane borderPane = new BorderPane();

    Scene errorScene = new Scene(borderPane, 800, 600);
    errorStage.setScene(errorScene);

    final String stacktrace = exceptionUtil.getStacktrace(exception);
    TextArea textArea = new TextArea();
    textArea.appendText(stacktrace);
    borderPane.setCenter(textArea);

    errorStage.setResizable(true);
    errorStage.centerOnScreen();
    errorStage.show();
  }
}
