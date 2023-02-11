package com.example.firebasead;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.firebasead.Recycler.AdaptadorListado;
import com.example.firebasead.Recycler.PerfilesClientes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GestorMain extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private RecyclerView RVClientes;
    private AdaptadorListado al;
    FloatingActionButton anadirCliente;
    private ArrayList<PerfilesClientes> perfiles;
    private FirebaseFirestore db;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_perfiles);


        searchView = findViewById(R.id.buscador);
        searchView.setOnQueryTextListener(this);

        perfiles = new ArrayList<>();
        RVClientes = findViewById(R.id.RVClientes);
        RVClientes.setLayoutManager(new LinearLayoutManager(this));




        db = FirebaseFirestore.getInstance();
        CollectionReference clientes_firebase = db.collection("Clientes");

        clientes_firebase.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    PerfilesClientes perfilesClientes = new PerfilesClientes();
                    perfilesClientes.setNombre(document.get("Nombre").toString());
                    perfilesClientes.setApellido(document.get("Apellido").toString());
                    perfilesClientes.setDni_cliente(document.get("DNI").toString());
                    perfilesClientes.setDni_gestor(document.get("DNI_Gestor").toString());
                    perfilesClientes.setTel(document.get("Num_Tel").toString());
                    perfilesClientes.setContraseña(document.get("Contraseña").toString());
                    perfiles.add(perfilesClientes);


                }
                al = new AdaptadorListado(perfiles,GestorMain.this);
                RVClientes.setAdapter(al);
                al.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GestorMain.this, "Error al cargar los datos ", Toast.LENGTH_SHORT).show();

            }
        });



        anadirCliente=findViewById(R.id.añadir_cliente);
        anadirCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestorMain.this, AgregarCliente.class));
                finish();
            }
        });



    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        al.filtrado(s);

        return false;
    }

}