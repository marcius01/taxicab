package tech.skullprogrammer.taxicab.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;
import tech.skullprogrammer.taxicab.model.Driver;

@ApplicationScoped
public class DriverRepository implements PanacheMongoRepository<Driver> {

    public Driver findByUsername(ObjectId userId) {
        return this.find("userId", userId).firstResult();
    }

}
