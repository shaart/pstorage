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
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import shaart.pstorage.ui.SystemTrayIcon;

/**
 * Abstract class for running Spring Boot App via JavaFX.
 */
@Slf4j
public abstract class AbstractJavaFxApplicationSupport extends Application {

  private static String[] savedArgs;

  @Getter
  private Exception contextLoadingException;

  private SystemTrayIcon systemTrayIcon = SystemTrayIcon.INSTANCE;
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
    Platform.setImplicitExit(false);

    log.trace("Starting spring boot");
    try {
      context = SpringApplication.run(getClass(), savedArgs);
      context.getAutowireCapableBeanFactory().autowireBean(this);

      systemTrayIcon.initialize();
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
      contextLoadingException = e;
    } finally {
      log.trace("Destroying splash screen");
      Platform.runLater(this::closeSplash);
    }
  }

  @Override
  public void stop() throws Exception {
    systemTrayIcon.remove();
    log.trace("Stopping the application");
    super.stop();
    if (context != null) {
      context.close();
    }
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