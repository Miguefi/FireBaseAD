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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;

public class Login extends AppCompatActivity {

    ImageView logo;
    Button login;
    EditText usuario, contraseña;
    TextView crearCuenta, olvidar, alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.log);
        usuario = findViewById(R.id.user);
        contraseña = findViewById(R.id.password);
        usuario = findViewById(R.id.user);
        crearCuenta = findViewById(R.id.createAccount);
        olvidar = findViewById(R.id.forget);
        logo = findViewById(R.id.logo);
        alert = findViewById(R.id.alert);

        //LOG IN
        login.setOnClickListener(v -> {
            String dni = usuario.getText().toString();
            String pass = contraseña.getText().toString();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference gestores = db.collection("Gestores");
            Query gestor = gestores.whereEqualTo("DNI", dni).whereEqualTo("Contraseña", pass);

            gestor.get().addOnCompleteListener(task -> {
                String dni1 ="", contraseña="", nombre="", apellido="", telefono="";

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        dni1 = document.get("DNI").toString();
                        contraseña = document.get("Contraseña").toString();
                        nombre = document.get("Nombre").toString();
                        apellido = document.get("Apellido").toString();
                        telefono = document.get("Num_Tel").toString();

                    }

                    Gestor gestorObjeto = new Gestor(dni1, contraseña, nombre, apellido, telefono);

                    if (gestorObjeto.getDNI().equals("")){

                        AlphaAnimation animation = new AlphaAnimation(0, 1);
                        animation.setDuration(4000);
                        alert.startAnimation(animation);
                        alert.setVisibility(View.VISIBLE);
                        AlphaAnimation animation2 = new AlphaAnimation(1, 0);
                        animation2.setDuration(4000);
                        alert.startAnimation(animation2);
                        alert.setVisibility(View.INVISIBLE);

                    } else {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Gestor", (Serializable) gestorObjeto);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();

                    }

                } else Log.w(TAG, "Error select gestor.", task.getException());
            });

        });

        // RECUPERA CONTRASEÑA
        olvidar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Recovery.class);
            startActivity(intent);
        });

        //CREAR CUENTA
        crearCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, NuevoGestor.class);
            startActivity(intent);
            finish();
        });

    }
}