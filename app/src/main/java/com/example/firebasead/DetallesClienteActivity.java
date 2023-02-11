package com.example.firebasead;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasead.Recycler.AdaptadorListado;
import com.example.firebasead.Recycler.PerfilesClientes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class DetallesClienteActivity extends AppCompatActivity {

    TextView nombre, apellido, dniCliente, dniGestor, telefono, contraseña;

    AdaptadorListado adapter;
    Button botonBorrar;
    Button botonActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_cliente);

        nombre = findViewById(R.id.nombre_detalle);
        apellido = findViewById(R.id.apellido_detalle);
        dniCliente = findViewById(R.id.dni_detalle);
        dniGestor = findViewById(R.id.dnigestor_detalle);
        telefono = findViewById(R.id.telefono_detalle);
        contraseña = findViewById(R.id.contraseña_detalle);
        botonBorrar = findViewById(R.id.boton_borrar);
        botonActualizar = findViewById(R.id.boton_actualizar);


        PerfilesClientes perfil = (PerfilesClientes) getIntent().getSerializableExtra("perfil");

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetallesClienteActivity.this, EditarCliente.class);
                startActivity(intent);
            }
        });


        String nom = perfil.getNombre();
        nombre.setText(nom);

        String ape = perfil.getApellido();
        apellido.setText(ape);

        String dni = perfil.getDni_cliente();
        dniCliente.setText(perfil.getDni_cliente());


        dniGestor.setText(perfil.getDni_gestor());
        telefono.setText(perfil.getTel());
        contraseña.setText(perfil.getContraseña());


        botonBorrar = findViewById(R.id.boton_borrar);
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Clientes").whereEqualTo("DNI", dni)
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
                                    new AlertDialog.Builder(DetallesClienteActivity.this)
                                            .setTitle("¿Estás seguro de que deseas eliminar el cliente?")
                                            .setMessage("El cliente desaparecerá de la base de datos y no podrás recuperarlo")
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    assert finalRef != null;
                                                    finalRef.delete().addOnSuccessListener(aVoid -> Toast.makeText(DetallesClienteActivity.this, "cliente eliminado!", Toast.LENGTH_LONG).show())
                                                            .addOnFailureListener(e -> Toast.makeText(DetallesClienteActivity.this, "Error eliminando cliente " + e, Toast.LENGTH_LONG).show());
                                                    startActivity(new Intent(DetallesClienteActivity.this, GestorMain.class));
                                                    finish();
                                                }
                                            })
                                            .setNegativeButton(android.R.string.no, null).show();
                                } else {
                                    Log.w(ConstraintLayoutStates.TAG, "Error select cliente.", task.getException());
                                }
                            }
                        });
            }

        });
    }

}