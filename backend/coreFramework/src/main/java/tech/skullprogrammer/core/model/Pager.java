package tech.skullprogrammer.core.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Pager<T> {

    private List<T> result;
    private int size;
    private int page;
    private long total;
}
