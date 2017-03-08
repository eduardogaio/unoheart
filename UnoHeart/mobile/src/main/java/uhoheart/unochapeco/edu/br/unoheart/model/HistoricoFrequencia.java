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
 * Created by Eduardo on 12/01/2017.
 */

public class HistoricoFrequencia {

    private Integer codigo;
    private Paciente paciente;
    private Date datahora;
    private Integer frequencia;

    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    public HistoricoFrequencia parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        HistoricoFrequencia f = gson.fromJson(JSONString, HistoricoFrequencia.class);
        return f;
    }

    public HistoricoFrequencia parse(InputStream in) throws UnsupportedEncodingException {
        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        HistoricoFrequencia f = gson.fromJson(reader, HistoricoFrequencia.class);
        return f;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }
}
