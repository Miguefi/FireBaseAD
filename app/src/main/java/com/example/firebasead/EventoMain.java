package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasead.calendario.AdaptadorEventos;
import com.example.firebasead.database.eventosDatabase.Evento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventoMain extends AppCompatActivity {

    private static final int CLAVE_LISTA = 55;
    private static final int CLAVE_AÑADIR = 50;
    RecyclerView RVEventos;
    AdaptadorEventos aE = new AdaptadorEventos(new ArrayList<>());
    FloatingActionButton anyadirEvento;
    private ArrayList<Evento> eventos = new ArrayList<>();
    String pattern = "dd-MM-yy HH:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    SearchView buscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_eventos);

        buscador.findViewById(R.id.buscador);
        buscador.setBackgroundResource(R.drawable.ic_search);

        anyadirEvento = findViewById(R.id.añadir);

        RVEventos = (RecyclerView) findViewById(R.id.RVEventos);
        RVEventos.setHasFixedSize(true);
        RVEventos.setLayoutManager(new LinearLayoutManager(this));

        poblarRecyclerView();

        anyadirEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventoMain.this, NuevoEvento.class);
                intent.putExtra("Añadir", CLAVE_AÑADIR);
                controladorEventos.launch(intent);
            }
        });

    }

    ActivityResultLauncher controladorEventos = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                int code = result.getResultCode();
                switch (code) {
                    case RESULT_CANCELED:
                        Log.d(TAG, "Vuelve cancelado");
                        break;
                    case NuevoEvento.CLAVE_INSERTADO:
                        Log.d(TAG, "EVENTO INSERTADO");
                        poblarRecyclerView();
                        break;

                    case EventoDetalle.CLAVE_MODIFICADO:
                        Log.d(TAG, "EVENTO MODIFICADO");
                        poblarRecyclerView();
                        break;

                    case EventoDetalle.CLAVE_ELIMINADO:
                        Log.d(TAG, "EVENTO ELIMINADO");
                        poblarRecyclerView();
                        break;

                }

            }
    );

    public void poblarRecyclerView() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Eventos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    eventos = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference ref = document.getReference();
                        Evento evento = new Evento();
                        Timestamp timestamp = (Timestamp) document.get("Inicio");
                        evento.setId(document.getId());
                        evento.setTitulo(document.get("Titulo").toString());
                        evento.setFechaInicio(simpleDateFormat.format(timestamp.toDate()));
                        eventos.add(evento);
                    }
                    aE = new AdaptadorEventos(eventos);
                    RVEventos.setAdapter(aE);
                    aE.setClickListener(new AdaptadorEventos.ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, Evento evento) {
                            Intent intent = new Intent(EventoMain.this, com.example.firebasead.EventoDetalle.class);
                            intent.putExtra("Detalle", CLAVE_LISTA);
                            intent.putExtra("Evento" , evento);
                            controladorEventos.launch(intent);
                        }
                    });
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}