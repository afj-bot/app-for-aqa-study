package com.afj.solution.buyitapp.constans;

import static com.afj.solution.buyitapp.constans.Urls.FE_URL;

/**
 * @author Tomash Gombosh
 */
public final class Redirects {
    public static final String USER_DISABLED_URL = String.format("%s/blocked", FE_URL);
    public static final String USER_LOCKED_URL = String.format("%s/locked", FE_URL);
    public static final String USER_PRIVACY_POLICY_URL = String.format("%s/privacy-policy", FE_URL);

    private Redirects() {
        throw new AssertionError(" Utility classes should not have a public or default constructor. [HideUtilityClassConstructor]");
    }
}
