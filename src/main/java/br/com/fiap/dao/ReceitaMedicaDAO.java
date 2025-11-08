package br.com.fiap.dao;

import br.com.fiap.beans.Paciente;
import br.com.fiap.beans.ReceitaMedica;
import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaMedicaDAO {

    private Connection minhaConexao;

    public ReceitaMedicaDAO() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public String insert(ReceitaMedica receita) throws SQLException {
        String sql = "INSERT INTO RECEITA_MEDICA (DATA_EMISSAO, MEDICAMENTO, DOSAGEM, FREQUENCIA, DURACAO, ID_PACIENTE) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql);

        stmt.setDate(1, java.sql.Date.valueOf(receita.getDataEmissao()));
        stmt.setString(2, receita.getMedicamento());
        stmt.setString(3, receita.getDosagem());
        stmt.setString(4, receita.getFrequencia());
        stmt.setString(5, receita.getDuracao());
        stmt.setInt(6, receita.getPaciente().getIdPaciente());

        stmt.execute();
        stmt.close();
        return "Receita cadastrada com sucesso!";
    }

    public List<ReceitaMedica> receitasPaciente(int idPaciente) throws SQLException {
        List<ReceitaMedica> lista = new ArrayList<>();
        String sql = "SELECT r.ID_RECEITA, r.DATA_EMISSAO, r.MEDICAMENTO, r.DOSAGEM, r.FREQUENCIA, r.DURACAO, " +
                "p.ID_PACIENTE, p.NOME_PACIENTE " +
                "FROM RECEITA_MEDICA r " +
                "JOIN PACIENTE p ON r.ID_PACIENTE = p.ID_PACIENTE " +
                "WHERE r.ID_PACIENTE = ? " +
                "ORDER BY r.DATA_EMISSAO DESC";

        PreparedStatement ps = minhaConexao.prepareStatement(sql);
        ps.setInt(1, idPaciente);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Paciente p = new Paciente(
                    rs.getInt("ID_PACIENTE"),
                    rs.getString("NOME_PACIENTE")
            );

            ReceitaMedica r = new ReceitaMedica(
                    rs.getInt("ID_RECEITA"),
                    rs.getDate("DATA_EMISSAO").toLocalDate(),
                    rs.getString("MEDICAMENTO"),
                    rs.getString("DOSAGEM"),
                    rs.getString("FREQUENCIA"),
                    rs.getString("DURACAO"),
                    p
            );

            lista.add(r);
        }

        rs.close();
        ps.close();
        return lista;
    }

    public String update(ReceitaMedica receita) throws SQLException {
        String sql = "UPDATE RECEITA_MEDICA SET DATA_EMISSAO = ?, MEDICAMENTO = ?, DOSAGEM = ?, FREQUENCIA = ?, DURACAO = ?, ID_PACIENTE = ? WHERE ID_RECEITA = ?";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql);
        stmt.setDate(1, java.sql.Date.valueOf(receita.getDataEmissao()));
        stmt.setString(2, receita.getMedicamento());
        stmt.setString(3, receita.getDosagem());
        stmt.setString(4, receita.getFrequencia());
        stmt.setString(5, receita.getDuracao());
        stmt.setInt(6, receita.getPaciente().getIdPaciente());
        stmt.setInt(7, receita.getIdReceita());
        stmt.executeUpdate();
        stmt.close();
        return "Receita médica atualizada com sucesso!";
    }

    public String delete(int idReceita) throws SQLException {
        String sql = "DELETE FROM RECEITA_MEDICA WHERE ID_RECEITA = ?";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql);
        stmt.setInt(1, idReceita);
        stmt.execute();
        stmt.close();
        return "Receita médica deletada com sucesso!";
    }
}
