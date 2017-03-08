package uhoheart.unochapeco.edu.br.unoheart.model.type;

/**
 * Created by Eduardo on 12/01/2017.
 */

public enum TipoSexo {
    FEMININO("F", "Feminino"),
    MASCULINO("M", "Masculino");

    private final String codigo;
    private final String descricao;

    private TipoSexo(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

    public static TipoSexo parse(String value) {
        if (value != null) {
            for (TipoSexo tipo : values()) {
                if (value.equalsIgnoreCase(tipo.getCodigo())) {
                    return tipo;
                }
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + value);
    }
}
