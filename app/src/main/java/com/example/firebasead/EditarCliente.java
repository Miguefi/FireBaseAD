package com.example.firebasead;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasead.Recycler.Cliente;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import java.util.HashMap;
import java.util.Map;


public class EditarCliente extends AppCompatActivity {


    private Button actualizar;
    private EditText nombre, apellido, dniGestor, telefono, contraseña;
    private TextView dniCliente;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);


        actualizar = findViewById(R.id.boton_actualizar_cliente);
        nombre = findViewById(R.id.nombreActualizar);
        apellido = findViewById(R.id.apellidActualizar);
        dniCliente = findViewById(R.id.dni_actualizar);
        dniGestor = findViewById(R.id.dnigestorActualizar);
        contraseña = findViewById(R.id.contraseñaActualizar);
        telefono = findViewById(R.id.telefonoActualizar);



        db = FirebaseFirestore.getInstance();
        Cliente perfil = (Cliente) getIntent().getSerializableExtra("perfil");
        nombre.setText(perfil.getNombre());
        apellido.setText( perfil.getApellido());
        dniCliente.setText(perfil.getDni_cliente());
        dniGestor.setText(perfil.getDni_gestor());
        contraseña.setText(perfil.getContraseña());
        telefono.setText( perfil.getTel());




        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Clientes").whereEqualTo("DNI", perfil.getDni_cliente()).get().
                        addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            String s_nombre = nombre.getText().toString();
                            String s_apellido = apellido.getText().toString();
                            String s_dniGestor = dniGestor.getText().toString();
                            String s_contraseña = contraseña.getText().toString();
                            String s_dniCliente=dniCliente.getText().toString();
                            String s_telefono = telefono.getText().toString();
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            // Obtener el ID único del documento
                            String id = documentSnapshot.getId();
                            Map<String, Object> modificado = new HashMap<>();
                            modificado.put("Nombre", s_nombre);
                            modificado.put("Apellido", s_apellido);
                            modificado.put("DNI", s_dniCliente);
                            modificado.put("DNI_Gestor", s_dniGestor);
                            modificado.put("Num_Tel", s_telefono);
                            modificado.put("Contraseña", s_contraseña);

                            db.collection("Clientes").document(id).set(modificado, SetOptions.merge());

                            Toast.makeText(EditarCliente.this, "Datos del cliente actualizados", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditarCliente.this, ClienteMain.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                });
            }
        });

    }
}