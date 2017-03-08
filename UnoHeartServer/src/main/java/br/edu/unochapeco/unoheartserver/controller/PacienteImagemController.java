/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.controller;

import br.edu.unochapeco.unoheartserver.dao.PacienteDAO;
import br.edu.unochapeco.unoheartserver.dao.PacienteImagemDAO;
import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.model.PacienteImagem;
import br.edu.unochapeco.unoheartserver.model.exception.ErrorHandling;
import br.edu.unochapeco.unoheartserver.util.Constantes;
import br.edu.unochapeco.unoheartserver.util.Funcoes;

/**
 *
 * @author Eduardo
 */
public class PacienteImagemController {

    public PacienteImagem selecionarPacienteImagem(String id) throws Exception {
        return new PacienteImagemDAO().getByPaciente(new Paciente(Integer.valueOf(id)));
    }

    public ErrorHandling validarCadastro(PacienteImagem pacienteImagem) throws Exception {
        ErrorHandling listaErros = new ErrorHandling();

        if (pacienteImagem == null || pacienteImagem.getPaciente() == null) {            
            listaErros.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
        } else {
            if (pacienteImagem.getImagem() == null) {
                listaErros.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
            }
        }
        return listaErros;
    }

    public PacienteImagem realizarCadastro(PacienteImagem pacienteImagem) throws Exception {
        PacienteImagemDAO pacienteImagemDAO = new PacienteImagemDAO();
        return pacienteImagemDAO.insert(pacienteImagem);
    }

}
