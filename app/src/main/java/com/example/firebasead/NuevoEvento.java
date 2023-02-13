package com.example.firebasead;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.firebasead.database.eventosDatabase.Evento;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NuevoEvento extends AppCompatActivity {

    public static final int CLAVE_INSERTADO = 55;

    EditText titulo, fechaInicio, horaInicio, fechaFin, horaFin, latitud, longitud, descripcion;
    Button crearEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        titulo = findViewById(R.id.tituloCrearEvento);
        fechaInicio = findViewById(R.id.fechaInicioCrearEvento);
        horaInicio = findViewById(R.id.horaInicioCrearEvento);
        fechaFin = findViewById(R.id.fechaFinCrearEvento);
        horaFin = findViewById(R.id.horaFinCrearEvento);
        latitud = findViewById(R.id.latitudCrearEvento);
        longitud = findViewById(R.id.longitudCrearEvento);
        descripcion = findViewById(R.id.descripcionCrearEvento);
        crearEvento = findViewById(R.id.crearEvento);

        ManejadorFechas manejadorFechaSalida = new ManejadorFechas(fechaInicio);
        ManejadorFechas manejadorFechaVuelta = new ManejadorFechas(fechaFin);

        fechaInicio.setOnClickListener(manejadorFechaSalida);
        fechaFin.setOnClickListener(manejadorFechaVuelta);

        ManejadorHoras manejadorHoraSalida = new ManejadorHoras(horaInicio);
        ManejadorHoras manejadorHoraVuelta = new ManejadorHoras(horaFin);

        horaInicio.setOnClickListener(manejadorHoraSalida);
        horaFin.setOnClickListener(manejadorHoraVuelta);

        crearEvento.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               Map<String, Object> evento = prepararEvento();

               db.collection("Eventos").add(evento).addOnSuccessListener(documentReference -> {
                           Log.d(TAG, "Insertado evento con ID: " + documentReference.getId());

                       })
                       .addOnFailureListener(e -> Log.w(TAG, "Error insertando evento", e));

               Intent intent = new Intent(NuevoEvento.this, EventoMain.class);
               setResult(CLAVE_INSERTADO , intent);
               NuevoEvento.super.onBackPressed();
               finish();

           }

        });

    }

    public class ManejadorFechas implements View.OnClickListener {

        EditText fecha;

        public ManejadorFechas() {

        }

        public ManejadorFechas(EditText fecha) {
            this.fecha = fecha;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.fechaInicioCrearEvento:
                    showDatePickerDialog(fechaInicio);
                    break;

                case R.id.fechaFinCrearEvento:
                    showDatePickerDialog(fechaFin);
                    break;
            }
        }

    }

    public class ManejadorHoras implements View.OnClickListener {

        EditText hora;

        public ManejadorHoras() {

        }

        public ManejadorHoras(EditText hora) {
            this.hora = hora;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.horaInicioCrearEvento:
                    showTimePickerDialog(horaInicio);
                    break;

                case R.id.horaFinCrearEvento:
                    showTimePickerDialog(horaFin);
                    break;
            }
        }
    }

    private void showDatePickerDialog(final EditText fecha) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "-" + (month+1) + "-" + year;
                fecha.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showTimePickerDialog(final EditText hora) {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                // +1 because January is zero
                final String selectedHour = hourOfDay + ":" + minute;
                hora.setText(selectedHour);
            }
        });

        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public Map<String, Object> prepararEvento () {

        String s_titulo = titulo.getText().toString(), s_descripcion = descripcion.getText().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String stringDateInicio = fechaInicio.getText().toString() + " " + horaInicio.getText().toString();
        String stringDateFin = fechaFin.getText().toString() + " " + horaFin.getText().toString();
        Date dateInicio = null;
        Date dateFin = null;

        try {
            dateInicio = simpleDateFormat.parse(stringDateInicio);
            dateFin = simpleDateFormat.parse(stringDateFin);
        } catch (ParseException e) {
            Log.d(TAG, e.toString());
        }

        Timestamp timeStampInicio = new Timestamp(dateInicio);
        Timestamp timeStampFin = new Timestamp(dateFin);
        Float s_latitud = Float.valueOf(latitud.getText().toString());
        Float s_longitud = Float.valueOf(longitud.getText().toString());

        Map<String, Object> evento = new HashMap<>();

        evento.put("Titulo", s_titulo);
        evento.put("Inicio", timeStampInicio);
        evento.put("Fin", timeStampFin);
        evento.put("Latitud", s_latitud);
        evento.put("Longitud", s_longitud);
        evento.put("Descripcion", s_descripcion);

        return evento;

    }
}