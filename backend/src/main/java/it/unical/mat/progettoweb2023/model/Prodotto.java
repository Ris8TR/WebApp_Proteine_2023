package it.unical.mat.progettoweb2023.model;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
public class Prodotto {
    private Integer id_prodotto;
    private String nome;
    private String marchio;
    private Integer size_cps;
    private Integer size_gr;
    private Boolean vegan;
    private Boolean available;
    private Boolean lactose_free;
    private Integer prezzo;
    private String descrizione;
    private byte[] val_nutr;   // base64
    private String categoria;


    //GETTERS

    public String getCategoria() {  return categoria;  }

    public Integer getId_prodotto() {  return id_prodotto;  }

    public String getNome() {  return nome;  }

    public String getMarchio() {  return marchio;  }

    public Integer getSize_cps() {  return size_cps;  }

    public Integer getSize_gr() {  return size_gr;  }

    public Boolean getVegan() {  return vegan;  }

    public Boolean getAvailable() {  return available;  }

    public Integer getPrezzo() {  return prezzo;  }

    public Boolean getLactose_free() {  return lactose_free;  }

    public String getDescrizione() {  return descrizione;  }

    public String getVal_nutr() {
        if(val_nutr==null){
            return null;
        }
        else{
            return new String(val_nutr);
        }
    }



    //SETTERS

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }



    public void setId_prodotto(Integer id_prodotto) {
        this.id_prodotto = id_prodotto;
    }



    public void setNome(String nome) {
        this.nome = nome;
    }



    public void setMarchio(String marchio) {
        this.marchio = marchio;
    }



    public void setSize_cps(Integer size_cps) {
        this.size_cps = size_cps;
    }



    public void setSize_gr(Integer size_gr) {
        this.size_gr = size_gr;
    }



    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }



    public void setAvailable(Boolean available) {
        this.available = available;
    }



    public void setLactose_free(Boolean lactose_free) {
        this.lactose_free = lactose_free;
    }



    public void setPrezzo(Integer prezzo) {
        this.prezzo = prezzo;
    }



    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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
