package tech.skullprogrammer.taxicab.auth.rest;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tech.skullprogrammer.taxicab.auth.model.LoginDTO;
import tech.skullprogrammer.taxicab.auth.service.AuthService;

@Path("/auth")
public class AuthResource {

    @Inject
    private AuthService authService;

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(@Valid LoginDTO loginDTO) {
        String token = authService.generateToken(loginDTO);
        return Response.ok()
                .header("X-Auth-Token", token)
                .build();
    }

}
