package com.example.firebasead.Recycler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PerfilesClientes implements Serializable {

    public String nombre;
    public String apellido;
    public String contraseña;
    public String dni_cliente;
    public String dni_gestor;
    public String tel;

    public PerfilesClientes() {}

    public PerfilesClientes(String nombre, String apellido, String contraseña, String dni_cliente, String dni_gestor, String tel) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.dni_cliente = dni_cliente;
        this.dni_gestor = dni_gestor;
        this.tel = tel;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public String getDni_gestor() {
        return dni_gestor;
    }

    public void setDni_gestor(String dni_gestor) {
        this.dni_gestor = dni_gestor;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}