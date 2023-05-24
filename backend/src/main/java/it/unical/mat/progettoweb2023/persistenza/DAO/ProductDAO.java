package it.unical.mat.progettoweb2023.persistenza.DAO;

import it.unical.mat.progettoweb2023.model.Prodotto;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
public interface ProductDAO {
    List<Prodotto> getAllProducts();

    @CrossOrigin("http://localhost:4200")
    List<Prodotto> getAllProductsByCategory(String Category);

    Prodotto getProductbyId(Integer id_prodotto);

    List<Prodotto> getProductsbyCat(String cat);

    Integer getPrezzo(Integer id_prodotto);

    void AddProduct(Prodotto prodotto);

    void DeleteProduct(Integer ID);
}
