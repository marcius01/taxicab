package tech.skullprogrammer.taxicab.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.taxicab.model.User;

import java.util.Optional;

@Slf4j
@ApplicationScoped
public class RepositoryUser implements PanacheMongoRepository<User> {

    public User findByUsername(String username) {
        return this.find("username", username).firstResult();
    }
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        log.info("username: {} -- password: {}", username, password);
        return find("username = ?1 and  password = ?2", username, password).singleResultOptional();
    }

//    public Optional<User> findByUsernameAndPassword(String username, String password) {
//        return find("{ 'username': ?1, 'password': ?2 }", username, password).singleResultOptional();
//    }

}
