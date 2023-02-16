package com.example.firebasead;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasead.RecyclerArchivos.AdaptadorListado;
import com.example.firebasead.RecyclerArchivos.Archivos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NuevoArchivo extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton agregar;
    AdaptadorListado adapter;
    private FirebaseFirestore db;
    private List<Archivos> listaPerfiles;
    private SearchView buscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_archivo);
        buscador = findViewById(R.id.buscadorArchivo);
        buscador.setBackgroundResource(R.drawable.ic_search);
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String nombre) {
                adapter.filtrado(nombre);
                return false;
            }
        });

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
                    archivo.setId(document.getLong("ID"));
                    archivo.setNombre(document.get("Nombre").toString());
                    archivo.setPropietario(document.get("Propietario").toString());
                    archivo.setDniCliente(document.get("DNI_Cliente").toString());
                    archivo.setFechaCreacion(document.get("FechaCreacion").toString());
                    archivo.setExtension(document.get("Extencion").toString());
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

        agregar = findViewById(R.id.añadirArchivo);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NuevoArchivo.this, AgregarArchivo.class));
                finish();
            }
        });


    }

}