package org.bibliotecaviva.backend.persistance.repository;

import org.bibliotecaviva.backend.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByWorkIdOrderByCreatedAtDesc(UUID workId);
}
