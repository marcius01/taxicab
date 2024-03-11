package tech.skullprogrammer.taxicab.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tech.skullprogrammer.taxicab.model.User;

@Slf4j
@QuarkusTest
public class RepositoryUserTest {
    @Inject
    private RepositoryUser repositoryUser;

    @Test
    public void testUserRepo () {
        User user = this.repositoryUser.findByUsername("test@skullprogrammer.tech");
        log.info("User: " + user);
    }
}
