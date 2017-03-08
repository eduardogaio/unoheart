package uhoheart.unochapeco.edu.br.unoheart.model.type;

/**
 * Created by Eduardo on 12/01/2017.
 */

public enum UnidadeTempo {
    SEGUNDO("1", "Segundo") {
        @Override
        public int converter(int valor) {
            return valor * 1000;
        }
    },
    MINUTO("2", "Minuto") {
        @Override
        public int converter(int valor) {
            return (valor * 60) * 1000;
        }
    },
    HORA("3", "Hora") {
        @Override
        public int converter(int valor) {
            return ((valor * 60) * 60) * 1000;
        }
    };

    private final String codigo;
    private final String descricao;

    private UnidadeTempo(String codigo, String descricao) {
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

    public static UnidadeTempo parse(String value) {
        if (value != null) {
            for (UnidadeTempo tipo : values()) {
                if (value.equalsIgnoreCase(tipo.getCodigo())) {
                    return tipo;
                }
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + value);
    }

    public abstract int converter(int valor);
}
