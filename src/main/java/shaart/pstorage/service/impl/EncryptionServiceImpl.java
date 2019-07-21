package shaart.pstorage.service.impl;

import org.springframework.stereotype.Component;
import shaart.pstorage.service.EncryptionService;

@Component
public class EncryptionServiceImpl implements EncryptionService {

  @Override
  public String encrypt(String value) {
    return value;
  }

  @Override
  public String decrypt(String value) {
    return value;
  }
}
