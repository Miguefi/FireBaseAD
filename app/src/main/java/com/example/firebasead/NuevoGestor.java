package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NuevoGestor extends AppCompatActivity {

    EditText nombre, apellido, contraseña, num_tel;
    FloatingActionButton crearGestor;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_gestor);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        contraseña = findViewById(R.id.contraseña);
        num_tel = findViewById(R.id.telefono);
        crearGestor = findViewById(R.id.añadirGestor);
        crearGestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> gestor = new HashMap<>();
                gestor.put("DNI", "5463350V");
                gestor.put("Contraseña", contraseña.getText().toString());
                gestor.put("Nombre", nombre.getText().toString());
                gestor.put("Apellido", apellido.getText().toString());
                gestor.put("Num_Tel", num_tel.getText().toString());
                db.collection("Gestores").add(gestor)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
            }
        });

    }
}