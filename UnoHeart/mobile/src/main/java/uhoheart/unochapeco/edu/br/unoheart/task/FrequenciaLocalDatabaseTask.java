package uhoheart.unochapeco.edu.br.unoheart.task;

import android.content.Context;
import android.os.AsyncTask;

import uhoheart.unochapeco.edu.br.unoheart.dao.HistoricoFrequenciaDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;

/**
 * Created by Eduardo on 12/01/2017.
 */

public class FrequenciaLocalDatabaseTask extends AsyncTask<HistoricoFrequencia, String, Boolean> {
    private Context context;

    public FrequenciaLocalDatabaseTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(HistoricoFrequencia... historicoFrequencias) {
        try {
            for (int i = 0; i < historicoFrequencias.length; i++) {
                new HistoricoFrequenciaDAO(context).insert(historicoFrequencias[i]);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
