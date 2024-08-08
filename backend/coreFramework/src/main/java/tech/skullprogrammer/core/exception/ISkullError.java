package tech.skullprogrammer.core.exception;

import jakarta.annotation.Nonnull;

import java.util.Optional;

public interface ISkullError {

    int getStatus();

    String getErrorCode();

    default Optional<ISkullPayload> getErrorPayload() {
        return Optional.empty();
    }

    public interface App extends ISkullError {
        @Nonnull
        String getCode();

        @Nonnull
        default String baseCode() {
            return "SKULL";
        }

        @Nonnull
        default String codeSeparator() {
            return "-";
        }

        @Nonnull
        default String getErrorCode() {
            String result = this.baseCode();
            return result + this.codeSeparator() + this.getCode();
        }
    }
}
