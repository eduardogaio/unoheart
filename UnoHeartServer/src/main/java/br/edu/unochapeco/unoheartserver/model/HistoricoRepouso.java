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
import java.time.LocalDateTime;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Eduardo
 */
public class HistoricoRepouso implements PadraoModel<HistoricoRepouso> {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer codigo;
    @JsonDeserialize(using = CustomDateTimeDeserialize.class)
    @JsonSerialize(using = CustomDateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDateTime datahora;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String relatorio;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Paciente paciente;

    @Override
    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    @Override
    public HistoricoRepouso parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        HistoricoRepouso historicoRepouso = gson.fromJson(JSONString, HistoricoRepouso.class);
        return historicoRepouso;
    }

    @Override
    public HistoricoRepouso parse(InputStream in) throws Exception {
        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        HistoricoRepouso historicoRepouso = gson.fromJson(reader, HistoricoRepouso.class);
        return historicoRepouso;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getDatahora() {
        return datahora;
    }

    public void setDatahora(LocalDateTime datahora) {
        this.datahora = datahora;
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

}
