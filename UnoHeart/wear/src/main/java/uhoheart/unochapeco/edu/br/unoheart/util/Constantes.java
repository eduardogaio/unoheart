package uhoheart.unochapeco.edu.br.unoheart.util;

import uhoheart.unochapeco.edu.br.unoheart.model.type.UnidadeTempo;

/**
 * Created by Eduardo on 12/01/2017.
 */

public class Constantes {

    public static final String ENCODING_PADRAO = "UTF-8";
    public static final String ERRO_GENERICO = "Não foi possível processar a requisição, verifique sua conexão com a internet. Tente novamente mais tarde!";
    public static int INTERVALO_PADRAO = UnidadeTempo.SEGUNDO.converter(5);
    public static UnidadeTempo UNIDADE_TEMPO_PADRAO = UnidadeTempo.SEGUNDO;
}
