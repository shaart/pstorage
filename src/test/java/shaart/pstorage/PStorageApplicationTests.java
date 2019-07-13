package shaart.pstorage;

import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PStorageApplicationTests {

  @BeforeClass
  public static void bootstrapJavaFx(){
    new JFXPanel();
  }

  @Test
  public void contextLoads() {
    Assert.isTrue(true, "Application context should be able to load");
  }

}
