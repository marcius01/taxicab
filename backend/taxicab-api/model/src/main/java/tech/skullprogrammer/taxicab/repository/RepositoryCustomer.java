package tech.skullprogrammer.taxicab.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;
import tech.skullprogrammer.taxicab.model.customer.Customer;

@ApplicationScoped
public class RepositoryCustomer implements PanacheMongoRepository<Customer> {

    public Customer findByUsername(ObjectId userId) {
        return this.find("userId", userId).firstResult();
    }

}
