package uhoheart.unochapeco.edu.br.unoheart.model;

import com.google.gson.Gson;

import java.util.Date;

import uhoheart.unochapeco.edu.br.unoheart.util.CustomGsonBuilder;

/**
 * Created by Eduardo on 12/01/2017.
 */

public class HistoricoFrequencia {

    private Integer codigo;
    private Date datahora;
    private Integer frequencia;

    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }
}
