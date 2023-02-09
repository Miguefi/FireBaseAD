package com.example.firebasead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firebasead.database.eventosDatabase.Gestor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button gestor, clientes, archivos, eventos;
    Gestor gestorObject = new Gestor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestor = findViewById(R.id.gestor);
        clientes = findViewById(R.id.clientes);
        archivos = findViewById(R.id.archivos);
        eventos = findViewById(R.id.eventos);

        Bundle bundle = this.getIntent().getExtras();

        // Gestor : Inicia Sesion
        if(bundle != null) {
            gestorObject = (Gestor) bundle.getSerializable("Gestor");
            Toast.makeText(getApplicationContext(), "Sesión iniciada "+gestorObject.toString(), Toast.LENGTH_LONG).show();
        }

        gestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GestorMain.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HashMap", (Serializable) gestorObject);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GestorMain.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HashMap", (Serializable) gestorObject);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        gestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GestorMain.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HashMap", (Serializable) gestorObject);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GestorMain.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("HashMap", (Serializable) gestorObject);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}