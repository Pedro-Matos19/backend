package org.bibliotecaviva.backend.integration;

import com.fasterxml.jackson.databind.JsonNode;
import org.bibliotecaviva.backend.domain.entities.BookClub;
import org.bibliotecaviva.backend.domain.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookClubReviewControllerIntegrationTest extends IntegrationTestSupport {

    @Test
    void shouldCreateListUpdateDenyThirdPartyAndDeleteReview() throws Exception {
        User curator = createActiveCurator();
        User owner = createActiveStudent();
        User thirdParty = createActiveStudent();
        User admin = createActiveAdmin();
        BookClub bookClub = createBookClubInDatabase(curator, LocalDateTime.now().plusMonths(2));

        JsonNode createResponse = jsonFrom(mockMvc.perform(post("/bookclub/{bookClubId}/reviews", bookClub.getId())
                        .header("Authorization", bearer(owner))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "content", "Gostei bastante",
                                "rating", 4.5
                        ))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("Gostei bastante"))
                .andExpect(jsonPath("$.authorName").value(owner.getName()))
                .andExpect(jsonPath("$.rating").value(4.5))
                .andReturn());
        UUID reviewId = UUID.fromString(createResponse.get("id").asText());

        mockMvc.perform(get("/bookclub/{bookClubId}/reviews", bookClub.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(reviewId.toString()))
                .andExpect(jsonPath("$.content[0].content").value("Gostei bastante"))
                .andExpect(jsonPath("$.content[0].authorName").value(owner.getName()))
                .andExpect(jsonPath("$.content[0].rating").value(4.5));

        mockMvc.perform(put("/bookclub/{bookClubId}/reviews/{reviewId}", bookClub.getId(), reviewId)
                        .header("Authorization", bearer(owner))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "content", "Atualizado",
                                "rating", 5
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reviewId.toString()))
                .andExpect(jsonPath("$.content").value("Atualizado"))
                .andExpect(jsonPath("$.rating").value(5));

        mockMvc.perform(put("/bookclub/{bookClubId}/reviews/{reviewId}", bookClub.getId(), reviewId)
                        .header("Authorization", bearer(thirdParty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "content", "Tentativa de terceiro",
                                "rating", 2
                        ))))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403));

        mockMvc.perform(delete("/bookclub/{bookClubId}/reviews/{reviewId}", bookClub.getId(), reviewId)
                        .header("Authorization", bearer(admin)))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/bookclub/{bookClubId}/reviews/{reviewId}", bookClub.getId(), reviewId)
                        .header("Authorization", bearer(admin)))
                .andExpect(status().isNotFound());
    }

    @Test
    void createShouldReturnBadRequestForInvalidRating() throws Exception {
        User curator = createActiveCurator();
        User student = createActiveStudent();
        BookClub bookClub = createBookClubInDatabase(curator, LocalDateTime.now().plusMonths(2));

        mockMvc.perform(post("/bookclub/{bookClubId}/reviews", bookClub.getId())
                        .header("Authorization", bearer(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "content", "Gostei bastante",
                                "rating", 6
                        ))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.invalidFields").isArray());
    }

    @Test
    void anonymousUserShouldNotCreateReview() throws Exception {
        User curator = createActiveCurator();
        BookClub bookClub = createBookClubInDatabase(curator, LocalDateTime.now().plusMonths(2));

        mockMvc.perform(post("/bookclub/{bookClubId}/reviews", bookClub.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of(
                                "content", "Gostei bastante",
                                "rating", 4
                        ))))
                .andExpect(status().isForbidden());
    }

    @Test
    void listShouldExposePersistedReviewsWithoutAuthentication() throws Exception {
        User curator = createActiveCurator();
        User student = createActiveStudent();
        BookClub bookClub = createBookClubInDatabase(curator, LocalDateTime.now().plusMonths(2));
        createBookClubReviewInDatabase(student, bookClub, "Comentario salvo", BigDecimal.valueOf(4));

        mockMvc.perform(get("/bookclub/{bookClubId}/reviews", bookClub.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].content").value("Comentario salvo"))
                .andExpect(jsonPath("$.content[0].authorName").value(student.getName()))
                .andExpect(jsonPath("$.content[0].rating").value(4));
    }
}
