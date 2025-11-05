package br.com.fiap.dao;

import br.com.fiap.beans.Paciente;
import br.com.fiap.beans.ReceitaMedica;
import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceitaMedicaDAO {

    public Connection minhaConexao;

    public ReceitaMedicaDAO() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public String insert(ReceitaMedica receitaMedica) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO RECEITA_MEDICA (DATA_EMISSAO, MEDICAMENTO, DOSAGEM, FREQUENCIA, DURACAO, ID_PACIENTE) VALUES (?, ?, ?, ?, ?, ?)"
        );
        stmt.setDate(1, java.sql.Date.valueOf(receitaMedica.getDataEmissao()));
        stmt.setString(2, receitaMedica.getMedicamento());
        stmt.setString(3, receitaMedica.getDosagem());
        stmt.setString(4, receitaMedica.getFrquencia());
        stmt.setString(5, receitaMedica.getDuracao());
        stmt.setInt(6, receitaMedica.getPaciente().getIdPaciente());

        stmt.execute();
        stmt.close();

        return "Receita Médica cadastrada com sucesso!";
    }

    public List<ReceitaMedica> receitasPaciente(int idPaciente) throws SQLException {
        List<ReceitaMedica> listaReceita = new ArrayList<>();
        String sql = "SELECT r.ID_RECEITA, r.DATA_EMISSAO, r.MEDICAMENTO, r.DOSAGEM, r.FREQUENCIA, r.DURACAO, " +
                "p.ID_PACIENTE, p.NOME_PACIENTE " +
                "FROM RECEITA_MEDICA r " +
                "JOIN PACIENTE p ON r.ID_PACIENTE = p.ID_PACIENTE " +
                "WHERE r.ID_PACIENTE = ? " +
                "ORDER BY r.DATA_EMISSAO";

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

            r.setIdReceita(rs.getInt("ID_RECEITA"));
            listaReceita.add(r);
        }

        rs.close();
        ps.close();
        return listaReceita;
    }

    public String delete(int idReceita) throws SQLException {
        PreparedStatement stmt =
                minhaConexao.prepareStatement("Delete from RECEITA_MEDICA where ID_RECEITA = ?");
        stmt.setInt(1, idReceita);

        stmt.execute();
        stmt.close();

        return "Receita Médica deletada com sucesso!";
    }

    public String update(ReceitaMedica receitaMedica) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE RECEITA_MEDICA SET DATA_EMISSAO = ?, MEDICAMENTO = ?, DOSAGEM = ?, FREQUENCIA = ?, DURACAO = ?, ID_PACIENTE = ? " +
                        "WHERE ID_RECEITA = ?"
        );

        stmt.setDate(1, java.sql.Date.valueOf(receitaMedica.getDataEmissao()));
        stmt.setString(2, receitaMedica.getMedicamento());
        stmt.setString(3, receitaMedica.getDosagem());
        stmt.setString(4, receitaMedica.getFrquencia());
        stmt.setString(5, receitaMedica.getDuracao());
        stmt.setInt(6, receitaMedica.getPaciente().getIdPaciente());

        stmt.executeUpdate();
        stmt.close();

        return "Receita Médica atualizada com sucesso!";

    }

}
