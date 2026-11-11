package com.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Une seule classe de démarrage dans tout le projet.
// Spring scanne ce package (com.merchant) et tous ses sous-packages.
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
