package com.example.firebasead.calendario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seradmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Calendario extends AppCompatActivity {

    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        add = findViewById(R.id.add);
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendario.this, EventActivity.class);
                startActivity(intent);
            }
        });

        getSupportFragmentManager().
                beginTransaction().add(R.id.fragments_holder, new MonthFragment()).
                commit();

    }


}