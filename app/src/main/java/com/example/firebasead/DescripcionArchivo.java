package com.example.firebasead;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayoutStates;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasead.RecyclerArchivos.Archivos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

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
        borrar = findViewById(R.id.idBorrarArchivo);

        Archivos archivo = (Archivos) getIntent().getSerializableExtra("archivo");

        String nombreArchivo = archivo.getNombre();
        nombre.setText("Nombre: " + archivo.getNombre());
        propietario.setText("Propietario: " + archivo.getPropietario());
        dniPropietario.setText("DNI: " + archivo.getDniCliente());
        extension.setText("Extensión: " + archivo.getExtension());
        fecha.setText("Fecha fin: " + archivo.getFechaCreacion());

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Archivos").whereEqualTo("Nombre", nombreArchivo)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentReference ref = null;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(ConstraintLayoutStates.TAG, document.getId() + " => " + document.getData());
                                        ref = document.getReference();
                                    }

                                    // DELETE
                                    DocumentReference finalRef = ref;
                                    new AlertDialog.Builder(DescripcionArchivo.this)
                                            .setTitle("¿Estás seguro de que deseas eliminar el archivo?")
                                            .setMessage("El archivo desaparecerá de la base de datos y no podrás recuperarlo")
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    assert finalRef != null;
                                                    finalRef.delete().addOnSuccessListener(aVoid -> Toast.makeText(DescripcionArchivo.this, "Archivo eliminado!", Toast.LENGTH_LONG).show())
                                                            .addOnFailureListener(e -> Toast.makeText(DescripcionArchivo.this, "Error eliminando archivo " + e, Toast.LENGTH_LONG).show());
                                                    startActivity(new Intent(DescripcionArchivo.this, NuevoArchivo.class));
                                                    finish();
                                                }
                                            })
                                            .setNegativeButton(android.R.string.no, null).show();
                                } else {
                                    Log.w(ConstraintLayoutStates.TAG, "Error select documento.", task.getException());
                                }
                            }
                        });
            }

        });
    }
}