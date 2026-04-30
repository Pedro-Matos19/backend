package org.bibliotecaviva.backend.application.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bibliotecaviva.backend.application.dtos.request.BookClubReviewRequestDTO;
import org.bibliotecaviva.backend.application.dtos.response.BookClubReviewResponseDTO;
import org.bibliotecaviva.backend.domain.entities.BookClubReview;
import org.bibliotecaviva.backend.domain.entities.User;
import org.bibliotecaviva.backend.domain.enums.Role;
import org.bibliotecaviva.backend.domain.exceptions.CommentNotFoundException;
import org.bibliotecaviva.backend.persistence.repository.BookClubRepository;
import org.bibliotecaviva.backend.persistence.repository.BookClubReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookClubReviewService {

    private final BookClubReviewRepository reviewRepository;
    private final BookClubRepository bookClubRepository;

    public BookClubReviewResponseDTO create(UUID bookClubId, @Valid BookClubReviewRequestDTO dto, User user) {
        var toSave = toEntity(dto);
        toSave.setBookClub(bookClubRepository.findById(bookClubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found")));
        toSave.setUser(user);
        return toDTO(reviewRepository.save(toSave));
    }

    public Page<BookClubReviewResponseDTO> getByBookClubId(UUID bookClubId, Pageable pageable) {

        return reviewRepository.findByBookClubId(bookClubId, pageable)
                .map(this::toDTO);
    }

    public BookClubReviewResponseDTO update(UUID reviewId, User user, @Valid BookClubReviewRequestDTO dto) {
        var review = reviewRepository.findById(reviewId).orElseThrow(() -> new CommentNotFoundException("Comentário com id " + reviewId + " não encontrado"));

        if (isOwnerOrAdmin(user, review)) {
            review.setContent(dto.content());
            review.setRating(dto.rating());
            return toDTO(reviewRepository.save(review)); //todo: talvez desnecessario mas pode por um updated at
        } else {
            throw new AccessDeniedException("Você não pode editar este comentário");
        }
    }

    public void delete(UUID commentId, User user) {
        var review = reviewRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comentário com id " + commentId + " não encontrado"));
        if (isOwnerOrAdmin(user, review)) {
            reviewRepository.delete(review);
        } else {
            throw new AccessDeniedException("Você não pode deletar este comentário");
        }
    }

    private BookClubReview toEntity(BookClubReviewRequestDTO dto) {
        return BookClubReview.builder()
                .content(dto.content())
                .rating(dto.rating())
                .build();

    }

    private BookClubReviewResponseDTO toDTO(BookClubReview entity) {
        return new BookClubReviewResponseDTO(
                entity.getId(),
                entity.getContent(),
                entity.getUser().getName(),
                entity.getCreatedAt(),
                entity.getRating()
        );
    }

    private static boolean isOwnerOrAdmin(User user, BookClubReview review) {
        return review.getUser().getId().equals(user.getId()) || user.getRole() == Role.ADMIN;
    }

    public Page<ReviewSummaryResponseDTO> getAll(Pageable pageable) {
        return reviewRepository.findAllWithUserAndBookClub(pageable).map(c ->
                new ReviewSummaryResponseDTO(
                        c.getId(),
                        c.getContent(),
                        c.getCreatedAt(),
                        c.getRating(),
                        c.getUserName(),
                        c.getUserId(),
                        c.getBookClubTitle(),
                        c.getBookClubId()   )
        );
    }

    public Long countReviews() {
        return reviewRepository.count();
    }
}
