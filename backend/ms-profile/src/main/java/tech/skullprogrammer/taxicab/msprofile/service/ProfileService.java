package tech.skullprogrammer.taxicab.msprofile.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import tech.skullprogrammer.core.exception.GenericErrorPayload;
import tech.skullprogrammer.core.exception.SkullResourceException;
import tech.skullprogrammer.core.model.Pager;
import tech.skullprogrammer.core.model.PagingQueryParams;
import tech.skullprogrammer.core.utility.Utility;
import tech.skullprogrammer.taxicab.exception.SkullErrorImpl;
import tech.skullprogrammer.taxicab.model.Profile;
import tech.skullprogrammer.taxicab.model.customer.Wallet;
import tech.skullprogrammer.taxicab.msprofile.model.ProfileConfig;
import tech.skullprogrammer.taxicab.repository.ProfileRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@ApplicationScoped
public class ProfileService {

    @Inject
    ProfileRepository profileRepo;
    @Inject
    ProfileConfig config;

    public Pager<Profile> allProfiles(PagingQueryParams pagingQueryParams) {
        return profileRepo.findAll(pagingQueryParams);
    }

    public Profile myProfile(String name, String userIdString) {
        log.debug("{} ask for profile od userId {}", name, userIdString);
        ObjectId userId = Utility.verifyObjectId(userIdString);
        Optional<Profile> profile = profileRepo.findByUserId(userId);
        return profile.orElseThrow(() -> SkullResourceException.builder().error(SkullErrorImpl.NO_PROFILE).build());
    }

    public Profile createProfile(Profile profile) {
        checkProfileForCreation(profile);
        profileRepo.persist(profile);
        return profile;
    }

    private void checkProfileForCreation(Profile profile) {
        Map<String, String> errors = new HashMap<>();
        if (profile.getId() != null) errors.put("id", "id must be empty");
        if (ChronoUnit.YEARS.between(profile.getBirth(),LocalDate.now())< config.minYears()) errors.put("years", "younger than required age - " + config.minYears());
        checkWallet(profile.getWallet(), errors);
        manageErrors(errors);
    }

    private void checkWallet(Wallet wallet, Map<String, String> errors) {
        //TODO implements Wallet validation if necessary
        log.debug("No Wallet validation at this very moment");
    }

    private void manageErrors(Map<String, String> errors) {
        if (errors.isEmpty()) return;
        GenericErrorPayload.Builder payloadBuilder = GenericErrorPayload.builder();
        errors.forEach(payloadBuilder::putInfo);
        throw SkullResourceException.builder().error(SkullErrorImpl.NO_VALID_PROFILE).payload(payloadBuilder.build()).build();
    }

}
