package uhoheart.unochapeco.edu.br.unoheart.model.type;

/**
 * Created by Eduardo on 17/02/2017.
 */

public enum TabelaUltimaColeta {
    CODIGO("CODIGO", " INTEGER PRIMARY KEY AUTOINCREMENT "),
    FREQUENCIA("FREQUENCIA", " INTEGER "),
    DATAHORA("DATAHORA", " TIMESTAMP ");

    private final String campo;
    private final String sql;

    private TabelaUltimaColeta(String campo, String sql) {
        this.campo = campo;
        this.sql = sql;
    }

    public String getCampo() {
        return campo;
    }

    public String getSql() {
        return sql;
    }

    public static String toCreateTableSQL() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("CREATE TABLE ")
                .append(getTableNale())
                .append("(")
                .append(CODIGO.campo.concat(" ").concat(CODIGO.sql).concat(","))
                .append(DATAHORA.campo.concat(" ").concat(DATAHORA.sql).concat(","))
                .append(FREQUENCIA.campo.concat(" ").concat(FREQUENCIA.sql))
                .append(") ").toString();
    }

    public static String toDropTableSQL() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder
                .append("DROP TABLE IF EXISTS ")
                .append(getTableNale())
                .toString();
    }

    public static String getTableNale() {
        return " ULTIMACOLETA ";
    }

    @Override
    public String toString() {
        return this.campo;
    }
}
