package tech.skullprogrammer.core.exception;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;

@Builder
@RegisterForReflection
public class SkullResourceException extends RuntimeException{
    private ISkullError error;
    private ISkullPayload payload;

    public int getStatus() {
        return this.error.getStatus();
    }

    public String getErrorCode() {
        return this.error.getErrorCode();
    }

    public ISkullPayload getPayload() {return this.payload;}

}
