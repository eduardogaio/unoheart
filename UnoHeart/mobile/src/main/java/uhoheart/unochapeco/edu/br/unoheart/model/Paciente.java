package uhoheart.unochapeco.edu.br.unoheart.model;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import uhoheart.unochapeco.edu.br.unoheart.model.type.TipoSexo;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.util.CustomGsonBuilder;

/**
 * Created by Eduardo on 12/01/2017.
 */

public class Paciente implements Serializable {

    private Integer codigo;
    private String nome;
    private Date nascimento;
    private Date dtinicial;
    private TipoSexo sexo;
    private String email;
    private String senha;
    private String confSenha;
    private PacienteImagem pacienteImagem;

    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    public Paciente parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        Paciente paciente = gson.fromJson(JSONString, Paciente.class);
        return paciente;
    }

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

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public Date getDtinicial() {
        return dtinicial;
    }

    public void setDtinicial(Date dtinicial) {
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
