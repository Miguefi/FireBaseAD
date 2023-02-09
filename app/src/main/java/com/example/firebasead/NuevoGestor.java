package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NuevoGestor extends AppCompatActivity {

    EditText nombre, apellido, contraseña, num_tel, dni;
    Button crearGestor;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_gestor);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        imagen = findViewById(R.id.imagen);
        imagen.setImageResource(R.drawable.ic_launcher_foreground);
        dni = findViewById(R.id.dni);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        contraseña = findViewById(R.id.contraseña);
        num_tel = findViewById(R.id.telefono);
        crearGestor = findViewById(R.id.crear);
        crearGestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> gestor = new HashMap<>();
                gestor.put("DNI", dni.getText().toString());
                gestor.put("Contraseña", contraseña.getText().toString());
                gestor.put("Nombre", nombre.getText().toString());
                gestor.put("Apellido", apellido.getText().toString());
                gestor.put("Num_Tel", num_tel.getText().toString());
                db.collection("Gestores").add(gestor).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Intent intent = new Intent(getApplicationContext(), GestorMain.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("HashMap", (Serializable) gestor);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast toast = Toast.makeText(getApplicationContext(), "Error al crear cuenta", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

            }
        });

    }
}