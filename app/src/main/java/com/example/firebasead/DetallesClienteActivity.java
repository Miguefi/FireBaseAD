package com.example.firebasead;

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

import com.example.firebasead.Recycler.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class DetallesClienteActivity extends AppCompatActivity {

    private TextView nombre, apellido, dniCliente, dniGestor, telefono, contraseña;

    private Cliente perfil;
    private Button botonBorrar, botonActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_cliente);


        //inicializa las vistas,recupera los elementos de la interfaz
        initViews();
        // inicializa los listeners, agrega el comportamiento a los elementos de la interfaz de usuario (botones) cuando se hacen clic
        initListeners();

        // inicializa los valores, establece los valores en los elementos de la interfaz de usuario
        initValues();

    }

    private void initViews() {
        nombre = findViewById(R.id.nombre_detalle);
        apellido = findViewById(R.id.apellido_detalle);
        dniCliente = findViewById(R.id.dni_detalle);
        dniGestor = findViewById(R.id.dnigestor_detalle);
        telefono = findViewById(R.id.telefono_detalle);
        contraseña = findViewById(R.id.contraseña_detalle);
        botonBorrar = findViewById(R.id.boton_borrar);
        botonActualizar = findViewById(R.id.boton_actualizar);
    }

    private void initValues() {
        perfil = (Cliente) getIntent().getSerializableExtra("perfil");

        nombre.setText(perfil.getNombre());
        apellido.setText(perfil.getApellido());
        dniCliente.setText(perfil.getDni_cliente());
        dniGestor.setText(perfil.getDni_gestor());
        telefono.setText(perfil.getTel());
        contraseña.setText(perfil.getContraseña());
    }

    private void initListeners() {
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetallesClienteActivity.this, EditarCliente.class);
                intent.putExtra("perfil", perfil);
                startActivity(intent);
                finish();
            }
        });

        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClient();
            }
        });
    }

    private void deleteClient() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Clientes").whereEqualTo("DNI",  perfil.getDni_cliente())
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
                                            finalRef.delete().addOnSuccessListener(aVoid -> Toast.makeText
                                                            (DetallesClienteActivity.this, "cliente eliminado!", Toast.LENGTH_LONG).show())
                                                    .addOnFailureListener(e -> Toast.makeText(
                                                            DetallesClienteActivity.this, "Error eliminando cliente " + e, Toast.LENGTH_LONG).show());
                                            startActivity(new Intent(DetallesClienteActivity.this, ClienteMain.class));
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
}