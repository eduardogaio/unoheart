package br.edu.unochapeco.unoheartserver.dao.connection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import br.edu.unochapeco.unoheartserver.util.NamedParameterStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Eduardo Gaio
 */
public class DatabaseConnection {

    private Connection conexao = null;

    private static ConnectionFactory _driverManager;

    public DatabaseConnection() throws Exception {
        if (_driverManager == null) {
            _driverManager = new ConnectionFactory();
        }

        this.conexao = DriverManager.getConnection(_driverManager.getDatabaseAddr(), _driverManager.getProperties());
    }

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    public void close() {
        try {
            if (conexao.isClosed() == false) {
                if (conexao.getAutoCommit() == false) {
                    rollbackTransaction();
                }
                getConexao().close();
            }
        } catch (Exception ex) {

        }
    }

    public void startTransaction() throws Exception {
        if ((getConexao() != null) && (getConexao().getAutoCommit())) {
            getConexao().getAutoCommit();
            getConexao().setAutoCommit(false);
            getConexao().getTransactionIsolation();
            getConexao().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } else if (getConexao().getAutoCommit() == false) {
            System.out.println("Já esta em transação...");
        }
    }

    public void startTransaction(int IsolationLevel) throws Exception {
        if (getConexao() != null) {
            getConexao().setAutoCommit(false);
            getConexao().setTransactionIsolation(IsolationLevel);
        }

    }

    public void commitTransaction() throws Exception {
        if (getConexao() != null && getConexao().getAutoCommit() == false) {
            getConexao().commit();
            getConexao().setAutoCommit(true);
        }
    }

    public boolean inTransaction() throws Exception {
        return (getConexao() != null && getConexao().getAutoCommit() == false);
    }

    public void commitTransaction(boolean closeConnection) throws Exception {
        if (getConexao() != null && getConexao().getAutoCommit() == false) {
            getConexao().commit();
            getConexao().setAutoCommit(true);
            if (closeConnection) {
                getConexao().close();
            }
        }
    }

    public void rollbackTransaction() throws Exception {
        if (getConexao() != null && getConexao().getAutoCommit() == false) {
            getConexao().rollback();
            getConexao().setAutoCommit(true);
        }
    }

    public void rollbackTransaction(boolean closeConnection) throws Exception {
        if (getConexao() != null && getConexao().getAutoCommit() == false) {
            getConexao().rollback();
            getConexao().setAutoCommit(true);
            if (closeConnection) {
                getConexao().close();
            }
        }
    }

    public ResultSet selectQuery(String sql) throws Exception {
        Statement query = getConexao().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet result = query.executeQuery(sql);
        return result;
    }

    public boolean executeQuery(String sql) throws Exception {
        return execute(sql);
    }

    private boolean execute(String sql) throws Exception {
        Statement query = getConexao().createStatement();
        query.execute(sql);

        return true;
    }

    public int incrementaSequencia(String sequencia) throws SQLException {
        int novoCodigo = 0;
        NamedParameterStatement query = new NamedParameterStatement(
                conexao,
                "select nextval(:SEQUENCIA) as CODIGO "
                + "for update");
        query.setString("SEQUENCIA", sequencia);
        
        ResultSet rs = query.executeQuery();
        if (rs.next()) {
            novoCodigo = rs.getInt("CODIGO");
        }

        return novoCodigo;
    }

}
