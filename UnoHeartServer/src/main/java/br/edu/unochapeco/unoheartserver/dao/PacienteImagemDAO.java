/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.dao;

import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.model.PacienteImagem;
import br.edu.unochapeco.unoheartserver.types.TipoSexo;
import br.edu.unochapeco.unoheartserver.util.Funcoes;
import br.edu.unochapeco.unoheartserver.util.NamedParameterStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class PacienteImagemDAO extends PadraoDAOImpl implements IPadraoDAO<PacienteImagem> {

    public PacienteImagemDAO() {
    }

    public PacienteImagemDAO(DatabaseConnection conexao) {
        super(conexao);
    }

    public PacienteImagem getByPaciente(Paciente paciente) throws Exception {
        PacienteImagem pacienteImagem = null;
        abrirConexao();

        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO, PACIENTE_CODIGO, IMAGEM, DTHRINICIAL ")
                    .append("from PACIENTEIMAGEM ")
                    .append("where PACIENTE_CODIGO = :PACIENTE_CODIGO and DTHRFINAL is null ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("PACIENTE_CODIGO", paciente.getCodigo());

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    pacienteImagem = new PacienteImagem();
                    pacienteImagem.setCodigo(rs.getInt("CODIGO"));
                    pacienteImagem.setImagem(rs.getString("IMAGEM"));
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return pacienteImagem;
    }

    @Override
    public PacienteImagem getByCodigo(int codigo) throws Exception {
        PacienteImagem pacienteImagem = null;
        abrirConexao();

        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO, PACIENTE_CODIGO, IMAGEM, DTHRINICIAL ")
                    .append("from PACIENTEIMAGEM ")
                    .append("where CODIGO = :CODIGO");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("CODIGO", codigo);

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    pacienteImagem = new PacienteImagem();
                    pacienteImagem.setCodigo(rs.getInt("CODIGO"));
                    pacienteImagem.setImagem(rs.getString("IMAGEM"));
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return pacienteImagem;
    }

    @Override
    public PacienteImagem insert(PacienteImagem value) throws Exception {
        abrirConexao();

        try {
            final String CODIGO_SEQ = "pacienteimagem_codigo_seq";

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("insert into PACIENTEIMAGEM (CODIGO, PACIENTE_CODIGO, IMAGEM, DTHRINICIAL) ")
                    .append("values (:CODIGO, :PACIENTE_CODIGO, :IMAGEM, CLOCK_TIMESTAMP()) ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            value.setCodigo(conexao.incrementaSequencia(CODIGO_SEQ));

            query.setInt("CODIGO", value.getCodigo());
            query.setInt("PACIENTE_CODIGO", value.getPaciente().getCodigo());
            query.setString("IMAGEM", value.getImagem());

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
    public void update(PacienteImagem value) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PacienteImagem> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
