package shaart.pstorage.ui.component;

import java.util.function.BiFunction;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Button for table.
 *
 * @param <T> type of TableView
 */
public class ActionButtonTableCell<T> extends TableCell<T, Button> {

  private final Button actionButton;

  /**
   * Constructor for creating action button for table cell.
   *
   * @param label button's label
   * @param actionFunction action to be performed on event
   */
  public ActionButtonTableCell(String label, BiFunction<ActionEvent, T, T> actionFunction) {
    this.getStyleClass().add("action-button-table-cell");

    this.actionButton = new Button(label);
    this.actionButton.setOnAction((ActionEvent e) -> actionFunction.apply(e, getCurrentItem()));
    this.actionButton.setMaxWidth(Double.MAX_VALUE);
  }

  /**
   * Create button with callback for table's column.
   *
   * @param buttonLabel button's label
   * @param actionFunction action to be performed for event
   * @param <T> type of TableView
   * @return created callback
   */
  public static <T> Callback<TableColumn<T, Button>, TableCell<T, Button>> forTableColumn(
      String buttonLabel, BiFunction<ActionEvent, T, T> actionFunction) {
    return tableColumn -> new ActionButtonTableCell<>(buttonLabel, actionFunction);
  }

  public T getCurrentItem() {
    return getTableView().getItems().get(getIndex());
  }

  @Override
  protected void updateItem(Button item, boolean empty) {
    super.updateItem(item, empty);

    if (empty) {
      setGraphic(null);
    } else {
      setGraphic(actionButton);
    }
  }
}
