/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.controller;

import br.edu.unochapeco.unoheartserver.dao.HistoricoFrequenciaDAO;
import br.edu.unochapeco.unoheartserver.dao.PacienteDAO;
import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import br.edu.unochapeco.unoheartserver.model.FrequenciaFiltroGrafico;
import br.edu.unochapeco.unoheartserver.model.HistoricoFrequencia;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.model.exception.ErrorHandling;
import br.edu.unochapeco.unoheartserver.util.Constantes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class HistoricoFrequenciaController {

    public ErrorHandling validarInclusao(HistoricoFrequencia frequencia) throws Exception {
        ErrorHandling listaErros = new ErrorHandling();

        if (frequencia == null) {
            listaErros.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
        } else {
            if (frequencia.getPaciente() == null || frequencia.getPaciente().getCodigo() == null) {
                listaErros.addMessage("O cóigo do paciente deve ser informado");
            } else {
                if (frequencia.getDatahora() == null) {
                    listaErros.addMessage("A data e hora da colta deve ser informada");
                } else {
                    if (frequencia.getFrequencia() == null) {
                        listaErros.addMessage("A frequência cardíaca deve ser informada");
                    } else {

                        DatabaseConnection conexao = new DatabaseConnection();
                        try {
                            PacienteDAO pacienteDAO = new PacienteDAO(conexao);
                            if (pacienteDAO.getByCodigo(frequencia.getPaciente().getCodigo()) == null) {
                                listaErros.addMessage("O código do paciente informado não existe");
                            }

                        } finally {
                            conexao.close();
                        }
                    }
                }
            }

        }
        return listaErros;
    }

    public ErrorHandling remover(HistoricoFrequencia frequencia) throws Exception {
        ErrorHandling listaErros = new ErrorHandling();
        if (frequencia == null || frequencia.getCodigo() == null) {
            listaErros.addMessage(Constantes.MSG_REQUISICAO_INVALIDA);
        } else {
            new HistoricoFrequenciaDAO().remover(frequencia.getCodigo());
        }
        return listaErros;
    }

    public HistoricoFrequencia realizarInclusao(HistoricoFrequencia frequencia) throws Exception {
        HistoricoFrequenciaDAO frequenciaDAO = new HistoricoFrequenciaDAO();
        return frequenciaDAO.insert(frequencia);
    }

    public List<HistoricoFrequencia> listarFrequencia(FrequenciaFiltroGrafico dados) throws Exception {
        List<HistoricoFrequencia> itens = new ArrayList<>();
        
        DatabaseConnection connection = new DatabaseConnection();
        try {
            PacienteDAO pacienteDAO = new PacienteDAO(connection);
            Paciente paciente = dados.getPaciente();
            if (paciente != null) {                
                if (dados.getInicio() != null && dados.getFim() != null) {                    
                    itens = new HistoricoFrequenciaDAO().getAll(paciente, dados.getInicio(), dados.getFim());
                } else {
                    itens = new HistoricoFrequenciaDAO().getAll(paciente, LocalDate.now(), LocalDate.now());
                }

            }
        } finally {
            connection.close();
        }
        return itens;
    }
}
