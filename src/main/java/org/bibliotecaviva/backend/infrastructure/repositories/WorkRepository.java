package org.bibliotecaviva.backend.infrastructure.repositories;

import org.bibliotecaviva.backend.domain.entities.Work;
import org.bibliotecaviva.backend.domain.entities.WorkSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkRepository extends  JpaRepository<Work, UUID> {
    @Query(value = "SELECT * FROM obras",
            nativeQuery = true)
    List<WorkSummary> findAllSummary();
}
