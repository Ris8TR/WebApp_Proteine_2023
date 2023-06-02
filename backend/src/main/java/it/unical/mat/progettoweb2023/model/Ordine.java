package it.unical.mat.progettoweb2023.model;

import java.util.Map;

public class Ordine {

    private Integer n_ordine;
    private String email;
    private String data;
    private Integer totale;
    private Map<Prodotto, Integer> prodottoQuantita;



    //SETTERS

    public void setProdottoQuantita(Map<Prodotto, Integer> prodottoQuantita) {
        this.prodottoQuantita = prodottoQuantita;
    }
    public void setTotale(Integer totale) {

        this.totale = totale;
    }
    public void setN_ordine(Integer n_ordine) {

        this.n_ordine = n_ordine;
    }
    public void setEmail(String email) {

        this.email = email;
    }
    public void setData(String data) {

        this.data = data;
    }



    //GETTERS

    public Map<Prodotto, Integer> getProdottoQuantita() {  return prodottoQuantita; }
    public Integer getTotale() {  return totale; }
    public Integer getN_ordine() {  return n_ordine;  }
    public String getEmail() {  return email;  }
    public String getData() {  return data;  }
}
