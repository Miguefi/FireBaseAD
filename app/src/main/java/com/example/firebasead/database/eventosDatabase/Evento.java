package com.example.firebasead.database.eventosDatabase;

import com.google.firebase.database.Exclude;
import com.google.firebase.Timestamp;

import java.io.Serializable;
//import java.sql.Timestamp;

public class Evento implements Serializable {

    // Exclude previene que no lo escribamos, el id se autogenera
    @Exclude
    public String id;

    public String inicio;
    public Timestamp fin;
    public float latitud;
    public float longitud;
    public String titulo;
    public String descripcion;

    public Evento(){
        String inicio = this.inicio;
        Timestamp fin = this.fin;
        float latitud = this.latitud;
        float longitud = this.longitud;
        String titulo = this.titulo;
        String descripcion = this.descripcion;
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

    public Timestamp getFin() {
        return fin;
    }

    public void setFin(Timestamp fin) {
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
