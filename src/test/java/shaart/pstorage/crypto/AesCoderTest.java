package shaart.pstorage.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import shaart.pstorage.crypto.impl.AesCoder;
import shaart.pstorage.exception.CryptoException;

public class AesCoderTest {

  private static final String INITIAL_TEXT = "Text to be encrypted";
  private static final String EMPTY_TEXT = "";
  private static final String CORRECT_KEY = "aCorrectKey";
  private static final String CORRECT_SHORT_KEY = "a";
  private static final String WRONG_KEY = "WrongKey";

  private AesCoder aesCoder;

  @Before
  public void init() {
    aesCoder = new AesCoder();
    aesCoder.initialize();
  }

  @Test
  public void encryptThenDecryptWithSameKey() {
    final String encrypted = aesCoder.encrypt(INITIAL_TEXT, CORRECT_KEY);

    assertNotEquals(INITIAL_TEXT, encrypted);

    final String decrypted = aesCoder.decrypt(encrypted, CORRECT_KEY);

    assertEquals(INITIAL_TEXT, decrypted);
  }

  @Test
  public void encryptThenDecryptEmptyTextWithSameKey() {
    final String encrypted = aesCoder.encrypt(EMPTY_TEXT, CORRECT_KEY);

    assertNotEquals(EMPTY_TEXT, encrypted);

    final String decrypted = aesCoder.decrypt(encrypted, CORRECT_KEY);

    assertEquals(EMPTY_TEXT, decrypted);
  }

  @Test
  public void encryptThenDecryptWithSameShortKey() {
    final String encrypted = aesCoder.encrypt(INITIAL_TEXT, CORRECT_SHORT_KEY);

    assertNotEquals(INITIAL_TEXT, encrypted);

    final String decrypted = aesCoder.decrypt(encrypted, CORRECT_SHORT_KEY);

    assertEquals(INITIAL_TEXT, decrypted);
  }

  @Test(expected = CryptoException.class)
  public void encryptThenDecryptWithDifferentKeys() {
    final String encrypted = aesCoder.encrypt(INITIAL_TEXT, CORRECT_KEY);

    assertNotEquals(INITIAL_TEXT, encrypted);

    aesCoder.decrypt(encrypted, WRONG_KEY);
  }
}