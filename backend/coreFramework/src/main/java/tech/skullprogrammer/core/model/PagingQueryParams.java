package tech.skullprogrammer.core.model;

import io.quarkus.panache.common.Page;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.Data;

@Data
@RegisterForReflection
public class PagingQueryParams {
    @QueryParam("page")
    @DefaultValue("0")
    private @PositiveOrZero(
            message = "Page must not be negative"
    ) int page;

    @QueryParam("size")
    @DefaultValue("10")
    private @Positive(
            message = "Size must be positive"
    ) int size;

    public Page toPage() {
        return  Page.of(page, size);
    }
}