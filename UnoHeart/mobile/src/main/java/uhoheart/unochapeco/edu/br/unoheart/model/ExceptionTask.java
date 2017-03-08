package uhoheart.unochapeco.edu.br.unoheart.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 23/02/2017.
 */

public class ExceptionTask extends Exception {

    private List<String> listaErro;

    public ExceptionTask() {
        listaErro = new ArrayList<>();
    }

    public ExceptionTask(String erro) {
        this();
        this.listaErro.add(erro);
    }

    public ExceptionTask(List<String> listaErro) {
        this();
        this.listaErro = listaErro;
    }

    public List<String> getListaErro() {
        return listaErro;
    }

    public String getAll() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String erro : listaErro) {
            stringBuilder
                    .append(erro)
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
