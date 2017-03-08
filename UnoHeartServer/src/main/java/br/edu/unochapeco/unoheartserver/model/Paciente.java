/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.model;

import br.edu.unochapeco.unoheartserver.types.TipoSexo;
import br.edu.unochapeco.unoheartserver.util.Constantes;
import br.edu.unochapeco.unoheartserver.util.CustomDateDeserialize;
import br.edu.unochapeco.unoheartserver.util.CustomDateSerializer;
import br.edu.unochapeco.unoheartserver.util.CustomGsonBuilder;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author Eduardo
 */
public class Paciente implements PadraoModel<Paciente> {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer codigo;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String nome;
    @JsonDeserialize(using = CustomDateDeserialize.class)
    @JsonSerialize(using = CustomDateSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDate nascimento;
    @JsonDeserialize(using = CustomDateDeserialize.class)
    @JsonSerialize(using = CustomDateSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDate dtinicial;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private TipoSexo sexo;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String email;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String senha;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String confSenha;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private PacienteImagem pacienteImagem;

    public Paciente() {
    }

    public Paciente(Integer codigo) {
        this();
        this.codigo = codigo;
    }

    @JsonIgnore
    @Override
    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    @JsonIgnore
    @Override
    public Paciente parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        Paciente paciente = gson.fromJson(JSONString, Paciente.class);
        return paciente;
    }

    @JsonIgnore
    @Override
    public Paciente parse(InputStream in) throws UnsupportedEncodingException {
        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        Paciente paciente = gson.fromJson(reader, Paciente.class);
        return paciente;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public LocalDate getDtinicial() {
        return dtinicial;
    }

    public void setDtinicial(LocalDate dtinicial) {
        this.dtinicial = dtinicial;
    }

    public TipoSexo getSexo() {
        return sexo;
    }

    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfSenha() {
        return confSenha;
    }

    public void setConfSenha(String confSenha) {
        this.confSenha = confSenha;
    }

    public PacienteImagem getPacienteImagem() {
        return pacienteImagem;
    }

    public void setPacienteImagem(PacienteImagem pacienteImagem) {
        this.pacienteImagem = pacienteImagem;
    }

}
