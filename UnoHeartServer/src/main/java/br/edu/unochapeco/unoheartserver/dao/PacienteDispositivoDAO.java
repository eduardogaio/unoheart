/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.dao;

import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import br.edu.unochapeco.unoheartserver.model.PacienteDispositivo;
import br.edu.unochapeco.unoheartserver.util.NamedParameterStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class PacienteDispositivoDAO extends PadraoDAOImpl implements IPadraoDAO<PacienteDispositivo> {

    public PacienteDispositivoDAO() {
    }

    public PacienteDispositivoDAO(DatabaseConnection conexao) {
        super(conexao);
    }

    @Override
    public PacienteDispositivo getByCodigo(int codigo) throws Exception {
        PacienteDispositivo dispositivo = null;
        abrirConexao();

        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO, PACIENTE_CODIGO, DISPOSITIVO ")
                    .append("from PACIENTEDISPOSITIVO ")
                    .append("where CODIGO = :CODIGO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("CODIGO", codigo);

            try (ResultSet rs = query.executeQuery()) {
                PacienteDAO pacienteDAO = new PacienteDAO(conexao);
                if (rs.next()) {
                    dispositivo = new PacienteDispositivo();

                    dispositivo.setCodigo(rs.getInt("CODIGO"));
                    dispositivo.setPaciente(pacienteDAO.getByCodigo(rs.getInt("PACIENTE_CODIGO")));
                    dispositivo.setDispositivo(rs.getString("DISPOSITIVO"));
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return dispositivo;
    }
    public PacienteDispositivo getByDispositivo(PacienteDispositivo pacienteDispositivo) throws Exception {
        PacienteDispositivo dispositivo = null;
        abrirConexao();

        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO ")
                    .append("from PACIENTEDISPOSITIVO ")
                    .append("where PACIENTE_CODIGO = :PACIENTE_CODIGO and DISPOSITIVO = :DISPOSITIVO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("PACIENTE_CODIGO", pacienteDispositivo.getPaciente().getCodigo());
            query.setString("DISPOSITIVO", pacienteDispositivo.getDispositivo());

            try (ResultSet rs = query.executeQuery()) {
                PacienteDAO pacienteDAO = new PacienteDAO(conexao);
                if (rs.next()) {
                    dispositivo = getByCodigo(rs.getInt("CODIGO"));
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return dispositivo;
    }

    @Override
    public PacienteDispositivo insert(PacienteDispositivo dispositivo) throws Exception {
        abrirConexao();
        try {
            final String CODIGO_SEQ = "pacientedispositivo_codigo_seq";

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("insert into PACIENTEDISPOSITIVO ")
                    .append("(CODIGO, PACIENTE_CODIGO, DISPOSITIVO) ")
                    .append("values (:CODIGO, :PACIENTE_CODIGO, :DISPOSITIVO) ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            dispositivo.setCodigo(conexao.incrementaSequencia(CODIGO_SEQ));

            query.setInt("CODIGO", dispositivo.getCodigo());
            query.setInt("PACIENTE_CODIGO",  dispositivo.getPaciente().getCodigo());            
            query.setString("DISPOSITIVO", dispositivo.getDispositivo());            

            query.execute();

            dispositivo = getByCodigo(dispositivo.getCodigo());

            return dispositivo;

        } catch (Exception ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void update(PacienteDispositivo value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PacienteDispositivo> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




}
