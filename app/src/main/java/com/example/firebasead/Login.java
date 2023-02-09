package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasead.database.eventosDatabase.Gestor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    ImageView logo;
    Button login;
    EditText usuario, contraseña;
    TextView crearCuenta, olvidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        login = findViewById(R.id.sms);
        usuario = findViewById(R.id.user);
        contraseña = findViewById(R.id.password);
        usuario = findViewById(R.id.user);
        crearCuenta = findViewById(R.id.createAccount);
        olvidar = findViewById(R.id.forget);
        logo = findViewById(R.id.logo);

        //LOG IN
        login.setOnClickListener(v -> {
            String user = usuario.getText().toString();
            String pass = contraseña.getText().toString();

            CollectionReference gestores = db.collection("Gestores");
            Query gestor = gestores.whereEqualTo("DNI", user).whereEqualTo("Contraseña", pass);

            gestor.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());

                            Gestor gestorObjeto = new Gestor(document.get("DNI").toString(), document.get("Contraseña").toString(), document.get("Nombre").toString(), document.get("Apellido").toString(), document.get("Num_Telf").toString());

                            Intent intent = new Intent(getApplicationContext(), GestorMain.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Gestor", (Serializable) gestorObjeto);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
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
        });

    }
}