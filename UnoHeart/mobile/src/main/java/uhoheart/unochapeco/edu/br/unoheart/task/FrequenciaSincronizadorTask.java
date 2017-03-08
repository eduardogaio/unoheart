package uhoheart.unochapeco.edu.br.unoheart.task;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import uhoheart.unochapeco.edu.br.unoheart.dao.HistoricoFrequenciaDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.ErrorHandling;
import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;
import uhoheart.unochapeco.edu.br.unoheart.model.type.RotinasSistemaCadastro;

/**
 * Created by Eduardo on 16/01/2017.
 */

public class FrequenciaSincronizadorTask extends AsyncTask<HistoricoFrequencia, Void, HistoricoFrequencia> {

    private Context context;

    public FrequenciaSincronizadorTask(Context context) {
        this.context = context;
    }

    @Override
    protected HistoricoFrequencia doInBackground(HistoricoFrequencia... frequencias) {
        try {
            HttpURLConnection con = (HttpURLConnection) RotinasSistemaCadastro.CADASTRAR_FREQUENCIA.getUrl().openConnection();
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");
            String msg = frequencias[0].toJSONString();
            con.setFixedLengthStreamingMode(msg.getBytes().length);

            con.setDoInput(true);
            con.setDoOutput(true);

            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            con.connect();

            OutputStream os = con.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(msg.getBytes());
            bos.flush();

            if (con.getResponseCode() == 200) {
                HistoricoFrequencia frequencia = new HistoricoFrequencia().parse(con.getInputStream());
                return frequencia;
            } else {
                if (con.getResponseCode() == 202) {
                    ErrorHandling error = ErrorHandling.getInstance(con.getInputStream());
                    if (error != null) {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(HistoricoFrequencia historicoFrequencia) {

        try {
            if (historicoFrequencia != null) {
                HistoricoFrequenciaDAO dao = new HistoricoFrequenciaDAO(context);
                dao.delete(historicoFrequencia);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
