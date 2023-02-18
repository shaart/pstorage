package shaart.pstorage.ui;

import jakarta.annotation.PostConstruct;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import shaart.pstorage.component.UserDataContext;
import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.dto.CryptoResult;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.exception.AliasAlreadyExistsException;
import shaart.pstorage.exception.UnauthorizedException;
import shaart.pstorage.service.EncryptionService;
import shaart.pstorage.service.PasswordService;
import shaart.pstorage.service.SecurityAwareService;
import shaart.pstorage.ui.component.CopyPasswordAction;
import shaart.pstorage.ui.component.DeletePasswordAction;
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
    passwordColumn.setOnEditStart(passwordEditEventHandler());

    TableColumn<PasswordDto, Button> copyToClipboardColumn = new TableColumn<>("Actions");
    copyToClipboardColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

    copyToClipboardColumn.setCellFactory(CopyPasswordAction.createCallback(
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

    TableColumn<PasswordDto, Button> deletePasswordColumn = new TableColumn<>("Actions");
    deletePasswordColumn.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

    deletePasswordColumn.setCellFactory(DeletePasswordAction.createCallback(
        passwordDto -> {
          passwordService.deleteById(passwordDto.getId());
          userDataContext.removePassword(passwordDto.getAlias());
          table.getItems().remove(passwordDto);
        }));

    table.getColumns().add(0, idColumn);
    table.getColumns().add(1, aliasColumn);
    table.getColumns().add(2, passwordColumn);
    table.getColumns().add(3, copyToClipboardColumn);
    table.getColumns().add(4, deletePasswordColumn);
  }

  private EventHandler<CellEditEvent<PasswordDto, String>> passwordEditEventHandler() {
    return event -> {
      final PasswordDto password = event.getRowValue();
      final String title = String.format("Change '%s' password", password.getAlias());
      AlertHelper.showPasswordInputDialog(title, "Enter new password:", newPassword -> {
        if (!newPassword.isPresent()) {
          return;
        }
        final String newPasswordValue = newPassword.get();
        final CryptoResult cryptoResult = encryptionService.encryptForUser(
            CryptoDto.of(newPasswordValue),
            securityAwareService.currentUser()
                .orElseThrow(() ->
                    new UnauthorizedException("Can't update password for unauthorized user")));
        passwordService.updatePassword(password.getId(), cryptoResult.getEncryptionType(),
            cryptoResult.getValue());
        password.setEncryptedValue(cryptoResult.getValue());
        password.setEncryptionType(cryptoResult.getEncryptionType());

        userDataContext.updatePasswordValue(password.getAlias(), password.getAlias(),
            newPasswordValue);
      });
    };
  }

  private EventHandler<CellEditEvent<PasswordDto, String>> aliasEditEventHandler() {
    return event -> {
      final PasswordDto password = event.getRowValue();
      String newAlias = event.getNewValue();
      final String passwordId = password.getId();
      try {
        passwordService.updateAlias(passwordId, newAlias);
      } catch (DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        throw new AliasAlreadyExistsException(newAlias);
      }
      log.info("Alias updated successfully for password with id = {}", passwordId);
      String oldAlias = event.getOldValue();
      password.setAlias(newAlias);

      userDataContext.updatePasswordLabel(oldAlias, newAlias);
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