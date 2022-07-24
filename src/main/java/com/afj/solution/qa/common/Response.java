package com.afj.solution.qa.common;

import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static java.util.Objects.requireNonNull;

/**
 * @param <T> Object response
 * @author Tomash Gombosh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Response<T> {
    private T response;
    private HttpStatus statusCode;
    private Error error;

    public Response(final T response) {
        this.response = response;
    }

    public Response(final Consumer<Response<T>> builder) {
        requireNonNull(builder).accept(this);
    }
}
