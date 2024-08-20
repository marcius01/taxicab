package tech.skullprogrammer.taxicab.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;
import tech.skullprogrammer.core.model.Pager;
import tech.skullprogrammer.core.model.PagingQueryParams;
import tech.skullprogrammer.taxicab.model.Profile;
import tech.skullprogrammer.taxicab.model.customer.Customer;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProfileRepository implements PanacheMongoRepository<Profile> {

    public Optional<Profile> findByUserId(ObjectId userId) {
        return this.find("userId", userId).firstResultOptional();
    }

    public Pager<Profile> findAll(PagingQueryParams paging) {
        PanacheQuery<Profile> query = this.findAll();
        long totalCount = query.count();
        List<Profile> profiles = query.page(paging.toPage()).list();
        return Pager.<Profile>builder()
                .result(profiles)
                .page(paging.getPage())
                .size(paging.getSize())
                .total(totalCount)
                .build();
    }
}
