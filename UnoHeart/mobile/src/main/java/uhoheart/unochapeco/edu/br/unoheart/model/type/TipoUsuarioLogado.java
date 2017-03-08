package uhoheart.unochapeco.edu.br.unoheart.model.type;

/**
 * Created by Eduardo on 16/02/2017.
 */

public enum TipoUsuarioLogado {

    PACIENTE(1, "Prestador"),
    EQUIPE(2, "Equipe");

    private final int codigo;
    private final String descricao;

    private TipoUsuarioLogado(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

    public static TipoUsuarioLogado parse(int value) {

        for (TipoUsuarioLogado tipo : values()) {
            if (value == tipo.getCodigo()) {
                return tipo;
            }
        }

        throw new IllegalArgumentException("Valor inválido: " + value);
    }
}
