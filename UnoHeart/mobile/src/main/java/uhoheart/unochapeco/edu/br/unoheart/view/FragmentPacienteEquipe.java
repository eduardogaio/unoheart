package uhoheart.unochapeco.edu.br.unoheart.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import uhoheart.unochapeco.edu.br.unoheart.R;
import uhoheart.unochapeco.edu.br.unoheart.model.ListenerRetornoPacienteEquipe;
import uhoheart.unochapeco.edu.br.unoheart.model.PacienteEquipe;
import uhoheart.unochapeco.edu.br.unoheart.task.ListarPacienteEquipeTask;


public class FragmentPacienteEquipe extends Fragment implements ListenerRetornoPacienteEquipe {

    private static String ARG_PARAM1 = "ARG_PARAM1";
    private PacienteEquipe pacienteEquipe;
    private ListView listView;
    private Button btnAdiconarEquipe;

    public FragmentPacienteEquipe() {
        // Required empty public constructor
    }

    public static FragmentPacienteEquipe newInstance() {
        FragmentPacienteEquipe fragment = new FragmentPacienteEquipe();
        return fragment;
    }

    public static FragmentPacienteEquipe newInstance(PacienteEquipe param1) {
        FragmentPacienteEquipe fragment = new FragmentPacienteEquipe();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pacienteEquipe = new PacienteEquipe();

        if (getArguments() != null) {
            pacienteEquipe = (PacienteEquipe) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paciente_equipe, container, false);
        listView = (ListView) view.findViewById(R.id.listViewPacienteEquipe);
        btnAdiconarEquipe = (Button) view.findViewById(R.id.btnAdiconarEquipe);

        btnAdiconarEquipe.setText("ADICONAR ".concat(pacienteEquipe.getTipoEquipe().getDescricao().toUpperCase()));

        btnAdiconarEquipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity().getApplicationContext(), ActivitySelecionarEquipe.class);
                intent.putExtra("ActivitySelecionarEquipe", pacienteEquipe);
                getActivity().startActivityForResult(intent, 1);
                getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            }
        });

        selecionarEquipe();

        return view;
    }

    private void selecionarEquipe() {
        if (getContext() != null) {
            ListarPacienteEquipeTask task = new ListarPacienteEquipeTask(getContext(), this);
            task.execute(pacienteEquipe);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void processar(List<PacienteEquipe> itens) {
        if (getContext() != null) {
            if (itens == null || itens.isEmpty()) {
                String[] values = new String[]{"Nenhum item encontrado..."};

                ArrayAdapter<String> listAdapter =
                        new ArrayAdapter<String>(getContext(),
                                R.layout.item_listview_perfil_paciente, values);

                listView.setAdapter(listAdapter);
            } else {
                ArrayAdapter<PacienteEquipe> listAdapter =
                        new ArrayAdapter<PacienteEquipe>(getContext(),
                                R.layout.item_listview_perfil_paciente, itens);

                listView.setAdapter(listAdapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        PacienteEquipe pacienteEquipe = (PacienteEquipe) adapterView.getAdapter().getItem(i);
                        String value = ((TextView) view).getText().toString();

                    }
                });
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            selecionarEquipe();
        }
    }


}
