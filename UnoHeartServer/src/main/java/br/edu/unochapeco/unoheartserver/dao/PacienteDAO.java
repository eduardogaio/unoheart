/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.dao;

import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import br.edu.unochapeco.unoheartserver.model.Paciente;
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
public class PacienteDAO extends PadraoDAOImpl implements IPadraoDAO<Paciente> {

    public PacienteDAO() {
    }

    public PacienteDAO(DatabaseConnection conexao) {
        super(conexao);
    }

    @Override
    public Paciente getByCodigo(int codigo) throws Exception {

        Paciente paciente = null;
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO, NOME, NASCIMENTO, DTINICIAL, SEXO, EMAIL, SENHA ")
                    .append("from PACIENTE ")
                    .append("where CODIGO = :CODIGO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("CODIGO", codigo);
            PacienteImagemDAO pacienteImagemDAO = new PacienteImagemDAO(conexao);

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente();

                    paciente.setCodigo(rs.getInt("CODIGO"));
                    paciente.setNome(rs.getString("NOME"));
                    paciente.setEmail(rs.getString("EMAIL"));
                    paciente.setSenha(rs.getString("SENHA"));
                    paciente.setDtinicial(rs.getDate("DTINICIAL").toLocalDate());
                    paciente.setNascimento(rs.getDate("NASCIMENTO").toLocalDate());
                    paciente.setSexo(TipoSexo.parse(rs.getString("SEXO")));
                    paciente.setPacienteImagem(pacienteImagemDAO.getByPaciente(paciente));
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return paciente;
    }

    public Paciente getByEmail(String emal) throws Exception {

        Paciente paciente = null;
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO ")
                    .append("from PACIENTE ")
                    .append("where EMAIL = :EMAIL ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setString("EMAIL", emal);

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    paciente = getByCodigo(rs.getInt("CODIGO"));
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return paciente;
    }

    @Override
    public List<Paciente> getAll() throws Exception {

        List<Paciente> itens = new ArrayList<>();
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO, NOME, NASCIMENTO, DTINICIAL, SEXO, EMAIL, SENHA ")
                    .append("from PACIENTE ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    Paciente paciente = new Paciente();

                    paciente.setCodigo(rs.getInt("CODIGO"));
                    paciente.setNome(rs.getString("NOME"));
                    paciente.setEmail(rs.getString("EMAIL"));
                    paciente.setSenha(rs.getString("SENHA"));
                    paciente.setDtinicial(rs.getDate("DTINICIAL").toLocalDate());
                    paciente.setNascimento(rs.getDate("NASCIMENTO").toLocalDate());
                    paciente.setSexo(TipoSexo.parse(rs.getString("SEXO")));

                    itens.add(paciente);
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return itens;
    }

    public Paciente realizarLogin(String login, String senha) throws Exception {

        Paciente paciente = null;
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO ")
                    .append("from PACIENTE ")
                    .append("where lower(EMAIL) = lower(:EMAIL) and SENHA = :SENHA ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setString("EMAIL", login);
            query.setString("SENHA", Funcoes.encode(senha));

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    paciente = getByCodigo(rs.getInt("CODIGO"));
                }
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return paciente;
    }

    @Override
    public Paciente insert(Paciente paciente) throws Exception {
        abrirConexao();
        try {
            final String CODIGO_SEQ = "paciente_codigo_seq";

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("insert into PACIENTE ")
                    .append("(CODIGO, NOME, NASCIMENTO, DTINICIAL, SEXO, EMAIL, SENHA) ")
                    .append("values (:CODIGO, :NOME, :NASCIMENTO, CLOCK_TIMESTAMP(), :SEXO, :EMAIL, :SENHA) ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            paciente.setCodigo(conexao.incrementaSequencia(CODIGO_SEQ));

            query.setInt("CODIGO", paciente.getCodigo());
            query.setString("NOME", paciente.getNome());
            query.setLocalDate("NASCIMENTO", paciente.getNascimento());
            query.setString("SEXO", paciente.getSexo().getCodigo());
            query.setString("EMAIL", paciente.getEmail());
            query.setString("SENHA", Funcoes.encode(paciente.getSenha()));

            query.execute();

            paciente = getByCodigo(paciente.getCodigo());

            return paciente;

        } catch (Exception ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void update(Paciente paciente) throws Exception {
        abrirConexao();
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("update PACIENTE set ")
                    .append("NOME = :NOME, SENHA = :SENHA, SEXO = :SEXO, NASCIMENTO = :NASCIMENTO ")
                    .append("where CODIGO = :CODIGO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("CODIGO", paciente.getCodigo());
            query.setString("NOME", paciente.getNome());
            query.setString("SENHA", Funcoes.encode(paciente.getSenha()));
            query.setString("SEXO", paciente.getSexo().getCodigo());
            query.setLocalDate("NASCIMENTO", paciente.getNascimento());

            query.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
    }
            
}
