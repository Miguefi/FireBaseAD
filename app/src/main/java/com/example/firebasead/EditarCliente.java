package com.example.firebasead;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class EditarCliente extends AppCompatActivity {


    TextView nombre, apellido, dniCliente, dniGestor, telefono, contraseña;

    Button actualizar;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);


        actualizar = findViewById(R.id.boton_actualizar_cliente);
        db = FirebaseFirestore.getInstance();
        nombre = findViewById(R.id.nombreActualizar);
        apellido = findViewById(R.id.apellidActualizar);
        dniCliente = findViewById(R.id.dni_actualizar);
        dniGestor = findViewById(R.id.dnigestorActualizar);
        contraseña = findViewById(R.id.contraseñaActualizar);
        telefono = findViewById(R.id.telefonoActualizar);


        String dni_intent = getIntent().getStringExtra("dni");


        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                db.collection("Clientes").whereEqualTo("DNI", dni_intent).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            String s_nombre = nombre.getText().toString();
                            String s_apellido = apellido.getText().toString();
                            String s_dniGestor = dniGestor.getText().toString();
                            dniCliente.setText(dni_intent);
                            String s_contraseña = contraseña.getText().toString();
                            String s_telefono = telefono.getText().toString();
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            // Obtener el ID único del documento
                            String id = documentSnapshot.getId();
                            Map<String, Object> evento = new HashMap<>();
                            evento.put("Nombre", s_nombre);
                            evento.put("Apellido", s_apellido);
                            evento.put("DNI", dni_intent);
                            evento.put("DNI_Gestor", s_dniGestor);
                            evento.put("Num_Tel", s_telefono);
                            evento.put("Contraseña", s_contraseña);

                            db.collection("Clientes").document(id).set(evento, SetOptions.merge());
                            Toast.makeText(EditarCliente.this, "Datos del cliente actualizados", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditarCliente.this, GestorMain.class));
                            finish();
                        }
                    }
                });
            }
        });

    }
}