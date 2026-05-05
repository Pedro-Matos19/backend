package org.bibliotecaviva.backend.integration;

import org.bibliotecaviva.backend.domain.entities.User;
import org.bibliotecaviva.backend.domain.entities.textual.Article;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorkLikeIntegrationTest extends IntegrationTestSupport {

    @Test
    void authenticatedUserShouldLikeListAndUnlikeWork() throws Exception {
        User curator = createActiveCurator();
        User student = createActiveStudent();
        Article work = createArticleInDatabase(curator);
        String authorization = bearer(student);

        mockMvc.perform(put("/work/{id}/like", work.getId())
                        .header("Authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.liked").value(true))
                .andExpect(jsonPath("$.likeCount").value(1));

        mockMvc.perform(get("/work/liked")
                        .header("Authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(work.getId().toString()));

        mockMvc.perform(delete("/work/{id}/like", work.getId())
                        .header("Authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.liked").value(false))
                .andExpect(jsonPath("$.likeCount").value(0));
    }

    @Test
    void anonymousUserShouldNotLikeOrUnlikeWork() throws Exception {
        User curator = createActiveCurator();
        Article work = createArticleInDatabase(curator);

        mockMvc.perform(put("/work/{id}/like", work.getId()))
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/work/{id}/like", work.getId()))
                .andExpect(status().isForbidden());
    }
}
