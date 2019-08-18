package shaart.pstorage.service;

import java.util.List;
import shaart.pstorage.dto.PasswordDto;

public interface PasswordService {

  List<PasswordDto> findAll();

  List<PasswordDto> findAllByUser(String username);

  PasswordDto save(PasswordDto password);

}