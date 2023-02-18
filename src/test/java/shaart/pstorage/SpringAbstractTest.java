package shaart.pstorage;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
public abstract class SpringAbstractTest {

  @BeforeAll
  public static void bootstrapJavaFx() {
    new JFXPanel();
  }
}
