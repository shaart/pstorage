package shaart.pstorage.service;

import java.util.List;
import shaart.pstorage.dto.PasswordDto;

public interface PasswordService {

  PasswordDto save(PasswordDto password);

  List<PasswordDto> findAll();

}