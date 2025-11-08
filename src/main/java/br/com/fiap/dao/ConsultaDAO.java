package br.com.fiap.dao;

import br.com.fiap.beans.Consulta;
import br.com.fiap.beans.Paciente;
import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public Connection minhaConexao;

    public ConsultaDAO() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public String insert(Consulta consulta) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO CONSULTA (DATA_CONSULTA, HORA_CONSULTA, STATUS_CONSULTA, MOTIVO, ID_PACIENTE) VALUES (?, ?, ?, ?, ?)"
        );
        stmt.setDate(1, java.sql.Date.valueOf(consulta.getData()));
        stmt.setString(2, consulta.horaFormatada());
        stmt.setString(3, consulta.getStatus());
        stmt.setString(4, consulta.getMotivoConsulta());
        stmt.setInt(5, consulta.getPaciente().getIdPaciente());

        stmt.execute();
        stmt.close();

        return "Consulta cadastrada com sucesso!";
    }

    public List<Consulta> consultasPaciente(int idPaciente) throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT c.ID_CONSULTA, c.DATA_CONSULTA, c.HORA_CONSULTA, c.STATUS_CONSULTA, c.MOTIVO, " +
                "p.ID_PACIENTE, p.NOME_PACIENTE " +
                "FROM CONSULTA c " +
                "JOIN PACIENTE p ON c.ID_PACIENTE = p.ID_PACIENTE " +
                "WHERE c.ID_PACIENTE = ? " +
                "ORDER BY c.DATA_CONSULTA, c.HORA_CONSULTA";

        PreparedStatement ps = minhaConexao.prepareStatement(sql);
        ps.setInt(1, idPaciente);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Paciente p = new Paciente(
                    rs.getInt("ID_PACIENTE"),
                    rs.getString("NOME_PACIENTE")
            );

            // Trata hora nula
            String horaStr = rs.getString("HORA_CONSULTA");
            LocalTime hora = null;
            if (horaStr != null && !horaStr.isEmpty()) {
                hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));
            } else {
                hora = LocalTime.of(0, 0); // ou escolha outro valor padrão
            }

            // Trata data nula (opcional)
            java.sql.Date dataSql = rs.getDate("DATA_CONSULTA");
            java.time.LocalDate data = dataSql != null ? dataSql.toLocalDate() : null;

            Consulta c = new Consulta(
                    rs.getInt("ID_CONSULTA"),
                    data,
                    hora,
                    rs.getString("STATUS_CONSULTA"),
                    rs.getString("MOTIVO"),
                    p
            );

            c.setIdConsulta(rs.getInt("ID_CONSULTA"));
            lista.add(c);
        }

        rs.close();
        ps.close();
        return lista;
    }

    public String delete(int idConsulta) throws SQLException {
        PreparedStatement stmt =
                minhaConexao.prepareStatement("DELETE FROM CONSULTA WHERE ID_CONSULTA = ?");
        stmt.setInt(1, idConsulta);

        stmt.execute();
        stmt.close();

        return "Consulta deletada com sucesso!";
    }

    public String update(Consulta consulta) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE CONSULTA SET DATA_CONSULTA = ?, HORA_CONSULTA = ?, STATUS_CONSULTA = ?, MOTIVO = ?, ID_PACIENTE = ? " +
                        "WHERE ID_CONSULTA = ?"
        );

        stmt.setDate(1, java.sql.Date.valueOf(consulta.getData()));
        stmt.setString(2, consulta.horaFormatada());
        stmt.setString(3, consulta.getStatus());
        stmt.setString(4, consulta.getMotivoConsulta());
        stmt.setInt(5, consulta.getPaciente().getIdPaciente());
        stmt.setInt(6, consulta.getIdConsulta());

        stmt.executeUpdate();
        stmt.close();

        return "Consulta atualizada com sucesso!";
    }

}
