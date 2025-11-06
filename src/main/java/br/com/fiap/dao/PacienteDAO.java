package br.com.fiap.dao;

import br.com.fiap.beans.Paciente;
import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // 🔹 Não manter a conexão como atributo de instância

    // insert
    public String insert(Paciente paciente) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO PACIENTE (NOME_PACIENTE, CPF_PACIENTE, DATA_NASCIMENTO, TELEFONE_PACIENTE, SEXO_PACIENTE) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setDate(3, java.sql.Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getSexo());

            stmt.executeUpdate();
            return "Paciente cadastrado com sucesso!";
        }
    }

    // delete
    public String delete(String cpf) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM PACIENTE WHERE CPF_PACIENTE = ?";
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.executeUpdate();
            return "Paciente deletado com sucesso!";
        }
    }

    // update
    public String update(Paciente paciente) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE PACIENTE SET NOME_PACIENTE = ?, DATA_NASCIMENTO = ?, TELEFONE_PACIENTE = ?, SEXO_PACIENTE = ? WHERE CPF_PACIENTE = ?";
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setDate(2, java.sql.Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(3, paciente.getTelefone());
            stmt.setString(4, paciente.getSexo());
            stmt.setString(5, paciente.getCpf());

            stmt.executeUpdate();
            return "Paciente atualizado com sucesso!";
        }
    }

    // select all
    public List<Paciente> select() throws SQLException, ClassNotFoundException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM PACIENTE";

        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(rs.getInt("ID_PACIENTE"));
                p.setNome(rs.getString("NOME_PACIENTE"));
                p.setCpf(rs.getString("CPF_PACIENTE"));
                p.setDataNascimento(rs.getDate("DATA_NASCIMENTO").toLocalDate());
                p.setTelefone(rs.getString("TELEFONE_PACIENTE"));
                p.setSexo(rs.getString("SEXO_PACIENTE"));
                pacientes.add(p);
            }
        }

        return pacientes;
    }

    // buscar por CPF (login)
    public Paciente buscarLogin(String cpf) throws SQLException, ClassNotFoundException {
        Paciente paciente = null;
        String sql = "SELECT * FROM PACIENTE WHERE CPF_PACIENTE = ?";

        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paciente = new Paciente();
                    paciente.setIdPaciente(rs.getInt("ID_PACIENTE"));
                    paciente.setNome(rs.getString("NOME_PACIENTE"));
                    paciente.setCpf(rs.getString("CPF_PACIENTE"));
                    paciente.setDataNascimento(rs.getDate("DATA_NASCIMENTO").toLocalDate());
                    paciente.setTelefone(rs.getString("TELEFONE_PACIENTE"));
                    paciente.setSexo(rs.getString("SEXO_PACIENTE"));
                }
            }
        }

        return paciente;
    }
}
