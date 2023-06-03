package it.unical.mat.progettoweb2023.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Ordine {

    Integer n_ordine;
    String email;
    String data;
    Integer totale;
    Map<Prodotto, Integer> prodottoQuantita;



    public Map<Prodotto, Integer> getProdottoQuantita() {
        return prodottoQuantita;
    }

    public Integer getTotale() {
        return totale;
    }

    public Integer getN_ordine() {
        return n_ordine;
    }

    public String getEmail() {
        return email;
    }

    public String getData() {
        return data;
    }



    public void setEmail(String email) {

        this.email = email;
    }
    public void setTotale(Integer totale) {

        this.totale = totale;
    }
    public void setN_ordine(Integer n_ordine) {
        this.n_ordine = n_ordine;
    }

    public void setProdottoQuantita(Map<Prodotto, Integer> prodottoQuantita) {
        this.prodottoQuantita = prodottoQuantita;
    }

    public void setData(String data) {
        this.data = data;
    }
}
