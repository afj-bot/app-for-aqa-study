package com.afj.solution.buyitapp.common;

import java.util.List;
import java.util.function.Consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Objects.requireNonNull;

/**
 * @author Tomash Gombosh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Response<T> {
    private T response;
    private List<Error> error;

    public Response(final T response) {
        this.response = response;
    }

    public Response(final Consumer<Response<T>> builder) {
        requireNonNull(builder).accept(this);
    }

    /**
     * @author Tomash Gombosh
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Error {
        private String message;

        public Error(final Consumer<Error> builder) {
            requireNonNull(builder).accept(this);
        }
    }
}
