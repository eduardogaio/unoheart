package uhoheart.unochapeco.edu.br.unoheart.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;
import uhoheart.unochapeco.edu.br.unoheart.model.Paciente;
import uhoheart.unochapeco.edu.br.unoheart.model.type.TabelaHistoricoFrequencia;

/**
 * Created by Eduardo on 12/01/2017.
 */

public class HistoricoFrequenciaDAO extends PadraoDAO {


    public HistoricoFrequenciaDAO(Context context) {
        super(context);
    }

    public void insert(HistoricoFrequencia value) throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(TabelaHistoricoFrequencia.PACIENTE_CODIGO.getCampo(), value.getPaciente().getCodigo());
            values.put(TabelaHistoricoFrequencia.FREQUENCIA.getCampo(), value.getFrequencia());
            values.put(TabelaHistoricoFrequencia.DATAHORA.getCampo(), getDateTime());

            db.insert(TabelaHistoricoFrequencia.getTableNale(), null, values);

        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }

    public void delete(HistoricoFrequencia value) throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        try {
            if (value.getCodigo() != null) {
                StringBuilder strBuilder = new StringBuilder();
                strBuilder
                        .append("delete from HISTORICOFREQUENCIA ")
                        .append("where CODIGO = ")
                        .append(String.valueOf(value.getCodigo()));

                db.execSQL(strBuilder.toString());
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }

    public List<HistoricoFrequencia> getAll() throws Exception {
        List<HistoricoFrequencia> retorno = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try {
            StringBuilder query = new StringBuilder();
            query
                    .append("select CODIGO, FREQUENCIA, DATAHORA, PACIENTE_CODIGO ")
                    .append("from ".concat(TabelaHistoricoFrequencia.getTableNale()))
                    .append("order by CODIGO ");

            Cursor cursor = db.rawQuery(query.toString(), null);
            while (cursor.moveToNext()) {
                HistoricoFrequencia historicoFrequencia = new HistoricoFrequencia();
                historicoFrequencia.setFrequencia(
                        cursor.getInt(cursor.getColumnIndex(
                                TabelaHistoricoFrequencia.FREQUENCIA.getCampo())));

                historicoFrequencia.setCodigo(
                        cursor.getInt(cursor.getColumnIndex(
                                TabelaHistoricoFrequencia.CODIGO.getCampo())));

                Paciente paciente = new Paciente();
                paciente.setCodigo(
                        cursor.getInt(cursor.getColumnIndex(
                                TabelaHistoricoFrequencia.PACIENTE_CODIGO.getCampo())));
                historicoFrequencia.setPaciente(paciente);

                historicoFrequencia.setDatahora(getDateTime(
                        cursor.getString(cursor.getColumnIndex(
                                TabelaHistoricoFrequencia.DATAHORA.getCampo()))));

                retorno.add(historicoFrequencia);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }

        return retorno;
    }
}