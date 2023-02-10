package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasead.database.eventosDatabase.Gestor;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EditarGestor extends AppCompatActivity {

    EditText dni, contraseña, nombre, apellido, telefono;
    ImageView userPicture, listo;
    TextView alert;
    Button delete;
    Gestor gestorObject = new Gestor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            gestorObject = (Gestor) bundle.getSerializable("Gestor");
        } else { // SI BUNDLE ES NULO, NO ESTA LOGEADO
            finish();
        }

        setContentView(R.layout.activity_editar_gestor);
        dni = findViewById(R.id.dni);
        contraseña = findViewById(R.id.contraseña);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        telefono = findViewById(R.id.telefono);
        userPicture = findViewById(R.id.userPicture);
        delete = findViewById(R.id.delete);
        alert = findViewById(R.id.alert);
        listo = findViewById(R.id.listo);

        dni.setText(gestorObject.getDNI());
        contraseña.setText(gestorObject.getContraseña());
        nombre.setText(gestorObject.getNombre());
        apellido.setText(gestorObject.getApellido());
        telefono.setText(gestorObject.getNum_Telf());

        dni.setOnClickListener(v -> {
            AlphaAnimation animation = new AlphaAnimation(0, 1);
            animation.setDuration(4000);
            alert.startAnimation(animation);
            alert.setVisibility(View.VISIBLE);
            AlphaAnimation animation2 = new AlphaAnimation(1, 0);
            animation2.setDuration(4000);
            alert.startAnimation(animation2);
            alert.setVisibility(View.INVISIBLE);
        });

        listo.setOnClickListener(v -> {
            String s_dni = dni.getText().toString();
            String nom = nombre.getText().toString();
            String ape = apellido.getText().toString();
            String con = contraseña.getText().toString();
            String tel = telefono.getText().toString();

            //INSTANCIA BBDD
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            //SELECT DOCUMENTO / REGISTRO
            db.collection("Gestores").whereEqualTo("DNI", s_dni).limit(1).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentReference docRef = null;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        // UPDATE
                        DocumentReference ref = document.getReference();
                        ref.update("Nombre", nom);
                        ref.update("Apellido", ape);
                        ref.update("Contraseña", con);
                        ref.update("Num_Telf", tel);

                        docRef = ref;

                    }

                    Gestor gestorObjeto = new Gestor(s_dni, con, nom, ape, tel);

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("timestamp", FieldValue.serverTimestamp());

                    // ESPERAR A UNA RESPUESTA DEL SERVIDOR, ACTUALIZACIONES COMPLETADAS
                    assert docRef != null;
                    docRef.update(updates).addOnCompleteListener(task1 -> {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("Gestor", (Serializable) gestorObjeto);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    });

                } else {
                    Log.w(TAG, "Error select documento.", task.getException());
                }
            });

            finish();

        });

        delete.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Gestores").whereEqualTo("DNI", dni.getText().toString()).limit(1).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentReference ref = null;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        ref = document.getReference();
                    }

                    // DELETE
                    assert ref != null;
                    ref.delete().addOnSuccessListener(aVoid -> Log.d(TAG, "Docuemnto eliminado!"))
                            .addOnFailureListener(e -> Log.w(TAG, "Error eliminando documento", e));
                } else {
                    Log.w(TAG, "Error select documento.", task.getException());
                }
            });

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();

        });


    }
}