package uhoheart.unochapeco.edu.br.unoheart.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import uhoheart.unochapeco.edu.br.unoheart.dao.HistoricoFrequenciaDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;

public class SendMessageService extends Service {

    private Timer timer;
    private String nodeId;
    private final IBinder binder = new LocalBinder();
    private static final String LOG_TAG = "UnoHeart";

    @Override
    public void onCreate() {
        super.onCreate();
        inicializarTimer();
    }

    private void inicializarTimer() {

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                timer.cancel();
                enviarFrequenciaCardiaca();
                inicializarTimer();
                Log.d(LOG_TAG, "EXECUTANDO ENVIO....");
            }
        };

        timer.schedule(timerTask, 10000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }


    private GoogleApiClient getGoogleApiClient(Context context) {
        return new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();
    }

    private void retrieveDeviceNode() {
        final GoogleApiClient client = getGoogleApiClient(this);

        client.blockingConnect(5000, TimeUnit.MILLISECONDS);
        NodeApi.GetConnectedNodesResult result =
                Wearable.NodeApi.getConnectedNodes(client).await();
        List<Node> nodes = result.getNodes();
        if (nodes.size() > 0) {
            nodeId = nodes.get(0).getId();
        }
        client.disconnect();
    }

    private void enviarFrequenciaCardiaca() {
        /**
         * Recupera o smartphone sincronizado.
         */
        retrieveDeviceNode();
        final GoogleApiClient client = getGoogleApiClient(this);
        final String MESSAGE_PATH = "/frequenciacardiaca";

        if (nodeId != null) {
            client.blockingConnect(5000, TimeUnit.MILLISECONDS);
            try {
                final HistoricoFrequenciaDAO dao = new HistoricoFrequenciaDAO(getApplicationContext());
                List<HistoricoFrequencia> listaFrequencia = dao.getAll();

                for (final HistoricoFrequencia historicoFrequencia : listaFrequencia) {
                    /**
                     * Transmite os dados usando protocolo JSON.
                     */
                    String msg = historicoFrequencia.toJSONString();
                    Wearable.MessageApi.sendMessage(client, nodeId,
                            MESSAGE_PATH, msg.getBytes()).setResultCallback(
                            new ResultCallback<MessageApi.SendMessageResult>() {
                                @Override
                                public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                    if (sendMessageResult.getStatus().isSuccess()) {
                                        try {
                                            dao.delete(historicoFrequencia);
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                }
                            }
                    );
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            client.disconnect();
        }
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        SendMessageService getService() {
            return SendMessageService.this;
        }
    }

}
