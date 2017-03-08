/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.dao;

import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.model.PacientePatologia;
import br.edu.unochapeco.unoheartserver.util.NamedParameterStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class PacientePatologiaDAO extends PadraoDAOImpl implements IPadraoDAO<PacientePatologia> {

    public PacientePatologiaDAO() {
    }

    public PacientePatologiaDAO(DatabaseConnection conexao) {
        super(conexao);
    }

    @Override
    public PacientePatologia getByCodigo(int codigo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PacientePatologia insert(PacientePatologia value) throws Exception {
        abrirConexao();

        try {
            final String CODIGO_SEQ = "pacientepatologia_codigo_seq";

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("insert into PACIENTEPATOLOGIA(CODIGO, PACIENTE_CODIGO, RELATORIO, DTINICIAL) ")
                    .append("values (:CODIGO, :PACIENTE_CODIGO, :RELATORIO, :DTINICIAL) ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            value.setCodigo(conexao.incrementaSequencia(CODIGO_SEQ));

            query.setInt("CODIGO", value.getCodigo());
            query.setInt("PACIENTE_CODIGO", value.getPaciente().getCodigo());
            query.setString("RELATORIO", value.getRelatorio());
            query.setDate("DTINICIAL", java.sql.Date.valueOf(value.getDtinicial()));

            query.execute();

            value = getByCodigo(value.getCodigo());

            return value;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void update(PacientePatologia value) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PacientePatologia> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<PacientePatologia> getAll(Paciente paciente) throws Exception {

        List<PacientePatologia> itens = new ArrayList<>();
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select PACIENTEPATOLOGIA(CODIGO, PACIENTE_CODIGO, RELATORIO, DTINICIAL ")
                    .append("from PACIENTEPATOLOGIA ")
                    .append("where DTFINAL is null and PACIENTE_CODIGO = :PACIENTE_CODIGO ")
                    .append("order by RELATORIO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );
            query.setInt("PACIENTE_CODIGO", paciente.getCodigo());            

            try (ResultSet rs = query.executeQuery()) {
                PacienteDAO pacienteDAO = new PacienteDAO(conexao);

                while (rs.next()) {
                    PacientePatologia pacientePatologia = new PacientePatologia();

                    pacientePatologia.setCodigo(rs.getInt("CODIGO"));
                    pacientePatologia.setRelatorio(rs.getString("RELATORIO"));
                    pacientePatologia.setDtinicial(rs.getDate("DTINICIAL").toLocalDate());
                    pacientePatologia.setPaciente(paciente);

                    itens.add(pacientePatologia);
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return itens;
    }

}
