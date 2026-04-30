package org.bibliotecaviva.backend.persistence.repository;

import org.bibliotecaviva.backend.domain.entities.Comment;
import org.bibliotecaviva.backend.domain.entities.projections.CommentSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findByWorkIdOrderByCreatedAtDesc(UUID workId, Pageable pageable);

    Long countByWork_Id(UUID workId);

    @Query("SELECT c.id AS id, c.content AS content, c.createdAt AS createdAt, " +
            "u.name AS userName,u.id as userId, " +
            "w.title AS workTitle,w.id as workId " +
            "FROM Comment c JOIN c.user u JOIN c.work w")
    Page<CommentSummary> findAllWithUserAndWork(Pageable pageable);
}
