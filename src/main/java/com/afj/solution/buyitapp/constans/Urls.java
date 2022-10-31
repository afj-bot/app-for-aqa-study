package com.afj.solution.buyitapp.constans;

import static java.util.Objects.nonNull;

/**
 * @author Tomash Gombosh
 */
public final class Urls {

    public static final String APP_DOMAIN = nonNull(System.getenv("APP_DOMAIN"))
            ? System.getenv("APP_DOMAIN")
            : "localhost";
    public static final String FE_URL = nonNull(System.getenv("FE_URL"))
            ? System.getenv("FE_URL")
            : "http://localhost:3000";

    private Urls() {
        throw new AssertionError(" Utility classes should not have a public or default constructor. [HideUtilityClassConstructor]");
    }
}
