package uhoheart.unochapeco.edu.br.unoheart.task;

import android.os.AsyncTask;

import uhoheart.unochapeco.edu.br.unoheart.MainActivity;
import uhoheart.unochapeco.edu.br.unoheart.dao.HistoricoFrequenciaDAO;
import uhoheart.unochapeco.edu.br.unoheart.dao.UltimaColetaDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;

/**
 * Created by Eduardo on 16/01/2017.
 */

public class RegistarUltimaColetaTask extends AsyncTask<HistoricoFrequencia, Void, Void> {


    private MainActivity activity;

    public RegistarUltimaColetaTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(HistoricoFrequencia... dados) {
        if (dados.length > 0) {
            UltimaColetaDAO ultimaColetaDAO = new UltimaColetaDAO(activity.getApplicationContext());
            HistoricoFrequenciaDAO historicoFrequenciaDAO = new HistoricoFrequenciaDAO(activity.getApplicationContext());
            try {
                ultimaColetaDAO.delete();
                ultimaColetaDAO.insert(dados[0]);

                historicoFrequenciaDAO.insert(dados[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
