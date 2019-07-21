package shaart.pstorage.ui;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.service.PasswordService;

/**
 * Controller for main form.
 */
public class MainFormController {

  private static final String EMPTY = "";

  @Autowired
  private PasswordService passwordService;

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
    List<PasswordDto> passwords = passwordService.findAll();
    data = FXCollections.observableArrayList(passwords);

    TableColumn<PasswordDto, String> idColumn = new TableColumn<>("ID");
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

    TableColumn<PasswordDto, String> aliasColumn = new TableColumn<>("Alias");
    aliasColumn.setCellValueFactory(new PropertyValueFactory<>("alias"));

    TableColumn<PasswordDto, String> passwordColumn = new TableColumn<>("Password");
    passwordColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

    table.getColumns().add(0, idColumn);
    table.getColumns().add(1, aliasColumn);
    table.getColumns().add(2, passwordColumn);

    table.setItems(data);
  }

  /**
   * Add password to storage.
   */
  @FXML
  public void addPassword() {
    PasswordDto password = PasswordDto.builder()
        .alias(txtAlias.getText())
        .encryptedValue(txtEncryptedValue.getText())
        .build();

    final PasswordDto savedPassword = passwordService.save(password);

    data.add(savedPassword);

    clearFields();
  }

  private void clearFields() {
    txtAlias.setText(EMPTY);
    txtEncryptedValue.setText(EMPTY);
  }
}