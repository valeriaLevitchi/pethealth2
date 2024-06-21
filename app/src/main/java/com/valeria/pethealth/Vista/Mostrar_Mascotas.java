package com.valeria.pethealth.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valeria.pethealth.Controlador.LogicaBaseDatos;
import com.valeria.pethealth.Controlador.MainActivity;
import com.valeria.pethealth.R;

import java.util.ArrayList;


public class Mostrar_Mascotas extends AppCompatActivity {
    ListView MascotasView;
    String elementolclicado;
    Button btn;
    LogicaBaseDatos Controlador =  new LogicaBaseDatos();
    MainActivity Main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_mascotas);
        Intent intent = getIntent();
        String miDato = intent.getStringExtra("nombreUsuario");

        MascotasView =  (ListView) findViewById(R.id.MascotasListView);
        btn = (Button) findViewById(R.id.registrarNuevaMascota);




        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(miDato).child("Mascotas");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> list = new ArrayList<>();
                ArrayAdapter adapter = new ArrayAdapter<String>(Mostrar_Mascotas.this, android.R.layout.simple_list_item_1, list);

                MascotasView.setAdapter(adapter);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getKey();

                    list.add(name );
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        MascotasView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                elementolclicado = (String) parent.getItemAtPosition(position);
                Intent fichaActivity = new Intent(Mostrar_Mascotas.this, VistaFichaMascota.class);
                fichaActivity.putExtra("dato" , elementolclicado);
                fichaActivity.putExtra("nombreusuario" , miDato);

                startActivity(fichaActivity);

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(Mostrar_Mascotas.this, RegistrarMascota.class);
                intento.putExtra("dato" , miDato);
                startActivity(intento);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(miDato).child("Mascotas");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> list = new ArrayList<>();
                        ArrayAdapter adapter = new ArrayAdapter<String>(Mostrar_Mascotas.this, android.R.layout.simple_list_item_1,   list);

                        MascotasView.setAdapter(adapter);
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String name = ds.getKey();
                            Object value = ds.getValue();
                            list.add(name );
                        }



                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle possible errors.
                    }
                });
            }
        });


    }

}
