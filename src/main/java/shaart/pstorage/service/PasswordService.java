package shaart.pstorage.service;

import java.util.List;
import shaart.pstorage.dto.PasswordDto;

public interface PasswordService {

  List<PasswordDto> findAll();

  PasswordDto save(PasswordDto password);

}