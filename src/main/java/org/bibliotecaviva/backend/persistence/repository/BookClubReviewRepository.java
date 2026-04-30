package org.bibliotecaviva.backend.persistence.repository;

import org.bibliotecaviva.backend.application.services.ReviewSummaryResponseDTO;
import org.bibliotecaviva.backend.domain.entities.BookClubReview;
import org.bibliotecaviva.backend.domain.entities.projections.ReviewSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookClubReviewRepository extends JpaRepository<BookClubReview, UUID> {
    Page<BookClubReview> findByBookClubId(UUID bookClubId, Pageable pageable);

    @Query("SELECT r.id AS id, r.content AS content, r.createdAt AS createdAt, r.rating AS rating," +
            "u.name AS userName,u.id as userId, " +
            "b.bookName AS bookClubTitle,b.id as bookClubId " +
            "FROM BookClubReview r JOIN r.user u JOIN r.bookClub b")
    Page<ReviewSummary> findAllWithUserAndBookClub(Pageable pageable);
}
