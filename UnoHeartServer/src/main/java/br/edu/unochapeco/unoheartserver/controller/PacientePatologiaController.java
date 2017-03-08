/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.controller;

import br.edu.unochapeco.unoheartserver.dao.PacientePatologiaDAO;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.model.PacientePatologia;
import br.edu.unochapeco.unoheartserver.model.exception.ErrorHandling;
import br.edu.unochapeco.unoheartserver.util.Constantes;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class PacientePatologiaController {

    public List<PacientePatologia> listarPacientePatologia(Paciente paciente) throws Exception {
        return new PacientePatologiaDAO().getAll(paciente);
    }

    public ErrorHandling validarCadastro(PacientePatologia pacientePatologia) throws Exception {
        ErrorHandling listaErros = new ErrorHandling();

        if (pacientePatologia == null || pacientePatologia.getPaciente() != null) {
            listaErros.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
        } else {
            if (pacientePatologia.getRelatorio() == null || pacientePatologia.getRelatorio().equals("")) {
                listaErros.addMessage("O relatório deve ser informado.");
            }

            if (pacientePatologia.getDtinicial() == null) {
                listaErros.addMessage("Informe a data inicial.");
            }
        }
        return listaErros;
    }

    public PacientePatologia realizarCadastro(PacientePatologia pacientePatologia) throws Exception {
        PacientePatologiaDAO pacientePatologiaDAO = new PacientePatologiaDAO();
        return pacientePatologiaDAO.insert(pacientePatologia);
    }

}
