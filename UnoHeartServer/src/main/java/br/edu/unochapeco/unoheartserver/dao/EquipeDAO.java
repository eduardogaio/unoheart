/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.dao;

import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import br.edu.unochapeco.unoheartserver.model.Equipe;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.model.PacienteEquipe;
import br.edu.unochapeco.unoheartserver.model.TipoEquipe;
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
public class EquipeDAO extends PadraoDAOImpl implements IPadraoDAO<Equipe> {

    public EquipeDAO() {
    }

    public EquipeDAO(DatabaseConnection conexao) {
        super(conexao);
    }

    @Override
    public Equipe getByCodigo(int codigo) throws Exception {
        abrirConexao();
        Equipe equipe = null;
        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO, NOME, TIPOEQUIPE_CODIGO, EMAIL, SENHA, DTINICIAL ")
                    .append("from EQUIPE ")
                    .append("where CODIGO = :CODIGO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("CODIGO", codigo);

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    equipe = new Equipe();

                    equipe.setCodigo(rs.getInt("CODIGO"));
                    equipe.setNome(rs.getString("NOME"));
                    equipe.setEmail(rs.getString("EMAIL"));
                    equipe.setSenha(rs.getString("SENHA"));
                    equipe.setDtinicial(rs.getDate("DTINICIAL").toLocalDate());
                    equipe.setTipoEquipe(TipoEquipe.parse(rs.getInt("TIPOEQUIPE_CODIGO")));
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return equipe;
    }

    public Equipe getByEmail(String emal) throws Exception {

        Equipe equipe = null;
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO ")
                    .append("from EQUIPE ")
                    .append("where EMAIL = :EMAIL ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setString("EMAIL", emal);

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    equipe = getByCodigo(rs.getInt("CODIGO"));
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return equipe;
    }

    @Override
    public Equipe insert(Equipe value) throws Exception {
        abrirConexao();

        try {
            final String CODIGO_SEQ = "equipe_codigo_seq";

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("insert into EQUIPE ")
                    .append("(CODIGO, NOME, TIPOEQUIPE_CODIGO, EMAIL, SENHA, DTINICIAL) ")
                    .append("values (:CODIGO, :NOME, :TIPOEQUIPE_CODIGO, :EMAIL, :SENHA, CLOCK_TIMESTAMP()) ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            value.setCodigo(conexao.incrementaSequencia(CODIGO_SEQ));

            query.setInt("CODIGO", value.getCodigo());
            query.setString("NOME", value.getNome());
            query.setString("EMAIL", value.getEmail());
            query.setString("SENHA", Funcoes.encode(value.getSenha()));
            query.setInt("TIPOEQUIPE_CODIGO", value.getTipoEquipe().getCodigo());

            query.execute();

            value = getByCodigo(value.getCodigo());

            return value;

        } catch (Exception ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void update(Equipe value) throws Exception {
        abrirConexao();
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("update EQUIPE set ")
                    .append("NOME = :NOME, TIPOEQUIPE_CODIGO = :TIPOEQUIPE_CODIGO, ")
                    .append("SENHA = :SENHA ")
                    .append("where CODIGO = :CODIGO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("CODIGO", value.getCodigo());
            query.setString("NOME", value.getNome());
            query.setString("SENHA", Funcoes.encode(value.getSenha()));
            query.setInt("TIPOEQUIPE_CODIGO", value.getTipoEquipe().getCodigo());

            query.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Equipe> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Equipe> getAll(Paciente paciente) throws Exception {
        List<Equipe> itens = new ArrayList<>();
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO, NOME, TIPOEQUIPE_CODIGO, EMAIL, SENHA, DTINICIAL ")
                    .append("from EQUIPE ")
                    .append("where PACIENTE_CODIGO = :CODIGO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );
            query.setInt("CODIGO", paciente.getCodigo());

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    Equipe equipe = new Equipe();

                    equipe.setCodigo(rs.getInt("CODIGO"));
                    equipe.setNome(rs.getString("NOME"));
                    equipe.setEmail(rs.getString("EMAIL"));
                    equipe.setSenha(rs.getString("SENHA"));
                    equipe.setDtinicial(rs.getDate("DTINICIAL").toLocalDate());
                    equipe.setTipoEquipe(TipoEquipe.parse(rs.getInt("TIPOEQUIPE_CODIGO")));

                    itens.add(equipe);
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return itens;
    }

    public Equipe realizarLogin(String login, String senha) throws Exception {

        Equipe equipe = null;
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO ")
                    .append("from EQUIPE ")
                    .append("where EMAIL = :EMAIL and SENHA = :SENHA ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setString("EMAIL", login);
            query.setString("SENHA", Funcoes.encode(senha));

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    equipe = getByCodigo(rs.getInt("CODIGO"));
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return equipe;
    }

    public List<Equipe> getAll(PacienteEquipe pacienteEquipe) throws Exception {

        List<Equipe> itens = new ArrayList<>();
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select E.CODIGO, E.NOME, E.TIPOEQUIPE_CODIGO, E.EMAIL, E.SENHA, E.DTINICIAL ")
                    .append("from EQUIPE E ")
                    .append("where E.TIPOEQUIPE_CODIGO = :TIPOEQUIPE_CODIGO ")
                    .append("and not exists(select EQUIPE_CODIGO ")
                    .append("from PACIENTEEQUIPE PE  ")
                    .append("where PE.EQUIPE_CODIGO = E.CODIGO and PE.DTFINAL is null ")
                    .append("and PE.PACIENTE_CODIGO = :PACIENTE_CODIGO) ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );
            query.setInt("TIPOEQUIPE_CODIGO", pacienteEquipe.getTipoEquipe().getCodigo());
            query.setInt("PACIENTE_CODIGO", pacienteEquipe.getPaciente().getCodigo());

            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Equipe equipe = new Equipe();

                equipe.setCodigo(rs.getInt("CODIGO"));
                equipe.setNome(rs.getString("NOME"));
                equipe.setEmail(rs.getString("EMAIL"));
                equipe.setSenha(rs.getString("SENHA"));
                equipe.setDtinicial(rs.getDate("DTINICIAL").toLocalDate());
                equipe.setTipoEquipe(TipoEquipe.parse(rs.getInt("TIPOEQUIPE_CODIGO")));

                itens.add(equipe);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return itens;
    }

}
