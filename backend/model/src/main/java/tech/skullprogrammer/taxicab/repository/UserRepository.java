package tech.skullprogrammer.taxicab.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.taxicab.model.User;

import java.util.Optional;

@Slf4j
@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<User> {

    public User findByUsername(String username) {
        return this.find("username", username).firstResult();
    }
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return find("email = ?1 and  password = ?2", email, password).singleResultOptional();
    }

    public Optional<User> findByEmail(String email) {
        return find("email = ?1", email).singleResultOptional();
    }

}
