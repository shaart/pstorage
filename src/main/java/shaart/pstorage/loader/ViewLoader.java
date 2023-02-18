package shaart.pstorage.loader;

import java.io.IOException;
import shaart.pstorage.dto.ViewHolder;

/**
 * Loader for FXML view resources.
 */
public interface ViewLoader {

  /**
   * Load a view using FXML loader and FXML injections.
   *
   * @param resourceUrl path to resource
   * @return wrapper with view and controller
   */
  <T> ViewHolder<T> loadView(String resourceUrl) throws IOException;
}
