package org.bibliotecaviva.backend.persistence.repository;

import org.bibliotecaviva.backend.domain.entities.BookClub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface BookClubRepository extends JpaRepository<BookClub, UUID> {
    Optional<BookClub> findFirstByDateAfterOrderByDateAsc(LocalDateTime dateAfter);

    @Query(value = "SELECT COUNT(*) FROM book_club_participants WHERE book_club_id = :id", nativeQuery = true)
    Long countParticipants(UUID id);

    @Query(value = "SELECT coalesce(AVG(reviews.rating),0.0) FROM BookClubReview reviews WHERE reviews.bookClub.id = :id")
    BigDecimal getAverageRating(UUID id);

    @Query("SELECT b, COUNT(DISTINCT p),COALESCE(AVG(reviews.rating),0.0) as rating FROM BookClub b LEFT JOIN b.participants p LEFT JOIN BookClubReview reviews on b = reviews.bookClub GROUP BY b")
    Page<Object[]> findAllWithParticipantCountAndAverageRating(Pageable pageable);

    boolean existsBookClubByDateBetween(LocalDateTime dateAfter, LocalDateTime dateBefore);

    boolean existsBookClubByDateBetweenAndIdNot(LocalDateTime dateAfter, LocalDateTime dateBefore, UUID id);
}