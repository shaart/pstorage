package shaart.pstorage;

import java.util.Objects;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import shaart.pstorage.config.PStorageProperties;
import shaart.pstorage.dto.ViewHolder;
import shaart.pstorage.handler.GlobalExceptionHandler;
import shaart.pstorage.util.ExceptionUtil;

/**
 * Application entry point.
 */
@Lazy
@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(PStorageProperties.class)
public class PStorageApplication extends AbstractJavaFxApplicationSupport {

  @Value("${pstorage.ui.title:PStorage}")
  private String windowTitle;

  private ViewHolder initialViewHolder;

  private ExceptionUtil exceptionUtil = ExceptionUtil.getInstance();

  public static void main(String[] args) {
    log.info("Launching app...");
    launchApp(args);
  }

  @Autowired
  @Qualifier("loginView")
  public void setInitialViewHolder(ViewHolder initialViewHolder) {
    this.initialViewHolder = initialViewHolder;
  }

  @Override
  public void start(Stage stage) {
    final Exception contextLoadingException = getContextLoadingException();

    if (Objects.nonNull(contextLoadingException)) {
      showErrorWindow(stage, contextLoadingException);
      return;
    }

    GlobalExceptionHandler globalExceptionHandler = GlobalExceptionHandler.getInstance();
    Thread.setDefaultUncaughtExceptionHandler(globalExceptionHandler::handle);

    showInitialView(stage);
  }

  private void showErrorWindow(Stage stage, Throwable contextLoadingException) {
    stage.setTitle("PStorage: Initialization error");

    final String stacktrace = exceptionUtil.getStacktrace(contextLoadingException);

    TextArea textArea = new TextArea();
    textArea.appendText("An error occurred on application initialization:\n");
    textArea.appendText(stacktrace);

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(textArea);

    Scene scene = new Scene(borderPane, 800, 600);
    stage.setScene(scene);
    stage.setResizable(true);
    stage.centerOnScreen();
    stage.show();
  }

  private void showInitialView(Stage stage) {
    stage.setTitle(windowTitle);
    stage.setScene(new Scene(initialViewHolder.getView()));
    stage.setResizable(true);
    stage.centerOnScreen();
    stage.show();
  }
}
