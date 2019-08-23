package shaart.pstorage;

import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class SpringAbstractTest {

  @BeforeClass
  public static void bootstrapJavaFx() {
    new JFXPanel();
  }
}
