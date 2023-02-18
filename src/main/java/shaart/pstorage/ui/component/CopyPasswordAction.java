package shaart.pstorage.ui.component;

import java.util.function.BiFunction;
import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.ui.CommandFactory;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CopyPasswordAction {

  public static final CommandFactory COMMAND_FACTORY = CommandFactory.INSTANCE;

  /**
   * Creates a callback that applies decrypt function and performs 'copy to clipboard' action on
   * resulting value.
   *
   * @param decryptPasswordFunction function to extract decrypted value from {@link PasswordDto}
   * @return resulting callback for table column factory
   */
  public static Callback<
      TableColumn<PasswordDto, Button>,
      TableCell<PasswordDto, Button>
      > createCallback(Function<PasswordDto, String> decryptPasswordFunction) {
    return ActionButtonTableCell.forTableColumn("Copy to clipboard",
        getProcessEventFunction(decryptPasswordFunction));
  }

  private static BiFunction<ActionEvent, PasswordDto, PasswordDto> getProcessEventFunction(
      Function<PasswordDto, String> decryptPasswordFunction) {
    return (event, passwordDto) -> {
      try {
        log.info("Got 'copy to clipboard' action for password with alias '{}'",
            passwordDto.getAlias());
        final String decrypted = decryptPasswordFunction.apply(passwordDto);
        final Runnable copyPasswordAction = COMMAND_FACTORY.createCopyPasswordAction(decrypted);
        copyPasswordAction.run();

        log.info("'copy to clipboard' action for password with alias '{}' performed",
            passwordDto.getAlias());
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
      return passwordDto;
    };
  }
}
