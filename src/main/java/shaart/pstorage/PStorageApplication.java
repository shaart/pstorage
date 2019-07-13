package shaart.pstorage;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import shaart.pstorage.dto.ViewHolder;

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
}
