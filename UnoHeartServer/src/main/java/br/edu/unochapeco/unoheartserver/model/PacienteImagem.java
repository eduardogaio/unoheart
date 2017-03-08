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
public class PacienteImagem implements PadraoModel<PacienteImagem> {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer codigo;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Paciente paciente;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String imagem;
    @JsonDeserialize(using = CustomDateTimeDeserialize.class)
    @JsonSerialize(using = CustomDateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDateTime dthrinicial;
    @JsonDeserialize(using = CustomDateTimeDeserialize.class)
    @JsonSerialize(using = CustomDateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDateTime dthrfinal;

    @Override
    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    @Override
    public PacienteImagem parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        PacienteImagem pacienteImagem = gson.fromJson(JSONString, PacienteImagem.class);
        return pacienteImagem;
    }

    @Override
    public PacienteImagem parse(InputStream in) throws Exception {
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public LocalDateTime getDthrinicial() {
        return dthrinicial;
    }

    public void setDthrinicial(LocalDateTime dthrinicial) {
        this.dthrinicial = dthrinicial;
    }

    public LocalDateTime getDthrfinal() {
        return dthrfinal;
    }

    public void setDthrfinal(LocalDateTime dthrfinal) {
        this.dthrfinal = dthrfinal;
    }

}
