package shaart.pstorage;

import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;

public class PStorageApplicationTests extends SpringAbstractTest {

  @BeforeClass
  public static void bootstrapJavaFx() {
    new JFXPanel();
  }

  @Test
  public void contextLoads() {
    Assert.isTrue(true, "Application context should be able to load");
  }

}
