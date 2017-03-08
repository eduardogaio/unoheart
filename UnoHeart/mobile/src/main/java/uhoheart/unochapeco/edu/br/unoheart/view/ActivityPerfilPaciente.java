package uhoheart.unochapeco.edu.br.unoheart.view;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

import uhoheart.unochapeco.edu.br.unoheart.R;
import uhoheart.unochapeco.edu.br.unoheart.dao.UsuarioLogadoDAO;
import uhoheart.unochapeco.edu.br.unoheart.model.ExceptionTask;
import uhoheart.unochapeco.edu.br.unoheart.model.Paciente;
import uhoheart.unochapeco.edu.br.unoheart.model.PacienteEquipe;
import uhoheart.unochapeco.edu.br.unoheart.model.PacienteImagem;
import uhoheart.unochapeco.edu.br.unoheart.model.type.TipoEquipe;
import uhoheart.unochapeco.edu.br.unoheart.service.FrequenciaCardiacaService;
import uhoheart.unochapeco.edu.br.unoheart.service.FrequenciaSincronizadorService;
import uhoheart.unochapeco.edu.br.unoheart.task.CadastroPacienteImagemPerfilTask;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.util.Funcoes;
import uhoheart.unochapeco.edu.br.unoheart.util.RoundedImageView;

public class ActivityPerfilPaciente extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txtPerfilPacienteNome;
    private TextView txtPerfilPacienteEmail;
    private ImageView perfilPacienteImageView;
    private Paciente perfil;
    private int menuID = -1;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_paciente);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        startService(new Intent(this, FrequenciaCardiacaService.class));
        startService(new Intent(this, FrequenciaSincronizadorService.class));

//        bindService(new Intent(MainActivity.this, FrequenciaCardiacaService.class), new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName componentName, IBinder binder) {
//                Log.d(LOG_TAG, "connected to service.");
//                // set our change listener to get change events
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName componentName) {
//                Log.d(LOG_TAG, "desconnected to service.");
//            }
//        }, Service.BIND_AUTO_CREATE);


        View header = navigationView.getHeaderView(0);

        txtPerfilPacienteNome = (TextView) header.findViewById(R.id.perfilPacienteNome);
        txtPerfilPacienteEmail = (TextView) header.findViewById(R.id.perfilPacienteEmail);
        perfilPacienteImageView = (ImageView) header.findViewById(R.id.perfilPacienteImageView);

        perfilPacienteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            try {
                final Bitmap bmp = getBitmapFromUri(selectedImage);
                setImagePerfil(bmp);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String img = Funcoes.encodeToBase64(bmp, Bitmap.CompressFormat.JPEG, 30);
                        gravarimagem(img);
                    }
                }).start();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    private void gravarimagem(String img) {
        if (this.perfil.getPacienteImagem() == null) {
            this.perfil.setPacienteImagem(new PacienteImagem());
            this.perfil.getPacienteImagem().setImagem(img);
        }

        UsuarioLogadoDAO usuarioLogadoDAO = new UsuarioLogadoDAO(this);
        usuarioLogadoDAO.gravarImagem(img);

        CadastroPacienteImagemPerfilTask cadastroPacienteImagemPerfilTask = new CadastroPacienteImagemPerfilTask(this);
        cadastroPacienteImagemPerfilTask.execute(this.perfil);
    }

    private void setImagePerfil(Bitmap bmp) {
        try {
            if (bmp != null) {
                perfilPacienteImageView.setImageBitmap(new RoundedImageView(this).getCroppedBitmap(bmp, 130));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Constantes.USUARIO_LOGADO instanceof Paciente) {
            this.perfil = (Paciente) Constantes.USUARIO_LOGADO;

            txtPerfilPacienteNome.setText(this.perfil.getNome());
            txtPerfilPacienteEmail.setText(this.perfil.getEmail());
            if (this.perfil.getPacienteImagem() != null && this.perfil.getPacienteImagem().getImagem() != null) {
                setImagePerfil(Funcoes.decodeBase64(this.perfil.getPacienteImagem().getImagem()));
            }
        }
        abrirPerfil();
    }

    private void abrirPerfil() {
        FragmentPerfilPaciente fragmentPerfilPaciente = new FragmentPerfilPaciente();
        carregarFragment(fragmentPerfilPaciente);
        setTitle("Manutenção Perfil");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_perfil_paciente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sair) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        menuID = item.getItemId();
        return carregarViewItemMenu(item.getTitle());
    }


    private boolean carregarViewItemMenu(CharSequence title) {

        if (menuID == R.id.nav_sair) {
            logout();
        } else if (menuID == R.id.nav_perfil) {
            abrirPerfil();
        } else if (menuID == R.id.nav_cuidador) {
            PacienteEquipe pacienteEquipe = new PacienteEquipe();
            pacienteEquipe.setPaciente((Paciente) Constantes.USUARIO_LOGADO);
            pacienteEquipe.setTipoEquipe(TipoEquipe.CUIDADOR);

            FragmentPacienteEquipe fragmentPacienteEquipe = FragmentPacienteEquipe.newInstance(pacienteEquipe);

            carregarFragment(fragmentPacienteEquipe);
        } else if (menuID == R.id.nav_familiar) {
            PacienteEquipe pacienteEquipe = new PacienteEquipe();
            pacienteEquipe.setPaciente((Paciente) Constantes.USUARIO_LOGADO);
            pacienteEquipe.setTipoEquipe(TipoEquipe.FAMILIAR);

            FragmentPacienteEquipe fragmentPacienteEquipe = FragmentPacienteEquipe.newInstance(pacienteEquipe);

            carregarFragment(fragmentPacienteEquipe);
        } else if (menuID == R.id.nav_medico) {
            PacienteEquipe pacienteEquipe = new PacienteEquipe();
            pacienteEquipe.setPaciente((Paciente) Constantes.USUARIO_LOGADO);
            pacienteEquipe.setTipoEquipe(TipoEquipe.MEDICO);

            FragmentPacienteEquipe fragmentPacienteEquipe = FragmentPacienteEquipe.newInstance(pacienteEquipe);

            carregarFragment(fragmentPacienteEquipe);
        } else if (menuID == R.id.nav_frequencia) {
            PacienteEquipe pacienteEquipe = new PacienteEquipe();
            pacienteEquipe.setPaciente((Paciente) Constantes.USUARIO_LOGADO);
            pacienteEquipe.setTipoEquipe(TipoEquipe.MEDICO);

            FragmentFrequenciaCardiacaPaciente fragmentFrequenciaCardiacaPaciente = FragmentFrequenciaCardiacaPaciente.newInstance();

            carregarFragment(fragmentFrequenciaCardiacaPaciente);
        }

        if (title != null) {
            setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void carregarFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void logout() {
        UsuarioLogadoDAO dao = new UsuarioLogadoDAO(getBaseContext());
        dao.remover();

        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
        finish();
    }

    public void processarGravacaoRemotaImagem(ExceptionTask exceptionTask, PacienteImagem pacienteImagem) {
        if (exceptionTask != null) {
            Toast.makeText(this, exceptionTask.getAll(), Toast.LENGTH_SHORT).show();
        } else {
            if (pacienteImagem == null) {
                Toast.makeText(this, "Erro ao gravar imagem no servidor!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("title", getTitle().toString());
        outState.putString("menuID", String.valueOf(menuID));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CharSequence title = savedInstanceState.getString("title");
        CharSequence menu = savedInstanceState.getString("menuID");

        if (title != null) {
            setTitle(title);
        }

        if (menu != null) {
            menuID = Integer.valueOf(menu.toString());
            carregarViewItemMenu(title);
        } else {
            abrirPerfil();
        }
    }


}
