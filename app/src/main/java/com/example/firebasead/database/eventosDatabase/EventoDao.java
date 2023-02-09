package com.example.firebasead.database.eventosDatabase;

import com.example.firebasead.database.FirebaseDao;
import com.example.firebasead.database.Listeners.RetrievalEventListener;
import com.google.firebase.database.DataSnapshot;

import java.sql.Timestamp;

public class EventoDao extends FirebaseDao<Evento> {
    public EventoDao(){
        // Specify the table name for the class
        super("Eventos");
    }

    @Override
    protected void parseDataSnapshot(DataSnapshot dataSnapshot, RetrievalEventListener<Evento> retrievalEventListener) {
        final Evento evento = new Evento();
        evento.id = dataSnapshot.getKey();

        // IMPORTANT NOTE: make sure that the variable is EXACTLY the same as the node.

        evento.titulo = dataSnapshot.child("Titulo").getValue().toString();
        evento.descripcion = dataSnapshot.child("Descripcion").getValue().toString();
        evento.inicio = Timestamp.valueOf(dataSnapshot.child("Inicio").getValue().toString());
        evento.fin = Timestamp.valueOf(dataSnapshot.child("Fin").getValue().toString());
        evento.latitud = Float.parseFloat(dataSnapshot.child("Latitud").getValue().toString());
        evento.longitud = Float.parseFloat(dataSnapshot.child("Longitud").getValue().toString());

        retrievalEventListener.OnDataRetrieved(evento);
    }

}