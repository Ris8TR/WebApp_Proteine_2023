package it.unical.mat.progettoweb2023.persistenza.DAO;

import it.unical.mat.progettoweb2023.model.Ordine;

import java.util.List;

public interface OrderDAO {

    List<Ordine> getOrdersbyEmail(String email);
    Ordine getOrderbyN(Integer n_ordine);
    List<Ordine> getAllOrders();
    Integer AddOrder(Ordine order);
    void DeleteOrder(Ordine order);

}
