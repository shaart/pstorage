package shaart.pstorage.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import shaart.pstorage.SpringAbstractTest;
import shaart.pstorage.config.PstorageProperties;
import shaart.pstorage.crypto.impl.AesCoder;
import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.exception.CryptoException;

class EncryptionServiceImplTest extends SpringAbstractTest {

  @Autowired
  private PstorageProperties properties;

  @Autowired
  private AesCoder aesCoder;

  @InjectMocks
  private EncryptionServiceImpl encryptionService;

  @BeforeEach
  void init() {
    encryptionService = new EncryptionServiceImpl(properties, aesCoder);
  }

  @Test
  void encrypt() {
    final String initial = "initialTestPassword";

    final String encrypted = encryptionService.encrypt(CryptoDto.of(initial)).getValue();

    assertNotNull(encrypted);

    assertNotEquals(initial, encrypted);
  }

  @Test
  void encryptSeveralTimes() {
    final String initial = "initialTestPassword";

    final String encrypted = encryptionService.encrypt(CryptoDto.of(initial)).getValue();
    final String encryptedAgain = encryptionService.encrypt(CryptoDto.of(initial)).getValue();

    assertNotNull(encrypted);
    assertNotNull(encryptedAgain);

    assertNotEquals(initial, encrypted);
    assertNotEquals(initial, encryptedAgain);

    assertEquals(
        encrypted,
        encryptedAgain,
        "Encryption of same string in different places should be the same"
    );
  }

  @Test
  void encryptWithKey() {
    final String initial = "initialTestPassword";

    final String firstKey = "firstKey";
    final String secondKey = "secondKey";

    final String encryptedWithFirst = encryptionService.encrypt(CryptoDto.of(initial), firstKey)
        .getValue();
    final String encryptedWithSecond = encryptionService.encrypt(CryptoDto.of(initial), secondKey)
        .getValue();

    assertNotNull(encryptedWithFirst);
    assertNotNull(encryptedWithSecond);

    assertNotEquals(initial, encryptedWithFirst);
    assertNotEquals(initial, encryptedWithSecond);

    assertNotEquals(
        encryptedWithFirst,
        encryptedWithSecond,
        "Encrypting same string with different keys should be different"
    );
  }

  @Test
  void decryptAfterEncrypt() {
    final String initial = "initialTestPassword";

    final String encrypted = encryptionService.encrypt(CryptoDto.of(initial)).getValue();

    final String decrypted = encryptionService.decrypt(CryptoDto.of(encrypted)).getValue();

    assertNotNull(decrypted);
    assertEquals(initial, decrypted);
  }

  @Test
  void decryptWithCorrectKey() {
    final String initial = "initialTestPassword";

    final String key = "aKey";

    final String encryptedWithFirst = encryptionService.encrypt(CryptoDto.of(initial), key)
        .getValue();
    final String decrypted = encryptionService.decrypt(CryptoDto.of(encryptedWithFirst), key)
        .getValue();

    assertNotNull(decrypted);
    assertEquals(initial, decrypted);
  }

  @Test
  void decryptWithWrongKey() {
    final String initial = "initialTestPassword";

    final String key = "aKey";
    final String wrongKey = "wrongKey";

    final String encryptedWithFirst = encryptionService.encrypt(CryptoDto.of(initial), key)
        .getValue();

    Executable executable = () -> encryptionService.decrypt(CryptoDto.of(encryptedWithFirst),
        wrongKey);
    assertThrows(CryptoException.class, executable);
  }

  @Test
  void encryptWithShortKey() {
    final String initial = "initialTestPassword";

    final String firstKey = "a";

    final String encryptedWithFirst = encryptionService.encrypt(CryptoDto.of(initial), firstKey)
        .getValue();

    assertNotNull(encryptedWithFirst);
    assertNotEquals(initial, encryptedWithFirst);
  }

  @Test
  void encryptWithLongKey() {
    final String initial = "initialTestPassword";

    final String firstKey = "1234567890qwertyuiopasdfghjklzxcvbnm1234567890qwertyuiopasdfghjkl";

    final String encryptedWithFirst = encryptionService.encrypt(CryptoDto.of(initial), firstKey)
        .getValue();

    assertNotNull(encryptedWithFirst);
    assertNotEquals(initial, encryptedWithFirst);
  }
}