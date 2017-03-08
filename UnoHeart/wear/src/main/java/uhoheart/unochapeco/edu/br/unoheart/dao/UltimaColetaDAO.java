package uhoheart.unochapeco.edu.br.unoheart.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uhoheart.unochapeco.edu.br.unoheart.model.HistoricoFrequencia;
import uhoheart.unochapeco.edu.br.unoheart.model.type.TabelaUltimaColeta;

/**
 * Created by Eduardo on 12/01/2017.
 */

public class UltimaColetaDAO extends PadraoDAO {


    public UltimaColetaDAO(Context context) {
        super(context);
    }

    public void insert(HistoricoFrequencia value) throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(TabelaUltimaColeta.FREQUENCIA.getCampo(), value.getFrequencia());
            values.put(TabelaUltimaColeta.DATAHORA.getCampo(), getDateTime());

            db.insert(TabelaUltimaColeta.getTableNale(), null, values);
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
                    .append("select CODIGO, FREQUENCIA, DATAHORA ")
                    .append("from ".concat(TabelaUltimaColeta.getTableNale()))
                    .append("order by CODIGO desc");

            Cursor cursor = db.rawQuery(query.toString(), null);
            while (cursor.moveToNext()) {
                HistoricoFrequencia historicoFrequencia = new HistoricoFrequencia();

                historicoFrequencia.setCodigo(cursor.getInt(cursor.getColumnIndex(
                        TabelaUltimaColeta.CODIGO.getCampo())));

                historicoFrequencia.setFrequencia(
                        cursor.getInt(cursor.getColumnIndex(
                                TabelaUltimaColeta.FREQUENCIA.getCampo())));

                historicoFrequencia.setDatahora(getDateTime(
                        cursor.getString(cursor.getColumnIndex(
                                TabelaUltimaColeta.DATAHORA.getCampo()))));

                retorno.add(historicoFrequencia);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }

        return retorno;
    }

    public void delete() throws Exception {
        SQLiteDatabase db = getWritableDatabase();
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("delete from ULTIMACOLETA ");

            db.execSQL(strBuilder.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
}