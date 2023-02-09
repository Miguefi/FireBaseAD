package com.example.firebasead.database.eventosDatabase;

import com.google.firebase.database.Exclude;

import java.sql.Timestamp;

public class Evento {

    // Exclude previene que no lo escribamos, el id se autogenera
    @Exclude
    public String id;

    public Timestamp inicio;
    public Timestamp fin;
    public float latitud;
    public float longitud;
    public String titulo;
    public String descripcion;

    public Evento(){
        Timestamp inicio = this.inicio;
        Timestamp fin = this.fin;
        float latitud = this.latitud;
        float longitud = this.longitud;
        String titulo = this.titulo;
        String descripcion = this.descripcion;
    }
}
