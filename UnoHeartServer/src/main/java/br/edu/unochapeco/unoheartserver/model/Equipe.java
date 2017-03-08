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
public class Equipe implements PadraoModel<Equipe> {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Integer codigo;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String nome;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private TipoEquipe tipoEquipe;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String email;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String senha;
    @JsonDeserialize(using = CustomDateDeserialize.class)
    @JsonSerialize(using = CustomDateSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    private LocalDate dtinicial;
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String confSenha;

    @Override
    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    @Override
    public Equipe parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        Equipe equipe = gson.fromJson(JSONString, Equipe.class);
        return equipe;
    }

    @Override
    public Equipe parse(InputStream in) throws Exception {
        Gson gson = CustomGsonBuilder.getInstance();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Constantes.ENCODING_PADRAO));
        Equipe equipe = gson.fromJson(reader, Equipe.class);
        return equipe;
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

    public TipoEquipe getTipoEquipe() {
        return tipoEquipe;
    }

    public void setTipoEquipe(TipoEquipe tipoEquipe) {
        this.tipoEquipe = tipoEquipe;
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

    public LocalDate getDtinicial() {
        return dtinicial;
    }

    public void setDtinicial(LocalDate dtinicial) {
        this.dtinicial = dtinicial;
    }

    public String getConfSenha() {
        return confSenha;
    }

    public void setConfSenha(String confSenha) {
        this.confSenha = confSenha;
    }

}
