package uhoheart.unochapeco.edu.br.unoheart.model.type;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Eduardo on 17/01/2017.
 */

public enum RotinasSistemaListagem {

    LISTAR_FREQUENCIA(
            "Rotina para exibir a listagem de frequencia cardiaca do paciente",
            "http://192.168.1.103:8084//UnoHeartServer/rest/frequenciacardiaca/listar/"),
    SELECIONAR_PACIENTE_EQUIPE(
            "Rotina para exibir a listagem de frequencia cardiaca do paciente",
            "http://192.168.1.103:8084//UnoHeartServer/rest/paciente/listarequipe/"),
    SELECIONAR_EQUIPE(
            "Rotina para exibir a listagem da equipe para selecionar",
            "http://192.168.1.103:8084//UnoHeartServer/rest/equipe/listar/");

    RotinasSistemaListagem(String descricao, String url) {
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
