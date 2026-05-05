package org.bibliotecaviva.backend.domain.entities.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ReviewSummary {

    UUID getId();

    String getContent();

    BigDecimal getRating();

    String getUserName();

    String getUserId();


    String getBookClubTitle();

    String getBookClubId();

    LocalDateTime getCreatedAt();

}
