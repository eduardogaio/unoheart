/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.model;

import br.edu.unochapeco.unoheartserver.types.TipoUnidadeTempo;
import br.edu.unochapeco.unoheartserver.util.Constantes;
import br.edu.unochapeco.unoheartserver.util.CustomGsonBuilder;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Eduardo
 */
public class PacienteParametro implements PadraoModel<PacienteParametro> {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Paciente paciente;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer intervalo;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private TipoUnidadeTempo unidadetempo;

    @Override
    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    @Override
    public PacienteParametro parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        PacienteParametro pacienteParametro = gson.fromJson(JSONString, PacienteParametro.class);
        return pacienteParametro;
    }

    @Override
    public PacienteParametro parse(InputStream in) throws Exception {
        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        PacienteParametro pacienteParametro = gson.fromJson(reader, PacienteParametro.class);
        return pacienteParametro;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Integer getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(Integer intervalo) {
        this.intervalo = intervalo;
    }

    public TipoUnidadeTempo getUnidadetempo() {
        return unidadetempo;
    }

    public void setUnidadetempo(TipoUnidadeTempo unidadetempo) {
        this.unidadetempo = unidadetempo;
    }

}
