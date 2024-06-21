package com.valeria.pethealth.Vista;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.json.gson.GsonFactory;
;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.valeria.pethealth.Controlador.ControladorAPICalendar;
import com.valeria.pethealth.Controlador.MainActivity;
import com.valeria.pethealth.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class CrearEventoVista extends AppCompatActivity {
    EditText TituloET, DescripcionET;


    TextView PruebaTv;




    Button SubirBt, FechaIniBt, FechaFinBT;
    private Calendar calendar;

    private ControladorAPICalendar calendario;

String StartformattedDateTime, EndformattedDateTime;



    private GoogleSignInClient mGoogleSignInClient;
    String WEB_CLIENT_ID = "371636222892-26hscm1flqki0dbhl42b1c3soclet348.apps.googleusercontent.com";
    private static final int RC_SIGN_IN = 9001;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearevento);
        Intent intent = getIntent();
        String miDato = intent.getStringExtra("Mascota");

        calendario = new ControladorAPICalendar();


        FechaIniBt = findViewById(R.id.FechaInicBT);
        FechaFinBT = findViewById(R.id.FechaFinBT);
        SubirBt = findViewById(R.id.RecordatorioBT);
        PruebaTv = findViewById(R.id.TituloTV);
        TituloET = findViewById(R.id.TituloET);
        DescripcionET = findViewById(R.id.DescripcionET);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new com.google.android.gms.common.api.Scope("https://www.googleapis.com/auth/calendar"))
                .requestServerAuthCode(WEB_CLIENT_ID)
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Inicia el flujo de inicio de sesión
        signIn();



        FechaIniBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker();
            }
        });

        FechaFinBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePickerFinal();
            }
        });

        SubirBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = "titulo";
                titulo = String.valueOf(TituloET.getText());
                String descripcion = "ddescripcion";
                descripcion = String.valueOf(DescripcionET.getText());
                createEvent(titulo, descripcion, miDato);

            }
        });


    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            calendario.handleSignInResult(CrearEventoVista.this ,task);
        }
    }

    private void createEvent(String titulo, String Descripcion, String mascota) {
        DateTime startDateTime = new DateTime(StartformattedDateTime); // Hora local de Madrid
        DateTime endDateTime = new DateTime(EndformattedDateTime); // Hora local de Madrid

        calendario.createEvent(titulo, mascota, Descripcion, startDateTime, endDateTime);
    }

    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CrearEventoVista.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        StartformattedDateTime = String.format("%04d-%02d-%02dT%02d:%02d:00+02:00",
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH) + 1, // Los meses son 0-based
                                calendar.get(Calendar.DAY_OF_MONTH),
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE));


                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showDateTimePickerFinal() {
        final Calendar currentDate = Calendar.getInstance();
        calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CrearEventoVista.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        EndformattedDateTime = String.format("%04d-%02d-%02dT%02d:%02d:00+02:00",
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH) + 1, // Los meses son 0-based
                                calendar.get(Calendar.DAY_OF_MONTH),
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE));




                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }





}
