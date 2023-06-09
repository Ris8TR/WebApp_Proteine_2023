package it.unical.mat.progettoweb2023.controller;

import it.unical.mat.progettoweb2023.model.Ordine;
import it.unical.mat.progettoweb2023.model.Prodotto;
import it.unical.mat.progettoweb2023.model.User;
import it.unical.mat.progettoweb2023.persistenza.sql.OrderSQL;
import it.unical.mat.progettoweb2023.persistenza.sql.ProductSQL;
import it.unical.mat.progettoweb2023.persistenza.sql.UserSQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Category;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@CrossOrigin("http://localhost:4200")
public class ServletController {




    //mapping user requests
    @RequestMapping(value = "/user/**", method = {RequestMethod.GET, RequestMethod.POST})
    public String userHandler(HttpServletRequest request,HttpServletResponse resp) throws IOException {
        String user = new Getc(request.getCookies(),"user").Get();
        String sess = new Getc(request.getCookies(),"sessionId").Get();
        String resource = request.getRequestURI().substring("/user/".length());
        if(user==null && sess==null){
            resp.sendRedirect("http://localhost:8080/login");
            return null;
        }
        else if(new UserSQL().getUserByEmail(user).getAdmin()){
            resp.sendRedirect("http://localhost:8080/admin");
            return null;
        }
        else{
            if(resource.contains(".html")){
                resource = resource.substring(0, resource.indexOf(".html"));
            }
            if(resource==""){
                return "/user/index";
            }
            else if(resource.equals("info")){
                Info(request);
                return "/user/"+resource;
            } else if(resource.equals("ordini")) {
                Ordini(request);
                return "/user/"+resource;
            }
            else{
                return "error";
            }
        }
    }

    //mapping admin requests
    @RequestMapping(value = "/admin/**", method = {RequestMethod.GET, RequestMethod.POST})
    public String adminHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String adm = new Getc(request.getCookies(),"admin").Get();
        String resource = request.getRequestURI().substring("/admin/".length());
        if (adm==null){
            response.sendRedirect("http://localhost:8080/login");
            return null;
        }
        else if (adm.equals("1")){
            if(resource.contains(".html")){
                resource = resource.substring(0, resource.indexOf(".html"));
            }
            if(resource==""){
                return "/admin/index";
            }
            if(resource.equals("orders")){
                Order(request);
                return "/admin/"+resource;
            }
            else if(resource.equals("users")){
                User(request);
                return "/admin/"+resource;
            }
            else if(resource.equals("catalog")){
                Product(request);
                return "/admin/"+resource;
            }
            else if(resource.equals("neworder")){
                return "/admin/"+resource;
            }
            else if(resource.equals("newuser")){
                return "/admin/"+resource;
            }
            else if(resource.equals("newprod")){
                return "/admin/"+resource;
            }
        }
        else{
            return "error";
        }
        return null;
    }

    @RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
    public ResponseEntity<List<Prodotto>> getAllProduct(HttpServletRequest request, HttpServletResponse response) {
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        int pageSize = 12; // Numero di prodotti per pagina

        // Calcola l'indice di inizio per la pagina corrente
        int startIndex = (pageNumber) * pageSize;

        List<Prodotto> prodotti = new ProductSQL().getAllProducts();

        // Controlla se l'indice di inizio è superiore alla dimensione della lista dei prodotti
        if (startIndex >= prodotti.size()) {
            return ResponseEntity.ok().body(Collections.emptyList()); // Non ci sono più prodotti
        }

        // Calcola l'indice di fine per la pagina corrente
        int endIndex = Math.min(startIndex + pageSize, prodotti.size());

        // Estrai i prodotti per la pagina corrente
        List<Prodotto> prodottiPagina = prodotti.subList(startIndex, endIndex);

        return ResponseEntity.ok().body(prodottiPagina);
    }


    @RequestMapping(value = "/getProductById/**", method = RequestMethod.GET)
    public ResponseEntity<Prodotto> getProductById(HttpServletRequest request) {
        String resource = request.getRequestURI().substring("/getProductById/".length());
        Prodotto prodotto = new ProductSQL().getProductbyId(Integer.valueOf(resource));
        if (prodotto == null) {
            // Prodotto non trovato, restituisci una risposta 404 Not Found
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(prodotto);
    }

    @RequestMapping(value = "/getProductByCategory", method = RequestMethod.GET)
    public ResponseEntity<List<Prodotto>> getProductByCategory(HttpServletRequest request, HttpServletResponse response,
                                                               @RequestParam("pageNumber") int pageNumber) {
        int pageSize = 12; // Numero di prodotti per pagina

        // Calcola l'indice di inizio per la pagina corrente
        int startIndex = (pageNumber) * pageSize;

        String productCategory = request.getParameter("productCategory");
        List<Prodotto> prodotti = new ProductSQL().getAllProductsByCategory(productCategory);

        // Controlla se l'indice di inizio è superiore alla dimensione della lista dei prodotti
        if (startIndex >= prodotti.size()) {
            return ResponseEntity.ok().body(Collections.emptyList()); // Non ci sono più prodotti
        }

        // Calcola l'indice di fine per la pagina corrente
        int endIndex = Math.min(startIndex + pageSize, prodotti.size());

        // Estrai i prodotti per la pagina corrente
        List<Prodotto> prodottiPagina = prodotti.subList(startIndex, endIndex);

        return ResponseEntity.ok().body(prodottiPagina);
    }


    @RequestMapping(value = "/getProductsBySearch", method = RequestMethod.GET)
    public ResponseEntity<List<Prodotto>> getProductsBySearch(HttpServletRequest request, HttpServletResponse response,
    @RequestParam("pageNumber") int pageNumber) {
        int pageSize = 12; // Numero di prodotti per pagina

        // Calcola l'indice di inizio per la pagina corrente
        int startIndex = (pageNumber) * pageSize;

        String searchKeyword = request.getParameter("searchKeyword");
        List<Prodotto> prodotti = new ProductSQL().getProductsBySearch(searchKeyword);

        // Controlla se l'indice di inizio è superiore alla dimensione della lista dei prodotti
        if (startIndex >= prodotti.size()) {
            return ResponseEntity.ok().body(Collections.emptyList()); // Non ci sono più prodotti
        }

        // Calcola l'indice di fine per la pagina corrente
        int endIndex = Math.min(startIndex + pageSize, prodotti.size());

        // Estrai i prodotti per la pagina corrente
        List<Prodotto> prodottiPagina = prodotti.subList(startIndex, endIndex);

        return ResponseEntity.ok().body(prodottiPagina);
    }




    protected void Product(HttpServletRequest request) {
        List<Prodotto> prodotti = new ProductSQL().getAllProducts();
        request.setAttribute("prodotti", prodotti);
    }
    protected void Order(HttpServletRequest request) {
        List<Ordine> ordini = new OrderSQL().getAllOrders();
        // imposta i dati come attributi della richiesta
        request.setAttribute("ordini", ordini);
    }
    protected void User(HttpServletRequest request){
        List<User> utenti = new UserSQL().getAllUsers();
        // imposta i dati come attributi della richiesta
        request.setAttribute("utenti", utenti);
    }
    protected void Info(HttpServletRequest request) {
        String email = new UserSQL().getUserByEmail(new Getc(request.getCookies(), "user").Get()).getEmail();
        String name = new UserSQL().getUserByEmail(new Getc(request.getCookies(), "user").Get()).getName();
        String surname = new UserSQL().getUserByEmail(new Getc(request.getCookies(), "user").Get()).getLastname();
        String street_1 = new UserSQL().getUserByEmail(new Getc(request.getCookies(), "user").Get()).getStreet_1();
        String street_2 = new UserSQL().getUserByEmail(new Getc(request.getCookies(), "user").Get()).getStreet_2();
        Integer cap = new UserSQL().getUserByEmail(new Getc(request.getCookies(), "user").Get()).getCap();
        String city = new UserSQL().getUserByEmail(new Getc(request.getCookies(), "user").Get()).getCity();
        String country = new UserSQL().getUserByEmail(new Getc(request.getCookies(), "user").Get()).getCountry();
        String cell = new UserSQL().getUserByEmail(new Getc(request.getCookies(), "user").Get()).getCellphone();
        // imposta i dati come attributi della richiesta
        request.setAttribute("email", email);
        request.setAttribute("name", name);
        request.setAttribute("surname", surname);
        request.setAttribute("street_1", street_1);
        request.setAttribute("street_2", street_2);
        request.setAttribute("cap", cap);
        request.setAttribute("city", city);
        request.setAttribute("country", country);
        request.setAttribute("cell", cell);
    }
    protected void Ordini(HttpServletRequest request){
        String email = new UserSQL().getUserByEmail(new Getc(request.getCookies(),"user").Get()).getEmail();
        List<Ordine> ordini = new OrderSQL().getOrdersbyEmail(email);
        // imposta i dati come attributi della richiesta
        request.setAttribute("storicoOrdini", ordini);
    }
}

