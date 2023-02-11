package com.example.firebasead;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayoutStates;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasead.RecyclerArchivos.Archivos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ModificarArchivo extends AppCompatActivity {

    private TextView nombre, propietario, dniPropietario, fechaFin, extension;
    private Button actualizar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_archivo);
        nombre = findViewById(R.id.modificarNombreArchivo);
        propietario= findViewById(R.id.modificarNombrePropietario);
        dniPropietario = findViewById(R.id.modificarDniPropietarioCrear);
        fechaFin = findViewById(R.id.modificarFechaFinCrearArchivo);
        extension = findViewById(R.id.modificarExtensionCrearArchivo);
        actualizar = findViewById(R.id.modificarArchivo);

        Archivos archivo = (Archivos) getIntent().getSerializableExtra("modificarArchivo");

        //Para pintar la informacion ya existente del usuario en los plain text
        long idMod = archivo.getId();
        nombre.setText( archivo.getNombre());
        propietario.setText( archivo.getPropietario());
        dniPropietario.setText(archivo.getDniCliente());
        extension.setText(archivo.getExtension());
        fechaFin.setText(archivo.getFechaCreacion());


        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Archivos").whereEqualTo("ID", idMod)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentReference ref = null;
                                    String nombreMod = nombre.getText().toString(), propietarioMod = propietario.getText().toString(),
                                    dniMod = dniPropietario.getText().toString(), fechaMod = fechaFin.getText().toString(), extensionMod = extension.getText().toString();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        ref = document.getReference();
                                    }
                                    // UPDATE
                                    Map<String, Object> modificado = new HashMap<>();
                                    modificado.put("Nombre", nombreMod);
                                    modificado.put("Propietario", propietarioMod);
                                    modificado.put("DNI_Cliente", dniMod);
                                    modificado.put("FechaCreacion", fechaMod);
                                    modificado.put("Extencion", extensionMod);
                                    assert ref != null;
                                    ref.update(modificado)
                                            .addOnSuccessListener(aVoid -> Toast.makeText(ModificarArchivo.this, "Archivo actualizado!", Toast.LENGTH_LONG).show())
                                            .addOnFailureListener(e -> Toast.makeText(ModificarArchivo.this, "Error actualizando archivo " + e, Toast.LENGTH_LONG).show());
                                            startActivity(new Intent(ModificarArchivo.this, NuevoArchivo.class));
                                            finish();
                                } else {
                                    Log.w(ConstraintLayoutStates.TAG, "Error select documento.", task.getException());
                                }
                            }
                        });

            }
        });


    }
}