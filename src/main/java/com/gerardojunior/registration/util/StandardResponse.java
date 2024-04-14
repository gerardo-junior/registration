package com.gerardojunior.registration.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;
import org.springframework.data.domain.Pageable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StandardResponse<T> {

    private Meta meta;

    private T data;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    public static class Meta {

        private String code;

        private String message;

        @Hidden
        private Pageable pageable;

    }

    public StandardResponse(String code, String message, T data) {
        this.data = data;
        this.meta = new Meta(code, message, null);
    }

    public StandardResponse(String code, String message, T data, Pageable pageable) {
        this.data = data;
        this.meta = new Meta(code, message, pageable);
    }

}
