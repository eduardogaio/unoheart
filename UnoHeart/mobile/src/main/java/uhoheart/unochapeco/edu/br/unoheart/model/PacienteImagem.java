package uhoheart.unochapeco.edu.br.unoheart.model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.util.CustomGsonBuilder;

/**
 * Created by Eduardo on 06/03/2017.
 */

public class PacienteImagem implements Serializable {
    private Integer codigo;
    private String imagem;
    private Paciente paciente;

    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    public PacienteImagem parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        PacienteImagem pacienteImagem = gson.fromJson(JSONString, PacienteImagem.class);
        return pacienteImagem;
    }

    public PacienteImagem parse(InputStream in) throws UnsupportedEncodingException {
        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        PacienteImagem pacienteImagem = gson.fromJson(reader, PacienteImagem.class);
        return pacienteImagem;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
