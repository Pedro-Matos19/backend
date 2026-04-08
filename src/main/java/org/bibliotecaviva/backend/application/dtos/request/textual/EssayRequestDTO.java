package org.bibliotecaviva.backend.application.dtos.request.textual;

import jakarta.validation.constraints.*;
import org.bibliotecaviva.backend.application.dtos.request.WorkRequest;

import java.time.LocalDateTime;

/**
 * DTO for {@link org.bibliotecaviva.backend.domain.entities.textual.Essay}
 */
public record EssayRequestDTO(
        @NotBlank(message = "Title cannot be blank") @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
        String title,
        @Email(message = "Author must be a valid email address(can be changed later)") @NotBlank(message = "Author email cannot be blank")
        String author,
        @NotNull(message = "Data cannot be empty") @PastOrPresent(message = "Publication date cannot be in the future")
        LocalDateTime publicationDate,
        @NotBlank(message = "description cannot be blank") @Size(min = 15, message = "Description must be at least 15 characters long")
        String description,
        @NotBlank(message = "Content can not be blank") //Veriicar dps se coloca minimo e maximo de caracteres
        String content,
        //TODO: verificar regras de redação, manter so multiplos de 20, verficair minimo
        @NotNull(message = "Rate can not be null") @Max(value = 1000, message = "Rate must be less than or equal to 1000")
        @Min(value = 0, message = "Rate must be greater than or equal to 0")
        Integer rate,
        @NotBlank(message = "Theme cannot be blank")
        String theme,
        @NotBlank(message = "Theme description cannot be blank")
        String themeDescription,
        @NotBlank(message = "Feedback cannot be blank")
        String feedback
) implements WorkRequest {
}