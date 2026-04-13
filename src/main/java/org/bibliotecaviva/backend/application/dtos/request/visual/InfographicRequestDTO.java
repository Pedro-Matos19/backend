package org.bibliotecaviva.backend.application.dtos.request.visual;

import jakarta.validation.constraints.*;
import org.bibliotecaviva.backend.application.dtos.request.WorkRequest;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

/**
 * DTO for {@link org.bibliotecaviva.backend.domain.entities.visual.Infographic}
 */
public record InfographicRequestDTO(
        @NotBlank(message = "Title cannot be blank") @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
        String title,
        @Email(message = "Author must be a valid email address(can be changed later)") @NotBlank(message = "Author email cannot be blank")
        String author,
        @NotNull(message = "Data cannot be empty") @PastOrPresent(message = "Publication date cannot be in the future")
        LocalDateTime publicationDate,
        @NotBlank(message = "description cannot be blank") @Size(min = 15, message = "Description must be at least 15 characters long")
        String description,
        @URL @NotBlank(message = "URL cannot be blank") //Colocar um pattern no url para dominios especificos
        String url
) implements WorkRequest {
}