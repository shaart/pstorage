package shaart.pstorage.service.impl;

import static java.util.Objects.isNull;

import java.util.EnumMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shaart.pstorage.config.PStorageProperties;
import shaart.pstorage.crypto.Coder;
import shaart.pstorage.dto.CryptoDto;
import shaart.pstorage.dto.CryptoResult;
import shaart.pstorage.enumeration.EncryptionType;
import shaart.pstorage.exception.CryptoException;
import shaart.pstorage.service.EncryptionService;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptionServiceImpl implements EncryptionService {

  private final PStorageProperties pstorageProperties;
  private final Coder coder;
  private Map<EncryptionType, Coder> codersMap = new EnumMap<>(EncryptionType.class);

  @PostConstruct
  public void init() {
    codersMap.put(coder.getEncryptionType(), coder);
  }

  @Override
  public CryptoResult encrypt(CryptoDto encryptionDto) {
    String key = pstorageProperties.getAes().getCommon().getKey();

    return encrypt(encryptionDto, key);
  }

  @Override
  public CryptoResult encrypt(CryptoDto encryptionDto, String key) {
    return CryptoResult.builder()
        .value(coder.encrypt(encryptionDto.getValue(), key))
        .encryptionType(coder.getEncryptionType())
        .build();
  }

  @Override
  public CryptoResult decrypt(CryptoDto decryptionDto) {
    String key = pstorageProperties.getAes().getCommon().getKey();

    return decrypt(decryptionDto, key);
  }

  @Override
  public CryptoResult decrypt(CryptoDto decryptionDto, String key) {
    return CryptoResult.builder()
        .value(coder.decrypt(decryptionDto.getValue(), key))
        .encryptionType(coder.getEncryptionType())
        .build();
  }

  @Override
  public CryptoResult decrypt(EncryptionType encryptionType, CryptoDto cryptoDto, String key) {
    final Coder foundCoder = codersMap.get(encryptionType);
    if (isNull(foundCoder)) {
      throw new CryptoException("Not found coder for type " + encryptionType.name());
    }

    return CryptoResult.builder()
        .value(foundCoder.decrypt(cryptoDto.getValue(), key))
        .encryptionType(this.coder.getEncryptionType())
        .build();
  }
}
