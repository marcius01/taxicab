package tech.skullprogrammer.taxicab.auth.client;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import tech.skullprogrammer.core.mapping.ClientExceptionMapper;
import tech.skullprogrammer.taxicab.auth.filter.ProfileClientBearerToken;
import tech.skullprogrammer.taxicab.model.Profile;

@Path("profile")
@RegisterRestClient(configKey = "profile-client")
@RegisterProvider(ProfileClientBearerToken.class)
@RegisterProvider(ClientExceptionMapper.class) // Register client mapper for exception
public interface IProfileClient {

    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkProfile(Profile profile);

    @Path("/")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProfile(Profile profile);

}
