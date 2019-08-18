package shaart.pstorage.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum CommandFactory {
  INSTANCE;

  public ActionListener createExitCommand() {
    return actionEvent -> {
      if (log.isInfoEnabled()) {
        log.info("Exiting application command received at {}", new Date(actionEvent.getWhen()));
      }
      Platform.exit();
    };
  }

  public ActionListener createCopyPasswordCommand(final String encryptedPassword,
      final UnaryOperator<String> decryptFunction) {

    return createCopyPasswordCommand(decryptFunction.apply(encryptedPassword));
  }

  public ActionListener createCopyPasswordCommand(final String copyValue) {
    return actionEvent -> {
      final Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      final StringSelection selection = new StringSelection(copyValue);
      systemClipboard.setContents(selection, selection);
    };
  }
}
