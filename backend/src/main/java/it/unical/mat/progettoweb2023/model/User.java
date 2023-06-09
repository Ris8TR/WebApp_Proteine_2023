package it.unical.mat.progettoweb2023.model;

public class User {
    String email;
    String password;
    String name;
    String lastname;
    String street_1;
    String street_2;
    Integer cap;
    String city;
    String country;
    String cellphone;
    Boolean admin;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getStreet_1() {
        return street_1;
    }

    public String getStreet_2() {
        return street_2;
    }

    public Integer getCap() {
        return cap;
    }

    public void setCap(Integer cap) {
        this.cap = cap;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCellphone() {
        return cellphone;
    }

    public Boolean getAdmin() {
        return admin;
    }


    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public void setCellphone(String cellphone) {

        this.cellphone = cellphone;
    }

    public void setStreet_1(String street_1) {

        this.street_1 = street_1;
    }

    public void setStreet_2(String street_2) {

        this.street_2 = street_2;
    }

    public void setName(String name) {

        this.name = name;
    }


    public void setLastname(String lastname) {

        this.lastname = lastname;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setAdmin(Boolean admin) {

        this.admin = admin;
    }

}
