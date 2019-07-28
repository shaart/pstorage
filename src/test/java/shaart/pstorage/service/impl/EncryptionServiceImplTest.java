package shaart.pstorage.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import shaart.pstorage.SpringAbstractTest;
import shaart.pstorage.config.PStorageProperties;
import shaart.pstorage.crypto.AesCoder;
import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.exception.CryptoException;

public class EncryptionServiceImplTest extends SpringAbstractTest {

  @Autowired
  private PStorageProperties properties;

  @Autowired
  private AesCoder aesCoder;

  @InjectMocks
  private EncryptionServiceImpl encryptionService;

  @Before
  public void init() {
    encryptionService = new EncryptionServiceImpl(properties, aesCoder);
  }

  @Test
  public void encrypt() {
    final String initial = "initialTestPassword";

    final String encrypted = encryptionService.encrypt(CryptoDto.of(initial));

    Assert.assertNotNull(encrypted);

    Assert.assertNotEquals(initial, encrypted);
  }

  @Test
  public void encryptSeveralTimes() {
    final String initial = "initialTestPassword";

    final String encrypted = encryptionService.encrypt(CryptoDto.of(initial));
    final String encryptedAgain = encryptionService.encrypt(CryptoDto.of(initial));

    Assert.assertNotNull(encrypted);
    Assert.assertNotNull(encryptedAgain);

    Assert.assertNotEquals(initial, encrypted);
    Assert.assertNotEquals(initial, encryptedAgain);

    Assert.assertEquals("Encryption of same string in different places should be the same",
        encrypted, encryptedAgain);
  }

  @Test
  public void encryptWithKey() {
    final String initial = "initialTestPassword";

    final String firstKey = "firstKey";
    final String secondKey = "secondKey";

    final String encryptedWithFirst = encryptionService.encrypt(CryptoDto.of(initial), firstKey);
    final String encryptedWithSecond = encryptionService.encrypt(CryptoDto.of(initial), secondKey);

    Assert.assertNotNull(encryptedWithFirst);
    Assert.assertNotNull(encryptedWithSecond);

    Assert.assertNotEquals(initial, encryptedWithFirst);
    Assert.assertNotEquals(initial, encryptedWithSecond);

    Assert.assertNotEquals("Encrypting same string with different keys should be different",
        encryptedWithFirst, encryptedWithSecond);
  }

  @Test
  public void decryptAfterEncrypt() {
    final String initial = "initialTestPassword";

    final String encrypted = encryptionService.encrypt(CryptoDto.of(initial));

    final String decrypted = encryptionService.decrypt(CryptoDto.of(encrypted));

    Assert.assertNotNull(decrypted);
    Assert.assertEquals(initial, decrypted);
  }

  @Test
  public void decryptWithCorrectKey() {
    final String initial = "initialTestPassword";

    final String key = "aKey";

    final String encryptedWithFirst = encryptionService.encrypt(CryptoDto.of(initial), key);
    final String decrypted = encryptionService.decrypt(CryptoDto.of(encryptedWithFirst), key);

    Assert.assertNotNull(decrypted);
    Assert.assertEquals(initial, decrypted);
  }

  @Test(expected = CryptoException.class)
  public void decryptWithWrongKey() {
    final String initial = "initialTestPassword";

    final String key = "aKey";
    final String wrongKey = "wrongKey";

    final String encryptedWithFirst = encryptionService.encrypt(CryptoDto.of(initial), key);

    encryptionService.decrypt(CryptoDto.of(encryptedWithFirst), wrongKey);
  }

  @Test
  public void encryptWithShortKey() {
    final String initial = "initialTestPassword";

    final String firstKey = "a";

    final String encryptedWithFirst = encryptionService.encrypt(CryptoDto.of(initial), firstKey);

    Assert.assertNotNull(encryptedWithFirst);
    Assert.assertNotEquals(initial, encryptedWithFirst);
  }

  @Test
  public void encryptWithLongKey() {
    final String initial = "initialTestPassword";

    final String firstKey = "1234567890qwertyuiopasdfghjklzxcvbnm1234567890qwertyuiopasdfghjkl";

    final String encryptedWithFirst = encryptionService.encrypt(CryptoDto.of(initial), firstKey);

    Assert.assertNotNull(encryptedWithFirst);
    Assert.assertNotEquals(initial, encryptedWithFirst);
  }
}