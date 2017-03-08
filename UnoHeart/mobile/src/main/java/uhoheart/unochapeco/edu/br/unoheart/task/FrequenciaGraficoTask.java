package uhoheart.unochapeco.edu.br.unoheart.task;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import uhoheart.unochapeco.edu.br.unoheart.model.ErrorHandling;
import uhoheart.unochapeco.edu.br.unoheart.model.FrequenciaFiltroGrafico;
import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;
import uhoheart.unochapeco.edu.br.unoheart.model.type.RotinasSistemaListagem;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.util.CustomGsonBuilder;
import uhoheart.unochapeco.edu.br.unoheart.view.FragmentFrequenciaCardiacaPaciente;

/**
 * Created by Eduardo on 16/01/2017.
 */

public class FrequenciaGraficoTask extends AsyncTask<FrequenciaFiltroGrafico, Void, List<HistoricoFrequencia>> {

    private FragmentFrequenciaCardiacaPaciente activity;

    public FrequenciaGraficoTask(FragmentFrequenciaCardiacaPaciente activity) {
        this.activity = activity;
    }

    @Override
    protected List<HistoricoFrequencia> doInBackground(FrequenciaFiltroGrafico... dados) {
        if (activity != null) {
            try {
                URL url = RotinasSistemaListagem.LISTAR_FREQUENCIA.getUrl();
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setReadTimeout(15000);
                con.setConnectTimeout(15000);
                con.setRequestMethod("PUT");

                String msg = dados[0].toJSONString();
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
                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), Constantes.ENCODING_PADRAO));

                    Gson g = CustomGsonBuilder.getInstance();
                    TypeToken<List<HistoricoFrequencia>> tpToken = new TypeToken<List<HistoricoFrequencia>>() {

                    };

                    List<HistoricoFrequencia> itens = g.fromJson(reader, tpToken.getType());

                    return itens;
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
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<HistoricoFrequencia> itens) {
        if (activity != null) {
            activity.processar(itens);
        }

    }

}
