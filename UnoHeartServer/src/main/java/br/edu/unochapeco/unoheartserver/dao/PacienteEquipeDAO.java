/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.dao;

import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.model.PacienteEquipe;
import br.edu.unochapeco.unoheartserver.model.PadraoModel;
import br.edu.unochapeco.unoheartserver.types.TipoSexo;
import br.edu.unochapeco.unoheartserver.util.NamedParameterStatement;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class PacienteEquipeDAO extends PadraoDAOImpl implements IPadraoDAO<PacienteEquipe> {

    public PacienteEquipeDAO() {
    }

    public PacienteEquipeDAO(DatabaseConnection conexao) {
        super(conexao);
    }

    public List<PacienteEquipe> getAll(PacienteEquipe pacienteEquipe) throws Exception {

        List<PacienteEquipe> itens = new ArrayList<>();
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select PE.DTINICIAL, PE.PACIENTE_CODIGO, PE.EQUIPE_CODIGO ")
                    .append("from PACIENTEEQUIPE PE ")
                    .append("left join EQUIPE E on E.CODIGO = PE.EQUIPE_CODIGO ")
                    .append("where PE.DTFINAL is null and E.TIPOEQUIPE_CODIGO = :TIPOEQUIPE_CODIGO and PE.PACIENTE_CODIGO = :PACIENTE_CODIGO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("PACIENTE_CODIGO", pacienteEquipe.getCodigo());
            query.setInt("TIPOEQUIPE_CODIGO", pacienteEquipe.getTipoEquipe().getCodigo());

            try (ResultSet rs = query.executeQuery()) {
                PacienteDAO pacienteDAO = new PacienteDAO(conexao);
                EquipeDAO equipeDAO = new EquipeDAO(conexao);

                while (rs.next()) {
                    PacienteEquipe item = new PacienteEquipe();
                    item.setDtinicial(rs.getDate("DTINICIAL").toLocalDate());
                    item.setEqupe(equipeDAO.getByCodigo(rs.getInt("EQUIPE_CODIGO")));
                    item.setPaciente(pacienteDAO.getByCodigo(rs.getInt("PACIENTE_CODIGO")));

                    itens.add(item);
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return itens;
    }

    @Override
    public PacienteEquipe getByCodigo(int codigo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PacienteEquipe insert(PacienteEquipe value) throws Exception {        
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("insert into PACIENTEEQUIPE CODIGO, PACIENTE_CODIGO, DTINICIAL, EQUIPE_CODIGO) ")
                    .append("values (:CODIGO, :PACIENTE_CODIGO, CLOCK_TIMESTAMP(), :EQUIPE_CODIGO) ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("PACIENTE_CODIGO", value.getPaciente().getCodigo());
            query.setInt("EQUIPE_CODIGO", value.getEqupe().getCodigo());
            query.execute();
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return value;
    }

    @Override
    public void update(PacienteEquipe value) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PacienteEquipe> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
