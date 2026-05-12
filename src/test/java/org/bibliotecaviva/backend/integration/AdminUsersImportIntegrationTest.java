package org.bibliotecaviva.backend.integration;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bibliotecaviva.backend.domain.entities.User;
import org.bibliotecaviva.backend.domain.enums.Role;
import org.bibliotecaviva.backend.domain.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminUsersImportIntegrationTest extends IntegrationTestSupport {

    @Test
    void adminShouldImportValidUsersFromSpreadsheet() throws Exception {
        User admin = createActiveAdmin();
        String firstEmail = uniqueEmail("importado");
        String secondEmail = uniqueEmail("importado");
        MockMultipartFile file = importFile(sheet -> {
            row(sheet, 0, "name", "email", "password");
            row(sheet, 1, "Aluno Importado Um", firstEmail, "123456");
            row(sheet, 2, "Aluno Importado Dois", secondEmail, "abcdef");
        });

        mockMvc.perform(multipart("/admin/users/import")
                        .file(file)
                        .header("Authorization", bearer(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRows").value(2))
                .andExpect(jsonPath("$.createdCount").value(2))
                .andExpect(jsonPath("$.failedCount").value(0))
                .andExpect(jsonPath("$.erros.length()").value(0));

        flushAndClear();

        assertImportedUser(firstEmail, "Aluno Importado Um", "123456");
        assertImportedUser(secondEmail, "Aluno Importado Dois", "abcdef");
    }

    @Test
    void adminShouldPartiallyImportSpreadsheetAndReportRowErrors() throws Exception {
        User admin = createActiveAdmin();
        User existingUser = createActiveStudent();
        String importedEmail = uniqueEmail("importado");
        String invalidEmail = uniqueEmail("linha-invalida");
        MockMultipartFile file = importFile(sheet -> {
            row(sheet, 0, "name", "email", "password");
            row(sheet, 1, "Aluno Novo", importedEmail, "123456");
            row(sheet, 2, "Aluno Existente", existingUser.getEmail(), "123456");
            row(sheet, 3, "A", invalidEmail, "123");
        });

        mockMvc.perform(multipart("/admin/users/import")
                        .file(file)
                        .header("Authorization", bearer(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRows").value(3))
                .andExpect(jsonPath("$.createdCount").value(1))
                .andExpect(jsonPath("$.failedCount").value(2))
                .andExpect(jsonPath("$.erros[0].row").value(3))
                .andExpect(jsonPath("$.erros[0].email").value(existingUser.getEmail()))
                .andExpect(jsonPath("$.erros[0].message").value("Email ja cadastrado"))
                .andExpect(jsonPath("$.erros[1].row").value(4))
                .andExpect(jsonPath("$.erros[1].email").value(invalidEmail))
                .andExpect(jsonPath("$.erros[1].message").value(
                        "Nome deve conter entre 2 e 100 caracteres; Senha deve conter no minimo 6 caracteres"));

        flushAndClear();

        assertImportedUser(importedEmail, "Aluno Novo", "123456");
        assertTrue(userRepository.findByEmail(invalidEmail).isEmpty());
    }

    @Test
    void adminShouldReceiveBadRequestWhenSpreadsheetIsInvalid() throws Exception {
        User admin = createActiveAdmin();
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "users.csv",
                "text/csv",
                "name,email,password".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/admin/users/import")
                        .file(file)
                        .header("Authorization", bearer(admin)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("O arquivo precisa estar no formato .xlsx"))
                .andExpect(jsonPath("$.path").value("/admin/users/import"));
    }

    @Test
    void nonAdminShouldNotImportUsers() throws Exception {
        User student = createActiveStudent();
        String importedEmail = uniqueEmail("bloqueado");
        MockMultipartFile file = importFile(sheet -> {
            row(sheet, 0, "name", "email", "password");
            row(sheet, 1, "Aluno Bloqueado", importedEmail, "123456");
        });

        mockMvc.perform(multipart("/admin/users/import")
                        .file(file)
                        .header("Authorization", bearer(student)))
                .andExpect(status().isForbidden());

        flushAndClear();
        assertTrue(userRepository.findByEmail(importedEmail).isEmpty());
    }

    private void assertImportedUser(String email, String name, String rawPassword) {
        User importedUser = userRepository.findByEmail(email).orElseThrow();

        assertEquals(name, importedUser.getName());
        assertEquals(Role.ALUNO, importedUser.getRole());
        assertEquals(Status.PENDING, importedUser.getAccountStatus());
        assertTrue(passwordEncoder.matches(rawPassword, importedUser.getPassword()));
    }

    private static MockMultipartFile importFile(Consumer<Sheet> sheetConsumer) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("users");
            sheetConsumer.accept(sheet);
            workbook.write(outputStream);
            return new MockMultipartFile("file", "users.xlsx", xlsxContentType(), outputStream.toByteArray());
        }
    }

    private static void row(Sheet sheet, int rowIndex, String... values) {
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < values.length; i++) {
            row.createCell(i).setCellValue(values[i]);
        }
    }

    private static String xlsxContentType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }
}
