package com.gerardojunior.registration.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Pageable;

@Data
public class StandardResponse {

    private Meta meta;

    private Object result;

    private Object errors;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    public static class Meta {

        private String code;

        private String message;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Pageable pageable;

    }

    public StandardResponse(String code, String message, Object data) {
        this.result = data;
        this.meta = new Meta(code, message, null);
    }

    public StandardResponse(String code, String message, Object data, Pageable pageable) {
        this.result = data;
        this.meta = new Meta(code, message, pageable);
    }

}
