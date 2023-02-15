package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasead.database.eventosDatabase.Gestor;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;

public class FalseRecovery extends AppCompatActivity {

    EditText telefono, escribirSMS;
    Button mandarSMS;
    TextView alert;
    ActivityResultLauncher<String> requestPermissionLauncherSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        telefono = findViewById(R.id.Phone);
        escribirSMS = findViewById(R.id.escribirSMS);
        mandarSMS = findViewById(R.id.SMS);
        alert = findViewById(R.id.alert);

        mandarSMS.setOnClickListener(v -> {

            String tel = telefono.getText().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference gestores = db.collection("Gestores");
            Query gestor = gestores.whereEqualTo("Num_Telf", tel);

            gestor.get().addOnCompleteListener(task -> {
                String dni1 ="", contraseña="", nombre="", apellido="", telefono="";

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        dni1 = document.get("DNI").toString();
                        contraseña = document.get("Contraseña").toString();
                        nombre = document.get("Nombre").toString();
                        apellido = document.get("Apellido").toString();
                        telefono = document.get("Num_Telf").toString();

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

                        escribirSMS.setVisibility(View.VISIBLE);

                        escribirSMS.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {}
                            @Override
                            public void afterTextChanged(Editable s) {

                                if(s.length()==6){

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("Gestor", (Serializable) gestorObjeto);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });
                    }

                } else Log.w(TAG, "Error select telefono.", task.getException());

            });
        });

    }

}