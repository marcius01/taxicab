package tech.skullprogrammer.core.mapping;

//To use with rest clients

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import tech.skullprogrammer.core.exception.ISkullError;
import tech.skullprogrammer.core.exception.ISkullPayload;
import tech.skullprogrammer.core.exception.SkullResourceException;

import java.util.Map;

@Slf4j
@Provider
public class ClientExceptionMapper implements ResponseExceptionMapper<SkullResourceException> {

    @SuppressWarnings("unchecked")
    @Override
    public SkullResourceException toThrowable(Response response) {
        try {
            Map<String, Object> errorBody = response.readEntity(new GenericType<>() {});
            ISkullError skullError = new ISkullError() {
                @Override
                public int getStatus() {
                    return response.getStatus();
                }

                @Override
                public String getErrorCode() {
                    return (String)errorBody.get("code");
                }
            };
            Map<String, Object> payloadMap = (Map<String, Object>) errorBody.get("payload");
            ISkullPayload skullPayload = new SkullPayloadAdapter(payloadMap);

            return SkullResourceException.builder()
                    .error(skullError)
                    .payload(skullPayload)
                    .build();
        } catch (Throwable ex) {
            log.info("ex: {}", ex.toString());
            throw new RuntimeException(ex);
        }

    }

   // @Override
    public boolean handles(int status, Response response) {
        log.info("status code to manage: {}", status);
        return status >= 300;
    }

    private record SkullPayloadAdapter(Map<String, Object> actualPayload) implements ISkullPayload {
        @Override
        @JsonValue
        public Map<String, Object> actualPayload() {
            return actualPayload;
        }
    }
}

