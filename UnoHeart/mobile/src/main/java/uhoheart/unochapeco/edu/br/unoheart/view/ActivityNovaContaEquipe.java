package uhoheart.unochapeco.edu.br.unoheart.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;

import uhoheart.unochapeco.edu.br.unoheart.R;
import uhoheart.unochapeco.edu.br.unoheart.model.Equipe;
import uhoheart.unochapeco.edu.br.unoheart.model.ExceptionTask;
import uhoheart.unochapeco.edu.br.unoheart.model.type.TipoEquipe;
import uhoheart.unochapeco.edu.br.unoheart.task.CadastroEquipeTask;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;

public class ActivityNovaContaEquipe extends AppCompatActivity {

    private Button btnGravarEquipe;
    private EditText txtLogin;
    private EditText txtNome;
    private Spinner spinnerTipoEquipe;
    private EditText txtSenha;
    private EditText txtConfSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_conta_equipe);

        setTitle("Cadastro Equipe");
        inicializarObjetos();
    }

    public void processarCadastroEquipe(ExceptionTask exceptionTask, Equipe equipe) {
        if (exceptionTask != null) {
            Toast.makeText(this,
                    exceptionTask.getAll(),
                    Toast.LENGTH_LONG).show();
        } else {
            Constantes.USUARIO_LOGADO = equipe;
//            Intent intent = new Intent(this, HomeActivity.class);
//            startActivity(intent);
//            finish();
        }
    }

    private void inicializarObjetos() {

        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        spinnerTipoEquipe = (Spinner) findViewById(R.id.spinnerTipoEquipe);
        txtConfSenha = (EditText) findViewById(R.id.txtConfSenha);
        btnGravarEquipe = (Button) findViewById(R.id.btnGravarEquipe);


        spinnerTipoEquipe.setAdapter(new ArrayAdapter<TipoEquipe>(this,
                R.layout.item_spinner, TipoEquipe.values()));


        btnGravarEquipe.setOnClickListener(new View.OnClickListener() {
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
        Equipe equipe = new Equipe();
        equipe.setEmail(txtLogin.getText().toString());
        equipe.setNome(txtNome.getText().toString());
        equipe.setSenha(txtSenha.getText().toString());
        equipe.setConfSenha(txtConfSenha.getText().toString());
        if (spinnerTipoEquipe.getSelectedItem() != null) {
            equipe.setTipoEquipe((TipoEquipe) spinnerTipoEquipe.getSelectedItem());
        }


        CadastroEquipeTask cadastroEquipeTask = new CadastroEquipeTask(this);
        cadastroEquipeTask.execute(equipe);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
}
