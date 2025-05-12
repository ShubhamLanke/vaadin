package org.vaddin.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaddin.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
