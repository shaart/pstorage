package shaart.pstorage.ui.tray;

import java.awt.Menu;
import java.awt.MenuItem;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;

/**
 * Menu for copying passwords to clipboard.
 */
@Slf4j
public class PasswordsMenu extends Menu {

  public static final PasswordsMenu instance = new PasswordsMenu();

  private PasswordsMenu() {
    super("Passwords");
  }

  public static PasswordsMenu getInstance() {
    return instance;
  }

  public void addPassword(MenuItem passwordItem) {
    add(passwordItem);
  }

  public void removePassword(String alias) {
    updateIfFound(item -> item.getLabel().equals(alias), (item, index) -> remove(index));
  }

  public void changePasswords(List<MenuItem> passwordItems) {
    removeAll();
    passwordItems.forEach(this::add);
  }

  /**
   * Updates item at tray's menu for changed password.
   *
   * @param oldAlias previous alias of password if changed or current if not
   * @param updatedMenuItem updated menu item
   */
  public void updatePassword(String oldAlias, MenuItem updatedMenuItem) {
    updateIfFound(item -> item.getLabel().equals(oldAlias), (item, index) -> {
      remove(index);
      insert(updatedMenuItem, index);
    });
  }

  public void updatePasswordAlias(String oldAlias, String newAlias) {
    updateIfFound(item -> item.getLabel().equals(oldAlias),
        (item, index) -> item.setLabel(newAlias));
  }

  private void updateIfFound(Predicate<MenuItem> isSame, ObjIntConsumer<MenuItem> updater) {
    final int itemCount = getItemCount();
    boolean wasFound = false;
    for (int i = 0; i < itemCount; i++) {
      final MenuItem item = getItem(i);
      if (isSame.test(item)) {
        final String prevLabel = item.getLabel();
        updater.accept(item, i);
        log.info("Tray's menuItem with alias '{}' was updated", prevLabel);
        wasFound = true;
        break;
      }
    }
    if (!wasFound) {
      log.warn("MenuItem with received criteria was not found, nothing to update");
    }
  }
}
