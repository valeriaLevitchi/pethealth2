package com.valeria.pethealth.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.valeria.pethealth.Controlador.LogicaBaseDatos;
import com.valeria.pethealth.Modelo.Mascota;
import com.valeria.pethealth.R;

public class RegistrarMascota extends AppCompatActivity {
    LogicaBaseDatos Controlador =  new LogicaBaseDatos();

    Mascota mascota;

    EditText nombre, peso, altura, fechaNacimiento;

    Button subirBt;

    Spinner tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarmascota);
        Intent intent = getIntent();
        String miDato = intent.getStringExtra("dato");
        tipo = (Spinner) findViewById(R.id.tiposp);
        nombre = (EditText) findViewById(R.id.nombreet);
        peso = (EditText) findViewById(R.id.pesotet);
        altura = (EditText) findViewById(R.id.alturaet);
        fechaNacimiento =(EditText) findViewById(R.id.fechaet);
        subirBt = (Button) findViewById(R.id.subirbt);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Mascotas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adapter);

        tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccionado = parent.getItemAtPosition(position).toString();
                mascota = new Mascota();

                Controlador.obtenerTipo(seleccionado, mascota);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        subirBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NombreStr, PesoStr, NacimientoStr, AlturaStr;
                NombreStr = String.valueOf(nombre.getText());
                PesoStr = String.valueOf(peso.getText());
                NacimientoStr = String.valueOf(fechaNacimiento.getText());
                AlturaStr = String.valueOf(altura.getText());
                Toast.makeText(RegistrarMascota.this, NombreStr + " " +  PesoStr + NacimientoStr + AlturaStr + mascota.getTipo(), Toast.LENGTH_LONG).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(miDato);
                Toast.makeText(RegistrarMascota.this, miDato, Toast.LENGTH_LONG).show();
                Mascota mascotafinal = new Mascota(NombreStr, mascota.getTipo() , NacimientoStr, PesoStr, AlturaStr);

                ref.child("Mascotas").child(NombreStr).setValue(mascotafinal);

                Intent vuelta = new Intent(RegistrarMascota.this, Mostrar_Mascotas.class);
                startActivity(vuelta);
            }
        });
    }
}
