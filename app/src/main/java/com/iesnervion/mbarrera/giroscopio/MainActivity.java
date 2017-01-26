package com.iesnervion.mbarrera.giroscopio;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    boolean andar = false , giroDr = false , giroIzq = false;
    SensorManager mgr;
    Sensor sensor;
    TextView tvX , tvY ,tvZ;
    float ejeZ = 0  , ejeY = 0 , ejeX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvX = (TextView) findViewById(R.id.tvx);
        tvY = (TextView) findViewById(R.id.tvy);
        tvZ = (TextView) findViewById(R.id.tvZ);


        mgr=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = mgr.getDefaultSensor(SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ejeX = sensorEvent.values[0];
        ejeY = sensorEvent.values[1];
        ejeZ = sensorEvent.values[2];

        tvX.setText(String.valueOf(ejeX));
        tvY.setText(String.valueOf(ejeY));
        tvZ.setText(String.valueOf(ejeZ));

        //Andar
        if(ejeZ > 8 && andar==false){
            Toast.makeText(this, "ANDAR", Toast.LENGTH_SHORT).show();
            andar = true;
        }

        //Parar
        if(ejeZ < 4 && andar==true){
            Toast.makeText(this, "PARAR", Toast.LENGTH_SHORT).show();
            andar = false;
        }

        //Giro Derecha
       if(ejeY > 5 && giroDr==false){
           Toast.makeText(this, "GIRO DERECHA", Toast.LENGTH_SHORT).show();
           giroDr = true;
       }

        //Giro Izquierda
        if(ejeY < -5 && giroIzq==false){
            Toast.makeText(this, "Giro IZQUIERDA", Toast.LENGTH_SHORT).show();
            giroIzq = true;
        }

        //Desactivar GIRO
        if(giroIzq == true || giroDr == true){

            if(ejeY < 1 && ejeY > -1){
                Toast.makeText(this, "DESACTIVAR GIRO", Toast.LENGTH_SHORT).show();
                giroDr = false;
                giroIzq = false;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mgr.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mgr.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);
    }
}
