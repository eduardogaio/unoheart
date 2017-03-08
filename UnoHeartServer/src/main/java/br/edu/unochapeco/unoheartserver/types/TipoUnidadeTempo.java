/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.types;

/**
 *
 * @author Eduardo Gaio
 */
public enum TipoUnidadeTempo {

    SEGUNDO("0", "Segundo"),
    MINUTO("1", "Minuto"),
    HORA("2", "Hora");

    private final String codigo;
    private final String descricao;

    private TipoUnidadeTempo(String codigo, String descricao) {
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

    public static TipoUnidadeTempo parse(String value) {
        if (value != null) {
            for (TipoUnidadeTempo tipo : values()) {
                if (value.equalsIgnoreCase(tipo.getCodigo())) {
                    return tipo;
                }
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + value);
    }
}
