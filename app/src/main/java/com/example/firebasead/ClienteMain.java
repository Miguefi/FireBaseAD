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

import com.example.firebasead.Recycler.AdaptadorListado;
import com.example.firebasead.Recycler.Cliente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClienteMain extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private RecyclerView RVClientes;
    private AdaptadorListado adaptadorListado;
    FloatingActionButton anadirCliente;
    private ArrayList<Cliente> perfiles;
    private FirebaseFirestore db;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_main);

        // Inicialización de la SearchView y asignación del listener para detectar cambios en la búsqueda
        searchView = findViewById(R.id.buscador);
        searchView.setBackgroundResource(R.drawable.ic_search);
        searchView.setOnQueryTextListener(this);

        // Inicialización de la lista de perfiles
        perfiles = new ArrayList<>();

        // Inicialización del RecyclerView y asignación de un layout linear
        RVClientes = findViewById(R.id.RVClientes);
        RVClientes.setLayoutManager(new LinearLayoutManager(this));



        // Obtención de una instancia de FirebaseFirestore
        db = FirebaseFirestore.getInstance();
        //Obtención de la colección "Clientes" en Firebase
        CollectionReference clientes_firebase = db.collection("Clientes");
        // Obtiene los documentos en la colección de clientes y los agrega a la lista de perfiles
        clientes_firebase.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    perfiles.add(new Cliente(
                            document.get("Nombre").toString(),
                            document.get("Apellido").toString(),
                            document.get("DNI").toString(),
                            document.get("DNI_Gestor").toString(),
                            document.get("Num_Tel").toString(),
                            document.get("Contraseña").toString()
                    ));


                }
                adaptadorListado = new AdaptadorListado(perfiles, ClienteMain.this);
                RVClientes.setAdapter(adaptadorListado);
                adaptadorListado.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ClienteMain.this, "Error al cargar los datos ", Toast.LENGTH_SHORT).show();

            }
        });


        // Inicializa el botón flotante y establece un listener para la acción de agregar un cliente
        anadirCliente=findViewById(R.id.añadir_cliente);
        anadirCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre una nueva actividad para agregar un cliente
                startActivity(new Intent(ClienteMain.this, AgregarCliente.class));
                finish();
            }
        });



    }

    // Filtra la lista de perfiles según el texto ingresado en la vista de búsqueda
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adaptadorListado.filtrado(s);

        return false;
    }

}