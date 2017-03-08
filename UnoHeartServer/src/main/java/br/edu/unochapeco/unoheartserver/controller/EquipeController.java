/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.controller;

import br.edu.unochapeco.unoheartserver.dao.EquipeDAO;
import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import br.edu.unochapeco.unoheartserver.model.Equipe;
import br.edu.unochapeco.unoheartserver.model.PacienteEquipe;
import br.edu.unochapeco.unoheartserver.model.exception.ErrorHandling;
import br.edu.unochapeco.unoheartserver.util.Constantes;
import br.edu.unochapeco.unoheartserver.util.Funcoes;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class EquipeController {

    public ErrorHandling validarLogin(Equipe value) throws Exception {

        ErrorHandling error = new ErrorHandling();

        if (value == null) {
            error.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
        } else {
            if (Funcoes.isNull(value.getEmail()) || Funcoes.isNull(value.getSenha())) {
                error.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
            }
        }

        return error;
    }

    public Equipe realizarLogin(Equipe value) throws Exception {
        return new EquipeDAO().realizarLogin(value.getEmail(), value.getSenha());
    }

    public ErrorHandling validarCadastro(Equipe value) throws Exception {
        ErrorHandling listaErros = new ErrorHandling();

        if (value == null) {
            listaErros.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
        } else {
            if (Funcoes.isNull(value.getNome())) {
                listaErros.addMessage("Informe o nome completo");
            } else if (value.getNome().length() > 100) {
                listaErros.addMessage("Nome muito longo");
            }

            if (value.getTipoEquipe() == null) {
                listaErros.addMessage("Informe o tipo do profissional");
            }

            if (Funcoes.isNull(value.getEmail())) {
                listaErros.addMessage("Informe o e-mail");
            }

            if (Funcoes.isNull(value.getSenha()) || Funcoes.isNull(value.getConfSenha())) {
                listaErros.addMessage("Informe e confirme a senha");
            }

            DatabaseConnection conexao = new DatabaseConnection();
            try {
                EquipeDAO equipeDAO = new EquipeDAO(conexao);
                if (equipeDAO.getByEmail(value.getEmail()) != null) {
                    listaErros.addMessage("O e-mail informado já esta cadastrado");
                }

            } finally {
                conexao.close();
            }
        }
        return listaErros;
    }

    public Equipe realizarCadastro(Equipe value) throws Exception {
        EquipeDAO equipeDAO = new EquipeDAO();
        return equipeDAO.insert(value);
    }

    public void alterarCadastro(Equipe value) throws Exception {
        EquipeDAO equipeDAO = new EquipeDAO();
        equipeDAO.update(value);
    }

    public List<Equipe> listarEquipe(PacienteEquipe pacienteEquipe) throws Exception {
        EquipeDAO equipeDAO = new EquipeDAO();
        return equipeDAO.getAll(pacienteEquipe);
    }
}
