package com.example.firebasead;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, Object> gestor = new HashMap<>();
        Bundle bundle = this.getIntent().getExtras();

        if(bundle != null) {
            gestor = (Map<String, Object>) bundle.getSerializable("HashMap");
            Toast toast = Toast.makeText(getApplicationContext(), "Sesión iniciada "+gestor.toString(), Toast.LENGTH_LONG);
            toast.show();

        }

    }
}