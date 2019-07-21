package shaart.pstorage.loader.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import lombok.extern.slf4j.Slf4j;
import shaart.pstorage.dto.ViewHolder;
import shaart.pstorage.loader.ViewLoader;

@Slf4j
public class ViewLoaderImpl implements ViewLoader {

  @Override
  public <T> ViewHolder<T> loadView(String resourceUrl) throws IOException {
    try (InputStream fxmlStream = getClass().getClassLoader().getResourceAsStream(resourceUrl)) {
      FXMLLoader loader = new FXMLLoader();

      Objects.requireNonNull(fxmlStream, String.format("File '%s' not found!", resourceUrl));

      loader.load(fxmlStream);
      return ViewHolder.of(loader.getRoot(), loader.getController());
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
      throw e;
    }
  }
}
