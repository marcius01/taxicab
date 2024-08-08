package tech.skullprogrammer.core.exception;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.HashMap;
import java.util.Map;

@RegisterForReflection
public class GenericErrorPayload implements ISkullPayload {
    private final Map<String, Object> info;

    public static Builder builder() {
        return new Builder();
    }

    private GenericErrorPayload(Builder builder) {
        this.info = builder.info;
    }

    public Map<String, Object> getInfo() {
        return this.info;
    }

    public static class Builder {
        private Map<String, Object> info;

        public Builder() {
        }

        public Builder putInfo(String key, Object value) {
            if (this.info == null) {
                this.info = new HashMap();
            }

            this.info.put(key, value);
            return this;
        }

        public GenericErrorPayload build() {
            return new GenericErrorPayload(this);
        }
    }
}

