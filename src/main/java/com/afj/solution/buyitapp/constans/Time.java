package com.afj.solution.buyitapp.constans;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author Tomash Gombosh
 */
public final class Time {
    public static final Duration DEFAULT_ANONYMOUS_TOKEN_EXPIRED_TIME = Duration.of(5, ChronoUnit.YEARS);
    public static final Duration DEFAULT_TOKEN_EXPIRED_TIME = Duration.ofHours(1);

    private Time() {
        throw new AssertionError(" Utility classes should not have a public or default constructor. [HideUtilityClassConstructor]");
    }
}
