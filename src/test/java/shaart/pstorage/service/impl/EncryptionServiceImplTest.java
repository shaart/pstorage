package shaart.pstorage.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import shaart.pstorage.SpringAbstractTest;
import shaart.pstorage.config.PStorageProperties;

public class EncryptionServiceImplTest extends SpringAbstractTest {

  @Autowired
  private PStorageProperties properties;

  @InjectMocks
  private EncryptionServiceImpl encryptionService;

  @Before
  public void init() {
    encryptionService = new EncryptionServiceImpl(properties);
  }

  @Test
  public void encrypt() {
    final String initial = "initialTestPassword";

    final String encrypted = encryptionService.encrypt(initial);

    Assert.assertNotEquals(initial, encrypted);
  }

  @Test
  public void encryptSeveralTimes() {
    final String initial = "initialTestPassword";

    final String encrypted = encryptionService.encrypt(initial);
    final String encryptedAgain = encryptionService.encrypt(initial);

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

    final String encryptedWithFirst = encryptionService.encrypt(initial, firstKey);
    final String encryptedWithSecond = encryptionService.encrypt(initial, secondKey);

    Assert.assertNotEquals(initial, encryptedWithFirst);
    Assert.assertNotEquals(initial, encryptedWithSecond);
    Assert.assertNotEquals("Encrypting same string with different keys should be different",
        encryptedWithFirst, encryptedWithSecond);
  }

  @Test
  public void decryptAfterEncrypt() {
    final String initial = "initialTestPassword";

    final String encrypted = encryptionService.encrypt(initial);

    final String decrypted = encryptionService.decrypt(encrypted);

    Assert.assertEquals(initial, decrypted);
  }

  @Test
  public void decryptWithKey() {
    final String initial = "initialTestPassword";

    final String key = "aKey";

    final String encryptedWithFirst = encryptionService.encrypt(initial, key);
    final String decrypted = encryptionService.decrypt(encryptedWithFirst, key);

    Assert.assertEquals(initial, decrypted);
  }

  @Test
  public void decryptWithKeyWrongKey() {
    final String initial = "initialTestPassword";

    final String key = "aKey";
    final String wrongKey = "wrongKey";

    final String encryptedWithFirst = encryptionService.encrypt(initial, key);
    final String decrypted = encryptionService.decrypt(encryptedWithFirst, wrongKey);

    Assert.assertNotEquals(initial, decrypted);
  }
}