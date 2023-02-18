package shaart.pstorage;

//import javafx.embed.swing.JFXPanel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Tests does not see 'javafx.embed.swing' module, need investigation")
@SpringBootTest
public abstract class SpringAbstractTest {

  @BeforeAll
  public static void bootstrapJavaFx() {
//    new JFXPanel();
  }
}
