package shaart.pstorage.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shaart.pstorage.converter.PasswordConverter;
import shaart.pstorage.dto.PasswordDto;
import shaart.pstorage.entity.Password;
import shaart.pstorage.repository.PasswordRepository;
import shaart.pstorage.service.PasswordService;

@Service
@Transactional
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {

  private final PasswordRepository repository;
  private final PasswordConverter passwordConverter;

  @Override
  public PasswordDto save(PasswordDto passwordDto) {
    Password password = passwordConverter.convert(passwordDto);
    final Password savedPassword = repository.save(password);
    return passwordConverter.convert(savedPassword);
  }

  @Override
  public List<PasswordDto> findAll() {
    return repository.findAll().stream()
        .map(passwordConverter::convert)
        .collect(Collectors.toList());
  }
}