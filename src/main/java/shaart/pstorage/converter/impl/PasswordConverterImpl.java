package shaart.pstorage.converter.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shaart.pstorage.converter.PasswordConverter;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.entity.Password;
import shaart.pstorage.util.OperationUtil;

@Component
public class PasswordConverterImpl implements PasswordConverter {

  private OperationUtil operationUtil;

  @Autowired
  public void setOperationUtil(OperationUtil operationUtil) {
    this.operationUtil = operationUtil;
  }

  @Override
  public Password toEntity(PasswordDto passwordDto) {
    final Integer id = operationUtil.asInteger(passwordDto.getId());
    final Timestamp createdAt = operationUtil.asTimestamp(passwordDto.getCreatedAt());

    return Password.builder()
        .id(id)
        .alias(passwordDto.getAlias())
        .value(passwordDto.getEncryptedValue())
        .createdAt(createdAt)
        .build();
  }

  @Override
  public PasswordDto toDto(Password passwordEntity) {
    final String id = operationUtil.asString(passwordEntity.getId());
    final LocalDateTime createdAt = operationUtil.asDateTime(passwordEntity.getCreatedAt());

    return PasswordDto.builder()
        .id(id)
        .alias(passwordEntity.getAlias())
        .encryptedValue(passwordEntity.getValue())
        .createdAt(createdAt)
        .build();
  }
}
