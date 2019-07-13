package shaart.pstorage;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Abstract class for running Spring Boot App via JavaFX.
 */
@Slf4j
public abstract class AbstractJavaFxApplicationSupport extends Application {

  private static String[] savedArgs;

  private ConfigurableApplicationContext context;
  private Stage splashScreen;

  /**
   * Starting Spring Boot Application {@link PStorageApplication}.
   *
   * @param args application arguments
   */
  static void launchApp(String[] args) {
    log.trace("Launching the application");
    AbstractJavaFxApplicationSupport.savedArgs = args;
    Application.launch(PStorageApplication.class, args);
  }

  @Override
  public void init() {
    log.trace("Showing splash screen");
    Platform.runLater(this::showSplash);

    log.trace("Starting spring boot");
    try {
      context = SpringApplication.run(getClass(), savedArgs);
      context.getAutowireCapableBeanFactory().autowireBean(this);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
      Platform.runLater(this::errorWindow);
    }

    log.trace("Destroying splash screen");
    Platform.runLater(this::closeSplash);
  }

  private void errorWindow() {
    log.warn("Error window is not implemented");
  }

  @Override
  public void stop() throws Exception {
    log.trace("Stopping the application");
    super.stop();
    context.close();
  }

  private void showSplash() {
    try {
      splashScreen = new Stage(StageStyle.TRANSPARENT);
      splashScreen.setTitle("Splash");
      Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/splash.fxml"));
      Scene scene = new Scene(root, Color.TRANSPARENT);
      splashScreen.setScene(scene);
      splashScreen.show();
    } catch (IOException e) {
      log.error(e.getLocalizedMessage(), e);
    }
  }

  private void closeSplash() {
    splashScreen.close();
  }
}