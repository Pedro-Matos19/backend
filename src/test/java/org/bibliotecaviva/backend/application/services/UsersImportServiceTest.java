package org.bibliotecaviva.backend.application.services;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bibliotecaviva.backend.application.dtos.response.imports.ImportUsersErrorDTO;
import org.bibliotecaviva.backend.application.dtos.response.imports.ImportUsersResponseDTO;
import org.bibliotecaviva.backend.domain.entities.User;
import org.bibliotecaviva.backend.domain.enums.Role;
import org.bibliotecaviva.backend.domain.enums.Status;
import org.bibliotecaviva.backend.domain.exceptions.InvalidImportFileException;
import org.bibliotecaviva.backend.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersImportServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersImportService usersImportService;

    @Test
    void importUsersShouldSaveValidRowsAsPendingStudents() throws Exception {
        MockMultipartFile file = workbookFile(sheet -> {
            row(sheet, 0, "name", "email", "password");
            row(sheet, 1, "Ana Silva", "ana@teste.com", "123456");
            row(sheet, 2, "", "", "");
            row(sheet, 3, "Bruno Lima", "bruno@teste.com", "abcdef");
        });
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> "encoded-" + invocation.getArgument(0));

        ImportUsersResponseDTO response = usersImportService.importUsers(file);

        assertEquals(2, response.totalRows());
        assertEquals(2, response.createdCount());
        assertEquals(0, response.failedCount());
        assertTrue(response.erros().isEmpty());

        List<User> savedUsers = capturedSavedUsers();
        assertEquals(2, savedUsers.size());
        assertImportedUser(savedUsers.get(0), "Ana Silva", "ana@teste.com", "encoded-123456");
        assertImportedUser(savedUsers.get(1), "Bruno Lima", "bruno@teste.com", "encoded-abcdef");
    }

    @Test
    void importUsersShouldReturnRowErrorsAndSaveOnlyValidRows() throws Exception {
        MockMultipartFile file = workbookFile(sheet -> {
            row(sheet, 0, "name", "email", "password");
            row(sheet, 1, "A", "email-invalido", "123");
            row(sheet, 2, "Aluno Valido", "duplicado@teste.com", "123456");
            row(sheet, 3, "Aluno Duplicado", "duplicado@teste.com", "123456");
            row(sheet, 4, "Aluno Existente", "existente@teste.com", "123456");
        });
        when(userRepository.existsByEmail(anyString()))
                .thenAnswer(invocation -> "existente@teste.com".equals(invocation.getArgument(0)));
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> "encoded-" + invocation.getArgument(0));

        ImportUsersResponseDTO response = usersImportService.importUsers(file);

        assertEquals(4, response.totalRows());
        assertEquals(1, response.createdCount());
        assertEquals(3, response.failedCount());

        List<ImportUsersErrorDTO> errors = response.erros();
        assertEquals(2, errors.get(0).row());
        assertEquals("email-invalido", errors.get(0).email());
        assertTrue(errors.get(0).message().contains("Nome deve conter entre 2 e 100 caracteres"));
        assertTrue(errors.get(0).message().contains("Email invalido"));
        assertTrue(errors.get(0).message().contains("Senha deve conter no minimo 6 caracteres"));
        assertEquals(4, errors.get(1).row());
        assertEquals("Email duplicado na planilha", errors.get(1).message());
        assertEquals(5, errors.get(2).row());
        assertEquals("Email ja cadastrado", errors.get(2).message());

        List<User> savedUsers = capturedSavedUsers();
        assertEquals(1, savedUsers.size());
        assertImportedUser(savedUsers.get(0), "Aluno Valido", "duplicado@teste.com", "encoded-123456");
    }

    @Test
    void importUsersShouldRejectEmptyMultipartFile() {
        MockMultipartFile file = new MockMultipartFile("file", "users.xlsx", xlsxContentType(), new byte[0]);

        assertInvalidFileDoesNotSave(file, "Nao foi possivel localizar a planilha.");
    }

    @Test
    void importUsersShouldRejectInvalidExtension() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "users.csv",
                "text/csv",
                "name,email,password".getBytes(StandardCharsets.UTF_8));

        assertInvalidFileDoesNotSave(file, "O arquivo precisa estar no formato .xlsx");
    }

    @Test
    void importUsersShouldRejectEmptySpreadsheet() throws Exception {
        MockMultipartFile file = workbookFile(sheet -> {
        });

        assertInvalidFileDoesNotSave(file, "A planilha esta vazia");
    }

    @Test
    void importUsersShouldRejectSpreadsheetWithoutHeader() throws Exception {
        MockMultipartFile file = workbookFile(sheet -> row(sheet, 1, "Ana Silva", "ana@teste.com", "123456"));

        assertInvalidFileDoesNotSave(file, "A planilha precisa ter cabecalho.");
    }

    @Test
    void importUsersShouldRejectSpreadsheetWithIncompleteHeader() throws Exception {
        MockMultipartFile file = workbookFile(sheet -> {
            row(sheet, 0, "name", "email");
            row(sheet, 1, "Ana Silva", "ana@teste.com", "123456");
        });

        assertInvalidFileDoesNotSave(file, "A planilha precisa ter as colunas: name, email, password");
    }

    @Test
    void importUsersShouldRejectUnreadableFile() {
        MultipartFile file = unreadableFile();

        assertInvalidFileDoesNotSave(file, "Nao foi possivel ler a planilha.");
    }

    private void assertInvalidFileDoesNotSave(MultipartFile file, String expectedMessage) {
        InvalidImportFileException exception = assertThrows(
                InvalidImportFileException.class,
                () -> usersImportService.importUsers(file));

        assertEquals(expectedMessage, exception.getMessage());
        verify(userRepository, never()).saveAll(any());
    }

    private static void assertImportedUser(User user, String name, String email, String password) {
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(Role.ALUNO, user.getRole());
        assertEquals(Status.PENDING, user.getAccountStatus());
        assertNotNull(user.getLikedWorks());
        assertTrue(user.getLikedWorks().isEmpty());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<User> capturedSavedUsers() {
        ArgumentCaptor<Iterable<User>> captor = (ArgumentCaptor) ArgumentCaptor.forClass(Iterable.class);
        verify(userRepository).saveAll(captor.capture());
        return StreamSupport.stream(captor.getValue().spliterator(), false)
                .toList();
    }

    private static MockMultipartFile workbookFile(Consumer<Sheet> sheetConsumer) throws IOException {
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

    private static MultipartFile unreadableFile() {
        return new MultipartFile() {
            @Override
            public String getName() {
                return "file";
            }

            @Override
            public String getOriginalFilename() {
                return "users.xlsx";
            }

            @Override
            public String getContentType() {
                return xlsxContentType();
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 1;
            }

            @Override
            public byte[] getBytes() throws IOException {
                throw new IOException("Unreadable file");
            }

            @Override
            public InputStream getInputStream() throws IOException {
                throw new IOException("Unreadable file");
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                throw new IOException("Unreadable file");
            }
        };
    }

    private static String xlsxContentType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }
}
