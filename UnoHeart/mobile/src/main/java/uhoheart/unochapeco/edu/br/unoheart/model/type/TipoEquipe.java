package uhoheart.unochapeco.edu.br.unoheart.model.type;

/**
 * Created by Eduardo on 14/02/2017.
 */

public enum TipoEquipe {

    CUIDADOR(1, "Cuidador"),
    FAMILIAR(2, "Familiar"),
    MEDICO(3, "Médico");

    private final int codigo;
    private final String descricao;

    private TipoEquipe(int codigo, String descricao) {
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

    public static TipoEquipe parse(int value) {

        for (TipoEquipe tipo : values()) {
            if (value == tipo.getCodigo()) {
                return tipo;
            }
        }

        throw new IllegalArgumentException("Valor inválido: " + value);
    }
    public static TipoEquipe parse(String value) {

        for (TipoEquipe tipo : values()) {
            if (value == tipo.getDescricao()) {
                return tipo;
            }
        }

        throw new IllegalArgumentException("Valor inválido: " + value);
    }
}
