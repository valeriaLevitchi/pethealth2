package com.valeria.pethealth.Controlador;

import android.graphics.text.TextRunShaper;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class FichaMascota extends AppCompatActivity {

    double dias, SumaAgua, peso2, SumaComida;

    int SumaPaseo;
    String NombreUsuario, NombreMascota, tipo1;
    ArrayList<String> diasAguaArray, DiasPurgadosArray, diascomidaArray, DiasPurgadosComidaArray, diasPaseoArray, DiasPurgadosPaseoArray;
    ArrayList<Double> AguaArray, AguaPurgadoArray, ComidaArray, ComidaPurgadoArray;
    ArrayList<Integer> PaseoArray, PaseoPurgadoArray;


public void AgregarAgua(double Agua,  String nombreMascota, String Nombre_Usuario){

    NombreMascota = nombreMascota;
    NombreUsuario = Nombre_Usuario;
    Date fecha = new Date();
    SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
    String fechastr = format.format(fecha);


    DatabaseReference refdb = FirebaseDatabase.getInstance().getReference(Nombre_Usuario).child("Mascotas")
            .child(nombreMascota).child("Agua").child(fechastr);
    ;


    refdb.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                // La referencia ya existe
                Long valorActual = dataSnapshot.getValue(Long.class);
                if (valorActual != null) {
                    // Suma el número a valorActual y actualiza el valor en Firebase
                    refdb.setValue(valorActual + Agua);
                }
            } else {
                refdb.setValue(Agua);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Maneja el error
        }
    });




}

public void MediaAgua(String nombreMascota, String Nombre_Usuario, TextView TV,  TextView Estado){

    DatabaseReference refTipo = FirebaseDatabase.getInstance().getReference(Nombre_Usuario).child("Mascotas").child(nombreMascota);
    refTipo.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            tipo1 = String.valueOf(dataSnapshot.child("tipo").getValue());

            peso2 = Double.parseDouble(String.valueOf(dataSnapshot.child("peso").getValue()));
            DatabaseReference refdb = FirebaseDatabase.getInstance().getReference(Nombre_Usuario).child("Mascotas")
                    .child(nombreMascota).child("Agua");

            refdb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long numeroDeHijos = snapshot.getChildrenCount();
                    dias = Double.parseDouble(String.valueOf(numeroDeHijos));




                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




            DatabaseReference refdbAgua = FirebaseDatabase.getInstance().getReference(Nombre_Usuario).child("Mascotas")
                    .child(nombreMascota).child("Agua");



            refdbAgua.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    double suma = 0;
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Double valor = childSnapshot.getValue(Double.class);
                        if (valor != null) {
                            SumaAgua += valor;
                            Double total = SumaAgua/dias;
                            Double pesofinal = peso2 / 100 ;
                            Double totalformula = total / pesofinal;
                            double factor = Math.pow(10, 2); // Para redondear a 2 decimales
                            total = Math.round(total * factor) / factor;

                            if(tipo1.equals("Conejo")){
                                if(totalformula<= 50){
                                    Estado.setText("Tu conejito no esta Bebiendo agua !!OJO " );
                                } else if (totalformula  >= 100) {
                                    Estado.setText("Tu conejito esta bebiendo bastante agua");

                                }else{
                                    Estado.setText("Tu Conejo esta hidratado bien =)");
                                }
                            }  if (tipo1.equals("Perro")) {
                                if(totalformula<= 30){
                                    Estado.setText("Tu Perro no esta Bebiendo agua !!OJO");
                                } else if (totalformula  >= 100) {
                                    Estado.setText("Tu Perro esta bebiendo bastante agua");

                                }else{
                                    Estado.setText("Tu Perro esta hidratado bien =)");
                                }
                            } if(tipo1.equals("Gato")){
                                if(totalformula<= 50){
                                    Estado.setText("Tu gato no esta Bebiendo agua !!OJO");
                                } else if (totalformula  >= 100) {
                                    Estado.setText("Tu gato esta bebiendo bastante agua");

                                }else{
                                    Estado.setText("Tu gato esta hidratado bien =)");
                                }
                            }

                            TV.setText(String.valueOf(total));


                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Maneja el error
                }
            });




        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Handle possible errors.
        }
    });



}
    public void valoresGraficoAgua(GraphView graph, String NombreUsar, String Nombremasc){


        diasAguaArray = new ArrayList<>();
        AguaArray =  new ArrayList<>();
        DatabaseReference refdb = FirebaseDatabase.getInstance().getReference(NombreUsar).child("Mascotas")
                .child(Nombremasc).child("Agua");

        refdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Double valor = childSnapshot.getValue(Double.class);
                    AguaArray.add(valor);
                    String fechas = childSnapshot.getKey();
                    diasAguaArray.add(fechas);


                }

                DiasPurgadosArray =  new ArrayList<>(Collections.nCopies(7, " "));
                AguaPurgadoArray =   new ArrayList<>(Collections.nCopies(7, 0.0));


                int start = Math.max(0, AguaArray.size() - 7);
                int start2 = Math.max(0, diasAguaArray.size() - 7);

                for (int i = start; i < AguaArray.size(); i++) {
                    AguaPurgadoArray.set(i - start, AguaArray.get(i));
                }

                for (int i = start2; i < diasAguaArray.size(); i++) {
                    String valorArray = diasAguaArray.get(i);
                    String valorpartido = valorArray.substring(0, 4);
                    String valor_final = valorpartido.substring(2,4) + "/" +valorpartido.substring(0,2);
                    DiasPurgadosArray.set(i - start, valor_final);
                }


                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, AguaPurgadoArray.get(0)),
                        new DataPoint(1, AguaPurgadoArray.get(1)),
                        new DataPoint(2, AguaPurgadoArray.get(2)),
                        new DataPoint(3, AguaPurgadoArray.get(3)),
                        new DataPoint(4, AguaPurgadoArray.get(4)),
                        new DataPoint(5, AguaPurgadoArray.get(5)),
                        new DataPoint(6, AguaPurgadoArray.get(6))
                });

                graph.addSeries(series);
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(new String[] {DiasPurgadosArray.get(0) ,
                        String.valueOf(DiasPurgadosArray.get(1)),
                        DiasPurgadosArray.get(2),
                        DiasPurgadosArray.get(3),
                        DiasPurgadosArray.get(4),
                        DiasPurgadosArray.get(5),
                        DiasPurgadosArray.get(6)});

                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    public void MediaComida(String nombreMascota, String nombreUsuario, TextView mediaComidaTv, TextView estadoComidaTv) {
        DatabaseReference refTipo = FirebaseDatabase.getInstance().getReference(nombreUsuario).child("Mascotas").child(nombreMascota);
        refTipo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tipo1 = String.valueOf(dataSnapshot.child("tipo").getValue());

                peso2 = Double.parseDouble(String.valueOf(dataSnapshot.child("peso").getValue()));
                DatabaseReference refdb = FirebaseDatabase.getInstance().getReference(nombreUsuario).child("Mascotas")
                        .child(nombreMascota).child("Comida");

                refdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long numeroDeHijos = snapshot.getChildrenCount();
                        dias = Double.parseDouble(String.valueOf(numeroDeHijos));




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




                DatabaseReference refdbAgua = FirebaseDatabase.getInstance().getReference(nombreUsuario).child("Mascotas")
                        .child(nombreMascota).child("Comida");



                refdbAgua.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double suma = 0;
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Double valor = childSnapshot.getValue(Double.class);
                            if (valor != null) {
                                SumaComida += valor;
                                Double total = SumaComida/dias;
                                Double pesofinal = peso2 / 1000 ;
                                Double totalformula = total / pesofinal;
                                double factor = Math.pow(10, 2); // Para redondear a 2 decimales
                                total = Math.round(total * factor) / factor;

                                if(tipo1.equals("Conejo")){
                                    if(totalformula<= 30){
                                        estadoComidaTv.setText("Tu conejito no esta Comiendo pienso !!OJO  Deberia comer:" + pesofinal * 30    );
                                    }

                                else if (totalformula > 45){
                                        estadoComidaTv.setText("Tu Conejo esta comiendo mucho deberia comer :" + pesofinal * 45);
                                    } else{
                                        estadoComidaTv.setText("Tu Conejo esta  alimentado bien =)");
                                    }

                                }  if (tipo1.equals("Perro")) {
                                    double porcentaje = (pesofinal /100) * 2;
                                    double porcentajemax = (pesofinal /100) * 2.5;
                                    if(totalformula<= porcentaje ){
                                        estadoComidaTv.setText("Tu Perro no esta Comiendo !!OJO");
                                    } else if (totalformula  >= porcentajemax) {
                                        estadoComidaTv.setText("Tu Perro esta comiento bastante");

                                    }else{
                                        estadoComidaTv.setText("Tu Perro esta comiendo bien =)");
                                    }
                                } if(tipo1.equals("Gato")){
                                    if(totalformula<= 30){
                                        estadoComidaTv.setText("Tu gato no esta comiendo bien!!OJO");
                                    } else if (totalformula  >= 75) {
                                        estadoComidaTv.setText("Tu gato esta comiendo bastante");

                                    }else{
                                        estadoComidaTv.setText("Tu gato esta comiendo bien =)");
                                    }
                                }

                                mediaComidaTv.setText(String.valueOf(total));


                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Maneja el error
                    }
                });




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
}

    public void AgregarComida(double comidaAgregada, String nombreMascota, String nombreUsuario) {
        NombreMascota = nombreMascota;
        NombreUsuario = nombreUsuario;
        Date fecha = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
        String fechastr = format.format(fecha);


        DatabaseReference refdb = FirebaseDatabase.getInstance().getReference(nombreUsuario).child("Mascotas")
                .child(nombreMascota).child("Comida").child(fechastr);
        ;


        refdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // La referencia ya existe
                    Long valorActual = dataSnapshot.getValue(Long.class);
                    if (valorActual != null) {
                        // Suma el número a valorActual y actualiza el valor en Firebase
                        refdb.setValue(valorActual + comidaAgregada);
                    }
                } else {
                    refdb.setValue(comidaAgregada);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Maneja el error
            }
        });
    }

    public void valoresGraficoComida(GraphView graph, String NombreUsar, String Nombremasc){


        diascomidaArray = new ArrayList<>();
        ComidaArray =  new ArrayList<>();
        DatabaseReference refdb = FirebaseDatabase.getInstance().getReference(NombreUsar).child("Mascotas")
                .child(Nombremasc).child("Comida");

        refdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Double valor = childSnapshot.getValue(Double.class);
                    ComidaArray.add(valor);
                    String fechas = childSnapshot.getKey();
                    diascomidaArray.add(fechas);


                }

                DiasPurgadosComidaArray =  new ArrayList<>(Collections.nCopies(7, " "));
                ComidaPurgadoArray =   new ArrayList<>(Collections.nCopies(7, 0.0));


                int start = Math.max(0, ComidaArray.size() - 7);
                int start2 = Math.max(0, diascomidaArray.size() - 7);

                for (int i = start; i < ComidaArray.size(); i++) {
                    ComidaPurgadoArray.set(i - start, ComidaArray.get(i));
                }

                for (int i = start2; i < diascomidaArray.size(); i++) {
                    String valorArray = diascomidaArray.get(i);
                    String valorpartido = valorArray.substring(0, 4);
                    String valor_final = valorpartido.substring(2,4) + "/" +valorpartido.substring(0,2);
                    DiasPurgadosComidaArray.set(i - start, valor_final);
                }



                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, ComidaPurgadoArray.get(0)),
                        new DataPoint(1, ComidaPurgadoArray.get(1)),
                        new DataPoint(2, ComidaPurgadoArray.get(2)),
                        new DataPoint(3, ComidaPurgadoArray.get(3)),
                        new DataPoint(4, ComidaPurgadoArray.get(4)),
                        new DataPoint(5, ComidaPurgadoArray.get(5)),
                        new DataPoint(6, ComidaPurgadoArray.get(6))
                });

                graph.addSeries(series);
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setHorizontalLabels(new String[] {DiasPurgadosComidaArray.get(0) ,
                        String.valueOf(DiasPurgadosComidaArray.get(1)),
                        DiasPurgadosComidaArray.get(2),
                        DiasPurgadosComidaArray.get(3),
                        DiasPurgadosComidaArray.get(4),
                        DiasPurgadosComidaArray.get(5),
                        DiasPurgadosComidaArray.get(6)});

                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    public void MediaPaseo(String nombreMascota, String nombreUsuario, TextView mediaPaseoTv, TextView estadoPaseoTv) {
        DatabaseReference refTipo = FirebaseDatabase.getInstance().getReference(nombreUsuario).child("Mascotas").child(nombreMascota);
        refTipo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tipo1 = String.valueOf(dataSnapshot.child("tipo").getValue());

                peso2 = Double.parseDouble(String.valueOf(dataSnapshot.child("peso").getValue()));
                DatabaseReference refdb = FirebaseDatabase.getInstance().getReference(nombreUsuario).child("Mascotas")
                        .child(nombreMascota).child("Paseo");

                refdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long numeroDeHijos = snapshot.getChildrenCount();
                        dias = Double.parseDouble(String.valueOf(numeroDeHijos));




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




                DatabaseReference refdbAgua = FirebaseDatabase.getInstance().getReference(nombreUsuario).child("Mascotas")
                        .child(nombreMascota).child("Paseo");



                refdbAgua.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double suma = 0;
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            int valor = childSnapshot.getValue(Integer.class);
                            if (valor != 0) {
                                SumaPaseo += valor;
                                double mediaPaseo = SumaPaseo / dias;
                                double factor = Math.pow(10, 2); // Para redondear a 2 decimales
                                mediaPaseo = Math.round(mediaPaseo * factor) / factor;




                                if(tipo1.equals("Conejo")){
                                    if(mediaPaseo>= 10){
                                        estadoPaseoTv.setText("Cuidado con pasear demasiado a tu conejito !!OJO " );
                                    }

                                    else{
                                        estadoPaseoTv.setText("El Conejo esta bien feliz =)");
                                    }
                                }  if (tipo1.equals("Perro")) {

                                    if(mediaPaseo < 150 ){
                                        estadoPaseoTv.setText("Tu Perro no ha salido lo suficiente");
                                    } else if (mediaPaseo > 360) {
                                        estadoPaseoTv.setText("Tu Perro esta muy feliz por salir mucho =)");

                                    }else{
                                        estadoPaseoTv.setText("Tu Perro esta saliendo bien =)");
                                    }
                                } if(tipo1.equals("Gato")){
                                    if(mediaPaseo<= 15){
                                        estadoPaseoTv.setText("Tu gato no ha salido lo suficiente estos dias!!OJO");


                                    }else{
                                        estadoPaseoTv.setText("Tu gato esta saliendo bien, te lo agradece =)");
                                    }
                                }

                                mediaPaseoTv.setText(String.valueOf(mediaPaseo));


                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Maneja el error
                    }
                });




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
}

    public void valoresPaseoGrafico(GraphView graphViewPaseo, String nombreUsuario, String nombreMascota) {
        diasPaseoArray = new ArrayList<>();
        PaseoArray =  new ArrayList<>();
        DatabaseReference refdb = FirebaseDatabase.getInstance().getReference(nombreUsuario).child("Mascotas")
                .child(nombreMascota).child("Paseo");

        refdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    int valor = childSnapshot.getValue(Integer.class);
                    PaseoArray.add(valor);
                    String fechas = childSnapshot.getKey();
                    diasPaseoArray.add(fechas);


                }

                DiasPurgadosPaseoArray =  new ArrayList<>(Collections.nCopies(7, " "));
                PaseoPurgadoArray =   new ArrayList<>(Collections.nCopies(7, 0));


                int start = Math.max(0, PaseoArray.size() - 7);
                int start2 = Math.max(0, diasPaseoArray.size() - 7);

                for (int i = start; i < PaseoArray.size(); i++) {
                    PaseoPurgadoArray.set(i - start, PaseoArray.get(i));
                }

                for (int i = start2; i < diasPaseoArray.size(); i++) {
                    String valorArray = diasPaseoArray.get(i);
                    String valorpartido = valorArray.substring(0, 4);
                    String valor_final = valorpartido.substring(2,4) + "/" +valorpartido.substring(0,2);
                    DiasPurgadosPaseoArray.set(i - start, valor_final);
                }



                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, PaseoPurgadoArray.get(0)),
                        new DataPoint(1, PaseoPurgadoArray.get(1)),
                        new DataPoint(2, PaseoPurgadoArray.get(2)),
                        new DataPoint(3, PaseoPurgadoArray.get(3)),
                        new DataPoint(4, PaseoPurgadoArray.get(4)),
                        new DataPoint(5, PaseoPurgadoArray.get(5)),
                        new DataPoint(6, PaseoPurgadoArray.get(6))
                });

                graphViewPaseo.addSeries(series);
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphViewPaseo);
                staticLabelsFormatter.setHorizontalLabels(new String[] {DiasPurgadosPaseoArray.get(0) ,
                        String.valueOf(DiasPurgadosPaseoArray.get(1)),
                        DiasPurgadosPaseoArray.get(2),
                        DiasPurgadosPaseoArray.get(3),
                        DiasPurgadosPaseoArray.get(4),
                        DiasPurgadosPaseoArray.get(5),
                        DiasPurgadosPaseoArray.get(6)});

                graphViewPaseo.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void AgregarPaseo(int paseoAgregado, String nombreMascota, String nombreUsuario) {
        NombreMascota = nombreMascota;
        NombreUsuario = nombreUsuario;
        Date fecha = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
        String fechastr = format.format(fecha);


        DatabaseReference refdb = FirebaseDatabase.getInstance().getReference(nombreUsuario).child("Mascotas")
                .child(nombreMascota).child("Paseo").child(fechastr);
        ;


        refdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // La referencia ya existe
                    Long valorActual = dataSnapshot.getValue(Long.class);
                    if (valorActual != null) {
                        // Suma el número a valorActual y actualiza el valor en Firebase
                        refdb.setValue(valorActual + paseoAgregado);
                    }
                } else {
                    refdb.setValue(paseoAgregado);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Maneja el error
            }
        });
    }
}
