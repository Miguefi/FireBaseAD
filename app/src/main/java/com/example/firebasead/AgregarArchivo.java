package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayoutStates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebasead.RecyclerArchivos.Archivos;
import com.example.firebasead.database.eventosDatabase.Evento;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class AgregarArchivo extends AppCompatActivity {

    private TextView nombre, propietario, dniPropietario, fechaFin, extension;
    private Button agregar;
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

            db.collection("Archivos")
                    .orderBy("ID", Query.Direction.DESCENDING)
                    .limit(1)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            int highestID = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Obtener el ID más alto de los documentos existentes
                                highestID = document.getLong("ID").intValue();
                            }

                            // Incrementar el ID más alto en uno
                            highestID++;

                            // Crear un nuevo documento con el ID resultante
                            DocumentReference newDocument = db.collection("Archivos").document();
                            Map<String, Object> archivo = new HashMap<>();
                            archivo.put("ID", highestID);
                            archivo.put("Nombre", s_nombre);
                            archivo.put("Propietario", s_propietario);
                            archivo.put("DNI_Cliente", s_dni);
                            archivo.put("FechaCreacion", s_fecha);
                            archivo.put("Extencion", s_extension);
                            newDocument.set(archivo);
                        } else {
                            Log.w(ConstraintLayoutStates.TAG, "Error al recuperar el último documento", task.getException());
                        }
                        startActivity(new Intent(AgregarArchivo.this, NuevoArchivo.class));
                        finish();
            });
        });
    }
}