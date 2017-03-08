/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.model.exception;

import br.edu.unochapeco.unoheartserver.util.Constantes;
import br.edu.unochapeco.unoheartserver.util.CustomGsonBuilder;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class ErrorHandling implements Serializable {

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

}
