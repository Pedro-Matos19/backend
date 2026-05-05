package org.bibliotecaviva.backend.application.services;

import org.bibliotecaviva.backend.application.dtos.request.BookClubReviewRequestDTO;
import org.bibliotecaviva.backend.application.dtos.response.BookClubReviewResponseDTO;
import org.bibliotecaviva.backend.application.dtos.response.ReviewSummaryResponseDTO;
import org.bibliotecaviva.backend.domain.entities.BookClub;
import org.bibliotecaviva.backend.domain.entities.BookClubReview;
import org.bibliotecaviva.backend.domain.entities.User;
import org.bibliotecaviva.backend.domain.entities.projections.ReviewSummary;
import org.bibliotecaviva.backend.domain.enums.Role;
import org.bibliotecaviva.backend.domain.enums.Status;
import org.bibliotecaviva.backend.domain.exceptions.CommentNotFoundException;
import org.bibliotecaviva.backend.persistence.repository.BookClubRepository;
import org.bibliotecaviva.backend.persistence.repository.BookClubReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookClubReviewServiceTest {

    @Mock
    private BookClubReviewRepository reviewRepository;

    @Mock
    private BookClubRepository bookClubRepository;

    @InjectMocks
    private BookClubReviewService reviewService;

    @Test
    void createShouldPersistReviewForExistingBookClub() {
        UUID bookClubId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        User user = buildUser(UUID.randomUUID(), Role.ALUNO);
        BookClub bookClub = buildBookClub(bookClubId);
        BookClubReviewRequestDTO request = new BookClubReviewRequestDTO("Gostei bastante", BigDecimal.valueOf(4.5));
        LocalDateTime createdAt = LocalDateTime.now();

        when(bookClubRepository.findById(bookClubId)).thenReturn(Optional.of(bookClub));
        when(reviewRepository.save(any(BookClubReview.class))).thenAnswer(invocation -> {
            BookClubReview review = invocation.getArgument(0);
            review.setId(reviewId);
            review.setCreatedAt(createdAt);
            return review;
        });

        BookClubReviewResponseDTO response = reviewService.create(bookClubId, request, user);

        assertEquals(reviewId, response.id());
        assertEquals(request.content(), response.content());
        assertEquals(user.getName(), response.authorName());
        assertEquals(createdAt, response.createdAt());
        assertEquals(request.rating(), response.rating());

        ArgumentCaptor<BookClubReview> captor = ArgumentCaptor.forClass(BookClubReview.class);
        verify(reviewRepository).save(captor.capture());
        assertEquals(request.content(), captor.getValue().getContent());
        assertEquals(request.rating(), captor.getValue().getRating());
        assertSame(bookClub, captor.getValue().getBookClub());
        assertSame(user, captor.getValue().getUser());
    }

    @Test
    void createShouldFailWhenBookClubDoesNotExist() {
        UUID bookClubId = UUID.randomUUID();
        User user = buildUser(UUID.randomUUID(), Role.ALUNO);
        BookClubReviewRequestDTO request = new BookClubReviewRequestDTO("Gostei bastante", BigDecimal.valueOf(4));
        when(bookClubRepository.findById(bookClubId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reviewService.create(bookClubId, request, user));

        verify(reviewRepository, never()).save(any());
    }

    @Test
    void getByBookClubIdShouldMapReviews() {
        UUID bookClubId = UUID.randomUUID();
        User user = buildUser(UUID.randomUUID(), Role.ALUNO);
        BookClub bookClub = buildBookClub(bookClubId);
        BookClubReview review = buildReview(UUID.randomUUID(), user, bookClub, "Legal", BigDecimal.valueOf(5));
        Pageable pageable = PageRequest.of(0, 10);
        when(reviewRepository.findByBookClubId(bookClubId, pageable)).thenReturn(new PageImpl<>(List.of(review)));

        Page<BookClubReviewResponseDTO> response = reviewService.getByBookClubId(bookClubId, pageable);

        assertEquals(1, response.getTotalElements());
        assertEquals(review.getId(), response.getContent().getFirst().id());
        assertEquals("Legal", response.getContent().getFirst().content());
        assertEquals(BigDecimal.valueOf(5), response.getContent().getFirst().rating());
    }

    @Test
    void getAllShouldMapReviewSummaries() {
        UUID reviewId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID bookClubId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);
        ReviewSummary summary = mock(ReviewSummary.class);

        when(summary.getId()).thenReturn(reviewId);
        when(summary.getContent()).thenReturn("Resumo");
        when(summary.getCreatedAt()).thenReturn(createdAt);
        when(summary.getRating()).thenReturn(BigDecimal.valueOf(4));
        when(summary.getUserName()).thenReturn("Aluno");
        when(summary.getUserId()).thenReturn(userId.toString());
        when(summary.getBookClubTitle()).thenReturn("Dom Casmurro");
        when(summary.getBookClubId()).thenReturn(bookClubId.toString());
        when(reviewRepository.findAllWithUserAndBookClub(pageable)).thenReturn(new PageImpl<>(List.of(summary)));

        Page<ReviewSummaryResponseDTO> response = reviewService.getAll(pageable);

        assertEquals(1, response.getTotalElements());
        ReviewSummaryResponseDTO dto = response.getContent().getFirst();
        assertEquals(reviewId, dto.id());
        assertEquals("Resumo", dto.content());
        assertEquals(createdAt, dto.createdAt());
        assertEquals(BigDecimal.valueOf(4), dto.rating());
        assertEquals("Aluno", dto.userName());
        assertEquals(userId.toString(), dto.userId());
        assertEquals("Dom Casmurro", dto.bookClubTitle());
        assertEquals(bookClubId.toString(), dto.bookClubId());
    }

    @Test
    void updateShouldAllowOwner() {
        UUID bookClubId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        User owner = buildUser(UUID.randomUUID(), Role.ALUNO);
        BookClubReview review = buildReview(reviewId, owner, buildBookClub(bookClubId), "Antigo", BigDecimal.valueOf(3));
        BookClubReviewRequestDTO request = new BookClubReviewRequestDTO("Atualizado", BigDecimal.valueOf(5));

        when(reviewRepository.findByIdAndBookClub_Id(reviewId, bookClubId)).thenReturn(Optional.of(review));
        when(reviewRepository.save(review)).thenReturn(review);

        BookClubReviewResponseDTO response = reviewService.update(reviewId, bookClubId, owner, request);

        assertEquals("Atualizado", review.getContent());
        assertEquals(BigDecimal.valueOf(5), review.getRating());
        assertEquals("Atualizado", response.content());
    }

    @Test
    void updateShouldFailWhenUserIsNotOwnerOrAdmin() {
        UUID bookClubId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        User owner = buildUser(UUID.randomUUID(), Role.ALUNO);
        User other = buildUser(UUID.randomUUID(), Role.ALUNO);
        BookClubReview review = buildReview(reviewId, owner, buildBookClub(bookClubId), "Antigo", BigDecimal.valueOf(3));
        BookClubReviewRequestDTO request = new BookClubReviewRequestDTO("Atualizado", BigDecimal.valueOf(5));

        when(reviewRepository.findByIdAndBookClub_Id(reviewId, bookClubId)).thenReturn(Optional.of(review));

        assertThrows(AccessDeniedException.class, () -> reviewService.update(reviewId, bookClubId, other, request));

        verify(reviewRepository, never()).save(any());
    }

    @Test
    void updateShouldFailWhenReviewDoesNotExist() {
        UUID bookClubId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        User user = buildUser(UUID.randomUUID(), Role.ALUNO);
        BookClubReviewRequestDTO request = new BookClubReviewRequestDTO("Atualizado", BigDecimal.valueOf(5));
        when(reviewRepository.findByIdAndBookClub_Id(reviewId, bookClubId)).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> reviewService.update(reviewId, bookClubId, user, request));
    }

    @Test
    void deleteShouldAllowAdmin() {
        UUID bookClubId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        User owner = buildUser(UUID.randomUUID(), Role.ALUNO);
        User admin = buildUser(UUID.randomUUID(), Role.ADMIN);
        BookClubReview review = buildReview(reviewId, owner, buildBookClub(bookClubId), "Antigo", BigDecimal.valueOf(3));

        when(reviewRepository.findByIdAndBookClub_Id(reviewId, bookClubId)).thenReturn(Optional.of(review));

        reviewService.delete(reviewId, bookClubId, admin);

        verify(reviewRepository).delete(review);
    }

    @Test
    void deleteShouldFailWhenUserIsNotOwnerOrAdmin() {
        UUID bookClubId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        User owner = buildUser(UUID.randomUUID(), Role.ALUNO);
        User other = buildUser(UUID.randomUUID(), Role.ALUNO);
        BookClubReview review = buildReview(reviewId, owner, buildBookClub(bookClubId), "Antigo", BigDecimal.valueOf(3));

        when(reviewRepository.findByIdAndBookClub_Id(reviewId, bookClubId)).thenReturn(Optional.of(review));

        assertThrows(AccessDeniedException.class, () -> reviewService.delete(reviewId, bookClubId, other));

        verify(reviewRepository, never()).delete(any());
    }

    @Test
    void countReviewsShouldDelegateToRepository() {
        when(reviewRepository.count()).thenReturn(7L);

        assertEquals(7L, reviewService.countReviews());
    }

    private static BookClub buildBookClub(UUID id) {
        return BookClub.builder()
                .id(id)
                .bookName("Dom Casmurro")
                .date(LocalDateTime.now().plusDays(10))
                .build();
    }

    private static BookClubReview buildReview(UUID id, User user, BookClub bookClub, String content, BigDecimal rating) {
        return BookClubReview.builder()
                .id(id)
                .content(content)
                .rating(rating)
                .createdAt(LocalDateTime.now())
                .user(user)
                .bookClub(bookClub)
                .build();
    }

    private static User buildUser(UUID id, Role role) {
        return User.builder()
                .id(id)
                .name(role == Role.ADMIN ? "Admin" : "Aluno")
                .email(id + "@teste.com")
                .password("123456")
                .role(role)
                .accountStatus(Status.ACTIVE)
                .build();
    }
}
