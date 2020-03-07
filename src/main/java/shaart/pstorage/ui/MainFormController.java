package shaart.pstorage.ui;

import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import shaart.pstorage.component.UserDataContext;
import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.dto.CryptoResult;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.exception.AliasAlreadyExistsException;
import shaart.pstorage.service.EncryptionService;
import shaart.pstorage.service.PasswordService;
import shaart.pstorage.service.SecurityAwareService;
import shaart.pstorage.ui.component.CopyPasswordAction;
import shaart.pstorage.ui.util.AlertHelper;

/**
 * Controller for main form.
 */
@Slf4j
public class MainFormController {

  private static final String EMPTY = "";
  private static final String ERROR_STRING = "error";

  @Autowired
  private PasswordService passwordService;

  @Autowired
  private EncryptionService encryptionService;

  @Autowired
  private SecurityAwareService securityAwareService;

  @Autowired
  private UserDataContext userDataContext;

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
    aliasColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    aliasColumn.setOnEditCommit(aliasEditEventHandler());

    TableColumn<PasswordDto, String> passwordColumn = new TableColumn<>("Password");
    passwordColumn.setCellValueFactory(new PropertyValueFactory<>("showedEncryptedValue"));

    TableColumn<PasswordDto, Button> actionsColumn = new TableColumn<>("Actions");
    actionsColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

    actionsColumn.setCellFactory(CopyPasswordAction.createCallback(
        passwordDto -> {
          Optional<UserDto> userDto = securityAwareService.currentUser();

          if (!userDto.isPresent()) {
            raiseUnauthorizedAlert();
            return ERROR_STRING;
          }

          final CryptoDto cryptoDto = CryptoDto.of(passwordDto.getEncryptedValue());
          final UserDto user = userDto.get();
          final CryptoResult cryptoResult = encryptionService.decryptForUser(cryptoDto, user);
          return cryptoResult.getValue();
        }));

    table.getColumns().add(0, idColumn);
    table.getColumns().add(1, aliasColumn);
    table.getColumns().add(2, passwordColumn);
    table.getColumns().add(3, actionsColumn);
  }

  private EventHandler<CellEditEvent<PasswordDto, String>> aliasEditEventHandler() {
    return event -> {
      String newAlias = event.getNewValue();
      final String passwordId = event.getRowValue().getId();
      try {
        passwordService.updateAlias(passwordId, newAlias);
      } catch (DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        throw new AliasAlreadyExistsException(newAlias);
      }
      log.info("Alias updated successfully for password with id = {}", passwordId);
      event.getRowValue().setAlias(newAlias);
    };
  }

  void fillWithData() {
    List<PasswordDto> passwords = passwordService.findAll(() -> securityAwareService.currentUser());
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
      raiseUnauthorizedAlert();
      return;
    }

    final CryptoDto encryptionDto = CryptoDto.of(txtEncryptedValue.getText());
    final CryptoResult encrypted = encryptionService.encryptForUser(encryptionDto, userDto.get());

    PasswordDto password = PasswordDto.builder()
        .alias(txtAlias.getText())
        .encryptedValue(encrypted.getValue())
        .encryptionType(encrypted.getEncryptionType())
        .user(userDto.get())
        .build();

    log.debug("Saving password with alias {} for user {}",
        password.getAlias(),
        password.getUser().getName());

    final PasswordDto savedPassword;
    try {
      savedPassword = passwordService.save(password);
    } catch (DataIntegrityViolationException e) {
      log.error(e.getMessage(), e);
      throw new AliasAlreadyExistsException(password.getAlias());
    }

    data.add(savedPassword);
    userDataContext.addPassword(txtAlias.getText(), txtEncryptedValue.getText());

    clearFields();
  }

  private void raiseUnauthorizedAlert() {
    log.trace("User not found in security context - unauthorized");
    AlertHelper.showAlert(AlertType.ERROR, "Error", "Unauthorized");
  }

  private void clearFields() {
    txtAlias.setText(EMPTY);
    txtEncryptedValue.setText(EMPTY);
  }
}