package org.bibliotecaviva.backend.infrastructure.repositories;

import org.bibliotecaviva.backend.domain.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkRepository extends JpaRepository<Work, UUID> {
}
