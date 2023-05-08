package it.unical.mat.progettoweb2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Progettoweb2023Application {

    public static void main(String[] args) {
        SpringApplication.run(Progettoweb2023Application.class, args);
    }

}
