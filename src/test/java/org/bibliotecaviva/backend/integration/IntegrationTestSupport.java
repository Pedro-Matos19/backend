package org.bibliotecaviva.backend.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.bibliotecaviva.backend.application.services.JwtService;
import org.bibliotecaviva.backend.domain.entities.Comment;
import org.bibliotecaviva.backend.domain.entities.User;
import org.bibliotecaviva.backend.domain.entities.textual.Article;
import org.bibliotecaviva.backend.domain.enums.Role;
import org.bibliotecaviva.backend.domain.enums.Status;
import org.bibliotecaviva.backend.persistance.repository.CommentRepository;
import org.bibliotecaviva.backend.persistance.repository.UserRepository;
import org.bibliotecaviva.backend.persistance.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
abstract class IntegrationTestSupport {

    protected static final String RAW_PASSWORD = "123456";

    @Autowired
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected WorkRepository workRepository;

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected EntityManager entityManager;

    protected User createActiveAdmin() {
        return createUser("Admin", uniqueEmail("admin"), Role.ADMIN, Status.ACTIVE);
    }

    protected User createActiveCurator() {
        return createUser("Curador", uniqueEmail("curador"), Role.CURADOR, Status.ACTIVE);
    }

    protected User createActiveStudent() {
        return createUser("Aluno", uniqueEmail("aluno"), Role.ALUNO, Status.ACTIVE);
    }

    protected User createPendingStudent() {
        return createUser("Aluno pendente", uniqueEmail("pendente"), Role.ALUNO, Status.PENDING);
    }

    protected User createUser(String name, String email, Role role, Status status) {
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(RAW_PASSWORD))
                .role(role)
                .accountStatus(status)
                .likedWorks(new HashSet<>())
                .build();
        return userRepository.saveAndFlush(user);
    }

    protected Article createArticleInDatabase(User author) {
        return createArticleInDatabase(author, uniqueTitle("Artigo"));
    }

    protected Article createArticleInDatabase(User author, String title) {
        Article article = Article.builder()
                .title(title)
                .author(author)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .description("Descricao valida para teste")
                .content("Conteudo do artigo")
                .viewCount(0L)
                .build();
        return workRepository.saveAndFlush(article);
    }

    protected Comment createCommentInDatabase(User user, Article work, String content) {
        Comment comment = Comment.builder()
                .content(content)
                .user(user)
                .work(work)
                .build();
        return commentRepository.saveAndFlush(comment);
    }

    protected String bearer(User user) {
        return "Bearer " + jwtService.generateToken(user);
    }

    protected String json(Object value) throws Exception {
        return objectMapper.writeValueAsString(value);
    }

    protected JsonNode jsonFrom(MvcResult result) throws Exception {
        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    protected String uniqueEmail(String prefix) {
        return prefix + "-" + UUID.randomUUID() + "@teste.com";
    }

    protected String uniqueTitle(String prefix) {
        return prefix + " " + UUID.randomUUID();
    }

    protected Map<String, Object> baseWorkPayload(String title, String authorEmail) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("title", title);
        payload.put("author", authorEmail);
        payload.put("publicationDate", LocalDateTime.now().minusDays(1).toString());
        payload.put("description", "Descricao valida para teste");
        return payload;
    }

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
