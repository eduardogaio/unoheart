/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.model;

import br.edu.unochapeco.unoheartserver.util.Constantes;
import br.edu.unochapeco.unoheartserver.util.CustomGsonBuilder;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Eduardo
 */
public class HistoricoAltura implements PadraoModel<HistoricoAltura> {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer codigo;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Paciente paciente;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDateTime datahora;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Float altura;

    @Override
    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    @Override
    public HistoricoAltura parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        HistoricoAltura historicoAltura = gson.fromJson(JSONString, HistoricoAltura.class);
        return historicoAltura;
    }

    @Override
    public HistoricoAltura parse(InputStream in) throws Exception {
        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        HistoricoAltura historicoAltura = gson.fromJson(reader, HistoricoAltura.class);
        return historicoAltura;
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

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

}
