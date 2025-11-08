package br.com.fiap.dao;

import br.com.fiap.beans.Exame;
import br.com.fiap.beans.Paciente;
import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExameDAO {

    public Connection minhaConexao;

    public ExameDAO() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public String insert(Exame exame) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO EXAME (DATA_EXAME, HORA_EXAME, STATUS_EXAME, TIPO_EXAME, LOCAL_EXAME, ID_PACIENTE) VALUES (?, ?, ?, ?, ?, ?)"
        );
        stmt.setDate(1, java.sql.Date.valueOf(exame.getData()));
        stmt.setString(2, exame.horaFormatada());
        stmt.setString(3, exame.getStatus());
        stmt.setString(4, exame.getTipoExame());
        stmt.setString(5, exame.getLocal());
        stmt.setInt(6, exame.getPaciente().getIdPaciente());

        stmt.execute();
        stmt.close();
        return "Exame cadastrado com sucesso!";
    }

    public List<Exame> examesPaciente(int idPaciente) throws SQLException {
        List<Exame> listaExame = new ArrayList<>();
        String sql = "SELECT e.ID_EXAME, e.DATA_EXAME, e.HORA_EXAME, e.STATUS_EXAME, e.TIPO_EXAME, e.LOCAL_EXAME, " +
                "p.ID_PACIENTE, p.NOME_PACIENTE " +
                "FROM EXAME e JOIN PACIENTE p ON e.ID_PACIENTE = p.ID_PACIENTE " +
                "WHERE e.ID_PACIENTE = ? ORDER BY e.DATA_EXAME, e.HORA_EXAME";

        PreparedStatement ps = minhaConexao.prepareStatement(sql);
        ps.setInt(1, idPaciente);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Paciente p = new Paciente(
                    rs.getInt("ID_PACIENTE"),
                    rs.getString("NOME_PACIENTE")
            );

            LocalTime hora = LocalTime.parse(rs.getString("HORA_EXAME"), DateTimeFormatter.ofPattern("HH:mm"));

            Exame e = new Exame(
                    rs.getInt("ID_EXAME"),
                    rs.getDate("DATA_EXAME").toLocalDate(),
                    hora,
                    rs.getString("STATUS_EXAME"),
                    rs.getString("TIPO_EXAME"),
                    rs.getString("LOCAL_EXAME"),
                    p
            );

            listaExame.add(e);
        }

        rs.close();
        ps.close();
        return listaExame;
    }

    public Exame buscarPorId(int idExame) throws SQLException {
        String sql = "SELECT e.ID_EXAME, e.DATA_EXAME, e.HORA_EXAME, e.STATUS_EXAME, e.TIPO_EXAME, e.LOCAL_EXAME, " +
                "p.ID_PACIENTE, p.NOME_PACIENTE " +
                "FROM EXAME e JOIN PACIENTE p ON e.ID_PACIENTE = p.ID_PACIENTE WHERE e.ID_EXAME = ?";
        PreparedStatement ps = minhaConexao.prepareStatement(sql);
        ps.setInt(1, idExame);
        ResultSet rs = ps.executeQuery();

        Exame exame = null;
        if (rs.next()) {
            Paciente p = new Paciente(
                    rs.getInt("ID_PACIENTE"),
                    rs.getString("NOME_PACIENTE")
            );

            LocalTime hora = LocalTime.parse(rs.getString("HORA_EXAME"), DateTimeFormatter.ofPattern("HH:mm"));

            exame = new Exame(
                    rs.getInt("ID_EXAME"),
                    rs.getDate("DATA_EXAME").toLocalDate(),
                    hora,
                    rs.getString("STATUS_EXAME"),
                    rs.getString("TIPO_EXAME"),
                    rs.getString("LOCAL_EXAME"),
                    p
            );
        }

        rs.close();
        ps.close();
        return exame;
    }

    public String update(Exame exame) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE EXAME SET DATA_EXAME = ?, HORA_EXAME = ?, STATUS_EXAME = ?, TIPO_EXAME = ?, LOCAL_EXAME = ?, ID_PACIENTE = ? WHERE ID_EXAME = ?"
        );
        stmt.setDate(1, java.sql.Date.valueOf(exame.getData()));
        stmt.setString(2, exame.horaFormatada());
        stmt.setString(3, exame.getStatus());
        stmt.setString(4, exame.getTipoExame());
        stmt.setString(5, exame.getLocal());
        stmt.setInt(6, exame.getPaciente().getIdPaciente());
        stmt.setInt(7, exame.getIdExame());

        stmt.executeUpdate();
        stmt.close();

        return "Exame atualizado com sucesso!";
    }

    public String delete(int idExame) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement("DELETE FROM EXAME WHERE ID_EXAME = ?");
        stmt.setInt(1, idExame);
        stmt.execute();
        stmt.close();
        return "Exame deletado com sucesso!";
    }
}
