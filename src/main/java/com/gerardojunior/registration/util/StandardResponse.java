package com.gerardojunior.registration.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Pageable;

@Data
public class StandardResponse {

    private Meta meta;

    private Object data;

    @Data
    @AllArgsConstructor
    public class Meta {

        private String code;

        private String message;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Pageable pageable;

    }

    public StandardResponse(String code, String message, Object data) {
        this.data = data;
        this.meta = new Meta(code, message, null);
    }

    public StandardResponse(String code, String message, Object data, Pageable pageable) {
        this.data = data;
        this.meta = new Meta(code, message, pageable);
    }

}
