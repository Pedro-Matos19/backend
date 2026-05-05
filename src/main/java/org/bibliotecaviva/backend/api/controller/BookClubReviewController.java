package org.bibliotecaviva.backend.api.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bibliotecaviva.backend.application.dtos.request.BookClubReviewRequestDTO;
import org.bibliotecaviva.backend.application.dtos.response.BookClubReviewResponseDTO;
import org.bibliotecaviva.backend.application.services.BookClubReviewService;
import org.bibliotecaviva.backend.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookclub/{bookClubId}/reviews")
@RequiredArgsConstructor
public class BookClubReviewController {

    private final BookClubReviewService bookClubReviewService;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Comment created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "BookClub not found")
    @ApiResponse(responseCode = "401", description = "Unauthorized, user must be authenticated to create a comment")
    public ResponseEntity<BookClubReviewResponseDTO> create(@PathVariable UUID bookClubId, @RequestBody @Valid BookClubReviewRequestDTO dto, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookClubReviewService.create(bookClubId, dto, user));
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully")
    @ApiResponse(responseCode = "404", description = "BookClub not found")
    public ResponseEntity<Page<BookClubReviewResponseDTO>> getByBookClubId(@PathVariable UUID bookClubId, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(bookClubReviewService.getByBookClubId(bookClubId, pageable));
    }

    @PutMapping("/{commentId}")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    public ResponseEntity<BookClubReviewResponseDTO> update(@PathVariable UUID commentId,
                                                            @PathVariable UUID bookClubId,
                                                            @RequestBody @Valid BookClubReviewRequestDTO dto,
                                                            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookClubReviewService.update(commentId,bookClubId, user, dto));
    }

    @DeleteMapping("/{commentId}")
    @ApiResponse(responseCode = "204", description = "Comment deleted")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    public ResponseEntity<Void> delete(@PathVariable UUID commentId,
                                       @PathVariable UUID bookClubId,
                                       @AuthenticationPrincipal User user) {
        bookClubReviewService.delete(commentId,bookClubId, user);
        return ResponseEntity.noContent().build();
    }
}


