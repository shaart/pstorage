package shaart.pstorage.config;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shaart.pstorage.dto.ViewHolder;
import shaart.pstorage.loader.ViewLoader;
import shaart.pstorage.loader.impl.ViewLoaderImpl;
import shaart.pstorage.ui.LoginFormController;
import shaart.pstorage.ui.MainFormController;

@Configuration
public class ViewControllersConfiguration {

  @Bean
  public ViewLoader viewLoader() {
    return new ViewLoaderImpl();
  }

  @Bean
  public ViewHolder<MainFormController> mainView() throws IOException {
    return viewLoader().loadView("view/fxml/mainForm.fxml");
  }

  @Bean
  public ViewHolder<LoginFormController> loginView() throws IOException {
    return viewLoader().loadView("view/fxml/loginForm.fxml");
  }

  /**
   * JavaFX MainForm controller bean.
   */
  @Bean
  public MainFormController mainFormController() throws IOException {
    return mainView().getController();
  }

  @Bean
  public LoginFormController createUserFormController() throws IOException {
    return loginView().getController();
  }
}
