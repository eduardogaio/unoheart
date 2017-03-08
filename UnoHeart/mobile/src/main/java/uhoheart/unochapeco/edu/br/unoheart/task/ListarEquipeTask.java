package uhoheart.unochapeco.edu.br.unoheart.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import uhoheart.unochapeco.edu.br.unoheart.model.Equipe;
import uhoheart.unochapeco.edu.br.unoheart.model.ErrorHandling;
import uhoheart.unochapeco.edu.br.unoheart.model.ListenerRetornoEquipe;
import uhoheart.unochapeco.edu.br.unoheart.model.PacienteEquipe;
import uhoheart.unochapeco.edu.br.unoheart.model.type.RotinasSistemaListagem;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.util.CustomGsonBuilder;

/**
 * Created by Eduardo on 16/01/2017.
 */

public class ListarEquipeTask extends AsyncTask<PacienteEquipe, Void, List<Equipe>> {

    private Context context;
    private ListenerRetornoEquipe retorno;

    public ListarEquipeTask(Context context, ListenerRetornoEquipe retorno) {
        this.context = context;
        this.retorno = retorno;
    }

    @Override
    protected List<Equipe> doInBackground(PacienteEquipe... pacienteEquipes) {
        try {
            URL url = RotinasSistemaListagem.SELECIONAR_EQUIPE.getUrl();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("PUT");

            String msg = pacienteEquipes[0].toJSONString();
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
                TypeToken<List<Equipe>> tpToken = new TypeToken<List<Equipe>>() {

                };

                List<Equipe> itens = g.fromJson(reader, tpToken.getType());

                return itens;
            } else {
                if (con.getResponseCode() == 202) {
                    ErrorHandling error = ErrorHandling.getInstance(con.getInputStream());
                    if (error != null) {
                        Toast.makeText(context,
                                error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context,
                            Constantes.ERRO_GENERICO,
                            Toast.LENGTH_LONG).show();
                }
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Equipe> itens) {
        retorno.processar(itens);
    }

}
