package com.afj.solution.productApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Tomash Gombosh
 */
@SpringBootApplication
@SuppressWarnings("PMD")
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    protected Application() {

    }
}
