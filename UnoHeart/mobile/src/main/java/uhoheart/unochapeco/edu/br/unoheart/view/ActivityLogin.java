package uhoheart.unochapeco.edu.br.unoheart.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import uhoheart.unochapeco.edu.br.unoheart.R;
import uhoheart.unochapeco.edu.br.unoheart.dao.UsuarioLogadoDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.Equipe;
import uhoheart.unochapeco.edu.br.unoheart.model.ExceptionTask;
import uhoheart.unochapeco.edu.br.unoheart.model.Paciente;
import uhoheart.unochapeco.edu.br.unoheart.task.LoginEquipeTask;
import uhoheart.unochapeco.edu.br.unoheart.task.LoginPacienteTask;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;

public class ActivityLogin extends AppCompatActivity {

    private EditText txtLogin;
    private EditText txtSenha;
    private Button btnCadastrar;
    private Button btnLogin;
    private RadioButton radioPaciente;
    private RadioButton radioEquipe;


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("UnoHeart - Login");
        inicializarObjetos();
        requisitarPermissaoInternet();

        radioPaciente.setSelected(true);
        onRadioPacienteClick(null);
    }


    private void abrirTelaCadastroPaciente() {
        Intent intent = new Intent(this, ActivityNovaContaPaciente.class);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
        startActivity(intent);
    }

    private void abrirTelaCadastroEquipe() {
        Intent intent = new Intent(this, ActivityNovaContaEquipe.class);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
        startActivity(intent);
    }

    private void realizarLoginPaciente() {
        Paciente paciente = new Paciente();
        paciente.setEmail(txtLogin.getText().toString());
        paciente.setSenha(txtSenha.getText().toString());

        LoginPacienteTask loginPacienteTask = new LoginPacienteTask(this);
        loginPacienteTask.execute(paciente);
    }

    private void realizarLoginEquipe() {
        Equipe equipe = new Equipe();
        equipe.setEmail(txtLogin.getText().toString());
        equipe.setSenha(txtSenha.getText().toString());

        LoginEquipeTask loginEquipeTask = new LoginEquipeTask(this);
        loginEquipeTask.execute();
    }


    private void inicializarObjetos() {
        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCadastrar = (Button) findViewById(R.id.btnCadastar);
        radioPaciente = (RadioButton) findViewById(R.id.radioPaciente);
        radioEquipe = (RadioButton) findViewById(R.id.radioEquipe);
    }

    public void validarLoginPaciente(ExceptionTask exceptionTask, Paciente paciente) {
        if (exceptionTask != null) {
            Toast.makeText(this,
                    exceptionTask.getAll(),
                    Toast.LENGTH_LONG).show();
        } else {
            if (paciente == null) {
                Toast.makeText(this,
                        "Login ou senha inválidos. Tente novamente!",
                        Toast.LENGTH_LONG).show();
            } else {
                UsuarioLogadoDAO usuarioLogadoDAO = new UsuarioLogadoDAO(getBaseContext());
                try {
                    usuarioLogadoDAO.inserirPaciente(paciente);
                    Constantes.USUARIO_LOGADO = paciente;
                    Intent intent = new Intent(this, ActivityPerfilPaciente.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this,
                            Constantes.ERRO_GENERICO,
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void validarLoginEquipe(ExceptionTask exceptionTask, Equipe equipe) {
        if (exceptionTask != null) {
            Toast.makeText(this,
                    exceptionTask.getAll(),
                    Toast.LENGTH_LONG).show();
        } else {
            if (equipe == null) {
                Toast.makeText(this,
                        "Login ou senha inválidos. Tente novamente!",
                        Toast.LENGTH_LONG).show();
            } else {
                UsuarioLogadoDAO usuarioLogadoDAO = new UsuarioLogadoDAO(getBaseContext());
                try {
                    usuarioLogadoDAO.inserirEquipe(equipe);
                    Constantes.USUARIO_LOGADO = equipe;
                    Intent intent = new Intent(this, ActivityLogin.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this,
                            Constantes.ERRO_GENERICO,
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void requisitarPermissaoInternet() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.INTERNET);

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.INTERNET)) {
                Toast.makeText(this,
                        "Não foi possível recuperar permissão. "
                                .concat("Ative permissão para leitura dos sensores e tente novamente!"),
                        Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.INTERNET},
                        permissionCheck);
            }
        }
    }

    public void onRadioPacienteClick(View view) {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarLoginPaciente();
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastroPaciente();
            }
        });
    }

    public void onRadioEquipeClick(View view) {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarLoginEquipe();
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastroEquipe();
            }
        });
    }
}
