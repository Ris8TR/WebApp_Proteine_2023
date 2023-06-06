package it.unical.mat.progettoweb2023.controller;

import it.unical.mat.progettoweb2023.model.ProdOrd;
import it.unical.mat.progettoweb2023.model.Prodotto;
import it.unical.mat.progettoweb2023.model.User;
import it.unical.mat.progettoweb2023.model.Ordine;
import it.unical.mat.progettoweb2023.persistenza.sql.OrderSQL;
import it.unical.mat.progettoweb2023.persistenza.sql.ProdOrdSQL;
import it.unical.mat.progettoweb2023.persistenza.sql.ProductSQL;
import it.unical.mat.progettoweb2023.persistenza.sql.UserSQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



@RestController
@CrossOrigin("http://localhost:4200")
public class PostController {

    @PostMapping("/login")
    @ResponseBody
    public Object login(HttpServletRequest req,HttpServletResponse resp,@RequestParam String email, @RequestParam String password) throws IOException {
        User user = new UserSQL().getUserByEmail(email);
        if(user != null){
            if(BCrypt.checkpw(password, user.getPassword())){
                    HttpSession session = req.getSession();
                    resp.addCookie(new Cookie("user",user.getEmail()));
                    resp.addCookie(new Cookie("sessionId",session.getId()));
                    if (user.getAdmin()){
                        resp.addCookie(new Cookie("admin","1"));
                        return 2;
                    }
                    return 1;
            }else{
                return -1;
            }
        }else{
            return 0;
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public Object register(HttpServletRequest req,HttpServletResponse resp, @RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String surname, @RequestParam String via_1, @RequestParam String via_2, @RequestParam Integer cap, @RequestParam String city, @RequestParam String nation, @RequestParam String phone, @RequestParam Boolean admin) throws IOException {

        User check = new UserSQL().getUserByEmail(email);
        if(check == null){
            UserSQL cmd = new UserSQL();
            User user = new User();
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(10)));
            user.setName(name);
            user.setLastname(surname);
            user.setStreet_1(via_1);
            user.setStreet_2(via_2);
            user.setCap(cap);
            user.setCity(city);
            user.setCountry(nation);
            user.setCellphone(phone);
            user.setAdmin(admin);
            cmd.createUser(user);
            HttpSession session = req.getSession();
            resp.addCookie(new Cookie("user",user.getEmail()));
            resp.addCookie(new Cookie("sessionId",session.getId()));
            return req.getSession().getId();
        }
        return -1;
    }

    @PostMapping("/updateuser")
    @ResponseBody
    public Object update(HttpServletRequest req,HttpServletResponse resp, @RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String surname, @RequestParam String via_1, @RequestParam String via_2, @RequestParam Integer cap, @RequestParam String city, @RequestParam String nation, @RequestParam String phone, @RequestParam Boolean admin){

            UserSQL upuser = new UserSQL();
            User user = new User();
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(10)));
            user.setName(name);
            user.setLastname(surname);
            user.setStreet_1(via_1);
            user.setStreet_2(via_2);
            user.setCap(cap);
            user.setCity(city);
            user.setCountry(nation);
            user.setCellphone(phone);
            user.setAdmin(admin);
            upuser.updateUser(user);
            return req.getSession().getId();
    }

    @PostMapping("/elimina-ordine/**")
    @ResponseBody
    public Object delorder(HttpServletRequest request,HttpServletResponse resp) throws IOException {
        String resource = request.getRequestURI().substring("/elimina-ordine/".length());
        Ordine order = new OrderSQL().getOrderbyN(Integer.valueOf(resource));
        new ProdOrdSQL().DelProdOrd(order.getN_ordine());
        new OrderSQL().DeleteOrder(order);
        resp.sendRedirect("http://localhost:8080/admin/orders");
        return null;
    }

    @PostMapping("/admin/addorder")
    @ResponseBody
    public Object addorder(HttpServletRequest request) {
        String email = request.getParameter("customerName");
        User usr = new UserSQL().getUserByEmail(email);
        if(usr==null){
            return -1;
        }
        else{
            Integer totale = 0;
            String date = request.getParameter("orderDate");
            Integer n = Integer.valueOf(request.getParameter("num"));
            Ordine order = new Ordine();
            ProdOrd prod = new ProdOrd();

            List<Integer> prods = new ArrayList<>();
            List<Integer> quantita = new ArrayList<>();
            order.setEmail(email);
            order.setData(date);
            if(n==0){
                Integer product = Integer.valueOf(request.getParameter("products[0].name"));
                Integer quantity = Integer.valueOf(request.getParameter("products[0].quantity"));
                prods.add(product);
                quantita.add(quantity);
                int pr = new ProductSQL().getPrezzo(product);
                totale += pr * quantity;
                order.setTotale(totale);
                prod.setId_prodotti(prods);
                prod.setQuantita(quantita);
            }
            else{
                Integer[] totali = new Integer[n];
                for(int i=0;i<n;i++){
                    int id=Integer.valueOf(request.getParameter("products["+i+"].name"));
                    int quant=Integer.valueOf(request.getParameter("products["+i+"].quantity"));
                    int p = new ProductSQL().getPrezzo(id);
                    int tot = p*quant;
                    totali[i] = tot;
                    prods.add(id); quantita.add(quant);
                }
                int somma = 0;
                for(int x:totali){
                    somma+=x;
                }
                order.setTotale(somma);
            }
            int ordid = new OrderSQL().AddOrder(order);
            prod.setId_prodotti(prods);
            prod.setQuantita(quantita);
            new ProdOrdSQL().AddProdOrd(prod,ordid);
            return null;
        }

    }

    @PostMapping("/checkout")
    @ResponseBody
    public Object checkout(HttpServletRequest request) throws IOException, ParseException {
        String user = new Getc(request.getCookies(),"user").Get();
        String sess = new Getc(request.getCookies(),"sessionId").Get();
        if((user==null && sess==null)){
            return -1;
        }
        else {
            // Ottenere i dati dell'ordine dalla richiesta
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if(br != null){
            json = br.readLine();
        }
        Object obj = new JSONParser().parse(json);
        JSONObject jo = (JSONObject) obj;
        JSONArray ja = (JSONArray) jo.get("items");
        Ordine ord = new Ordine();
        ProdOrd prodord = new ProdOrd();
        ord.setEmail(user);
        ord.setTotale(gettot(ja));
        ord.setData(String.valueOf(java.time.LocalDate.now()));
        int id=new OrderSQL().AddOrder(ord);
        prodord.setId_prodotti(prods(ja));
        prodord.setQuantita(quantity(ja));
        new ProdOrdSQL().AddProdOrd(prodord,id);
        return null;
        }
        }


    @PostMapping("/elimina-utente/**")
    @ResponseBody
    public Object deluser(HttpServletRequest request,HttpServletResponse resp) throws IOException {
        String resource = request.getRequestURI().substring("/elimina-utente/".length());
        new UserSQL().deleteUser(resource);
        resp.sendRedirect("http://localhost:8080/admin/users");
        return null;
    }

    @PostMapping("/admin/adduser")
    @ResponseBody
    public Object adduser(HttpServletRequest req,HttpServletResponse resp, @RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String surname, @RequestParam String via_1, @RequestParam String via_2, @RequestParam Integer cap, @RequestParam String city, @RequestParam String nation, @RequestParam String phone, @RequestParam Boolean admin) throws IOException {
        User check = new UserSQL().getUserByEmail(email);
        if(check == null){
            UserSQL cmd = new UserSQL();
            User user = new User();
            user.setEmail(email);
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(10)));
            user.setName(name);
            user.setLastname(surname);
            user.setStreet_1(via_1);
            user.setStreet_2(via_2);
            user.setCap(cap);
            user.setCity(city);
            user.setCountry(nation);
            user.setCellphone(phone);
            user.setAdmin(admin);
            cmd.createUser(user);
            return null;
        }
        else{
            return -1;
        }
    }

    @PostMapping("/admin/addprod")
    @ResponseBody
    public Object addprod(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        Integer id = new ProductSQL().GenID();
        String nome = req.getParameter("Nome");
        String marchio = req.getParameter("Marchio");
        Integer size_cps = Integer.valueOf(req.getParameter("size_cps"));
        Integer size_gr = Integer.valueOf(req.getParameter("size_gr"));
        String vegan = req.getParameter("vegan");
        String available = req.getParameter("available");
        String lactose_free = req.getParameter("lactose_free");
        Integer prezzo = Integer.valueOf(req.getParameter("prezzo"));
        String descrizione = req.getParameter("descrizione");
        String categoria = req.getParameter("categoria");
        byte[] val_nutr = getval_nutr(req);
        byte[] foto_prod = getfoto_prod(req);
        Prodotto prod = new Prodotto();
        prod.setId_prodotto(id); prod.setNome(nome); prod.setMarchio(marchio);
        if(size_cps==0){prod.setSize_cps(null);}else{prod.setSize_cps(size_cps);}
        if(size_gr==0){prod.setSize_gr(null);}else{prod.setSize_gr(size_gr);}
        if(vegan==null){prod.setVegan(false);}else{prod.setVegan(true);}
        if(available==null){prod.setAvailable(false);}else{prod.setAvailable(true);}
        if(lactose_free==null){prod.setLactose_free(false);}else{prod.setLactose_free(true);}
        prod.setPrezzo(prezzo); prod.setDescrizione(descrizione); prod.setVal_nutr(val_nutr);
        prod.setFoto(foto_prod);
        prod.setCategoria(categoria);
        new ProductSQL().AddProduct(prod);
        return null;
    }

    @PostMapping("/elimina-prod/**")
    @ResponseBody
    public Object delprod(HttpServletRequest request,HttpServletResponse resp) throws IOException {
        String resource = request.getRequestURI().substring("/elimina-prod/".length());
        new ProductSQL().DeleteProduct(Integer.valueOf(resource));
        resp.sendRedirect("http://localhost:8080/admin/catalog");
        return null;
    }


    private byte[] getval_nutr(HttpServletRequest req) throws ServletException, IOException {
        InputStream inputStream = req.getPart("image").getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] bytess = outputStream.toByteArray();
        inputStream.close();
        outputStream.close();
        String bs64 = Base64.encodeBase64String(bytess);
        byte[] bytes = bs64.getBytes();
        return bytes;
    }

    private byte[] getfoto_prod(HttpServletRequest req) throws ServletException, IOException{
        InputStream inputStream = req.getPart("imgprod").getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] bytess = outputStream.toByteArray();
        inputStream.close();
        outputStream.close();
        String bs64 = Base64.encodeBase64String(bytess);
        byte[] bytes = bs64.getBytes();
        return bytes;
    }

    private Integer gettot(JSONArray ja) throws ParseException {
        int tot=0;
        for(int i=0;i<ja.size();i++){
            int tmp=0,p,q;
            JSONObject data = (JSONObject) new JSONParser().parse(ja.get(i).toString());
            q = Long.valueOf(String.valueOf(data.get("quantity"))).intValue() ;
            p = new ProductSQL().getPrezzo(Long.valueOf(String.valueOf(data.get("product_id"))).intValue());
            tmp+=q*p;
            tot+=tmp;
        }
       return tot;
    }

    private List<Integer> prods(JSONArray ja) throws ParseException {
        List<Integer> prodotti = new ArrayList<>();
        for(int i=0;i<ja.size();i++){
            int p;
            JSONObject data = (JSONObject) new JSONParser().parse(ja.get(i).toString());
            p = Long.valueOf(String.valueOf(data.get("product_id"))).intValue();
            prodotti.add(p);
        }
        return prodotti;
    }

    private List<Integer> quantity(JSONArray ja) throws ParseException {
        List<Integer> quantity = new ArrayList<>();
        for(int i=0;i<ja.size();i++){
            int p;
            JSONObject data = (JSONObject) new JSONParser().parse(ja.get(i).toString());
            p = Long.valueOf(String.valueOf(data.get("quantity"))).intValue();
            quantity.add(p);
        }
        return quantity;
    }


}


