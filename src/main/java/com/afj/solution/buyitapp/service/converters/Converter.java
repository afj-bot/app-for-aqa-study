package com.afj.solution.buyitapp.service.converters;


/**
 * @author Tomash Gombosh
 */
public interface Converter<S, T> {
    T convert(S source);
}
