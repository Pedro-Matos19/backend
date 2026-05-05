package org.bibliotecaviva.backend.integration;

import com.fasterxml.jackson.databind.JsonNode;
import org.bibliotecaviva.backend.domain.entities.User;
import org.bibliotecaviva.backend.domain.entities.textual.Article;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerIntegrationTest extends IntegrationTestSupport {

    @Test
    void shouldCreateListUpdateDenyThirdPartyAndDeleteComment() throws Exception {
        User curator = createActiveCurator();
        User owner = createActiveStudent();
        User thirdParty = createActiveStudent();
        User admin = createActiveAdmin();
        Article work = createArticleInDatabase(curator);

        JsonNode createResponse = jsonFrom(mockMvc.perform(post("/work/{workId}/comments", work.getId())
                        .header("Authorization", bearer(owner))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("content", "Comentario inicial"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value("Comentario inicial"))
                .andExpect(jsonPath("$.authorName").value(owner.getName()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andReturn());
        UUID commentId = UUID.fromString(createResponse.get("id").asText());

        mockMvc.perform(get("/work/{workId}/comments", work.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(commentId.toString()))
                .andExpect(jsonPath("$.content[0].content").value("Comentario inicial"))
                .andExpect(jsonPath("$.content[0].authorName").value(owner.getName()));

        mockMvc.perform(put("/work/{workId}/comments/{commentId}", work.getId(), commentId)
                        .header("Authorization", bearer(owner))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("content", "Comentario atualizado"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentId.toString()))
                .andExpect(jsonPath("$.content").value("Comentario atualizado"));

        mockMvc.perform(put("/work/{workId}/comments/{commentId}", work.getId(), commentId)
                        .header("Authorization", bearer(thirdParty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("content", "Tentativa de terceiro"))))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403));

        mockMvc.perform(delete("/work/{workId}/comments/{commentId}", work.getId(), commentId)
                        .header("Authorization", bearer(admin)))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/work/{workId}/comments/{commentId}", work.getId(), commentId)
                        .header("Authorization", bearer(admin)))
                .andExpect(status().isNotFound());
    }

    @Test
    void createShouldReturnNotFoundWhenWorkDoesNotExist() throws Exception {
        User student = createActiveStudent();

        mockMvc.perform(post("/work/{workId}/comments", UUID.randomUUID())
                        .header("Authorization", bearer(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("content", "Comentario"))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
