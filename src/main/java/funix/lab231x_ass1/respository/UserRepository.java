package funix.lab231x_ass1.respository;

import funix.lab231x_ass1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUsername(String username);

  boolean existsByUsername(String username);
}
