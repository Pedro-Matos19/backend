package org.bibliotecaviva.backend.persistence.repository;

import org.bibliotecaviva.backend.domain.entities.Comment;
import org.bibliotecaviva.backend.domain.entities.projections.CommentSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findByWorkIdOrderByCreatedAtDesc(UUID workId, Pageable pageable);

    Long countByWork_Id(UUID workId);

    @Query("SELECT c.id AS id, c.content AS content, c.createdAt AS createdAt, " +
            "u.name AS userName,u.id as userId, " +
            "w.title AS workTitle,w.id as workId " +
            "FROM Comment c JOIN c.user u JOIN c.work w")
    Page<CommentSummary> findAllWithUserAndWork(Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO comment_likes (user_id,comment_id) VALUES (:userId, :commentId) ON CONFLICT (user_id, comment_id) DO NOTHING", nativeQuery = true)
    void likeComment(@Param("userId") UUID userId, @Param("commentId") UUID commentId);

    @Modifying
    @Query(value = "DELETE FROM comment_likes WHERE user_id = :userId AND comment_id = :commentId", nativeQuery = true)
    void unlikeComment(@Param("userId") UUID userId, @Param("commentId") UUID commentId);

    @Query(value = "SELECT COUNT(*) FROM comment_likes WHERE comment_id = :commentId", nativeQuery = true)
    long getLikeCount(@Param("commentId") UUID commentId);


}
