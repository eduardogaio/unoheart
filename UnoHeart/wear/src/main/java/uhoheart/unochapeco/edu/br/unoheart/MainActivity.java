package uhoheart.unochapeco.edu.br.unoheart;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;
import uhoheart.unochapeco.edu.br.unoheart.service.FrequenciaService;
import uhoheart.unochapeco.edu.br.unoheart.service.SendMessageService;
import uhoheart.unochapeco.edu.br.unoheart.task.RecuperarUltimaColetaTask;
import uhoheart.unochapeco.edu.br.unoheart.task.RegistarFrequenciaTask;
import uhoheart.unochapeco.edu.br.unoheart.task.RegistarUltimaColetaTask;

public class MainActivity extends WearableActivity implements SensorEventListener {

    private final int REQUEST_CODE_ASK_SINGLE_PERMISSION = 123;
    private BoxInsetLayout mContainerView;
    private TextView txtFrequencia;
    private TextView txtUltimaColeta;
    private TextView txtDescricao;
    private Button btnVerificar;
    private int currentValue;
    private boolean startDisplay;

    private Intent frequenciaService;
    private Intent sendMessageService;

    private SensorManager mSensorManager;

    private static final String LOG_TAG = "UnoHeart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        txtFrequencia = (TextView) findViewById(R.id.txtFrequencia);
        txtUltimaColeta = (TextView) findViewById(R.id.txtUltimaColeta);
        txtDescricao = (TextView) findViewById(R.id.txtDescricao);
        btnVerificar = (Button) findViewById(R.id.btnVerificar);

        currentValue = 0;
        startDisplay = false;
        txtFrequencia.setText("0");
        txtUltimaColeta.setText("");
        txtDescricao.setText("");

        requisitarPermissao();

        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (startDisplay) {
                    pararServicoDisplay();
                    inicializarServicosBackground();
                    recuperarUltimaColeta();
                } else {
                    finalizarServicosBackground();
                    iniciarServicoDisplay();
                }
            }
        });

        inicializarServicosBackground();
    }


    /**
     * Finaliza os serviços secundários de leitura de frequencia cardiaca
     */
    private void finalizarServicosBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (frequenciaService != null) {
                    getApplicationContext().unbindService(mConnectionFrequencia);
                    frequenciaService = null;
                }
                if (sendMessageService != null) {
                    getApplicationContext().unbindService(mConnectionMensagem);
                    sendMessageService = null;
                }
            }
        }).start();
    }

    /**
     * Inicializa o sensor para leitura de frequencia cardiaca
     */
    private void inicializarServicosBackground() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (frequenciaService == null) {
                    frequenciaService = new Intent(getApplicationContext(), FrequenciaService.class);
                    getApplicationContext().bindService(frequenciaService, mConnectionFrequencia, Service.BIND_AUTO_CREATE);
                }
                if (sendMessageService == null) {
                    sendMessageService = new Intent(getApplicationContext(), SendMessageService.class);
                    getApplicationContext().bindService(sendMessageService, mConnectionMensagem, Service.BIND_AUTO_CREATE);
                }
            }
        }).start();
    }

    private ServiceConnection mConnectionFrequencia = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.i("UnoHeart", "Desconectado....");
        }
    };
    private ServiceConnection mConnectionMensagem = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.i("UnoHeart", "Desconectado....");
        }
    };

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }


    @Override
    protected void onStart() {
        super.onStart();
        recuperarUltimaColeta();
    }

    private void limparTela() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                currentValue = 0;

                handler.post(new Runnable() {
                    public void run() {
                        txtFrequencia.setText("0");
                        txtUltimaColeta.setText("");
                        txtDescricao.setText("");
                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    private void updateDisplay() {

//        txtFrequencia.setVisibility(View.VISIBLE);
//        btnVerificar.setVisibility(View.VISIBLE);

//        txtTitulo.setVisibility(View.VISIBLE);

//        if (isAmbient()) {
//            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
//            mTextView.setTextColor(getResources().getColor(android.R.color.white));
//            mClockView.setVisibility(View.VISIBLE);
//
//            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
//        } else {
//            mContainerView.setBackground(null);
//            mTextView.setTextColor(getResources().getColor(android.R.color.black));
//            mClockView.setVisibility(View.GONE);
//        }
    }

    private void recuperarUltimaColeta() {
        RecuperarUltimaColetaTask recuperarUltimaColetaTask = new RecuperarUltimaColetaTask(MainActivity.this);
        recuperarUltimaColetaTask.execute();
    }

    public void recuperarUltimaColeta(List<HistoricoFrequencia> itens) {
        try {
            if (itens.isEmpty() == false) {
                txtFrequencia.setText(String.valueOf(itens.get(0).getFrequencia()));
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                txtUltimaColeta.setText(dateFormat.format(itens.get(0).getDatahora()));
                txtDescricao.setText("Última medição");
            } else {
                limparTela();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void iniciarServicoDisplay() {
        txtFrequencia.setText("0");
        txtUltimaColeta.setText("");
        txtDescricao.setText("Verificando...");

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

                Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                boolean res = mSensorManager.registerListener(MainActivity.this, mHeartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);

                handler.post(new Runnable() {
                    public void run() {
                        btnVerificar.setBackgroundResource(R.drawable.stop_button);
                    }
                });
            }
        };

        new Thread(runnable).start();

        startDisplay = true;
    }

    public void unregisterSensor() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(MainActivity.this);
            mSensorManager = null;
        }
    }

    private void pararServicoDisplay() {
        startDisplay = false;
        unregisterSensor();
        registrarUltimaColeta();

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        btnVerificar.setBackgroundResource(R.drawable.play_button);
                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    private void registrarUltimaColeta() {

        if (currentValue > 0) {
            HistoricoFrequencia historicoFrequencia = new HistoricoFrequencia();
            historicoFrequencia.setFrequencia(currentValue);

            RegistarUltimaColetaTask registarUltimaColetaTask = new RegistarUltimaColetaTask(MainActivity.this);
            registarUltimaColetaTask.execute(historicoFrequencia);
        }
        currentValue = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        pararServicoDisplay();
        inicializarServicosBackground();
    }

    private void displayFrequencia(final int newValue) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                currentValue = newValue;
                RegistarFrequenciaTask registarFrequenciaTask = new RegistarFrequenciaTask(MainActivity.this);
                HistoricoFrequencia historicoFrequencia = new HistoricoFrequencia();
                historicoFrequencia.setFrequencia(newValue);

                registarFrequenciaTask.execute(historicoFrequencia);
                handler.post(new Runnable() {
                    public void run() {
                        txtFrequencia.setText(Integer.toString(newValue));
                    }
                });
            }
        };

        new Thread(runnable).start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values.length > 0) {
            int newValue = Math.round(event.values[0]);
            if (currentValue != newValue && newValue != 0) {
                displayFrequencia(newValue);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void requisitarPermissao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BODY_SENSORS},
                    REQUEST_CODE_ASK_SINGLE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_SINGLE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(LOG_TAG, "Permissão concedida");
                } else {
                    Log.i(LOG_TAG, "Permissão negada");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
