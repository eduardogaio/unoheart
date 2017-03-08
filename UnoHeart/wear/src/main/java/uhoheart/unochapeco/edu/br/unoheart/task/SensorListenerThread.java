package uhoheart.unochapeco.edu.br.unoheart.task;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.List;

import uhoheart.unochapeco.edu.br.unoheart.dao.HistoricoFrequenciaDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;
import uhoheart.unochapeco.edu.br.unoheart.service.FrequenciaService;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Eduardo on 04/03/2017.
 */

public class SensorListenerThread implements Runnable {
    private static final String TAG = "SensorService";
    private final static int SENSOR_HEART_RATE = Sensor.TYPE_HEART_RATE;
    private SensorManager mSensorManager;
    private FrequenciaService context;
    private List<Integer> listamedia;

    public SensorListenerThread(FrequenciaService context) {
        this.context = context;
    }

    @Override
    public void run() {

        Log.d("RunTag", Thread.currentThread().getName()); // To display thread

        mSensorManager = ((SensorManager) context.getSystemService(SENSOR_SERVICE));

        Looper.prepare();
        Handler handler = new Handler() {

        };
        Sensor rotationVectorSensor = mSensorManager.getDefaultSensor(SENSOR_HEART_RATE);
        MySensorListener msl = new MySensorListener();
        mSensorManager.registerListener(msl, rotationVectorSensor, SensorManager.SENSOR_DELAY_FASTEST, handler);

        Looper.loop();
    }

    public void unregisterSensor() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(context);
            mSensorManager = null;
        }
    }

    private class MySensorListener implements SensorEventListener {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            Log.d("ListenerTag", Thread.currentThread().getName()); // To display thread
            if (event.values.length > 0) {
                int newValue = Math.round(event.values[0]);

                if (newValue != 0) {
                    listamedia.add(newValue);

                    if (listamedia.size() > 5) {
                        int media = 0;
                        for (int valor : listamedia) {
                            media += valor;
                        }
                        media = Math.round(media / listamedia.size());

                        HistoricoFrequencia historicoFrequencia = new HistoricoFrequencia();
                        historicoFrequencia.setFrequencia(media);

                        try {
                            new HistoricoFrequenciaDAO(context).insert(historicoFrequencia);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        listamedia.clear();

                        unregisterSensor();
                    }
                }
            }
        }
    }
}
