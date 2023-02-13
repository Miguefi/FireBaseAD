package com.example.firebasead.Recycler;

import java.io.Serializable;

public class Cliente implements Serializable {
    private String nombre;
    private String apellido;
    private String contraseña;
    private String dni_cliente;
    private String dni_gestor;
    private String tel;
    public Cliente(String nombre, String apellido, String dni_cliente,
                   String dni_gestor, String tel, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni_cliente = dni_cliente;
        this.dni_gestor = dni_gestor;
        this.tel = tel;
        this.contraseña = contraseña;
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