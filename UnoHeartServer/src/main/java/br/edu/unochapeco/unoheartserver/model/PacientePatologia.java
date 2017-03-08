/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.model;

import br.edu.unochapeco.unoheartserver.util.Constantes;
import br.edu.unochapeco.unoheartserver.util.CustomDateDeserialize;
import br.edu.unochapeco.unoheartserver.util.CustomDateSerializer;
import br.edu.unochapeco.unoheartserver.util.CustomGsonBuilder;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Eduardo
 */
public class PacientePatologia implements PadraoModel<PacientePatologia> {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer codigo;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Paciente paciente;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String relatorio;
    @JsonDeserialize(using = CustomDateDeserialize.class)
    @JsonSerialize(using = CustomDateSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDate dtinicial;
    @JsonDeserialize(using = CustomDateDeserialize.class)
    @JsonSerialize(using = CustomDateSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDate dtfinal;

    @Override
    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    @Override
    public PacientePatologia parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        PacientePatologia pacientePatologia = gson.fromJson(JSONString, PacientePatologia.class);
        return pacientePatologia;
    }

    @Override
    public PacientePatologia parse(InputStream in) throws Exception {
        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        PacientePatologia pacientePatologia = gson.fromJson(reader, PacientePatologia.class);
        return pacientePatologia;
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

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public LocalDate getDtinicial() {
        return dtinicial;
    }

    public void setDtinicial(LocalDate dtinicial) {
        this.dtinicial = dtinicial;
    }

    public LocalDate getDtfinal() {
        return dtfinal;
    }

    public void setDtfinal(LocalDate dtfinal) {
        this.dtfinal = dtfinal;
    }

}
