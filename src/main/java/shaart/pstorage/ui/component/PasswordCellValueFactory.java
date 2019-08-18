package shaart.pstorage.ui.component;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import shaart.pstorage.dto.PasswordDto;

/**
 * Factory that set into password cell encrypted value as "***".
 */
public class PasswordCellValueFactory<T> extends PropertyValueFactory<PasswordDto, T> {

  /**
   * Factory that set into password cell encrypted value as "***".
   */
  public PasswordCellValueFactory(String property) {
    super(property);
  }

  @Override
  public ObservableValue<T> call(CellDataFeatures<PasswordDto, T> param) {
    param.getValue().setEncryptedValue("***");
    return super.call(param);
  }
}
