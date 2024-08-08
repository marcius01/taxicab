package tech.skullprogrammer.core.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import tech.skullprogrammer.core.exception.ISkullPayload;

@RegisterForReflection
@Data
@Builder
public class ErrorResponseDTO {
    @Nonnull
    private String code;
    @Nullable
    private ISkullPayload payload;
}
