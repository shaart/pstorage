package shaart.pstorage;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class PstorageApplicationTests extends SpringAbstractTest {

  @BeforeAll
  public static void bootstrapJavaFx() {
    new JFXPanel();
  }

  @Test
  public void contextLoads() {
    Assert.isTrue(true, "Application context should be able to load");
  }

}
