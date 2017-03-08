package uhoheart.unochapeco.edu.br.unoheart.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import uhoheart.unochapeco.edu.br.unoheart.R;

public class ActivityPacienteAltura extends AppCompatActivity {

    private Button btnAdiconar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_altura);

        btnAdiconar = (Button) findViewById(R.id.btnAdiconarAltura);

        btnAdiconar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(getActivity().getApplicationContext(), ActivitySelecionarEquipe.class);
//                intent.putExtra("ActivitySelecionarEquipe", pacienteEquipe);
//                getActivity().startActivityForResult(intent, 1);
//                getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
}
