package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
