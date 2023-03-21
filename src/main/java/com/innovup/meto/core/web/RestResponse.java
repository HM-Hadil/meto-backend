package com.innovup.meto.core.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

    private final int code;
    private final HttpStatus status;
    private final T data;

    private RestResponse(T data, int statusCode) {
        this.data = data;
        this.code = statusCode;
        this.status = HttpStatus.valueOf(code);
    }

    public static <T> RestResponse<T> of(T data, int code) {
        return new RestResponse<>(data, code);
    }

    public static <T> RestResponse<T> empty(int code) {
        return new RestResponse<>(null, code);
    }
}
