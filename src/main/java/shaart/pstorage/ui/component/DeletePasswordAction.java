package shaart.pstorage.ui.component;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.ui.CommandFactory;

@Slf4j
public class DeletePasswordAction {

  public static final CommandFactory COMMAND_FACTORY = CommandFactory.INSTANCE;

  /**
   * Creates a callback that applies decrypt function and performs 'copy to clipboard' action on
   * resulting value.
   *
   * @param removePassword function to delete password using {@link PasswordDto}
   * @return resulting callback for table column factory
   */
  public static Callback<
      TableColumn<PasswordDto, Button>,
      TableCell<PasswordDto, Button>
      > createCallback(Consumer<PasswordDto> removePassword) {
    return ActionButtonTableCell.forTableColumn("Delete",
        getProcessEventFunction(removePassword));
  }

  private static BiFunction<ActionEvent, PasswordDto, PasswordDto> getProcessEventFunction(
      Consumer<PasswordDto> removePassword) {
    return (event, passwordDto) -> {
      try {
        log.info("Got 'remove password' action for password with alias '{}'",
            passwordDto.getAlias());
        removePassword.accept(passwordDto);

        log.info("'remove password' action for password with alias '{}' performed",
            passwordDto.getAlias());
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
      return passwordDto;
    };
  }
}
