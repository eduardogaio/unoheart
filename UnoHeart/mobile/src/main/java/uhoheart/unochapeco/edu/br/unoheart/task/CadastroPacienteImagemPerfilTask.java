package uhoheart.unochapeco.edu.br.unoheart.task;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import uhoheart.unochapeco.edu.br.unoheart.model.ErrorHandling;
import uhoheart.unochapeco.edu.br.unoheart.model.ExceptionTask;
import uhoheart.unochapeco.edu.br.unoheart.model.Paciente;
import uhoheart.unochapeco.edu.br.unoheart.model.PacienteImagem;
import uhoheart.unochapeco.edu.br.unoheart.model.type.RotinasSistemaCadastro;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.view.ActivityPerfilPaciente;

/**
 * Created by Eduardo on 16/01/2017.
 */

public class CadastroPacienteImagemPerfilTask extends AsyncTask<Paciente, Void, PacienteImagem> {

    private ExceptionTask exceptionTask;
    private ActivityPerfilPaciente activity;

    public CadastroPacienteImagemPerfilTask(ActivityPerfilPaciente activity) {
        this.activity = activity;
    }

    @Override
    protected PacienteImagem doInBackground(Paciente... pacientes) {
        try {
            HttpURLConnection con = (HttpURLConnection) RotinasSistemaCadastro.CADASTRAR_PACIENTE_IMAGEM_PERFIL.getUrl().openConnection();
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");

            Paciente p = pacientes[0];
            PacienteImagem pacienteImagem = new PacienteImagem();
            pacienteImagem = p.getPacienteImagem();
            pacienteImagem.setPaciente(new Paciente());
            pacienteImagem.getPaciente().setCodigo(p.getCodigo());

            String msg = pacienteImagem.toJSONString();
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
                pacienteImagem = new PacienteImagem().parse(con.getInputStream());
                return pacienteImagem;
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

    @Override
    protected void onPostExecute(PacienteImagem pacienteImagem) {
        this.activity.processarGravacaoRemotaImagem(exceptionTask, pacienteImagem);
    }
}
