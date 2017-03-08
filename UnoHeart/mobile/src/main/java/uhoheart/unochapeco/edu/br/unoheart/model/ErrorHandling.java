package uhoheart.unochapeco.edu.br.unoheart.model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.util.CustomGsonBuilder;

/**
 * Created by Eduardo on 17/01/2017.
 */

public class ErrorHandling {

    private List<String> error;

    public static ErrorHandling getInstance(InputStream in) throws UnsupportedEncodingException {

        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        ErrorHandling msg = gson.fromJson(reader, ErrorHandling.class);
        return msg;
    }

    public static ErrorHandling getInstance(String JSONString) throws UnsupportedEncodingException {

        Gson gson = CustomGsonBuilder.getInstance();
        ErrorHandling msg = gson.fromJson(JSONString, ErrorHandling.class);
        return msg;
    }

    public void addMessage(String msg) {
        if (this.error.contains(msg) == false) {
            this.error.add(msg);
        }
    }

    public ErrorHandling(String msg) {
        this();
        this.error.add(msg);
    }

    public ErrorHandling() {
        this.error = new ArrayList<>();
    }

    public ErrorHandling(List<String> error) {
        this.error = error;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String error : getError()) {
            stringBuilder
                    .append(error)
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}