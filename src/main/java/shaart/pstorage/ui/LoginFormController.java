package shaart.pstorage.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import shaart.pstorage.config.PStorageProperties;
import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.dto.CryptoResult;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.dto.ViewHolder;
import shaart.pstorage.service.EncryptionService;
import shaart.pstorage.service.SecurityAwareService;
import shaart.pstorage.service.UserService;
import shaart.pstorage.ui.util.AlertHelper;

/**
 * Controller for create user form.
 */
@Slf4j
public class LoginFormController {

  private static final String LESS_THAN_MAX_SYMBOLS = "%s should contain less than %d symbols";
  private static final String MORE_THAN_MIN_SYMBOLS = "%s should contain more than %d symbols";
  private static final String CANNOT_BE_EMPTY = "%s cannot be empty";
  private static final String PASS = "Password";
  private static final String USERNAME = "Username";

  private SystemTrayIcon systemTrayIcon = SystemTrayIcon.INSTANCE;

  @Autowired
  @Qualifier("mainView")
  private ViewHolder<MainFormController> mainViewHolder;

  @Autowired
  private UserService userService;

  @Autowired
  private EncryptionService encryptionService;

  @Autowired
  private SecurityAwareService securityAwareService;

  @Autowired
  private PStorageProperties pstorageProperties;

  // JavaFX Injections
  @FXML
  private TextField nameField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button loginButton;

  @FXML
  private Button registerButton;

  /**
   * <p>Initialization of JavaFX controller. Method is called after fields injection by FXML
   * loader.</p>
   * <p>This method must be called "initialize" otherwise it won't be called.</p>
   * <p>On this stage spring's beans are not initialized.</p>
   */
  @FXML
  public void initialize() {
    // Do nothing.
  }

  @FXML
  protected void login(ActionEvent event) {
    log.trace("Handling login action");
    Window owner = loginButton.getScene().getWindow();

    if (showErrorIfHasInvalidField(owner)) {
      return;
    }

    final CryptoDto passwordParam = CryptoDto.of(passwordField.getText());
    final CryptoResult encryptResult = encryptionService.encrypt(passwordParam);
    final String encrypted = encryptResult.getValue();

    boolean isCorrectCredentials = userService.isCorrectPasswordFor(nameField.getText(), encrypted);
    if (!isCorrectCredentials) {
      showValidationAlert(owner, Collections.singletonList("Incorrect username or password"));
      return;
    }

    logUserIn();

    showMainForm();
    closeLoginForm(event);
  }


  @FXML
  protected void register(ActionEvent event) {
    log.trace("Handling register action");
    Window owner = registerButton.getScene().getWindow();

    if (showErrorIfHasInvalidField(owner)) {
      return;
    }

    final String username = nameField.getText();
    if (userService.exists(username)) {
      AlertHelper.showAlert(AlertType.ERROR, "Registration error",
          "User with that username already exists!");
      return;
    }

    final CryptoDto passwordParam = CryptoDto.of(passwordField.getText());
    final CryptoResult encryptResult = encryptionService.encrypt(passwordParam);

    UserDto user = UserDto.builder()
        .name(username)
        .masterPassword(encryptResult.getValue())
        .encryptionType(encryptResult.getEncryptionType())
        .build();
    UserDto saved = userService.save(user);

    log.trace("User '{}' saved successfully", saved.getName());

    AlertHelper.showAlert(AlertType.INFORMATION, "Registration",
        String.format("Registration for user with name '%s' is successful!", username));
  }

  private void closeLoginForm(Event event) {
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.close();
  }

  private void logUserIn() {
    String username = nameField.getText();
    String password = passwordField.getText();

    securityAwareService.authorize(username, password);
  }

  private boolean showErrorIfHasInvalidField(Window owner) {
    List<String> errors = validateFields();
    if (!errors.isEmpty()) {
      showValidationAlert(owner, errors);
      log.trace("Validation failed");
      return true;
    }
    log.trace("Validation success");
    return false;
  }

  private void showMainForm() {
    Stage stage = new Stage();

    String title = pstorageProperties.getUi().getTitle();
    stage.setTitle(title);

    Parent mainFormView = mainViewHolder.getView();
    stage.setScene(new Scene(mainFormView));
    mainViewHolder.getController().fillWithData();

    stage.setResizable(true);
    stage.centerOnScreen();
    systemTrayIcon.setCurrentMainStage(stage);
    stage.show();
  }

  private void showValidationAlert(Window owner, List<String> errors) {
    String errorMessage = String.join("; ", errors);

    AlertHelper.showAlert(AlertType.ERROR, owner, "Validation form error", errorMessage);
  }

  private List<String> validateFields() {
    List<String> errors = new ArrayList<>();

    String username = nameField.getText();
    if (username.isEmpty()) {
      errors.add(String.format(CANNOT_BE_EMPTY, USERNAME));
    } else {
      Integer maxLength = pstorageProperties.getValidation().getUsername().getLength().getMax();
      if (username.length() > maxLength) {
        errors.add(String.format(LESS_THAN_MAX_SYMBOLS, USERNAME, maxLength));
      }
    }

    String password = passwordField.getText();
    if (password.isEmpty()) {
      errors.add(String.format(CANNOT_BE_EMPTY, PASS));
    } else {
      Integer minLength = pstorageProperties.getValidation().getPassword().getLength().getMin();
      if (password.length() < minLength) {
        errors.add(String.format(MORE_THAN_MIN_SYMBOLS, PASS, minLength));
      } else {
        Integer maxLength = pstorageProperties.getValidation().getPassword().getLength().getMax();
        if (password.length() > maxLength) {
          errors.add(String.format(LESS_THAN_MAX_SYMBOLS, PASS, maxLength));
        }
      }
    }

    return errors;
  }
}