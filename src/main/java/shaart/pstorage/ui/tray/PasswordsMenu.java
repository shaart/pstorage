package shaart.pstorage.ui.tray;

import java.awt.Menu;
import java.awt.MenuItem;
import java.util.List;

/**
 * Menu for copying passwords to clipboard.
 */
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

  public void removePassword(MenuItem item) {
    remove(item);
  }

  public void changePasswords(List<MenuItem> passwordItems) {
    removeAll();
    passwordItems.forEach(this::add);
  }
}
