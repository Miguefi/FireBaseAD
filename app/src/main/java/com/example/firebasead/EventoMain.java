package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasead.calendario.AdaptadorEventos;
import com.example.firebasead.database.Listeners.RetrievalEventListener;
import com.example.firebasead.database.eventosDatabase.Evento;
import com.example.firebasead.database.eventosDatabase.EventoDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

//import java.sql.Timestamp;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventoMain extends AppCompatActivity {

    public static final int NUMERO_PERFILES = 5;
    private static final int CLAVE_LISTA = 55;
    private static final int CLAVE_AÑADIR = 56;
    RecyclerView RVEventos;
    AdaptadorEventos aE = new AdaptadorEventos(new ArrayList<>());
    FloatingActionButton anyadirEvento;
    private ArrayList<Evento> eventos = new ArrayList<>();
    String pattern = "dd-MM HH:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_eventos);

        anyadirEvento = findViewById(R.id.añadir);

        RVEventos = (RecyclerView) findViewById(R.id.RVEventos);
        RVEventos.setHasFixedSize(true);
        RVEventos.setLayoutManager(new LinearLayoutManager(this));

//        EventoDao eventoDao = new EventoDao();
//
//        eventoDao.getAll(new RetrievalEventListener<List<Evento>>() {
//            @Override
//            public void OnDataRetrieved(List<Evento> eventos) {
//                //aE = new AdaptadorEventos(new ArrayList<>(eventos));
//                for (Evento evento: eventos) {
//                    Toast.makeText(EventoMain.this, evento.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Eventos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                DocumentReference ref = document.getReference();
                                Evento evento = new Evento();
                                Timestamp timestamp = (Timestamp) document.get("Inicio");
                                evento.setId(document.getId());
                                evento.setTitulo(document.get("Titulo").toString());
                                evento.setInicio(simpleDateFormat.format(timestamp.toDate()));
                                eventos.add(evento);
                                Log.d(TAG, evento.getTitulo()+" " +evento.getInicio()+" "+evento.getId());
                            }
                            Log.d(TAG,eventos.get(0).getTitulo()+ " " + eventos.get(0).getInicio());
                            aE = new AdaptadorEventos(eventos);
                            RVEventos.setAdapter(aE);
                            aE.setClickListener(new AdaptadorEventos.ItemClickListener() {
                                @Override
                                public void onClick(View view, int position, Evento evento) {
                                    Intent intent = new Intent(EventoMain.this, com.example.firebasead.EventoDetalle.class);
                                    intent.putExtra("Detalle", CLAVE_LISTA);
                                    intent.putExtra("Evento" , evento);
                                    startActivity(intent);
                                    Toast.makeText(EventoMain.this, "Estoy pinchando", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        Log.d(TAG, aE.getItemCount()+" items");
        //aL = new AdaptadorListado(perfiles);


        ActivityResultLauncher controladorGestor = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    //Log.d(TAG, "Vuelve cancelado");
                    int code = result.getResultCode();
                    /*switch (code) {
                        case RESULT_CANCELED:
                            break;
                        case CLAVE_INGRESAR:
                            Log.d(TAG, "NUEVO INGRESO");
                            PerfilesImagen nuevoPerfil = (PerfilesImagen) result.getData().getSerializableExtra(mensaje);
                            completo.add(nuevoPerfil);
                            contactoDao.insert(nuevoPerfil);
                            AdaptadorListado = new AdaptadorListado(completo, listener);
                            rV.setAdapter(AdaptadorListado);
                            break;

                        case CLAVE_VOLVER:
                            AdaptadorListado = new AdaptadorListado(completo, listener);
                            rV.setAdapter(AdaptadorListado);
                            break;

                        case CLAVE_ELIMINAR:
                            Log.d(TAG, "NUEVO ELIMINADO");
                            //Intent elim = result.getData();
                            String nom = result.getData().getStringExtra(mensaje2);
                            Log.d(TAG, nom);
                            PerfilesImagen elimPerfil = contactoDao.findByName(nom);
                            Log.d(TAG, elimPerfil.getNombre());
                            completo.remove(elimPerfil);
                            contactoDao.delete(elimPerfil);
                            AdaptadorListado = new AdaptadorImagen(completo, listener);
                            rV.setAdapter(AdaptadorListado);
                            break;

                    }*/

                });

        anyadirEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventoMain.this, NuevoEvento.class);
                intent.putExtra("Añadir", CLAVE_AÑADIR);
                controladorGestor.launch(intent);
            }
        });
    /*
        anadirCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GestorMain.this, Ingresar.class);
                intent.putExtra(CLAVE_LISTA, completo);
                someActivityResultLauncher.launch(intent);
            }
        });*/

        // Initialize Firestore
//        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
//                .setProjectId("your-project-id")
//                .build();
//        Firestore db = firestoreOptions.getService();
//
//        // Get a reference to the collection
//        com.google.cloud.firestore.CollectionReference ref = db.collection("your-collection-name");
//
//        // Get all documents in the collection
//        ref.get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                        System.out.println(document.getId() + " => " + document.getData());
//                    }
//                });

//        try {
//            FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");
//
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                    .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com")
//                    .build();
//
//            FirebaseApp.initializeApp(options);
//
//            FirebaseFirestore db2 = FirestoreOptions.getDefaultInstance().getService();
//
//            List<QueryDocumentSnapshot> documents = db2.collection("collectionName").get().get().getDocuments();
//
//            for (QueryDocumentSnapshot document : documents) {
//                System.out.println("Document data: " + document.getData());
//            }
//
//        } catch (Exception e) {
//            System.out.println("Error reading documents: " + e);
//        }

    }
}