package uhoheart.unochapeco.edu.br.unoheart.task;

import android.os.AsyncTask;

import java.util.List;

import uhoheart.unochapeco.edu.br.unoheart.MainActivity;
import uhoheart.unochapeco.edu.br.unoheart.dao.HistoricoFrequenciaDAO;
import uhoheart.unochapeco.edu.br.unoheart.dao.UltimaColetaDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;

/**
 * Created by Eduardo on 16/01/2017.
 */

public class RecuperarUltimaColetaTask extends AsyncTask<Void, Void, List<HistoricoFrequencia>> {


    private MainActivity activity;

    public RecuperarUltimaColetaTask(MainActivity activity) {
        this.activity = activity;
    }


    @Override
    protected List<HistoricoFrequencia> doInBackground(Void... voids) {

        UltimaColetaDAO ultimaColetaDAO = new UltimaColetaDAO(activity.getApplicationContext());
        List<HistoricoFrequencia> itens = null;
        try {
            itens = ultimaColetaDAO.getAll();
            if (itens != null && itens.isEmpty() == false) {
                new HistoricoFrequenciaDAO(activity.getApplicationContext()).insert(itens.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itens;
    }

    @Override
    protected void onPostExecute(List<HistoricoFrequencia> itens) {
        this.activity.recuperarUltimaColeta(itens);
    }
}
