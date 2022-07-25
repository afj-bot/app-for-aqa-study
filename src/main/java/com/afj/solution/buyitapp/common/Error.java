package com.afj.solution.buyitapp.common;

import java.util.function.Consumer;

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
public class Error {
    private String message;
    private String identifier;

    public Error(final Consumer<Error> builder) {
        requireNonNull(builder).accept(this);
    }
}
