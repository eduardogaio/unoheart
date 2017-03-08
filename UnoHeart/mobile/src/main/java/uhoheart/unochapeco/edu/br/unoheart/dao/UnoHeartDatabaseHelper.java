package uhoheart.unochapeco.edu.br.unoheart.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eduardo on 12/01/2017.
 */

public class UnoHeartDatabaseHelper extends SQLiteOpenHelper {

    private static final String NOME = "unoheart.db";
    private static final int VERSAO = 8;

    public UnoHeartDatabaseHelper(Context context) {
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /**
         * Cria as tabelas do sistema.
         */

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("CREATE TABLE HISTORICOFREQUENCIA ")
                .append("(")
                .append("CODIGO INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append("DATAHORA TIMESTAMP, ")
                .append("PACIENTE_CODIGO INTEGER, ")
                .append("FREQUENCIA INTEGER ")
                .append("); ");

        sqLiteDatabase.execSQL(stringBuilder.toString());

        stringBuilder = new StringBuilder();
        stringBuilder
                .append("CREATE TABLE USUARIOLOGADO ")
                .append("(")
                .append("CODIGO INTEGER, ")
                .append("NOME TEXT, ")
                .append("EMAIL TEXT, ")
                .append("SENHA TEXT, ")
                .append("TIPO INTEGER, ")
                .append("NASCIMENTO DATE, ")
                .append("IMAGEM TEXT ")
                .append(");");
        try {
            sqLiteDatabase.execSQL(stringBuilder.toString());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS HISTORICOFREQUENCIA");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USUARIOLOGADO");

        /**
         * Recria as tabelas.
         */
        this.onCreate(sqLiteDatabase);
    }
}
