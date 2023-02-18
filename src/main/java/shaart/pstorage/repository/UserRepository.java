package shaart.pstorage.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shaart.pstorage.entity.User;

@Transactional(propagation = Propagation.MANDATORY)
public interface UserRepository extends CrudRepository<User, UUID> {

  List<User> findAll();

  Optional<User> findByName(String name);

  boolean existsByName(String name);
}