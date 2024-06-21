package com.valeria.pethealth.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valeria.pethealth.Controlador.FichaMascota;
import com.valeria.pethealth.R;

import java.util.ArrayList;

public class VistaFichaMascota extends AppCompatActivity {

TextView NombreTV, MediaAguaTV,MasDetalleTV, EstadoTV, EstadoComidaTv, MediaComidaTv, MasDetalleComidaTV,
    EstadoPaseoTv, MediaPaseoTv, MasDetallePaseoTV;
FichaMascota Fm = new FichaMascota();


String tipo;

Double peso;
EditText AguaEt, ComidaET, PaseoET;

Button btnAgua, btComida, btPaseo, btNotificacion;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fichamascota);
        Intent intent = getIntent();

        String nombreMascota = intent.getStringExtra("dato");
        String nombreUsuario = intent.getStringExtra("nombreusuario");








        btNotificacion = (Button) findViewById(R.id.NotificacionBT);
        EstadoTV = (TextView) findViewById(R.id.EstadoAnimalTV);
        MasDetalleTV = (TextView) findViewById(R.id.VerMasAguaTV);
        NombreTV = findViewById(R.id.NombreTV);
        NombreTV.setText(nombreMascota);
        MediaAguaTV = (TextView) findViewById(R.id.MediaAguaTV);
        AguaEt = findViewById(R.id.AgregarAguaET);
        btnAgua = findViewById(R.id.SubirAguaBT);

        //Notificacion
        btNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noti = new Intent(VistaFichaMascota.this, CrearEventoVista.class);
                noti.putExtra("Mascota", nombreMascota);
                startActivity(noti);
            }
        });

        //Comida
        EstadoComidaTv = (TextView) findViewById(R.id.EstadoAnimaComidaTV);
        MediaComidaTv = (TextView) findViewById(R.id.MediaComidaTV);
        MasDetalleComidaTV = (TextView) findViewById(R.id.VerMasComidaTV);
        ComidaET = (EditText) findViewById(R.id.AgregarComidaET);
        btComida = (Button) findViewById(R.id.SubirComidaBT);

        //Paseo

        EstadoPaseoTv = (TextView) findViewById(R.id.EstadoAnimalPaseoTV);
        MediaPaseoTv = (TextView) findViewById(R.id.MediaPaseoTV);
        MasDetallePaseoTV = (TextView) findViewById(R.id.VerMasPaseoTV);
        PaseoET = (EditText) findViewById(R.id.AgregarPaseoET);
        btPaseo = (Button) findViewById(R.id.SubirPaseoBT);

        Fm.MediaAgua(nombreMascota, nombreUsuario,MediaAguaTV, EstadoTV);

        MasDetalleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Graficos = new Intent(VistaFichaMascota.this, VistaGraficoAgua.class);
                Graficos.putExtra("nombremascota" , nombreMascota);
                Graficos.putExtra("nombreusuario" , nombreUsuario);
                startActivity(Graficos);
            }
        });

        btnAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double AguaAgregada = Double.parseDouble(String.valueOf(AguaEt.getText()));
                Fm.AgregarAgua(AguaAgregada, nombreMascota, nombreUsuario);
                Fm.MediaAgua(nombreMascota, nombreUsuario,MediaAguaTV, EstadoTV);




            }
        });








        Fm.MediaComida(nombreMascota, nombreUsuario,MediaComidaTv, EstadoComidaTv);

        MasDetalleComidaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Graficos = new Intent(VistaFichaMascota.this, VistaGraficoAgua.class);
                Graficos.putExtra("nombremascota" , nombreMascota);
                Graficos.putExtra("nombreusuario" , nombreUsuario);
                startActivity(Graficos);
            }
        });

        btComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double ComidaAgregada = Double.parseDouble(String.valueOf(ComidaET.getText()));
                Fm.AgregarComida(ComidaAgregada, nombreMascota, nombreUsuario);
                Fm.MediaComida(nombreMascota, nombreUsuario,MediaComidaTv, EstadoComidaTv);




            }
        });

        //Paseo

        Fm.MediaPaseo(nombreMascota, nombreUsuario,MediaPaseoTv, EstadoPaseoTv);

        MasDetallePaseoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Graficos = new Intent(VistaFichaMascota.this, VistaGraficoAgua.class);
                Graficos.putExtra("nombremascota" , nombreMascota);
                Graficos.putExtra("nombreusuario" , nombreUsuario);
                startActivity(Graficos);
            }
        });

        btPaseo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PaseoAgregada = Integer.parseInt(String.valueOf(PaseoET.getText()));
                Fm.AgregarPaseo(PaseoAgregada, nombreMascota, nombreUsuario);
                Fm.MediaPaseo(nombreMascota, nombreUsuario,MediaPaseoTv, EstadoPaseoTv);




            }
        });






    }
}
