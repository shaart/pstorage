package shaart.pstorage.component;

import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.dto.CryptoResult;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.dto.UserDto;
import shaart.pstorage.service.EncryptionService;
import shaart.pstorage.service.PasswordService;
import shaart.pstorage.ui.CommandFactory;
import shaart.pstorage.ui.tray.PasswordsMenu;

@Component
@RequiredArgsConstructor
public class UserDataContext {

  private final PasswordService passwordService;
  private final EncryptionService encryptionService;

  private CommandFactory commandFactory = CommandFactory.INSTANCE;

  public void loadUserData(UserDto user) {
    loadFavoritePasswords(user);
  }

  private void loadFavoritePasswords(UserDto user) {
    final List<PasswordDto> userFavoritePasswords =
        passwordService.findFavoritesByUser(user.getName());

    final CryptoResult decryptedMasterPassword =
        encryptionService.decrypt(CryptoDto.of(user.getMasterPassword()));

    final List<MenuItem> favoritePasswordItems = userFavoritePasswords.stream()
        .map(toPasswordMenuItem(decryptedMasterPassword))
        .collect(Collectors.toList());

    PasswordsMenu.getInstance().changePasswords(favoritePasswordItems);
  }

  private Function<PasswordDto, MenuItem> toPasswordMenuItem(CryptoResult decryptedMasterPassword) {
    return passwordDto -> {
      final CryptoResult decrypted = encryptionService.decrypt(
          passwordDto.getEncryptionType(),
          CryptoDto.of(passwordDto.getEncryptedValue()),
          decryptedMasterPassword.getValue());

      return toPasswordMenuItem(passwordDto.getAlias(), decrypted.getValue());
    };
  }

  private MenuItem toPasswordMenuItem(String label, String copyValue) {
    final MenuItem item = new MenuItem(label);
    final ActionListener copyPasswordCommand = commandFactory.createCopyPasswordCommand(copyValue);

    item.addActionListener(copyPasswordCommand);
    return item;
  }

  public void addPassword(String alias, String value) {
    PasswordsMenu.getInstance().addPassword(toPasswordMenuItem(alias, value));
  }

  /**
   * Updates password's item in tray.
   *
   * @param oldAlias previous alias of password before update if changed or current if not
   * @param newAlias new alias or previous if not changed
   * @param newDecryptedPassword decrypted value of a new password
   */
  public void updatePasswordValue(String oldAlias, String newAlias, String newDecryptedPassword) {
    final MenuItem menuItem = toPasswordMenuItem(newAlias, newDecryptedPassword);
    PasswordsMenu.getInstance().updatePassword(oldAlias, menuItem);
  }

  /**
   * Updates password's item in tray.
   *
   * @param oldAlias previous alias of password before update
   * @param newAlias new alias
   */
  public void updatePasswordLabel(String oldAlias, String newAlias) {
    PasswordsMenu.getInstance().updatePasswordAlias(oldAlias, newAlias);
  }

  /**
   * Removes password with alias.
   *
   * @param alias password's alias
   */
  public void removePassword(String alias) {
    PasswordsMenu.getInstance().removePassword(alias);
  }
}
