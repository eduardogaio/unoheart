package uhoheart.unochapeco.edu.br.unoheart.model.type;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Eduardo on 17/01/2017.
 */

public enum RotinasSistemaCadastro {
    LOGIN_PACIENTE(
            "Realizar login do paciente no sistema",
            "http://192.168.1.103:8084//UnoHeartServer/rest/paciente/login"),
    LOGIN_EQUIPE(
            "Realizar login da equipe no sistema",
            "http://192.168.1.103:8084//UnoHeartServer/rest/equipe/login"),
    CADASTRAR_PACIENTE(
            "Rotina para criação de conta para acesso ao sistema pelo paciente",
            "http://192.168.1.103:8084//UnoHeartServer/rest/paciente/incluir"),
    CADASTRAR_EQUIPE(
            "Rotina para criação de conta para acesso ao sistema pela equipe",
            "http://192.168.1.103:8084//UnoHeartServer/rest/equipe/incluir"),
    CADASTRAR_PATOLOGIA(
            "Rotina para incluir patologia do paciente",
            "http://192.168.1.103:8084//UnoHeartServer/rest/pacientepatologia/incluir"),
    CADASTRAR_FREQUENCIA(
            "Rotina para cadastrar frequencia cardiaca do paciente",
            "http://192.168.1.103:8084//UnoHeartServer/rest/frequenciacardiaca/incluir"),
    CADASTRAR_PACIENTE_EQUIPE(
            "Rotina para criação de conta para acesso ao sistema pela equipe",
            "http://192.168.1.103:8084//UnoHeartServer/rest/paciente/incluirequipe"),
    CADASTRAR_PACIENTE_IMAGEM_PERFIL(
            "Rotina para cadastrar a imagem do perfil do paciente",
            "http://192.168.1.103:8084//UnoHeartServer/rest/pacienteimagem/incluir");

    RotinasSistemaCadastro(String descricao, String url) {
        this.descricao = descricao;
        this.url = url;
    }

    private String codigo;
    private String descricao;
    private String url;


    public String getDescricao() {
        return descricao;
    }

    public URL getUrl() {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return this.descricao;
    }


}
