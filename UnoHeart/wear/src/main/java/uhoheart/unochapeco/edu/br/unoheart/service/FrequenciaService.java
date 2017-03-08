package uhoheart.unochapeco.edu.br.unoheart.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

import uhoheart.unochapeco.edu.br.unoheart.dao.HistoricoFrequenciaDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;
import uhoheart.unochapeco.edu.br.unoheart.model.type.UnidadeTempo;

public class FrequenciaService extends Service implements SensorEventListener {
    private Timer timer;
    private final IBinder binder = new LocalBinder();
    private SensorManager sensorManager;
    private boolean sensorRegistrado = false;

    @Override
    public void onCreate() {
        super.onCreate();
        inicializarTimer();
    }

    private void inicializarTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                /**
                 * Inicializa o sensor em segundo plano.
                 * O sensor não retorna nenhum valor caso estiver
                 * fora fo pulso, portanto, ao iniciar a coleta novamente
                 * deverá ser cancelada a anterior.
                 */
                if (sensorRegistrado) {
                    unregisterSensor();
                }
                sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                Sensor heartSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                sensorRegistrado = sensorManager.registerListener(
                        FrequenciaService.this, heartSensor, SensorManager.SENSOR_DELAY_FASTEST);

            }
        };
        timer.schedule(timerTask, 0, UnidadeTempo.SEGUNDO.converter(25));
    }

    public void unregisterSensor() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
            sensorManager = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        unregisterSensor();
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        if (event.values.length > 0) {
            /**
             * Captura o valor retornado pelo sensor.
             */
            int newValue = Math.round(event.values[0]);
            if (newValue != 0) {
                /**
                 * Grava a frequência cardíaca no banco de dados local.
                 */
                HistoricoFrequencia historicoFrequencia = new HistoricoFrequencia();
                historicoFrequencia.setFrequencia(newValue);
                try {
                    new HistoricoFrequenciaDAO(getBaseContext()).insert(historicoFrequencia);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                unregisterSensor();
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public class LocalBinder extends Binder {
        FrequenciaService getService() {
            return FrequenciaService.this;
        }
    }
}
