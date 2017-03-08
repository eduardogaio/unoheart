/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.model;

import br.edu.unochapeco.unoheartserver.util.Constantes;
import br.edu.unochapeco.unoheartserver.util.CustomDateTimeDeserialize;
import br.edu.unochapeco.unoheartserver.util.CustomDateTimeSerializer;
import br.edu.unochapeco.unoheartserver.util.CustomGsonBuilder;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Eduardo
 */
public class HistoricoFrequencia implements PadraoModel<HistoricoFrequencia> {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer codigo;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Paciente paciente;
    @JsonDeserialize(using = CustomDateTimeDeserialize.class)
    @JsonSerialize(using = CustomDateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDateTime datahora;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer frequencia;

    @JsonIgnore
    @Override
    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    @JsonIgnore
    @Override
    public HistoricoFrequencia parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        HistoricoFrequencia f = gson.fromJson(JSONString, HistoricoFrequencia.class);
        return f;
    }

    @JsonIgnore
    @Override
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

    public LocalDateTime getDatahora() {
        return datahora;
    }

    public void setDatahora(LocalDateTime datahora) {
        this.datahora = datahora;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

}
