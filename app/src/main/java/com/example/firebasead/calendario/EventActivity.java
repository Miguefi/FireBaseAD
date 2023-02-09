package com.example.firebasead.calendario;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.seradmin.R;
import com.google.android.material.appbar.MaterialToolbar;

public class EventActivity extends AppCompatActivity {

    EditText event_title, event_location, event_description;
    AppCompatCheckBox event_all_day;
    TextView event_start_date, event_start_time, event_end_date, event_end_time, event_repetition;
    ImageView event_color, event_show_on_map;
    MaterialToolbar event_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        EditText event_title = (EditText) findViewById(R.id.event_title);
        EditText event_location = (EditText) findViewById(R.id.event_location);
        ImageView event_show_on_map = findViewById(R.id.event_show_on_map);
        EditText event_description = (EditText) findViewById(R.id.event_description);
        AppCompatCheckBox event_all_day = (AppCompatCheckBox) findViewById(R.id.event_all_day);
        TextView event_start_date = (TextView) findViewById(R.id.event_start_date);
        TextView event_start_time = (TextView) findViewById(R.id.event_start_time);
        TextView event_end_date = (TextView) findViewById(R.id.event_end_date);
        TextView event_end_time = (TextView) findViewById(R.id.event_end_time);
        TextView event_repetition = (TextView) findViewById(R.id.event_repetition);
        ImageView event_color = (ImageView) findViewById(R.id.event_color);

//        MaterialToolbar event_toolbar = findViewById(R.id.event_toolbar);
//        getMenuInflater().inflate(R.menu.menu_event, (Menu) event_toolbar);

        //Selección Fecha y Horarios
        event_start_date.setOnClickListener(v -> setupStartDate());
//        event_start_time.setOnClickListener(v -> setupStartTime());
//        event_end_date.setOnClickListener(v -> setupEndDate());
//        event_end_time.setOnClickListener(v -> setupEndTime());

        

        //Abrir Mapa
//        event_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                abrirMapa();
//            }
//        });
//        event_show_on_map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                abrirMapa();
//            }
//        });






    }

    private void setupStartDate() {

    }


    private void abrirMapa(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MapaFragment mapaFragment = new MapaFragment();
        fragmentTransaction.add(mapaFragment, "MapaFragment");
        fragmentTransaction.commit();
    }
}