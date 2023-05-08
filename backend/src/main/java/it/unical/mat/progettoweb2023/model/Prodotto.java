package it.unical.mat.progettoweb2023.model;

public class Prodotto {
    Integer id_prodotto;
    String nome;
    String marchio;
    Integer size_cps;
    Integer size_gr;
    Boolean vegan;
    Boolean available;
    Boolean lactose_free;
    Integer prezzo;
    String descrizione;
    byte[] val_nutr;   //base64
    String categoria;
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getId_prodotto() {
        return id_prodotto;
    }

    public void setId_prodotto(Integer id_prodotto) {
        this.id_prodotto = id_prodotto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarchio() {
        return marchio;
    }

    public void setMarchio(String marchio) {
        this.marchio = marchio;
    }

    public Integer getSize_cps() {
        return size_cps;
    }

    public void setSize_cps(Integer size_cps) {
        this.size_cps = size_cps;
    }

    public Integer getSize_gr() {
        return size_gr;
    }

    public void setSize_gr(Integer size_gr) {
        this.size_gr = size_gr;
    }

    public Boolean getVegan() {
        return vegan;
    }

    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getLactose_free() {
        return lactose_free;
    }

    public void setLactose_free(Boolean lactose_free) {
        this.lactose_free = lactose_free;
    }

    public Integer getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Integer prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getVal_nutr() {
        if(val_nutr==null){
            return null;
        }
        else{
            return new String(val_nutr);
        }
    }

    public void setVal_nutr(byte[] val_nutr) {
        if(val_nutr==null){
            this.val_nutr = null;
        }
        else{
            this.val_nutr = val_nutr;
        }

    }
}
