package com.example.firebasead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button gestor, clientes, archivos, eventos;
    Map<String, Object> gestorObject = new HashMap<>();

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
            gestorObject = (Map<String, Object>) bundle.getSerializable("HashMap");
            Toast toast = Toast.makeText(getApplicationContext(), "Sesión iniciada "+gestorObject.toString(), Toast.LENGTH_LONG);
            toast.show();
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