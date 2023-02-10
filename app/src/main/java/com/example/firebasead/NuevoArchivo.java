package com.example.firebasead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firebasead.RecyclerArchivos.AdaptadorListado;
import com.example.firebasead.RecyclerArchivos.Archivos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NuevoArchivo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button agregar;
    private AdaptadorListado adapter;
    FirebaseFirestore db;
    private List<Archivos> listaPerfiles;

    private  List<Archivos> archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_archivo);

        listaPerfiles = new ArrayList<>();
        recyclerView = findViewById(R.id.idRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(NuevoArchivo.this));

        db = FirebaseFirestore.getInstance();
        CollectionReference archivosRef = db.collection("Archivos");

        archivosRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Archivos archivo = new Archivos();
                    archivo.setId(document.get("ID").toString());
                    archivo.setNombre(document.get("Nombre").toString());
                    archivo.setPropietario(document.get("Propietario").toString());
                    archivo.setDniCliente(document.get("DNI_Cliente").toString());
                    archivo.setFechaCreacion(document.get("FechaCreacion").toString());
                    archivo.setExtension(document.get("Extencion").toString());
                    archivo.setIdCarpeta(document.get("ID_Carpeta").toString());
                    listaPerfiles.add(archivo);
                }

                adapter = new AdaptadorListado(listaPerfiles);
                recyclerView.setAdapter(adapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NuevoArchivo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}