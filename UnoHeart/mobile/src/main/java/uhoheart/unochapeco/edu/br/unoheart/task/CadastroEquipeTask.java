package uhoheart.unochapeco.edu.br.unoheart.task;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import uhoheart.unochapeco.edu.br.unoheart.model.Equipe;
import uhoheart.unochapeco.edu.br.unoheart.model.ErrorHandling;
import uhoheart.unochapeco.edu.br.unoheart.model.ExceptionTask;
import uhoheart.unochapeco.edu.br.unoheart.model.type.RotinasSistemaCadastro;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.view.ActivityNovaContaEquipe;

/**
 * Created by Eduardo on 16/01/2017.
 */

public class CadastroEquipeTask extends AsyncTask<Equipe, Void, Equipe> {

    private ExceptionTask exceptionTask;
    private ActivityNovaContaEquipe activity;

    public CadastroEquipeTask(ActivityNovaContaEquipe activity) {
        this.activity = activity;
    }

    @Override
    protected Equipe doInBackground(Equipe... equipes) {
        try {
            HttpURLConnection con = (HttpURLConnection) RotinasSistemaCadastro.CADASTRAR_EQUIPE.getUrl().openConnection();
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");
            String msg = equipes[0].toJSONString();
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
                Equipe p = new Equipe().parse(con.getInputStream());
                return p;
            } else {
                if (con.getResponseCode() == 202) {
                    ErrorHandling error = ErrorHandling.getInstance(con.getInputStream());
                    if (error != null) {
                        throw new ExceptionTask(error.getError());
                    }
                } else {
                    throw new ExceptionTask(Constantes.ERRO_GENERICO);
                }
            }
            con.disconnect();
        } catch (ExceptionTask e) {
            this.exceptionTask = e;
        } catch (Exception e) {
            e.printStackTrace();
            this.exceptionTask = new ExceptionTask(Constantes.ERRO_GENERICO);
        }
        return null;
    }

    public ExceptionTask getExceptionTask() {
        return exceptionTask;
    }

    @Override
    protected void onPostExecute(Equipe equipe) {
        this.activity.processarCadastroEquipe(exceptionTask, equipe);
    }
}
