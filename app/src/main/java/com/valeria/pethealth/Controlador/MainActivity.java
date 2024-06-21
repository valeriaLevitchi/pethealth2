package com.valeria.pethealth.Controlador;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;



import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

// imoports de chatgpt
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.CalendarScopes;
//fin de los imports


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.api.services.calendar.model.EventReminder;
import com.valeria.pethealth.R;
import com.valeria.pethealth.Vista.Login;
import com.valeria.pethealth.Vista.Register;
import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {


public LogicaBaseDatos Controlador = new LogicaBaseDatos();
private static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;



//cALENDARIO





    ControladorAPICalendar Calendario = new ControladorAPICalendar();

    //cALENDARIO


Button btnregister, btnlogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);

        btnlogin = findViewById(R.id.Login);
        btnregister = findViewById(R.id.Register);





        //Calendario














        //Fin Calendario

        //Login
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });


        //REGISTRAR
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);

            }
        });




    }

    private void showToast(String meseege) {
        Toast.makeText(this, meseege, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_GET_ACCOUNTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El usuario ha concedido el permiso GET_ACCOUNTS
                // Puedes proceder con tu lógica aquí
                showToast("Permiso GET_ACCOUNTS concedido");
            } else {
                // El usuario ha denegado el permiso GET_ACCOUNTS
                // Maneja esta situación según sea necesario
                showToast("Permiso GET_ACCOUNTS denegado");
            }
        }
    }










}