package org.bibliotecaviva.backend.application.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CommentRequestDTO(
        @NotBlank(message = "O conteúdo do comentário não pode ser vazio")
        @Size(max = 200, message = "O comentário deve ter no máximo 200 caracteres")
        String content
) {
}
