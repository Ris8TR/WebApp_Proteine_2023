package it.unical.mat.progettoweb2023.model;

import java.util.List;

public class ProdOrd {
    List<Integer> id_prodotti;
    Integer n_ordine;
    List<Integer> quantita;

    public List<Integer> getId_prodotti() {
        return id_prodotti;
    }

    public void setId_prodotti(List<Integer> id_prodotti) {
        this.id_prodotti = id_prodotti;
    }

    public Integer getN_ordine() {
        return n_ordine;
    }

    public void setN_ordine(Integer n_ordine) {
        this.n_ordine = n_ordine;
    }

    public List<Integer> getQuantita() {
        return quantita;
    }

    public void setQuantita(List<Integer> quantita) {
        this.quantita = quantita;
    }
}
