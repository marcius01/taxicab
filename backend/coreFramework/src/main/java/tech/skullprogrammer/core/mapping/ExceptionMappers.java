package tech.skullprogrammer.core.mapping;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.skullprogrammer.core.exception.GenericErrorPayload;
import tech.skullprogrammer.core.exception.SkullResourceException;
import tech.skullprogrammer.core.model.ErrorResponseDTO;

@Provider
@RegisterForReflection
public class ExceptionMappers {
    private static final Logger log = LoggerFactory.getLogger(ExceptionMappers.class);

    public ExceptionMappers() {
    }

    @ServerExceptionMapper
    public Response skullResourceException(SkullResourceException exception) {
        log.debug("SkullException", exception);
        ErrorResponseDTO response = ErrorResponseDTO.builder().code(exception.getErrorCode()).payload(exception.getPayload()).build();
        return Response.status(exception.getStatus()).entity(response).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
    @ServerExceptionMapper
    public Response notFoundException(NotFoundException exception) {
        log.debug("NotFoundException", exception);
        return Response
                .status(Status.NOT_FOUND)
                .entity(ErrorResponseDTO.builder().payload(GenericErrorPayload.builder().putInfo("error", "HTTP 404 Not Found").build()).build())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

//    @ServerExceptionMapper
//    public Response unrecognizedPropertyException(UnrecognizedPropertyException exception) {
//        log.error("UnrecognizedPropertyException", exception);
//        SkullResourceException skullResourceException = SkullResourceException.builder().error(SkullErrorDefaultImpl.VALIDATION_ERROR).build();
//        ErrorResponseDTO response = ErrorResponseDTO.builder().code(skullResourceException.getErrorCode()).build();
//        return Response.status(skullResourceException.getStatus()).entity(response).type(MediaType.APPLICATION_JSON_TYPE).build();
//    }
}
