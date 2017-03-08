package uhoheart.unochapeco.edu.br.unoheart.task;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import uhoheart.unochapeco.edu.br.unoheart.model.ErrorHandling;
import uhoheart.unochapeco.edu.br.unoheart.model.ExceptionTask;
import uhoheart.unochapeco.edu.br.unoheart.model.Paciente;
import uhoheart.unochapeco.edu.br.unoheart.model.type.RotinasSistemaCadastro;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.view.ActivityLogin;

/**
 * Created by Eduardo on 16/01/2017.
 */

public class LoginPacienteTask extends AsyncTask<Paciente, Void, Paciente> {

    private ExceptionTask exceptionTask;
    private ActivityLogin activity;

    public LoginPacienteTask(ActivityLogin activity) {
        this.activity = activity;
    }

    @Override
    protected Paciente doInBackground(Paciente... pacientes) {
        try {
            URL url = RotinasSistemaCadastro.LOGIN_PACIENTE.getUrl();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("PUT");

            String msg = pacientes[0].toJSONString();
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
                Paciente p = new Paciente().parse(con.getInputStream());
                return p;
            } else {
                if (con.getResponseCode() == 202) {
                    ErrorHandling error = ErrorHandling.getInstance(con.getInputStream());
                    if (error != null) {
                        if (error != null) {
                            throw new ExceptionTask(error.getError());
                        }
                    } else {
                        throw new ExceptionTask(Constantes.ERRO_GENERICO);
                    }
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

    @Override
    protected void onPostExecute(Paciente paciente) {
        this.activity.validarLoginPaciente(exceptionTask, paciente);
    }

}
