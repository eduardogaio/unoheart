package uhoheart.unochapeco.edu.br.unoheart.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uhoheart.unochapeco.edu.br.unoheart.R;
import uhoheart.unochapeco.edu.br.unoheart.model.ExceptionTask;
import uhoheart.unochapeco.edu.br.unoheart.model.Paciente;
import uhoheart.unochapeco.edu.br.unoheart.model.type.TipoSexo;
import uhoheart.unochapeco.edu.br.unoheart.task.CadastroPacienteTask;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;

public class ActivityNovaContaPaciente extends AppCompatActivity {

    private Button btnGravarPaciente;
    private EditText txtLogin;
    private EditText txtNome;
    private EditText txtNascimento;
    private RadioButton rbFeminino;
    private RadioButton rbMasculino;
    private EditText txtSenha;
    private EditText txtConfSenha;
//    private RadioGroup radioGroup;

    private static String LOG_TAG = "teste3";

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        Log.d(LOG_TAG, "onSupportParentActivityIntent");
        return super.getSupportParentActivityIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_conta_paciente);

        setTitle("Cadastro Paciente");
        inicializarObjetos();
    }


    @Override
    protected void onPostResume() {
        Log.d(LOG_TAG, "onPostResume");
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }

    private void inicializarObjetos() {
        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtNascimento = (EditText) findViewById(R.id.txtNascimento);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtConfSenha = (EditText) findViewById(R.id.txtConfSenha);

        btnGravarPaciente = (Button) findViewById(R.id.btnGravarPaciente);

//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                radioGroup.clearCheck();
//                radioGroup.check(i);
//            }
//        });


        btnGravarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    realizarCadastro();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void realizarCadastro() throws ParseException {
        Paciente paciente = new Paciente();
        paciente.setEmail(txtLogin.getText().toString());
        paciente.setNome(txtNome.getText().toString());
        paciente.setSenha(txtSenha.getText().toString());
        paciente.setConfSenha(txtConfSenha.getText().toString());
        SimpleDateFormat format = new SimpleDateFormat();
        Date date = format.parse(txtNascimento.getText().toString());

        if (rbMasculino.isChecked()) {
            paciente.setSexo(TipoSexo.MASCULINO);
        } else {
            paciente.setSexo(TipoSexo.FEMININO);
        }

        CadastroPacienteTask cadastroPacienteTask = new CadastroPacienteTask(this);
        cadastroPacienteTask.execute(paciente);
    }

    public void processarCadastroPaciente(ExceptionTask exceptionTask, Paciente paciente) {
        if (exceptionTask != null) {
            Toast.makeText(this,
                    exceptionTask.getAll(),
                    Toast.LENGTH_LONG).show();
        } else {
            Constantes.USUARIO_LOGADO = paciente;
            Intent intent = new Intent(this, ActivityPerfilPaciente.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

}
