package uhoheart.unochapeco.edu.br.unoheart.view;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import uhoheart.unochapeco.edu.br.unoheart.R;
import uhoheart.unochapeco.edu.br.unoheart.model.Equipe;
import uhoheart.unochapeco.edu.br.unoheart.model.ErrorHandling;
import uhoheart.unochapeco.edu.br.unoheart.model.ListenerRetornoEquipe;
import uhoheart.unochapeco.edu.br.unoheart.model.Paciente;
import uhoheart.unochapeco.edu.br.unoheart.model.PacienteEquipe;
import uhoheart.unochapeco.edu.br.unoheart.model.type.RotinasSistemaCadastro;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;

public class ActivitySelecionarEquipe extends AppCompatActivity implements ListenerRetornoEquipe {


//    String[] dataArray = new String[]{"India", "Androidhub4you", "Pakistan", "Srilanka", "Nepal", "Japan"};

    private ListView listView;
    private ArrayAdapter<Equipe> myAdapter;
    private PacienteEquipe pacienteEquipe;
    private List<Equipe> listaEquipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_equipe);

        listaEquipe = new ArrayList<>();
        myAdapter = new ArrayAdapter<Equipe>(getBaseContext(), android.R.layout.simple_list_item_1, listaEquipe);
        pacienteEquipe = (PacienteEquipe) getIntent().getExtras().get("ActivitySelecionarEquipe");

        listView = (ListView) findViewById(R.id.listview);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i + " --postion");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pesquisar_equipe, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_pesquisa).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                myAdapter.getFilter().filter(newText);
                System.out.println("on text chnge text: " + newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // this is your adapter that will be filtered
                myAdapter.getFilter().filter(query);
                System.out.println("on query submit: " + query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void processar(List<Equipe> itens) {
        if (itens == null || itens.isEmpty()) {
            listaEquipe = itens;

            myAdapter = new ArrayAdapter<Equipe>(getBaseContext(), android.R.layout.simple_list_item_1, itens);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Equipe equipe = (Equipe) adapterView.getAdapter().getItem(i);

                    PacienteEquipe pacienteEquipe = new PacienteEquipe();
                    pacienteEquipe.setPaciente((Paciente) Constantes.USUARIO_LOGADO);
                    pacienteEquipe.setEqupe(equipe);
                    incluirPacienteEquipe(pacienteEquipe);
                }
            });
        }
        listView.setAdapter(myAdapter);
    }

    private void incluirPacienteEquipe(PacienteEquipe pacienteEquipe) {

        try {
            URL url = RotinasSistemaCadastro.CADASTRAR_PACIENTE_EQUIPE.getUrl();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(15000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("PUT");

            String msg = pacienteEquipe.toJSONString();
            con.setFixedLengthStreamingMode(msg.getBytes().length);

            con.setDoInput(true);
            con.setDoOutput(true);

            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            con.connect();

            OutputStream os = con.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(msg.getBytes());
            bos.flush();

            if (con.getResponseCode() == 200) {

                setResult(1);
            } else {
                if (con.getResponseCode() == 202) {
                    ErrorHandling error = ErrorHandling.getInstance(con.getInputStream());
                    if (error != null) {
                        Toast.makeText(getBaseContext(),
                                error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(),
                            Constantes.ERRO_GENERICO,
                            Toast.LENGTH_LONG).show();
                }
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
}
