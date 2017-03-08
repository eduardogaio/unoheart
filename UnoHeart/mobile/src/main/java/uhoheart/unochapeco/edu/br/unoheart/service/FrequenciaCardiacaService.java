package uhoheart.unochapeco.edu.br.unoheart.service;

import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;
import uhoheart.unochapeco.edu.br.unoheart.model.Paciente;
import uhoheart.unochapeco.edu.br.unoheart.task.FrequenciaLocalDatabaseTask;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;

public class FrequenciaCardiacaService extends WearableListenerService {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    String nodeId;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        nodeId = messageEvent.getSourceNodeId();
        final String MESSAGE_PATH = "/frequenciacardiaca";

        try {
            if (Constantes.USUARIO_LOGADO != null) {
                if (Constantes.USUARIO_LOGADO instanceof Paciente) {
                    if (messageEvent.getPath() != null && messageEvent.getPath().equals(MESSAGE_PATH)) {
                        if (messageEvent.getData() != null) {
                            Paciente paciente = (Paciente) Constantes.USUARIO_LOGADO;
                            HistoricoFrequencia historicoFrequencia = new HistoricoFrequencia().parse(new String(messageEvent.getData()));
                            if (historicoFrequencia != null) {
                                historicoFrequencia.setPaciente(paciente);

                                FrequenciaLocalDatabaseTask localDatabaseTask = new FrequenciaLocalDatabaseTask(getBaseContext());
                                localDatabaseTask.execute(historicoFrequencia);
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            Toast.makeText(this, "Não foi possível gravar a frequência cardíaca!", Toast.LENGTH_LONG).show();
        }
    }

//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//    }

    /*
    private void reply(String message) {
        GoogleApiClient client = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();
        client.blockingConnect(5000, TimeUnit.MILLISECONDS);
        Wearable.MessageApi.sendMessage(client, nodeId, message, null);
        client.disconnect();
    }
    */

}
