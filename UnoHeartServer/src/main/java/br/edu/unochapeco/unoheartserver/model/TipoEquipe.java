/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.model;

/**
 *
 * @author Eduardo
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

}
