package budget_project.demo.repositories;

import budget_project.demo.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepo extends JpaRepository<Users, UUID> {
          Optional<Users> findByEmail(String email);
          Optional<Users> findByUserName(String userName);
          boolean existsByEmail(String email);

}
