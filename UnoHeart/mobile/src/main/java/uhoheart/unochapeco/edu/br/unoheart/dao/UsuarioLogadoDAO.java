package uhoheart.unochapeco.edu.br.unoheart.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;

import uhoheart.unochapeco.edu.br.unoheart.model.Equipe;
import uhoheart.unochapeco.edu.br.unoheart.model.Paciente;
import uhoheart.unochapeco.edu.br.unoheart.model.PacienteImagem;
import uhoheart.unochapeco.edu.br.unoheart.model.type.TipoUsuarioLogado;

/**
 * Created by Eduardo on 15/02/2017.
 */

public class UsuarioLogadoDAO extends PadraoDAO {

    public UsuarioLogadoDAO(Context context) {
        super(context);
    }

    public void inserirPaciente(Paciente value) throws Exception {
        remover();

        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("CODIGO", value.getCodigo());
            values.put("SENHA", value.getSenha());
            values.put("NOME", value.getNome());
            values.put("EMAIL", value.getEmail());
            if (value.getPacienteImagem() != null) {
                values.put("IMAGEM", value.getPacienteImagem().getImagem());
            }
            values.put("TIPO", TipoUsuarioLogado.PACIENTE.getCodigo());
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.getDefault());
            values.put("NASCIMENTO", dateFormat.format(value.getNascimento()));
            db.insert("USUARIOLOGADO", null, values);
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }

    public void gravarImagem(String img) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("IMAGEM", img);
            db.update("USUARIOLOGADO", values, null, null);
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }

    public void inserirEquipe(Equipe value) throws Exception {
        remover();

        SQLiteDatabase db = getWritableDatabase();
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("insert into USUARIOLOGADO ")
                    .append("(CODIGO, TIPO) ")
                    .append("values (")
                    .append(value.getCodigo())
                    .append(",")
                    .append(TipoUsuarioLogado.EQUIPE.getCodigo())
                    .append(")");

            db.execSQL(strBuilder.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }

    public void remover() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("delete from USUARIOLOGADO ");

            db.execSQL(strBuilder.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }

    public Paciente selecionarPaciente() {
        Paciente paciente = null;

        SQLiteDatabase db = getReadableDatabase();
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO, NOME, SENHA, EMAIL, TIPO, NASCIMENTO, IMAGEM ")
                    .append("from USUARIOLOGADO ")
                    .append("where TIPO = ")
                    .append(TipoUsuarioLogado.PACIENTE.getCodigo());
            Cursor cursor = db.rawQuery(strBuilder.toString(), null);
            if (cursor.moveToNext()) {
                paciente = new Paciente();
                paciente.setCodigo(cursor.getInt(cursor.getColumnIndex("CODIGO")));
                paciente.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
                paciente.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
                paciente.setSenha(cursor.getString(cursor.getColumnIndex("SENHA")));
                String img = cursor.getString(cursor.getColumnIndex("IMAGEM"));
                if (img != null) {
                    paciente.setPacienteImagem(new PacienteImagem());
                    paciente.getPacienteImagem().setImagem(img);
                }
                paciente.setNascimento(getDate(cursor.getString(cursor.getColumnIndex("NASCIMENTO"))));
            }

            return paciente;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }

    public Equipe selecionarEquipe() {
        Equipe equipe = null;

        SQLiteDatabase db = getReadableDatabase();
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO, TIPO ")
                    .append("from USUARIOLOGADO ")
                    .append("where TIPO = ")
                    .append(TipoUsuarioLogado.EQUIPE.getCodigo());
            Cursor cursor = db.rawQuery(strBuilder.toString(), null);
            if (cursor.moveToNext()) {
                equipe = new Equipe();
                equipe.setCodigo(cursor.getInt(cursor.getColumnIndex("CODIGO")));
            }

            return equipe;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
}
