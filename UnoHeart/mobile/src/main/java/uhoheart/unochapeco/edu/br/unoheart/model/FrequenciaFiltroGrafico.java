package uhoheart.unochapeco.edu.br.unoheart.model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.util.CustomGsonBuilder;

/**
 * Created by Eduardo on 04/03/2017.
 */

public class FrequenciaFiltroGrafico  {
    private Paciente paciente;
    private Date inicio;
    private Date fim;

    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    public FrequenciaFiltroGrafico parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        FrequenciaFiltroGrafico item = gson.fromJson(JSONString, FrequenciaFiltroGrafico.class);
        return item;
    }

    public FrequenciaFiltroGrafico parse(InputStream in) throws UnsupportedEncodingException {
        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        FrequenciaFiltroGrafico item = gson.fromJson(reader, FrequenciaFiltroGrafico.class);
        return item;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }
}
