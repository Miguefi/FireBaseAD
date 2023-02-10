package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebasead.RecyclerArchivos.Archivos;
import com.example.firebasead.database.eventosDatabase.Evento;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AgregarArchivo extends AppCompatActivity {

    TextView nombre, propietario, dniPropietario, fechaFin, extension;
    Button agregar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_archivo);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        nombre = findViewById(R.id.nombreCrearArchivo);
        propietario= findViewById(R.id.nombrePropietario);
        dniPropietario = findViewById(R.id.dniPropietarioCrear);
        fechaFin = findViewById(R.id.fechaFinCrearArchivo);
        extension = findViewById(R.id.extensionCrearArchivo);
        agregar = findViewById(R.id.crearArchivo);

        agregar.setOnClickListener(v -> {
            String s_nombre = nombre.getText().toString(), s_propietario = propietario.getText().toString(),
            s_dni = dniPropietario.getText().toString(), s_fecha = fechaFin.getText().toString(), s_extension = extension.getText().toString();

            Map<String, Object> evento = new HashMap<>();
            evento.put("Nombre", s_nombre);
            evento.put("Propietario", s_propietario);
            evento.put("DNI_Cliente", s_dni);
            evento.put("FechaCreacion", s_fecha);
            evento.put("Extencion", s_extension);

            db.collection("Archivos").add(evento).addOnSuccessListener(documentReference -> {
                        Archivos archivo = new Archivos(s_nombre, s_propietario, s_dni, s_fecha, s_extension);
                        startActivity(new Intent(AgregarArchivo.this, NuevoArchivo.class));
                        finish();
            });
        });
    }
}