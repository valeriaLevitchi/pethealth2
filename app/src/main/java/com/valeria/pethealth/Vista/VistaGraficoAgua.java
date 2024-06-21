package com.valeria.pethealth.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.valeria.pethealth.Controlador.FichaMascota;
import com.valeria.pethealth.R;

import java.util.ArrayList;

public class VistaGraficoAgua extends AppCompatActivity {
    FichaMascota FM = new FichaMascota();
    GraphView graphView, graphViewComida, graphViewPaseo;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graficoagua);
        graphView = findViewById(R.id.idGraphView);
        Intent intent = getIntent();
        String nombreMascota = intent.getStringExtra("nombremascota");
        String nombreUsuario = intent.getStringExtra("nombreusuario");
        graphViewComida = findViewById(R.id.idGraphComidaView);
        graphViewPaseo = findViewById(R.id.idGraphPaseoView);

        graphViewPaseo.setTitle("Grafico Paseo");


        graphViewPaseo.setTitleColor(R.color.purple_202);


        graphViewPaseo.setTitleTextSize(20);

        FM.valoresPaseoGrafico(graphViewPaseo, nombreUsuario, nombreMascota);

        graphViewComida.setTitle("Grafico Comida");


        graphViewComida.setTitleColor(R.color.purple_201);


        graphViewComida.setTitleTextSize(20);

        FM.valoresGraficoComida(graphViewComida, nombreUsuario, nombreMascota);



        graphView.setTitle("Grafico Agua");


        graphView.setTitleColor(R.color.purple_200);


        graphView.setTitleTextSize(20);

        FM.valoresGraficoAgua(graphView, nombreUsuario, nombreMascota);
    }
}
