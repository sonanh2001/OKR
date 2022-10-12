package org.aibles.okr3.repository;

import java.util.Optional;
import org.aibles.okr3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByUsername(String username);

  Optional<User> findByEmail(String email);

  Boolean existsByEmail(String email);
}
