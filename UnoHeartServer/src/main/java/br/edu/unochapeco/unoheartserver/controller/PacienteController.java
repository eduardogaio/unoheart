/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.controller;

import br.edu.unochapeco.unoheartserver.dao.PacienteDAO;
import br.edu.unochapeco.unoheartserver.dao.PacienteEquipeDAO;
import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.model.PacienteEquipe;
import br.edu.unochapeco.unoheartserver.model.PacienteImagem;
import br.edu.unochapeco.unoheartserver.model.exception.ErrorHandling;
import br.edu.unochapeco.unoheartserver.util.Constantes;
import br.edu.unochapeco.unoheartserver.util.Funcoes;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class PacienteController {

    public ErrorHandling validarLogin(Paciente paciente) throws Exception {

        ErrorHandling error = new ErrorHandling();

        if (paciente == null) {
            error.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
        } else {
            if (Funcoes.isNull(paciente.getEmail()) || Funcoes.isNull(paciente.getSenha())) {
                error.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
            }
        }

        return error;
    }

    public Paciente realizarLogin(Paciente paciente) throws Exception {
        return new PacienteDAO().realizarLogin(paciente.getEmail(), paciente.getSenha());
    }

    public ErrorHandling validarCadastro(Paciente paciente) throws Exception {
        ErrorHandling listaErros = new ErrorHandling();

        if (paciente == null) {
            listaErros.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
        } else {
            if (Funcoes.isNull(paciente.getNome())) {
                listaErros.addMessage("Informe o nome completo");
            } else if (paciente.getNome().length() > 100) {
                listaErros.addMessage("Nome muito longo");
            }

            if (paciente.getNascimento() == null) {
                listaErros.addMessage("Informe a data de nascimento");
            }

            if (Funcoes.isNull(paciente.getEmail())) {
                listaErros.addMessage("Informe o e-mail");
            }

            if (Funcoes.isNull(paciente.getSenha()) || Funcoes.isNull(paciente.getConfSenha())) {
                listaErros.addMessage("Informe e confirme a senha");
            }

            if (paciente.getSexo() == null) {
                listaErros.addMessage("Informe o tipo do sexo");
            }
            DatabaseConnection conexao = new DatabaseConnection();
            try {
                PacienteDAO pacienteDAO = new PacienteDAO(conexao);
                if (pacienteDAO.getByEmail(paciente.getEmail()) != null) {
                    listaErros.addMessage("O e-mail informado já esta cadastrado");
                }

            } finally {
                conexao.close();
            }
        }
        return listaErros;
    }

    public Paciente realizarCadastro(Paciente paciente) throws Exception {
        PacienteDAO pacienteDAO = new PacienteDAO();
        return pacienteDAO.insert(paciente);
    }

    public void alterarCadastro(Paciente paciente) throws Exception {
        PacienteDAO pacienteDAO = new PacienteDAO();
        pacienteDAO.update(paciente);
    }

    public List<Paciente> listarPaciente() throws Exception {
        return new PacienteDAO().getAll();
    }

    public List<PacienteEquipe> listartPacienteEquipe(PacienteEquipe pacienteEquipe) throws Exception {
        return new PacienteEquipeDAO().getAll(pacienteEquipe);
    }

    public boolean incluirEquipe(PacienteEquipe pacienteEquipe) throws Exception {
        if (pacienteEquipe != null && pacienteEquipe.getPaciente() != null && pacienteEquipe.getEqupe() != null) {
            PacienteEquipeDAO pacienteEquipeDAO = new PacienteEquipeDAO();
            pacienteEquipeDAO.insert(pacienteEquipe);
            return true;
        } else {
            return false;
        }
    }

}
