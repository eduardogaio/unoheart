package uhoheart.unochapeco.edu.br.unoheart.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import uhoheart.unochapeco.edu.br.unoheart.model.type.TabelaHistoricoFrequencia;
import uhoheart.unochapeco.edu.br.unoheart.model.type.TabelaUltimaColeta;

/**
 * Created by Eduardo on 12/01/2017.
 */

public class UnoHeartDatabaseWearHelper extends SQLiteOpenHelper {

    private static final String NOME = "wear-unoheart.db";
    private static final int VERSAO = 9;

    public UnoHeartDatabaseWearHelper(Context context) {
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /**
         * Inicializa o banco de dados.
         */
        sqLiteDatabase.execSQL(TabelaHistoricoFrequencia.toCreateTableSQL());
        sqLiteDatabase.execSQL(TabelaUltimaColeta.toCreateTableSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /**
         * Remove a tabela ao atualizar.
         */
        sqLiteDatabase.execSQL(TabelaHistoricoFrequencia.toDropTableSQL());
        sqLiteDatabase.execSQL(TabelaUltimaColeta.toDropTableSQL());

        /**
         * Recria as tabelas do sistema.
         */
        this.onCreate(sqLiteDatabase);
    }
}
