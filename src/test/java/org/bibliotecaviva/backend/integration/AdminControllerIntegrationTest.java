package org.bibliotecaviva.backend.integration;

import org.bibliotecaviva.backend.domain.entities.User;
import org.bibliotecaviva.backend.domain.entities.textual.Article;
import org.bibliotecaviva.backend.domain.enums.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerIntegrationTest extends IntegrationTestSupport {

    @Test
    void adminShouldListUsersFilteredByStatus() throws Exception {
        User admin = createActiveAdmin();
        User pending = createPendingStudent();

        mockMvc.perform(get("/admin")
                        .header("Authorization", bearer(admin))
                        .queryParam("status", "PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(pending.getId().toString()))
                .andExpect(jsonPath("$.content[0].email").value(pending.getEmail()));
    }

    @Test
    void adminShouldApproveRejectAndBlockUsers() throws Exception {
        User admin = createActiveAdmin();
        String authorization = bearer(admin);
        User approveTarget = createPendingStudent();
        User rejectTarget = createPendingStudent();
        User blockTarget = createActiveStudent();

        mockMvc.perform(patch("/admin/approve/{id}", approveTarget.getId())
                        .header("Authorization", authorization))
                .andExpect(status().isNoContent());
        flushAndClear();
        assertEquals(Status.ACTIVE, userRepository.findById(approveTarget.getId()).orElseThrow().getAccountStatus());

        mockMvc.perform(patch("/admin/reject/{id}", rejectTarget.getId())
                        .header("Authorization", authorization))
                .andExpect(status().isNoContent());
        flushAndClear();
        assertEquals(Status.REJECTED, userRepository.findById(rejectTarget.getId()).orElseThrow().getAccountStatus());

        mockMvc.perform(patch("/admin/block/{id}", blockTarget.getId())
                        .header("Authorization", authorization))
                .andExpect(status().isNoContent());
        flushAndClear();
        assertEquals(Status.BLOCKED, userRepository.findById(blockTarget.getId()).orElseThrow().getAccountStatus());
    }

    @Test
    void adminDashboardShouldReturnCurrentCounters() throws Exception {
        User admin = createActiveAdmin();
        createPendingStudent();
        User curator = createActiveCurator();
        User student = createActiveStudent();
        Article work = createArticleInDatabase(curator);
        createCommentInDatabase(student, work, "Comentario");

        mockMvc.perform(get("/admin/dashboard")
                        .header("Authorization", bearer(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPosts").value(1))
                .andExpect(jsonPath("$.totalComments").value(1))
                .andExpect(jsonPath("$.totalUsers").value(4))
                .andExpect(jsonPath("$.pendingUsers").value(1));
    }

    @Test
    void nonAdminShouldNotAccessAdminEndpoints() throws Exception {
        User student = createActiveStudent();

        mockMvc.perform(get("/admin")
                        .header("Authorization", bearer(student)))
                .andExpect(status().isForbidden());
    }
}
