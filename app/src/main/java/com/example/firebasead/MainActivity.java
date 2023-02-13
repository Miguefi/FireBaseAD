package com.example.firebasead;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasead.database.eventosDatabase.Gestor;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    Button gestor, clientes, archivos, eventos;
    Gestor gestorObject = new Gestor();
    TextView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestor = findViewById(R.id.gestor);
        clientes = findViewById(R.id.clientes);
        archivos = findViewById(R.id.archivos);
        eventos = findViewById(R.id.eventos);
        logOut = findViewById(R.id.logOut);

        // Gestor: Inicia Sesion
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            gestorObject = (Gestor) bundle.getSerializable("Gestor");
            Log.d(TAG,  gestorObject.toString());
        }

        gestor.setOnClickListener(v -> {
            actualizarDatosGestor();
            Intent intent = new Intent(getApplicationContext(), EditarGestor.class);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("Gestor", (Serializable) gestorObject);
            intent.putExtras(bundle1);
            startActivity(intent);
        });

        clientes.setOnClickListener(v -> {
            actualizarDatosGestor();
            Intent intent = new Intent(getApplicationContext(), ClienteMain.class);
            Bundle bundle12 = new Bundle();
            bundle12.putSerializable("Gestor", (Serializable) gestorObject);
            intent.putExtras(bundle12);
            startActivity(intent);

        });

        archivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NuevoArchivo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HashMap", (Serializable) gestorObject);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

        eventos.setOnClickListener(v -> {
            actualizarDatosGestor();
            Intent intent = new Intent(getApplicationContext(), EventoMain.class); //CAMBIAR ACTIVIDAD
            Bundle bundle14 = new Bundle();
            bundle14.putSerializable("Gestor", (Serializable) gestorObject);
            intent.putExtras(bundle14);
            startActivity(intent);

        });

        logOut.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });
    }

    private void actualizarDatosGestor() {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            gestorObject = (Gestor) bundle.getSerializable("Gestor");
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gestores = db.collection("Gestores");

        // VERIFICAR DNI GESTOR
        Query gestor = gestores.whereEqualTo("DNI", gestorObject.getDNI());

        gestor.get().addOnCompleteListener(task -> {
            String dni="", contraseña="", nombre="", apellido="", telefono="";

            if (task.isSuccessful()) {

                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    dni = document.get("DNI").toString();
                    contraseña = document.get("Contraseña").toString();
                    nombre = document.get("Nombre").toString();
                    apellido = document.get("Apellido").toString();
                    telefono = document.get("Num_Telf").toString();
                }

                Gestor gestorObjeto = new Gestor(dni, contraseña, nombre, apellido, telefono);
                setGestorObject(gestorObjeto); // ACTUALIZA gestorObjeto

            } else Log.w(TAG, "Error select gestor.", task.getException());
        });
    }

    private void setGestorObject(Gestor gestorObject) { this.gestorObject = gestorObject; }

}