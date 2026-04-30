package org.bibliotecaviva.backend.application.dtos.request;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * marker interface for all work requests
 */
@Schema(hidden = true)
public interface WorkRequest {
    //Todo: Colocar verificações de dominio das urls posteriormente
    String title();

    String authorName();

    String authorEmail(); //por enqunato ta mandando email, trocar dps por matricula/cpf

    LocalDateTime publicationDate();

    String description();

    String studentClass();
}
