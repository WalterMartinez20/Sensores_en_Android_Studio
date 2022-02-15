package com.example.sensoresenandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.crypto.AEADBadTagException;

public class MainActivity extends AppCompatActivity {
    // 1.- Variables
    // hace la comunicación con el hardware
    SensorManager sensorManager;
    // para representar al sensor
    Sensor sensor;
    // para determinar si algo de acerca al dispositivo
    SensorEventListener sensorEventListener;
    //MediaPlayer mp;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlace de la variable TextView con la vista
        final TextView texto = (TextView)findViewById(R.id.tvSensor);
        final TextView text2 = (TextView)findViewById(R.id.tvPosicion);
        Button bAcel = (Button)this.findViewById(R.id.btirAcele);
        //mp = MediaPlayer.create(this,R.raw.samsung_sound);

        bAcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mp.start();
                Intent irSensorAceler = new Intent(MainActivity.this, SensorAcelerometro.class);
                startActivity(irSensorAceler);
            }
        });

        // 2.- Aplicando el servicio
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        // El tipo de sensor que se utiliza
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        // Verificar si el dispositivo tiene este tipo de sensor.
        // si no lo tiene hay que terminar la acción
        if(sensor==null)finish();
        // llamamos al evento Listener para determinar determinar los cambios
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < sensor.getMaximumRange()){
                    //getWindow().getDecorView().setBackgroundColor(Color.RED);
                    texto.setText("Cambiando a color rojo");
                    text2.setText("Posicion cerca del oido");
                }else{
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    texto.setText("SENSOR DE PROXIMIDAD");
                    text2.setText("POSICION NORMAL");
                }
        }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };  // Agregamos el punto y coma
        // 4.-
        start();
    }//End: OnCreate.
    // 3.-
    public void start(){
        sensorManager.registerListener(sensorEventListener,sensor,2000*1000);
    }
    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    // Estos dos métodos se agregaron haciendo clic derecho en este punto
    // y buscando en la lista de métodos estos
    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}