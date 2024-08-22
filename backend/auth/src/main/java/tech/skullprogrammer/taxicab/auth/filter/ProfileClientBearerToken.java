package tech.skullprogrammer.taxicab.auth.filter;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import tech.skullprogrammer.taxicab.auth.security.JWTUtility;

import java.io.IOException;

@Provider
public class ProfileClientBearerToken implements ClientRequestFilter {

    @Inject
    JWTUtility jwtUtility;

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        clientRequestContext.getHeaders().add("Authorization", "Bearer " + jwtUtility.generateJWTSystemUser());
    }
}
