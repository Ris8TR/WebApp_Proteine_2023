package it.unical.mat.progettoweb2023.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class Getc {

    Cookie[] cook = null;
    String name = null;
    public Getc(Cookie[] c,String name) {
        this.cook = c;
        this.name = name;
    }

    public String Get(){
        if(this.cook!=null) {
        for (int i = 0; i < this.cook.length; i++) {
            Cookie cookie1 = this.cook[i];
            if (cookie1.getName().equals(this.name)) {
                return cookie1.getValue();
            }
        }
        }
        return null;
    }


    
}
