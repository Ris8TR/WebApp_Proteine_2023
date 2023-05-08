package it.unical.mat.progettoweb2023.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user = new Getc(req.getCookies(),"user").Get();
        String sess = new Getc(req.getCookies(),"sessionId").Get();
        if((user==null && sess==null)){
            return "login";
        }
        else{
            resp.sendRedirect("http://localhost:8080/");
            return null;
        }
    }


    @GetMapping ("/register")
    public String register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String user = new Getc(req.getCookies(),"user").Get();
        String sess = new Getc(req.getCookies(),"sessionId").Get();
        if((user==null && sess==null)){
            return "register";
        }
        else{
            resp.sendRedirect("http://localhost:8080/");
            return null;
        }
    }

    @GetMapping ("/")
    public String homepage() {
        return "homepage";
    }

    @GetMapping ("/admin")
    public void admin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("http://localhost:8080/admin/");
    }

    @GetMapping ("/user")
    public void user(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("http://localhost:8080/user/");
    }
}
