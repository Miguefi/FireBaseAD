package com.example.firebasead;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NuevoCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_cliente);

        Spinner spinner = (Spinner) findViewById(R.id.sociedad);
        Resources res = getResources();
        String [] sociedades = res.getStringArray(R.array.sociedades);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,R.layout.spinner_texto, sociedades) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (tv.getText().toString().equals("Tipo de Sociedad")) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(25);
                    tv.setPadding(0, 15, 20, 25);

                } else {
                    tv.setTextSize(18);
                    tv.setTextColor(Color.BLACK);
                    tv.setGravity(Gravity.NO_GRAVITY);
                    tv.setGravity(Gravity.FILL_VERTICAL);
                    tv.setPadding(55 , 25 , 0 , 25);
                }
                return view;
            }

        };

        spinner.setAdapter(adapter);

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedItemText = (String) parent.getItemAtPosition(position);
//                // If user change the default selection
//                // First item is disable and it is used for hint
//                if (position > 0) {
//                    // Notify the selected item text
//                    Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });



        //spinner.setPrompt("Sociedades");
    }
}