package shaart.pstorage.dto;

import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;
import shaart.pstorage.PStorageApplication;

/**
 * Wrapper. Controller should be bean. View will be used in entrypoint at {@link
 * PStorageApplication}.
 */
@Getter
@Setter
public class ViewHolder {

  private Parent view;
  private Object controller;

  private ViewHolder(Parent view, Object controller) {
    this.view = view;
    this.controller = controller;
  }

  public static ViewHolder of(Parent view, Object controller) {
    return new ViewHolder(view, controller);
  }
}
