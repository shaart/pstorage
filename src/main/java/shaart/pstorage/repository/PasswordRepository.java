package shaart.pstorage.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shaart.pstorage.entity.Password;

@Transactional(propagation = Propagation.MANDATORY)
public interface PasswordRepository extends CrudRepository<Password, Long> {

  List<Password> findAll();

}