package shaart.pstorage.dto;

import javafx.scene.Parent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import shaart.pstorage.PStorageApplication;

/**
 * Wrapper. Controller should be bean. View will be used in entrypoint at {@link
 * PStorageApplication}.
 */
@Getter
@Setter
@AllArgsConstructor
public class ViewHolder<T> {

  private Parent view;
  private T controller;

  public static <T> ViewHolder<T> of(Parent view, T controller) {
    return new ViewHolder<>(view, controller);
  }
}
