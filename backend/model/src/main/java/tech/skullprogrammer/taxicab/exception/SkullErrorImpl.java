package tech.skullprogrammer.taxicab.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import tech.skullprogrammer.core.exception.ISkullError;

@RequiredArgsConstructor
public enum SkullErrorImpl implements ISkullError.App
{
    BAD_REQUEST(HttpResponseStatus.BAD_REQUEST.code()),
    WRONG_OTP(HttpResponseStatus.BAD_REQUEST.code()),
    EXPIRED_OTP(HttpResponseStatus.BAD_REQUEST.code()),
    WRONG_CREDENTIALS(HttpResponseStatus.UNAUTHORIZED.code()),
    //PROFILE
    NO_PROFILE(HttpResponseStatus.BAD_REQUEST.code()),
    NO_VALID_PROFILE(HttpResponseStatus.BAD_REQUEST.code());


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
