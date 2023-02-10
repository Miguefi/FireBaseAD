package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasead.database.eventosDatabase.Evento;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NuevoEvento extends AppCompatActivity {

    EditText titulo, inicio, fin, latitud, longitud, descripcion;
    Button crearEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        titulo = findViewById(R.id.tituloCrearEvento);
        inicio = findViewById(R.id.fechaInicioCrearEvento);
        fin = findViewById(R.id.fechaFinCrearEvento);
        latitud = findViewById(R.id.latitudCrearEvento);
        longitud = findViewById(R.id.longitudCrearEvento);
        descripcion = findViewById(R.id.descripcionCrearEvento);
        crearEvento = findViewById(R.id.crearEvento);

        crearEvento.setOnClickListener(v -> {
            String s_titulo = titulo.getText().toString(), s_inicio = inicio.getText().toString(),
                    s_fin = fin.getText().toString(), s_descripcion = descripcion.getText().toString();
            Float s_latitud = Float.valueOf(latitud.getText().toString()), s_longitud = Float.valueOf(longitud.getText().toString());

            Map<String, Object> evento = new HashMap<>();
            evento.put("Titulo", s_titulo);
            evento.put("Inicio", s_inicio);
            evento.put("Fin", s_fin);
            evento.put("Latitud", s_latitud);
            evento.put("Longitud", s_longitud);
            evento.put("Descripcion", s_descripcion);

            db.collection("Eventos").add(evento).addOnSuccessListener(documentReference -> {
                        Log.d(TAG, "Insertado evento con ID: " + documentReference.getId());

                        Evento eventoObject = new Evento(s_titulo, s_inicio, s_fin, s_latitud, s_longitud, s_descripcion);
//
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("Evento", (Serializable) eventoObject);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error insert evento", e));

        });

    }
}