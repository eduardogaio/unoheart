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
import java.time.LocalDateTime;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Eduardo
 */
public class PacienteEquipe implements PadraoModel<PacienteEquipe> {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer codigo;
    @JsonDeserialize(using = CustomDateDeserialize.class)
    @JsonSerialize(using = CustomDateSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDate dtinicial;
    @JsonDeserialize(using = CustomDateDeserialize.class)
    @JsonSerialize(using = CustomDateSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDateTime dtfinal;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Equipe equpe;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private TipoEquipe tipoEquipe;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Paciente paciente;

    @Override
    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    @Override
    public PacienteEquipe parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        PacienteEquipe pacienteEquipe = gson.fromJson(JSONString, PacienteEquipe.class);
        return pacienteEquipe;
    }

    @Override
    public PacienteEquipe parse(InputStream in) throws Exception {
        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        PacienteEquipe pacienteEquipe = gson.fromJson(reader, PacienteEquipe.class);
        return pacienteEquipe;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDtinicial() {
        return dtinicial;
    }

    public void setDtinicial(LocalDate dtinicial) {
        this.dtinicial = dtinicial;
    }

    public LocalDateTime getDtfinal() {
        return dtfinal;
    }

    public void setDtfinal(LocalDateTime dtfinal) {
        this.dtfinal = dtfinal;
    }

    public Equipe getEqupe() {
        return equpe;
    }

    public void setEqupe(Equipe equpe) {
        this.equpe = equpe;
    }

    public TipoEquipe getTipoEquipe() {
        return tipoEquipe;
    }

    public void setTipoEquipe(TipoEquipe tipoEquipe) {
        this.tipoEquipe = tipoEquipe;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

}
