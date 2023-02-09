package com.example.firebasead.calendario;

import org.joda.time.DateTime;

public class Event {
    private DateTime inicio;
    private DateTime Fin;
    private String titulo;

    public Event(DateTime inicio, DateTime fin, String titulo) {
        this.inicio = inicio;
        Fin = fin;
        this.titulo = titulo;
    }

    public DateTime getInicio() {
        return inicio;
    }

    public void setInicio(DateTime inicio) {
        this.inicio = inicio;
    }

    public DateTime getFin() {
        return Fin;
    }

    public void setFin(DateTime fin) {
        Fin = fin;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
