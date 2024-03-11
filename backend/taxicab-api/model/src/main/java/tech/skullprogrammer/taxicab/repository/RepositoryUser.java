package tech.skullprogrammer.taxicab.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.skullprogrammer.taxicab.model.User;

@ApplicationScoped
public class RepositoryUser implements PanacheMongoRepository<User> {

    public User findByUsername(String username) {
        return this.find("username", username).firstResult();
    }

}
