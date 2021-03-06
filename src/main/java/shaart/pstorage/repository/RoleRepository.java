package shaart.pstorage.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import shaart.pstorage.entity.Role;

@Transactional(propagation = Propagation.MANDATORY)
public interface RoleRepository extends CrudRepository<Role, Integer> {

  List<Role> findAll();

  Optional<Role> findByName(String name);

  boolean existsByName(String name);
}