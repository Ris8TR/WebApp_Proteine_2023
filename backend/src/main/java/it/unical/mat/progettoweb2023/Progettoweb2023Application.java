package it.unical.mat.progettoweb2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ServletComponentScan
@CrossOrigin("http://localhost:4200")
public class Progettoweb2023Application {

    public static void main(String[] args) {
        SpringApplication.run(Progettoweb2023Application.class, args);
    }

}

