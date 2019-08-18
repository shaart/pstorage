package shaart.pstorage.ui;

import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.dto.CryptoResult;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.service.EncryptionService;
import shaart.pstorage.service.PasswordService;
import shaart.pstorage.service.SecurityAwareService;
import shaart.pstorage.ui.component.PasswordCellValueFactory;
import shaart.pstorage.ui.util.AlertHelper;

/**
 * Controller for main form.
 */
@Slf4j
public class MainFormController {

  private static final String EMPTY = "";

  @Autowired
  private PasswordService passwordService;

  @Autowired
  private EncryptionService encryptionService;

  @Autowired
  private SecurityAwareService securityAwareService;

  // JavaFX Injections
  @FXML
  private TableView<PasswordDto> table;
  @FXML
  private TextField txtAlias;
  @FXML
  private TextField txtEncryptedValue;

  private ObservableList<PasswordDto> data;

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

  /**
   * Initialize table on the form.
   */
  @PostConstruct
  public void init() {
    TableColumn<PasswordDto, String> idColumn = new TableColumn<>("ID");
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<PasswordDto, String> aliasColumn = new TableColumn<>("Alias");
    aliasColumn.setCellValueFactory(new PropertyValueFactory<>("alias"));

    TableColumn<PasswordDto, String> passwordColumn = new TableColumn<>("Password");
    passwordColumn.setCellValueFactory(new PasswordCellValueFactory<>("encryptedValue"));

    table.getColumns().add(0, idColumn);
    table.getColumns().add(1, aliasColumn);
    table.getColumns().add(2, passwordColumn);
  }

  void fillWithData() {
    List<PasswordDto> passwords = passwordService.findAll();
    data = FXCollections.observableArrayList(passwords);
    table.setItems(data);
  }

  /**
   * Add password to storage.
   */
  @FXML
  public void addPassword() {
    log.trace("Handling save password action");
    Optional<UserDto> userDto = securityAwareService.currentUser();

    if (!userDto.isPresent()) {
      log.trace("User not found in security context - unauthorized");
      AlertHelper.showAlert(AlertType.ERROR, "Error", "Unauthorized");
      return;
    }

    final CryptoDto encryptionDto = CryptoDto.of(txtEncryptedValue.getText());
    final String userMasterPassword = userDto.get().getMasterPassword();
    final CryptoResult encrypted = encryptionService.encrypt(encryptionDto, userMasterPassword);

    PasswordDto password = PasswordDto.builder()
        .alias(txtAlias.getText())
        .encryptedValue(encrypted.getValue())
        .encryptionType(encrypted.getEncryptionType())
        .user(userDto.get())
        .build();

    log.debug("Saving password with alias {} for user {}",
        password.getAlias(),
        password.getUser().getName());
    final PasswordDto savedPassword = passwordService.save(password);

    data.add(savedPassword);

    clearFields();
  }

  private void clearFields() {
    txtAlias.setText(EMPTY);
    txtEncryptedValue.setText(EMPTY);
  }
}