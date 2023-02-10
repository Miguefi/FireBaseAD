package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasead.database.eventosDatabase.Gestor;
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

        crearGestor.setOnClickListener(v -> {
            String s_dni = dni.getText().toString(), s_cont = contraseña.getText().toString(), s_nom = nombre.getText().toString(), s_ape = apellido.getText().toString(), s_num = num_tel.getText().toString();

            Map<String, Object> gestor = new HashMap<>();
            gestor.put("DNI", s_dni);
            gestor.put("Contraseña", s_cont);
            gestor.put("Nombre", s_nom);
            gestor.put("Apellido", s_ape);
            gestor.put("Num_Tel", s_num);

            db.collection("Gestores").add(gestor).addOnSuccessListener(documentReference -> {
                        Log.d(TAG, "Insert gestor con ID: " + documentReference.getId());

                        Gestor gestorObject = new Gestor(s_dni, s_cont, s_nom, s_ape, s_num);

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Gestor", (Serializable) gestorObject);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error insert gestor", e));

        });

    }
}