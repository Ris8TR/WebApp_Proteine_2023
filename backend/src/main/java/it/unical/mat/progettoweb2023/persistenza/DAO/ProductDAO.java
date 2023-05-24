package it.unical.mat.progettoweb2023.persistenza.DAO;

import it.unical.mat.progettoweb2023.model.Prodotto;
import java.util.List;
public interface ProductDAO {
    List<Prodotto> getAllProducts();

    Prodotto getProductbyId(Integer id_prodotto);

    List<Prodotto> getProductsbyCat(String cat);

    Integer getPrezzo(Integer id_prodotto);

    void AddProduct(Prodotto prodotto);

    void DeleteProduct(Integer ID);
}
