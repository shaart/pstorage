package shaart.pstorage;

//import javafx.embed.swing.JFXPanel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Tests does not see 'javafx.embed.swing' module, need investigation")
class PstorageApplicationTests extends SpringAbstractTest {

  @BeforeAll
  public static void bootstrapJavaFx() {
//    new JFXPanel();
  }

  @Test
  void contextLoads() {
    Assertions.assertTrue(true, "Application context should be able to load");
  }

}
