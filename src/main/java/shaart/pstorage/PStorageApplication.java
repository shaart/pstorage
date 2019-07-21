package shaart.pstorage;

import java.util.Objects;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import shaart.pstorage.dto.ViewHolder;
import shaart.pstorage.util.ExceptionUtil;

/**
 * Application entry point.
 */
@Lazy
@SpringBootApplication
public class PStorageApplication extends AbstractJavaFxApplicationSupport {

  @Value("${ui.title:JavaFX приложение}")
  private String windowTitle;

  private ViewHolder viewHolder;

  public static void main(String[] args) {
    launchApp(args);
  }

  @Override
  public void start(Stage stage) {
    final Exception contextLoadingException = getContextLoadingException();

    if (Objects.nonNull(contextLoadingException)) {
      showErrorWindow(stage, contextLoadingException);
      return;
    }

    stage.setTitle(windowTitle);
    stage.setScene(new Scene(viewHolder.getView()));
    stage.setResizable(true);
    stage.centerOnScreen();
    stage.show();
  }

  @Autowired
  @Qualifier("mainView")
  public void setViewHolder(ViewHolder viewHolder) {
    this.viewHolder = viewHolder;
  }

  private void showErrorWindow(Stage stage, Exception contextLoadingException) {
    stage.setTitle("PStorage: Initialization error");

    BorderPane borderPane = new BorderPane();

    Scene scene = new Scene(borderPane, 800, 600);
    TextArea textArea = new TextArea();

    final String stacktrace = ExceptionUtil.getInstance().getStacktrace(contextLoadingException);
    textArea.appendText("An error occurred on application initialization:\n");
    textArea.appendText(stacktrace);
    borderPane.setCenter(textArea);

    stage.setScene(scene);
    stage.setResizable(true);
    stage.centerOnScreen();
    stage.show();
  }
}
