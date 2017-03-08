package uhoheart.unochapeco.edu.br.unoheart.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Eduardo on 04/03/2017.
 */

public class Funcoes {

    public static int getIdade(Date dtnasc) {
        Calendar nascimento = Calendar.getInstance();
        nascimento.setTime(dtnasc);

        Calendar dataatual = Calendar.getInstance();
        dataatual.setTime(new Date());

        int idade = dataatual.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);
        return idade;
    }

    public static int getFCMaxima(Date dtnasc) {
        return getFCMaxima(getIdade(dtnasc));
    }

    public static int getFCMaxima(int idade) {
        return (int) Math.round(208 - (idade * 0.7));
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
