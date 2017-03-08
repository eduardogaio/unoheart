package uhoheart.unochapeco.edu.br.unoheart.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import uhoheart.unochapeco.edu.br.unoheart.R;
import uhoheart.unochapeco.edu.br.unoheart.adapter.MenuManutencaoPerfilPacienteAdapter;
import uhoheart.unochapeco.edu.br.unoheart.model.type.TipoOpcoesMenuPerfilPaciente;


public class FragmentPerfilPaciente extends Fragment {

    private ListView listView;
//    private ArrayAdapter<String> listAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil_paciente, container, false);
        listView = (ListView) view.findViewById(R.id.perfilPacienteListview);

        MenuManutencaoPerfilPacienteAdapter adapter = new MenuManutencaoPerfilPacienteAdapter(getContext(),
                TipoOpcoesMenuPerfilPaciente.values());
//
//        ArrayAdapter<TipoOpcoesMenuPerfilPaciente> listAdapter =
//                new ArrayAdapter<TipoOpcoesMenuPerfilPaciente>(getContext(),
//                        R.layout.item_listview_perfil_paciente, TipoOpcoesMenuPerfilPaciente.values());


        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TipoOpcoesMenuPerfilPaciente item = (TipoOpcoesMenuPerfilPaciente) adapterView.getAdapter().getItem(i);
                item.abrirActivity(getActivity());
            }
        });

        return view;
    }


}
