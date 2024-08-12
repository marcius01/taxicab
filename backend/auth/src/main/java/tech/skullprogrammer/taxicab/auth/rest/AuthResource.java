package tech.skullprogrammer.taxicab.auth.rest;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import tech.skullprogrammer.taxicab.auth.model.LoginDTO;
import tech.skullprogrammer.taxicab.auth.model.ResetPasswordDTO;
import tech.skullprogrammer.taxicab.auth.service.AuthService;

@Path("/auth")
public class AuthResource {

    @Inject
    private AuthService authService;

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response auth(@Valid LoginDTO loginDTO) {
        String token = authService.loginToken(loginDTO);
        return Response.ok()
                .header("X-Auth-Token", token)
                .build();
    }

    @Path("/reset")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response reset(@Valid ResetPasswordDTO resetPasswordDTO) {
        authService.resetPassword(resetPasswordDTO);
        return Response.ok().build();
    }



    @Path("/send-otp/{email}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response sendOTP(@PathParam(value = "email") String email, @QueryParam(value = "lang") @DefaultValue("en") String lang) {
        authService.sendOtpEmail(lang, email);
        return Response.ok().build();
    }

}
