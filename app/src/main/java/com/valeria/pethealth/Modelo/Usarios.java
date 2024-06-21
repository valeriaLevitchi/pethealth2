package com.valeria.pethealth.Modelo;

public class Usarios {
    String Usario, Password, id;

    public Usarios(String usario, String password, String id) {
        Usario = usario;
        Password = password;
        this.id = id;
    }

    public String getUsario() {
        return Usario;
    }

    public void setUsario(String usario) {
        Usario = usario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
