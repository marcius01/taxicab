package tech.skullprogrammer.taxicab.msprofile.rest;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;
import tech.skullprogrammer.core.Constants;
import tech.skullprogrammer.core.model.Pager;
import tech.skullprogrammer.core.model.PagingQueryParams;
import tech.skullprogrammer.taxicab.Roles;
import tech.skullprogrammer.taxicab.model.Profile;
import tech.skullprogrammer.taxicab.msprofile.service.ProfileService;

@Slf4j
@Path("/profile")
public class ProfileResource {

    @Inject
    ProfileService profileService;

    @Inject
    SecurityContext securityContext;
//    @Inject
//    @Claim(value = Constants.CLAIM_USER_ID)
//    String claimUserId;

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(Roles.ADMIN)
    public Pager<Profile> allProfiles(@Valid PagingQueryParams pagingParams) {
        return profileService.allProfiles(pagingParams);
    }

    @Path("/me")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated()
    public Profile me(@Valid PagingQueryParams pagingParams) {
        JsonWebToken jwt = (JsonWebToken) securityContext.getUserPrincipal();
        String userId = jwt.getClaim(Constants.CLAIM_USER_ID);
        return profileService.myProfile(securityContext.getUserPrincipal().getName(), userId);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Roles.ADMIN, Roles.SYSTEM})
    public Profile createProfile(Profile profile) {
        return profileService.createProfile(profile);
    }

    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Roles.SYSTEM})
    public Response checkProfile(Profile profile) {
        profileService.checkProfile(profile);
        return Response.ok().build();
    }

}
