package it.unical.mat.progettoweb2023.model;

public class Image {
    Long id;
    String name;
    String type;
    byte[] picByte;

    public Image() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Image(String name, String type, byte[] picByte) {
        super();
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }

}
