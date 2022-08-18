package com.afj.solution.buyitapp.service.localize;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

/**
 * @author Tomash Gombosh
 */
@Component
public class TranslatorService extends ResourceBundleMessageSource {

    public TranslatorService() {
        this.setDefaultEncoding("UTF-8");
        this.setUseCodeAsDefaultMessage(true);
        this.setBasenames("error", "localize");
    }

    public String toLocale(final String msgCode) {
        final Locale locale = LocaleContextHolder.getLocale();
        return this.getMessage(msgCode, null, locale);
    }

    public Map<String, Object> getLocalize() {
        final ResourceBundle resourceBundle = getResourceBundle("forms", LocaleContextHolder.getLocale());
        requireNonNull(resourceBundle, "Not found a localization");

        final Map<String, Object> objectMap = new ConcurrentHashMap<>();
        resourceBundle.keySet().forEach(k ->
                objectMap.put(k, resourceBundle.getString(k)));
        return objectMap;
    }
}
