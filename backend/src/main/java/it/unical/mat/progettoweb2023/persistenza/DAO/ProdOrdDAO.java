package it.unical.mat.progettoweb2023.persistenza.DAO;

import it.unical.mat.progettoweb2023.model.ProdOrd;

public interface ProdOrdDAO {

    void AddProdOrd(ProdOrd order, int id);

    void DelProdOrd(int id);
}
