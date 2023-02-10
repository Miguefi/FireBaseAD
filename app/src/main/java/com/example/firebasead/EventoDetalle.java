package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasead.database.eventosDatabase.Evento;
import com.example.firebasead.database.eventosDatabase.Gestor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class EventoDetalle extends AppCompatActivity {

    TextView tituloEventoDetalle, inicioEventoDetalle, finEventoDetalle, latitudEventoDetalle, longitudEventoDetalle, descripcionEventoDetalle;
    Button modificarEvento, eliminarEvento;

    String pattern = "dd-MM HH:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalle);

        tituloEventoDetalle = findViewById(R.id.tituloEventoDetalle);
        inicioEventoDetalle = findViewById(R.id.inicioEventoDetalle);
        finEventoDetalle = findViewById(R.id.finEventoDetalle);
        latitudEventoDetalle = findViewById(R.id.latitudEventoDetalle);
        longitudEventoDetalle = findViewById(R.id.longitudEventoDetalle);
        descripcionEventoDetalle = findViewById(R.id.descripcionEventoDetalle);

        modificarEvento = findViewById(R.id.modificarEvento);
        eliminarEvento = findViewById(R.id.eliminarEvento);

        Evento evento = (Evento) getIntent().getSerializableExtra("Evento");

//        tituloEventoDetalle.setText(evento.getTitulo());
//        inicioEventoDetalle.setText(evento.getInicio());
//        finEventoDetalle.setText(evento.getFin().toString());
//        latitudEventoDetalle.setText(evento.getLatitud() + "");
//        longitudEventoDetalle.setText(evento.getLongitud() + "");
//        descripcionEventoDetalle.setText(evento.getDescripcion());

        Log.d(TAG, evento.getId());


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Eventos").whereEqualTo("__name__", evento.getId()).limit(1).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Timestamp timestampInicio = (Timestamp) document.get("Inicio");
                                Timestamp timestampFin = (Timestamp) document.get("Fin");
                                tituloEventoDetalle.setText(tituloEventoDetalle.getText() + " " + document.get("Titulo").toString());
                                inicioEventoDetalle.setText(inicioEventoDetalle.getText() + " " + simpleDateFormat.format(timestampInicio.toDate()));
                                finEventoDetalle.setText(finEventoDetalle.getText() + " " + simpleDateFormat.format(timestampFin.toDate()));
                                latitudEventoDetalle.setText(latitudEventoDetalle.getText() + " " + document.get("Latitud").toString());
                                longitudEventoDetalle.setText(longitudEventoDetalle.getText() + " " + document.get("Longitud").toString());
                                descripcionEventoDetalle.setText(descripcionEventoDetalle.getText() + " " + document.get("Descripcion").toString());
                                //Log.d(TAG, evento.getTitulo()+" " +evento.getInicio());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        //Log.d(TAG, aE.getItemCount()+" items");
        //aL = new AdaptadorListado(perfiles);

        eliminarEvento.setOnClickListener((v) -> {
            db.collection("Eventos").whereEqualTo("__name__", evento.getId()).limit(1).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentReference ref = null;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    ref = document.getReference();
                                }

                                // DELETE
                                new AlertDialog.Builder(EventoDetalle.this)
                                        .setTitle("¿Estás seguro de que deseas eliminar el evento?")
                                        .setMessage("El evento desaparecerá de la base de datos y no podrás recuperarlo")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                Toast.makeText(EventoDetalle.this, "Yaay", Toast.LENGTH_SHORT).show();
                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                                //assert ref != null;
                                //ref.delete().addOnSuccessListener(aVoid -> Toast.makeText(EventoDetalle.this, "Evento eliminado!", Toast.LENGTH_LONG).show())
                                  //      .addOnFailureListener(e -> Toast.makeText(EventoDetalle.this, "Error eliminando evento " + e, Toast.LENGTH_LONG).show());
                            } else {
                                Log.w(TAG, "Error select documento.", task.getException());
                            }
                        }
                    });
        });

        modificarEvento.setOnClickListener((v) -> {
            db.collection("Eventos").whereEqualTo("__name__", evento.getId()).limit(1).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentReference docRef = null;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        // UPDATE
                        DocumentReference ref = document.getReference();
//                        ref.update("Nombre", nom);
//                        ref.update("Apellido", ape);
//                        ref.update("Contraseña", con);
//                        ref.update("Num_Telf", tel);

                        docRef = ref;

                    }

                    //Gestor gestorObjeto = new Gestor(s_dni, con, nom, ape, tel);

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("timestamp", FieldValue.serverTimestamp());

                    // ESPERAR A UNA RESPUESTA DEL SERVIDOR, ACTUALIZACIONES COMPLETADAS
                    assert docRef != null;
                    docRef.update(updates).addOnCompleteListener(task1 -> {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Bundle bundle1 = new Bundle();
                        //bundle1.putSerializable("Gestor", (Serializable) gestorObjeto);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                    });

                } else {
                    Log.w(TAG, "Error select documento.", task.getException());
                }
            });

        });
    }
}