package com.example.firebasead;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasead.Recycler.AdaptadorListado;
import com.example.firebasead.Recycler.PerfilesClientes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class DetallesClienteActivity extends AppCompatActivity {

    TextView nombre,apellido,dni,dniGestor,telefono,contraseña ;

    ImageView imgCliente;

    AdaptadorListado adapter;
    Button botonBorrar;
    Button botonActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_cliente);


        nombre=findViewById(R.id.nombre_detalle);
        apellido=findViewById(R.id.apellido_detalle);
        dni=findViewById(R.id.dni_detalle);
        dniGestor=findViewById(R.id.dnigestor_detalle);
        telefono=findViewById(R.id.telefono_detalle);
        contraseña=findViewById(R.id.contraseña_detalle);
        imgCliente=findViewById(R.id.imagen_cliente_detalle);

        PerfilesClientes perfil = (PerfilesClientes) getIntent().getSerializableExtra("perfil");


        String nom = perfil.getNombre();
        nombre.setText(nom);

        String ape=perfil.getApellido();
        apellido.setText(ape);


        dni.setText(perfil.getDni_cliente());
        dniGestor.setText(perfil.getDni_gestor());
        telefono.setText(perfil.getTel());
        contraseña.setText(perfil.getContraseña());



        botonBorrar = findViewById(R.id.boton_borrar);
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Clientes").whereEqualTo("Nombre",nom)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    documentSnapshot.getReference().delete();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                                Toast.makeText(DetallesClienteActivity.this, "Se ha borrado "+nom, Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error borrando documento", e);
                            }
                        });
            }
        });

       botonActualizar = findViewById(R.id.boton_actualizar);
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para actualizar el cliente en Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> cliente = new HashMap<>();
                cliente.put("Nombre","Miguel" );
                cliente.put("Apellido", "Suarez");
                db.collection("Clientes").document(nom).set(cliente, SetOptions.merge());

                // Mostrar un mensaje de confirmación
                Toast.makeText(DetallesClienteActivity.this, "Cliente actualizado exitosamente", Toast.LENGTH_SHORT).show();
            }
        });

















    }
}