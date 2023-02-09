package com.example.firebasead;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private static final int CLAVE_GESTOR = 55;
    private static final int CLAVE_CLIENTE = 56;
    private static final int CLAVE_OLVIDADO = 57;
    private static final int CLAVE_CREAR_CUENTA = 58;

    ImageView logo;
    Button login;
    EditText usuario, contraseña;
    TextView crearCuenta, olvidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        logo = findViewById(R.id.logo);
//        logo.setOnClickListener(v -> {
//            //Registra evento de click en foto (no funciona)
//            Bundle bundle = new Bundle();
//            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, logo.getContentDescription().toString());
//            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//            Toast.makeText(getApplicationContext(), "CLICK", Toast.LENGTH_LONG).show();
//        });

        login = findViewById(R.id.sms);
        usuario = findViewById(R.id.user);
        contraseña = findViewById(R.id.password);
        usuario = findViewById(R.id.user);
        crearCuenta = findViewById(R.id.createAccount);
        olvidar = findViewById(R.id.forget);


        logo = findViewById(R.id.logo);

        ActivityResultLauncher controladorLogin = registerForActivityResult(
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

        login.setOnClickListener(v -> {
            //Registra evento de click en foto (no funciona)
//            Bundle bundle = new Bundle();
//            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, login.getContentDescription().toString());
//            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//            Toast.makeText(getApplicationContext(), "CLICK", Toast.LENGTH_LONG).show();
            if (usuario.getText().toString().startsWith("G")) {
                Intent intent = new Intent(Login.this, GestorMain.class);
                intent.putExtra("GESTOR", CLAVE_GESTOR);
                controladorLogin.launch(intent);
            } else if (usuario.getText().toString().startsWith("C")) {
                Intent intent = new Intent(Login.this, GestorMain.class);
                intent.putExtra("CLIENTE", CLAVE_CLIENTE);
                controladorLogin.launch(intent);
            }
        });

        olvidar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Recovery.class);
            intent.putExtra("OLVIDADO", CLAVE_OLVIDADO);
            controladorLogin.launch(intent);
        });

        crearCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, NuevoGestor.class);
            intent.putExtra("OLVIDADO", CLAVE_CREAR_CUENTA);
            controladorLogin.launch(intent);
        });

    }
}