package uhoheart.unochapeco.edu.br.unoheart.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Eduardo on 12/01/2017.
 */

public class PadraoDAO {

    private Context context;
    private UnoHeartDatabaseWearHelper database;
    private final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public PadraoDAO(Context context) {
        this.context = context;
        database = new UnoHeartDatabaseWearHelper(context);
    }

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                DATE_TIME_FORMAT, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public Date getDateTime(String text) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            return dateFormat.parse(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SQLiteDatabase getWritableDatabase() {
        return database.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase() {
        return database.getReadableDatabase();
    }
}
