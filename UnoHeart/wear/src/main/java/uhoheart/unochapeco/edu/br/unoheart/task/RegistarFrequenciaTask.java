package uhoheart.unochapeco.edu.br.unoheart.task;

import android.os.AsyncTask;

import uhoheart.unochapeco.edu.br.unoheart.MainActivity;
import uhoheart.unochapeco.edu.br.unoheart.dao.HistoricoFrequenciaDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;

/**
 * Created by Eduardo on 16/01/2017.
 */

public class RegistarFrequenciaTask extends AsyncTask<HistoricoFrequencia, Void, Void> {


    private MainActivity activity;

    public RegistarFrequenciaTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(HistoricoFrequencia... dados) {
        if (dados.length > 0) {
            HistoricoFrequenciaDAO historicoFrequenciaDAO = new HistoricoFrequenciaDAO(activity.getApplicationContext());
            try {
                historicoFrequenciaDAO.insert(dados[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
