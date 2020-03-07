package shaart.pstorage.handler;

import static java.util.Objects.isNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import shaart.pstorage.exception.AliasAlreadyExistsException;
import shaart.pstorage.exception.PasswordNotFoundException;
import shaart.pstorage.exception.UserNotFoundException;
import shaart.pstorage.ui.util.AlertHelper;
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
  private List<Class<? extends Exception>> expectedExceptions = Arrays.asList(
      UserNotFoundException.class,
      PasswordNotFoundException.class,
      AliasAlreadyExistsException.class);

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

    Throwable causeException = getCauseIfExpectedAndPossible(exception, RuntimeException.class);
    causeException = getCauseIfExpectedAndPossible(causeException, InvocationTargetException.class);
    if (expectedExceptions.contains(causeException.getClass())) {
      final String message = causeException.getMessage();
      final String contentMessage = isNull(message)
          ? causeException.getClass().getSimpleName()
          : message;
      AlertHelper.showAlert(AlertType.ERROR, "Error", contentMessage);
    } else {
      showGlobalErrorWindow(exception);
    }
  }

  private Throwable getCauseIfExpectedAndPossible(Throwable exception,
      Class<? extends Exception> expected) {
    if (exception.getClass() == expected && exception.getCause() != null) {
      exception = exception.getCause();
    }
    return exception;
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
