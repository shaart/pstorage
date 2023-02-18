package shaart.pstorage.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shaart.pstorage.entity.Password;

@Transactional(propagation = Propagation.MANDATORY)
public interface PasswordRepository extends CrudRepository<Password, UUID> {

  List<Password> findAll();

  List<Password> findAllByUserName(String name);
}