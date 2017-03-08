/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unochapeco.unoheartserver.dao;

import br.edu.unochapeco.unoheartserver.model.HistoricoFrequencia;
import br.edu.unochapeco.unoheartserver.model.Paciente;
import br.edu.unochapeco.unoheartserver.types.TipoSexo;
import br.edu.unochapeco.unoheartserver.util.NamedParameterStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class HistoricoFrequenciaDAO extends PadraoDAOImpl implements IPadraoDAO<HistoricoFrequencia> {

    @Override
    public HistoricoFrequencia getByCodigo(int codigo) throws Exception {
        HistoricoFrequencia frequencia = null;
        abrirConexao();

        try {

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select CODIGO, PACIENTE_CODIGO, DATAHORA, FREQUENCIA, DTHREXCLUSAO ")
                    .append("from HISTORICOFREQUENCIA ")
                    .append("where CODIGO = :CODIGO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("CODIGO", codigo);

            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    frequencia = new HistoricoFrequencia();

                    frequencia.setCodigo(rs.getInt("CODIGO"));
                    frequencia.setDatahora(rs.getTimestamp("DATAHORA").toLocalDateTime());
                    frequencia.setFrequencia(rs.getInt("FREQUENCIA"));
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }

        return frequencia;
    }

    public void remover(int codigo) throws Exception {
        abrirConexao();

        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("update HISTORICOFREQUENCIA set ")
                    .append("DTHREXCLUSAO = CLOCK_TIMESTAMP() ")
                    .append("where CODIGO = :CODIGO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            query.setInt("CODIGO", codigo);
            query.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
    }

    @Override
    public HistoricoFrequencia insert(HistoricoFrequencia value) throws Exception {
        abrirConexao();
        try {
            final String CODIGO_SEQ = "historicofrequencia_codigo_seq";

            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("insert into HISTORICOFREQUENCIA ")
                    .append("(CODIGO, PACIENTE_CODIGO, DATAHORA, FREQUENCIA) ")
                    .append("values (:CODIGO, :PACIENTE_CODIGO, :DATAHORA, :FREQUENCIA) ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );

            value.setCodigo(conexao.incrementaSequencia(CODIGO_SEQ));

            query.setInt("CODIGO", value.getCodigo());
            query.setInt("PACIENTE_CODIGO", value.getPaciente().getCodigo());
            query.setLocalDateTime("DATAHORA", value.getDatahora());
            query.setInt("FREQUENCIA", value.getFrequencia());

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
    public void update(HistoricoFrequencia value) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<HistoricoFrequencia> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<HistoricoFrequencia> getAll(Paciente paciente) throws Exception {
        List<HistoricoFrequencia> itens = new ArrayList<>();
        abrirConexao();

        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select FR.CODIGO, FR.PACIENTE_CODIGO, FR.DATAHORA, FR.FREQUENCIA, ")
                    .append("PA.NOME, PA.NASCIMENTO, PA.DTINICIAL, PA.SEXO, PA.EMAIL, PA.SENHA ")
                    .append("from HISTORICOFREQUENCIA FR ")
                    .append("left join PACIENTE PA on PA.CODIGO = FR.PACIENTE_CODIGO ")
                    .append("where FR.DTHREXCLUSAO is null ")
                    .append("and FR.PACIENTE_CODIGO = :PACIENTE_CODIGO ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );
            query.setInt("PACIENTE_CODIGO", paciente.getCodigo());

            try (ResultSet rs = query.executeQuery()) {                
                        
                while (rs.next()) {
                    HistoricoFrequencia frequencia = new HistoricoFrequencia();
                    
                    frequencia.setCodigo(rs.getInt("CODIGO"));
                    frequencia.setPaciente(paciente);
                    frequencia.setDatahora(rs.getTimestamp("DATAHORA").toLocalDateTime());
                    frequencia.setFrequencia(rs.getInt("FREQUENCIA"));
                    frequencia.setPaciente(paciente);

                    itens.add(frequencia);
                }
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            fecharConexao();
        }
        return itens;
    }
    public List<HistoricoFrequencia> getAll(Paciente paciente, LocalDate dtinicial, LocalDate dtfinal) throws Exception {
        List<HistoricoFrequencia> itens = new ArrayList<>();
        abrirConexao();

        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder
                    .append("select FR.CODIGO, FR.PACIENTE_CODIGO, FR.DATAHORA, FR.FREQUENCIA, ")
                    .append("PA.NOME, PA.NASCIMENTO, PA.DTINICIAL, PA.SEXO, PA.EMAIL, PA.SENHA ")
                    .append("from HISTORICOFREQUENCIA FR ")
                    .append("left join PACIENTE PA on PA.CODIGO = FR.PACIENTE_CODIGO ")
                    .append("where FR.DTHREXCLUSAO is null ")
                    .append("and FR.PACIENTE_CODIGO = :PACIENTE_CODIGO ")
                    .append("and cast(FR.DATAHORA as date) between :DTINICIAL and :DTFINAL ")
                    .append("and FR.FREQUENCIA > 0 ")
                    .append("order by FR.CODIGO desc ")
                    .append("limit 30 ");

            NamedParameterStatement query = new NamedParameterStatement(conexao.getConexao(),
                    strBuilder.toString()
            );
            query.setInt("PACIENTE_CODIGO", paciente.getCodigo());
            query.setLocalDate("DTINICIAL", dtinicial);
            query.setLocalDate("DTFINAL", dtfinal);
           

            try (ResultSet rs = query.executeQuery()) {                
                        
                while (rs.next()) {
                    HistoricoFrequencia frequencia = new HistoricoFrequencia();
                    
                    frequencia.setCodigo(rs.getInt("CODIGO"));
                    frequencia.setPaciente(paciente);
                    frequencia.setDatahora(rs.getTimestamp("DATAHORA").toLocalDateTime());
                    frequencia.setFrequencia(rs.getInt("FREQUENCIA"));
                    frequencia.setPaciente(paciente);

                    itens.add(frequencia);
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
