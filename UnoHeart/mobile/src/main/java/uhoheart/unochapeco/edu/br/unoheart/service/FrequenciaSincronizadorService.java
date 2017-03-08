package uhoheart.unochapeco.edu.br.unoheart.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import uhoheart.unochapeco.edu.br.unoheart.dao.HistoricoFrequenciaDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;
import uhoheart.unochapeco.edu.br.unoheart.task.FrequenciaSincronizadorTask;

public class FrequenciaSincronizadorService extends Service {

    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();

        inicializarTimer();
    }

    private void inicializarTimer() {
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            public void run() {
                timer.cancel();

                HistoricoFrequenciaDAO dao = new HistoricoFrequenciaDAO(getBaseContext());
                List<HistoricoFrequencia> listaFrequencia = null;
                try {
                    listaFrequencia = dao.getAll();

                    for (HistoricoFrequencia historicoFrequencia : listaFrequencia) {
                        Thread.sleep(1000);
                        FrequenciaSincronizadorTask task = new FrequenciaSincronizadorTask(getBaseContext());
                        task.execute(historicoFrequencia);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                inicializarTimer();
            }
        };

        timer.schedule(timerTask, 10000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
