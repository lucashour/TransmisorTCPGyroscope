package com.ciaa_poncho.lucashour.transmisortcpgyroscope;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.net.Socket;

public class GyroscopeData implements SensorEventListener {

    private static GyroscopeData singleton_instance = null;
    private SensorManager sensorManager;
    private float[] position;
    private Socket socket;

    protected GyroscopeData(){}

    public static GyroscopeData getInstance(Context context) {
        if(singleton_instance == null) {
            singleton_instance = new GyroscopeData();
            singleton_instance.initialize(context);
        }
        return singleton_instance;
    }

    private void initialize(Context context){
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),SensorManager.SENSOR_DELAY_NORMAL);
        this.position = new float[3];
        this.position[0] = 0;
        this.position[1] = 0;
        this.position[2] = 0;
    }

    public void init(Socket socket){
        this.socket = socket;
        this.sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        this.sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE){
            return;
        }

        String x,y,z;

        /* Eje X */
        position[0] += event.values[2];
        x = getAxisValueAsString(position[0]);
        /* Eje Y */
        position[1] += event.values[1];
        y = getAxisValueAsString(position[1]);
        /* Eje Z */
        position[2] += event.values[0];
        z = getAxisValueAsString(position[2]);

        String message = String.valueOf("(" + x + ";" + y + ";" + z + ")");
        sendDataToSocket(message);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Sin implementar
    }

    private String getAxisValueAsString(float value){
        return String.valueOf(Math.round(value));
    }

    /*************************************************************************/
    /* Operaciones para el envío información con el socket TCP */

    private boolean isSocketConnected(){
        if (this.socket != null){
            if (this.socket.isConnected())
                return true;
        }
        return false;
    }

    private void sendDataToSocket(String message){
        if (isSocketConnected()){
            TcpAsyncSend tcpCommunication = new TcpAsyncSend(socket,message);
            tcpCommunication.executeOnExecutor(TcpAsyncSend.THREAD_POOL_EXECUTOR);
        }
        else
            return;
    }

    /* fin de operaciones para el envío de información con el socet TCP */
    /*************************************************************************/


}
