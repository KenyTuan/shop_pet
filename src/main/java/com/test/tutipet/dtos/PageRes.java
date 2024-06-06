package com.test.tutipet.dtos;

import java.util.List;

public record PageRes<T>(
        List<T> content,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) {
}
