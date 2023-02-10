package com.example.firebasead;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.firebasead.RecyclerArchivos.Archivos;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DescripcionArchivo extends AppCompatActivity {

    private TextView nombre, propietario, dniPropietario, extension, fecha;
    private Button modificar, borrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_archivo);

        nombre = findViewById(R.id.idNombreArchivo);
        propietario = findViewById(R.id.idPropietarioArchivo);
        dniPropietario = findViewById(R.id.idDniClienteArchivo);
        extension = findViewById(R.id.idExtensionArchivo);
        fecha = findViewById(R.id.idFechaArchivo);

        Archivos archivo = (Archivos) getIntent().getSerializableExtra("archivo");

        nombre.setText(archivo.getNombre());
        propietario.setText(archivo.getPropietario());
        dniPropietario.setText(archivo.getDniCliente());
        extension.setText(archivo.getExtension());
        fecha.setText(archivo.getFechaCreacion());
    }
}