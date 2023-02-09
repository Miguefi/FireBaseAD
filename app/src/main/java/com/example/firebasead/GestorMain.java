package com.example.firebasead;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firebasead.InterfazUsuario.InterfazUsuario;
import com.example.firebasead.Recycler.AdaptadorListado;
import com.example.firebasead.Recycler.PerfilesClientes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class GestorMain extends AppCompatActivity {

    public static final int NUMERO_PERFILES = 5;
    private static final int CLAVE_LISTA = 55;
    private static final int CLAVE_AÑADIR = 56;
    RecyclerView RVClientes;
    AdaptadorListado aL;
    FloatingActionButton anadirCliente;
    private ArrayList<PerfilesClientes> perfiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_perfiles);

        anadirCliente = findViewById(R.id.añadir);

        RVClientes = (RecyclerView) findViewById(R.id.RVClientes);
        RVClientes.setHasFixedSize(true);
        RVClientes.setLayoutManager(new LinearLayoutManager(this));

        perfiles = new ArrayList(new PerfilesClientes().generarPerfiles(NUMERO_PERFILES));

        aL = new AdaptadorListado(perfiles);
        RVClientes.setAdapter(aL);


        ActivityResultLauncher controladorGestor = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    //Log.d(TAG, "Vuelve cancelado");
                    int code = result.getResultCode();
                    /*switch (code) {
                        case RESULT_CANCELED:
                            break;
                        case CLAVE_INGRESAR:
                            Log.d(TAG, "NUEVO INGRESO");
                            PerfilesImagen nuevoPerfil = (PerfilesImagen) result.getData().getSerializableExtra(mensaje);
                            completo.add(nuevoPerfil);
                            contactoDao.insert(nuevoPerfil);
                            AdaptadorListado = new AdaptadorListado(completo, listener);
                            rV.setAdapter(AdaptadorListado);
                            break;

                        case CLAVE_VOLVER:
                            AdaptadorListado = new AdaptadorListado(completo, listener);
                            rV.setAdapter(AdaptadorListado);
                            break;

                        case CLAVE_ELIMINAR:
                            Log.d(TAG, "NUEVO ELIMINADO");
                            //Intent elim = result.getData();
                            String nom = result.getData().getStringExtra(mensaje2);
                            Log.d(TAG, nom);
                            PerfilesImagen elimPerfil = contactoDao.findByName(nom);
                            Log.d(TAG, elimPerfil.getNombre());
                            completo.remove(elimPerfil);
                            contactoDao.delete(elimPerfil);
                            AdaptadorListado = new AdaptadorImagen(completo, listener);
                            rV.setAdapter(AdaptadorListado);
                            break;

                    }*/

                });

        aL.setClickListener(new AdaptadorListado.ItemClickListener() {
            @Override
            public void onClick(View view, int position, PerfilesClientes perfilesClientes) {
                Intent intent = new Intent(GestorMain.this, com.example.firebasead.InterfazUsuario.InterfazUsuario.class);
                intent.putExtra("Detalle", CLAVE_LISTA);
                controladorGestor.launch(intent);
            }
        });

        anadirCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestorMain.this, NuevoCliente.class);
                intent.putExtra("Añadir", CLAVE_AÑADIR);
                controladorGestor.launch(intent);
            }
        });
    /*
        anadirCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GestorMain.this, Ingresar.class);
                intent.putExtra(CLAVE_LISTA, completo);
                someActivityResultLauncher.launch(intent);
            }
        });*/
    }
}