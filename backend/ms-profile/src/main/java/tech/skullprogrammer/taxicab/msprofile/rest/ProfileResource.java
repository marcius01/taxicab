package tech.skullprogrammer.taxicab.msprofile.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/profile")
public class ProfileResource {

//    @Inject
//    private ProfileService profileService;

    @Inject
    private SecurityContext securityContext;

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response allProfiles() {
        log.info("Subject: {}", securityContext.getUserPrincipal().getName());
        return Response.ok()
                .build();
    }

}
