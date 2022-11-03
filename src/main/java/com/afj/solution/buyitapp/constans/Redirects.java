package com.afj.solution.buyitapp.constans;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tomash Gombosh
 */
@Configuration
public class Redirects {
    @Value("${app.frontend.url}")
    private String feUrl;
    @Getter
    private final String userDisabledUrl = String.format("%s/blocked", feUrl);
    @Getter
    private final String userLockedUrl = String.format("%s/locked", feUrl);
    @Getter
    private final String userPrivacyPolicyUrl = String.format("%s/privacy-policy", feUrl);
}
