package com.example.firebasead;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AgregarCliente extends AppCompatActivity {


    private TextView nombre, apellido, dniCliente, dniGestor, contraseña,telefono;
    private Button agregar;
    private FirebaseFirestore db;
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


        agregar.setOnClickListener(v -> addCliente());

    }

    private void addCliente() {
        String s_nombre = nombre.getText().toString();
        String s_apellido = apellido.getText().toString();
        String s_dniCliente = dniCliente.getText().toString();
        String s_dniGestor = dniGestor.getText().toString();
        String s_contraseña = contraseña.getText().toString();
        String s_telefono = telefono.getText().toString();

        Map<String, Object> cliente = new HashMap<>();
        cliente.put("Nombre", s_nombre);
        cliente.put("Apellido", s_apellido);
        cliente.put("DNI", s_dniCliente);
        cliente.put("DNI_Gestor", s_dniGestor);
        cliente.put("Num_Tel", s_telefono);
        cliente.put("Contraseña", s_contraseña);

        db.collection("Clientes").add(cliente)
                .addOnSuccessListener(documentReference -> {
                    startActivity(new Intent(AgregarCliente.this, ClienteMain.class));
                    finish();
                    Toast.makeText(this, "Añadido cliente " + s_nombre, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error adding document", e));
    }


}