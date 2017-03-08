/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.dao;

import br.edu.unochapeco.unoheartserver.dao.connection.DatabaseConnection;
import java.sql.SQLException;

/**
 *
 * @author Eduardo
 */
public class PadraoDAOImpl {

    protected DatabaseConnection conexao;
    protected boolean fecharConexao;
    
    public PadraoDAOImpl() {
        this.fecharConexao = true;
    }

    public PadraoDAOImpl(DatabaseConnection conexao) {
        this.fecharConexao = false;
        this.conexao = conexao;
    }    

    protected final void abrirConexao() throws Exception {
        if (conexao == null || conexao.getConexao().isClosed()) {
            conexao = new DatabaseConnection();
            fecharConexao = true;
        }
    }

    protected final void fecharConexao() throws SQLException {
        if (conexao != null && fecharConexao && (conexao.getConexao().isClosed() == false)) {
            conexao.close();
        }
    }
}
