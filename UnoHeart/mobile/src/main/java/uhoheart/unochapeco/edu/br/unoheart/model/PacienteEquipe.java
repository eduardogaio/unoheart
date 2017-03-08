package uhoheart.unochapeco.edu.br.unoheart.model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Date;

import uhoheart.unochapeco.edu.br.unoheart.model.type.TipoEquipe;
import uhoheart.unochapeco.edu.br.unoheart.util.Constantes;
import uhoheart.unochapeco.edu.br.unoheart.util.CustomGsonBuilder;

/**
 * Created by Eduardo on 18/02/2017.
 */

public class PacienteEquipe implements Serializable {
    private Integer codigo;
    private Date dtinicial;
    private Date dtfinal;
    private Equipe equpe;
    private TipoEquipe tipoEquipe;
    private Paciente paciente;

    @Override
    public String toString() {
        return getEqupe().getNome();
    }

    public String toJSONString() {
        Gson gson = CustomGsonBuilder.getInstance();
        String userJSONString = gson.toJson(this);
        return userJSONString;
    }

    public PacienteEquipe parse(String JSONString) {
        Gson gson = CustomGsonBuilder.getInstance();
        PacienteEquipe pacienteEquipe = gson.fromJson(JSONString, PacienteEquipe.class);
        return pacienteEquipe;
    }


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

    public Date getDtinicial() {
        return dtinicial;
    }

    public void setDtinicial(Date dtinicial) {
        this.dtinicial = dtinicial;
    }

    public Date getDtfinal() {
        return dtfinal;
    }

    public void setDtfinal(Date dtfinal) {
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
