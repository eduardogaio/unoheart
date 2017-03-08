package uhoheart.unochapeco.edu.br.unoheart.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import uhoheart.unochapeco.edu.br.unoheart.R;
import uhoheart.unochapeco.edu.br.unoheart.dao.UsuarioLogadoDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.Equipe;
import uhoheart.unochapeco.edu.br.unoheart.model.Paciente;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ActivitySplash extends AppCompatActivity {

    //    private static final int UI_ANIMATION_DELAY = 300;
//    private final Handler mHideHandler = new Handler();
    private View mContentView;
    //    private final Runnable mHidePart2Runnable = new Runnable() {
//        @SuppressLint("InlinedApi")
//        @Override
//        public void run() {
//            // Delayed removal of status and navigation bar
//
//            // Note that some of these constants are new as of API 16 (Jelly Bean)
//            // and API 19 (KitKat). It is safe to use them, as they are inlined
//            // at compile-time and do nothing on earlier devices.
//            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        }
//    };
    private View mControlsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

//        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        hide();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    UsuarioLogadoDAO usuarioLogadoDAO = new UsuarioLogadoDAO(getBaseContext());
                    Paciente paciente = usuarioLogadoDAO.selecionarPaciente();
                    Equipe equipe = usuarioLogadoDAO.selecionarEquipe();

                    if (paciente == null && equipe == null) {
                        Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (paciente != null) {
                            //ActivityLogin activityLogin = new ActivityLogin();
                            //LoginPacienteTask task = new LoginPacienteTask(activityLogin);
                            //task.execute(paciente);
                            Constantes.USUARIO_LOGADO = paciente;
                            Intent intent = new Intent(ActivitySplash.this, ActivityPerfilPaciente.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (equipe != null) {
                                Constantes.USUARIO_LOGADO = equipe;
//                        Intent intent = new Intent(ActivitySplash.this, ActivityPerfilPaciente.class);
//                        startActivity(intent);
//                        finish();
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 1000);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
//        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
//        mHideHandler.removeCallbacks(mShowPart2Runnable);
//        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }
}
