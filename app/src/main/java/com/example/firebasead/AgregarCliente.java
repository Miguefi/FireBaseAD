package com.example.firebasead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebasead.Recycler.PerfilesClientes;
import com.example.firebasead.RecyclerArchivos.Archivos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AgregarCliente extends AppCompatActivity {


    private TextView nombre, apellido, dniCliente, dniGestor, contraseña,telefono;
    private Button agregar;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        db = FirebaseFirestore.getInstance();
        nombre = findViewById(R.id.nombre_agregar_cliente);
        apellido= findViewById(R.id.apellido_agregar_cliente);
        dniCliente = findViewById(R.id.dniClienteCrear);
        dniGestor = findViewById(R.id.DniGestorCrear);
        contraseña = findViewById(R.id.Contraseña_crear_cliente);
        telefono = findViewById(R.id.tel_cliente_crear);
        agregar = findViewById(R.id.crearCliente);


        agregar.setOnClickListener(v -> {
            String s_nombre = nombre.getText().toString(), s_apellido = apellido.getText().toString(),
                    s_dniCliente = dniCliente.getText().toString(), s_dniGestor = dniGestor.getText().toString(),
                    s_contraseña = contraseña.getText().toString(),s_telefono = telefono.getText().toString();

            Map<String, Object> evento = new HashMap<>();
            evento.put("Nombre", s_nombre);
            evento.put("Apellido", s_apellido);
            evento.put("DNI", s_dniCliente);
            evento.put("DNI_Gestor", s_dniGestor);
            evento.put("Num_Tel", s_telefono);
            evento.put("Contraseña", s_contraseña);


            db.collection("Clientes").add(evento)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String id = documentReference.getId();
                            Log.d("TAG", "DocumentSnapshot added with ID: " + id);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error adding document", e);
                        }
                    });
        });


    }
}