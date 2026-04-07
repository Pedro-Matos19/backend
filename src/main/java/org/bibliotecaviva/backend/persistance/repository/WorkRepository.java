package org.bibliotecaviva.backend.persistance.repository;

import jakarta.transaction.Transactional;
import org.bibliotecaviva.backend.domain.entities.User;
import org.bibliotecaviva.backend.domain.entities.Work;
import org.bibliotecaviva.backend.domain.entities.WorkSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkRepository extends  JpaRepository<Work, UUID> {
    @Query(value = """
    SELECT w.id, w.title, w.publication_date, w.description, w.type,w.likes,w.view_count, u.name as author
    FROM obras w
    JOIN users u ON u.id = w.users_id
    WHERE (:type IS NULL OR type = :type)
    """, nativeQuery = true)
    List<WorkSummary> findAllSummary(@Param("type") String type);

    @Modifying
    @Transactional
    @Query("UPDATE Work w SET w.viewCount = w.viewCount + 1 WHERE w.id = :id")
    void incrementViewCount(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE Work w SET w.likes = w.likes + 1 WHERE w.id = :id")
    void incrementLikes(@Param("id") UUID id);
    boolean existsWorkByAuthorAndTitle(User author, String title);
}
