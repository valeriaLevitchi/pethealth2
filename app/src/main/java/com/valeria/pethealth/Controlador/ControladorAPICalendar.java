package com.valeria.pethealth.Controlador;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.valeria.pethealth.Vista.CrearEventoVista;

import java.util.Arrays;
import java.util.Collections;

public class ControladorAPICalendar {

    String aplication = "PetHealth";

    Context contexto;
    private static final String TAG = "MainActivity";

    String FechaI, FechaF, Titulo, Descripcion;

    Task<GoogleSignInAccount> completedTask1;



    private GoogleAccountCredential mCredential;

    public void handleSignInResult(Context contex, Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            completedTask1 = completedTask;
            if (account != null) {
                // Autenticación exitosa
                String accountName = account.getEmail();
                contexto = contex;
                Toast.makeText(contexto, "Signed in as: " + accountName, Toast.LENGTH_SHORT).show();

                mCredential = GoogleAccountCredential.usingOAuth2(
                        contexto.getApplicationContext(), Collections.singletonList(CalendarScopes.CALENDAR));
                mCredential.setSelectedAccountName(account.getEmail());

                // Configura las credenciales para la API de Google Calendar



                // Procede con la creación del evento en Google Calendar

            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(contexto, "Failed to sign in", Toast.LENGTH_SHORT).show();
        }
    }

    private static final String APPLICATION_NAME = "Google Calendar API Android Quickstart";



    public void createEvent(final String summary, final String location, final String Descripcion,
                            final com.google.api.client.util.DateTime startDateTime,
                            final com.google.api.client.util.DateTime endDateTime) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Calendar service = new Calendar.Builder(
                            GoogleNetHttpTransport.newTrustedTransport(),
                            GsonFactory.getDefaultInstance(),
                            mCredential)
                            .setApplicationName(APPLICATION_NAME)
                            .build();

                    Event event = new Event()
                            .setSummary(summary)
                            .setLocation(location)
                            .setDescription(Descripcion);

                    EventDateTime start = new EventDateTime()
                            .setDateTime(startDateTime)
                            .setTimeZone("Europe/Madrid"); // Zona horaria de Madrid
                    event.setStart(start);

                    EventDateTime end = new EventDateTime()
                            .setDateTime(endDateTime)
                            .setTimeZone("Europe/Madrid"); // Zona horaria de Madrid
                    event.setEnd(end);

                    // Añadir recordatorios
                    EventReminder[] reminderOverrides = new EventReminder[]{
                            new EventReminder().setMethod("popup").setMinutes(24 * 60), // 1 día antes
                            new EventReminder().setMethod("popup").setMinutes(60), // 1 hora antes
                    };
                    Event.Reminders reminders = new Event.Reminders()
                            .setUseDefault(false)
                            .setOverrides(Arrays.asList(reminderOverrides));
                    event.setReminders(reminders);

                    service.events().insert("primary", event).execute();
                } catch (Exception e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(contexto, "Event created successfully", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
