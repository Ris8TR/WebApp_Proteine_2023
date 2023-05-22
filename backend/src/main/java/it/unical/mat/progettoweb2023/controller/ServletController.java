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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@CrossOrigin("http://localhost:4200")
public class ServletController {

    //mapping user requests
    @RequestMapping(value = "/user/**", method = {RequestMethod.GET, RequestMethod.POST})
    public String userHandler(HttpServletRequest request,HttpServletResponse resp) throws IOException {
        String user = new Getc(request.getCookies(),"user").Get();
        String sess = new Getc(request.getCookies(),"sessionId").Get();
        String resource = request.getRequestURI().substring("/user/".length());
        if((user==null && sess==null)){
            resp.sendRedirect("http://localhost:8080/login");
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
        List<Prodotto> prodotti = new ProductSQL().getAllProducts();
        return ResponseEntity.ok().body(prodotti);
    }

    @RequestMapping(value = "/getProductsById", method = RequestMethod.GET)
    public ResponseEntity<Prodotto> getProductById(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") int id) {
        Prodotto prodotto = new ProductSQL().getProductbyId(id);
        System.out.print(id);

        if (prodotto == null) {
            // Prodotto non trovato, restituisci una risposta 404 Not Found
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(prodotto);
    }



    @RequestMapping(value = "URL DA MAPPARE", method = {RequestMethod.GET})
    public String getProdbyID(HttpServletRequest request, HttpServletResponse response){ //VOLENDO PUOI CAMBIARE ANCHE IL NOME DELLA FUNZIONE
        String resource = request.getRequestURI().substring("/admin/".length()); //modifica l´espressione tra virgolette in base all´url che mappi per evitare errori
        if(resource.contains(".html")){  //PICCOLO CICLO IF CHE PUOI DECIDERE SE TENERE O MENO IN BASE A COME SCRIVI IL CODICE
            resource = resource.substring(0, resource.indexOf(".html"));//CHE INVIA LA RICHIESTA PER FARE IL PARSING DELLA PAGINA PER EVITARE EVENTUALI ERRORI
        }
        Integer id = Integer.valueOf(request.getParameter("id")); //QUA PUOI MODIFICARE id IN BASE A COME HAI CHIAMATO IL PARAMETRO NEL FRONT
        Prodotto prod = new ProductSQL().getProductbyId(id);
        request.setAttribute("prodotto",prod); //qua setto l´oggetto prodotto estratto dal db
        return "URL MAPPATO"+resource;
        //PUOI USARLO COSÍ PERÓ DEVI CREARE UNA PAGINA DI RESOURCE COME LE ALTRE CHE HO FATTO PER AVERE I DATI
        //ALL´INTERNO,ALTRIMENTI PUOI USARE IL REQUEST DISPATCHER E MANDARE LA request CON I DATI DOVE VUOI
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


