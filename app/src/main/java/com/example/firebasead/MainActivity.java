package com.example.firebasead;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasead.database.eventosDatabase.Gestor;

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
            Log.d( "GESTOR MAIN",  gestorObject.toString());
            setGestorObject(gestorObject);
        }

        gestor.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditarGestor.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("Gestor", (Serializable) getGestorObject());
            Log.d( "GESTOR MAIN - EDIT",  getGestorObject().toString());
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
        });

        clientes.setOnClickListener(v -> {
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
            Intent intent = new Intent(getApplicationContext(), EventoMain.class);
            Bundle bundle14 = new Bundle();
            bundle14.putSerializable("Gestor", (Serializable) gestorObject);
            intent.putExtras(bundle14);
            startActivity(intent);

        });

        logOut.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("¿Estás seguro de que deseas salir de su cuenta?")
                    .setIcon(R.drawable.ic_baseline_logout_24)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {

                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();

                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            gestorObject = (Gestor) getIntent().getSerializableExtra("Gestor");
            Log.d( "MAIN RESUME",  gestorObject.toString());
            setGestorObject(gestorObject);
        }
    }


    private void setGestorObject(Gestor gestorObject) { this.gestorObject = gestorObject; }

    private Gestor getGestorObject() { return gestorObject; }

}