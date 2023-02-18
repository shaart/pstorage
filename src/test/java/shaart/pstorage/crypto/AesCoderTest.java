package shaart.pstorage.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import shaart.pstorage.crypto.impl.AesCoder;
import shaart.pstorage.exception.CryptoException;

class AesCoderTest {

  private static final String INITIAL_TEXT = "Text to be encrypted";
  private static final String EMPTY_TEXT = "";
  private static final String CORRECT_KEY = "aCorrectKey";
  private static final String CORRECT_SHORT_KEY = "a";
  private static final String WRONG_KEY = "WrongKey";

  private AesCoder aesCoder;

  @BeforeEach
  void init() {
    aesCoder = new AesCoder();
    aesCoder.initialize();
  }

  @Test
  void encryptThenDecryptWithSameKey() {
    final String encrypted = aesCoder.encrypt(INITIAL_TEXT, CORRECT_KEY);

    assertNotEquals(INITIAL_TEXT, encrypted);

    final String decrypted = aesCoder.decrypt(encrypted, CORRECT_KEY);

    assertEquals(INITIAL_TEXT, decrypted);
  }

  @Test
  void encryptThenDecryptEmptyTextWithSameKey() {
    final String encrypted = aesCoder.encrypt(EMPTY_TEXT, CORRECT_KEY);

    assertNotEquals(EMPTY_TEXT, encrypted);

    final String decrypted = aesCoder.decrypt(encrypted, CORRECT_KEY);

    assertEquals(EMPTY_TEXT, decrypted);
  }

  @Test
  void encryptThenDecryptWithSameShortKey() {
    final String encrypted = aesCoder.encrypt(INITIAL_TEXT, CORRECT_SHORT_KEY);

    assertNotEquals(INITIAL_TEXT, encrypted);

    final String decrypted = aesCoder.decrypt(encrypted, CORRECT_SHORT_KEY);

    assertEquals(INITIAL_TEXT, decrypted);
  }

  @Test
  void encryptThenDecryptWithDifferentKeys() {
    final String encrypted = aesCoder.encrypt(INITIAL_TEXT, CORRECT_KEY);

    assertNotEquals(INITIAL_TEXT, encrypted);

    Executable executable = () -> aesCoder.decrypt(encrypted, WRONG_KEY);
    assertThrows(CryptoException.class, executable);
  }
}