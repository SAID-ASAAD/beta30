package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
