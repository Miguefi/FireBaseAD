package com.example.firebasead.database.eventosDatabase;

import com.google.firebase.database.Exclude;
import com.google.firebase.Timestamp;

import java.io.Serializable;
//import java.sql.Timestamp;

public class Evento implements Serializable {

    // Exclude previene que no lo escribamos, el id se autogenera
    @Exclude
    public String id;

    public String titulo;
    public String inicio;
    public String fin;
    public float latitud;
    public float longitud;
    public String descripcion;

    public Evento(){
    }

    public Evento(String titulo, String inicio, String fin, float latitud, float longitud, String descripcion){
        titulo = this.titulo;
        inicio = this.inicio;
        fin = this.fin;
        latitud = this.latitud;
        longitud = this.longitud;
        descripcion = this.descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
