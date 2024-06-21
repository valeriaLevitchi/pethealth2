package com.valeria.pethealth.Modelo;

public class Mascota {
    String nombre,tipo, nacimiento, peso, altura ;

    public Mascota(String nombre, String tipo, String nacimiento, String peso, String altura) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nacimiento = nacimiento;
        this.peso = peso;
        this.altura = altura;
    }

    public Mascota() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }
}
