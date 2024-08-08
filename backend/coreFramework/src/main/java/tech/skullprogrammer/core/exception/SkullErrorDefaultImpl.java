package tech.skullprogrammer.core.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SkullErrorDefaultImpl implements ISkullError.App
{
    VALIDATION_ERROR(HttpResponseStatus.UNPROCESSABLE_ENTITY.code());


    private final int status;

    @Override
    public int getStatus() {
        return status;
    }

    @Nonnull
    @Override
    public String getCode() {
        return name();
    }
}
