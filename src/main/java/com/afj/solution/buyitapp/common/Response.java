package com.afj.solution.buyitapp.common;

import java.util.Collections;
import java.util.List;
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
 * @author Tomash Gombosh
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Response<T> {
    private T response;
    private Status status;
    private HttpStatus statusCode;

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
    @JsonInclude(Include.NON_NULL)
    public static class Status {
        private String responseStatus;
        private List<Error> errors;

        public Status(final String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public Status(final List<Error> errors) {
            this.errors = errors;
        }

        public Status(final String responseStatus, final Error error) {
            this.responseStatus = responseStatus;
            this.errors = Collections.singletonList(error);
        }

        public Status(final Error error) {
            this.errors = Collections.singletonList(error);
        }

        public Status(final Consumer<Status> builder) {
            requireNonNull(builder).accept(this);
        }
    }
}
