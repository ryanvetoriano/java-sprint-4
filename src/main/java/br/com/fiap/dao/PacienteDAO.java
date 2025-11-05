package br.com.fiap.dao;

import br.com.fiap.beans.Paciente;
import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public Connection minhaConexao;

    public PacienteDAO() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    // insert
    public String insert(Paciente paciente) throws SQLException {
        PreparedStatement stmt =
                minhaConexao.prepareStatement("INSERT INTO PACIENTE (NOME_PACIENTE, CPF_PACIENTE, DATA_NASCIMENTO, TELEFONE_PACIENTE, SEXO_PACIENTE) VALUES (?, ?, ?, ?, ?)");
                stmt.setString(1,paciente.getNome());
                stmt.setString(2,paciente.getCpf());
                stmt.setDate(3,java.sql.Date.valueOf(paciente.getDataNascimento()));
                stmt.setString(4,paciente.getTelefone());
                stmt.setString(5,paciente.getSexo());

                stmt.execute();
                stmt.close();

                return "Paciente cadastrado com sucesso!";
    }

    public String delete(String cpf) throws SQLException {
        PreparedStatement stmt =
                minhaConexao.prepareStatement("Delete from PACIENTE where CPF_PACIENTE = ?");
        stmt.setString(1, cpf);

        stmt.execute();
        stmt.close();

        return "Paciente deletado com sucesso!";
    }

    // UpDate
    public String update (Paciente paciente) throws SQLException {
        PreparedStatement stmt =
                minhaConexao.prepareStatement("Update PACIENTE set NOME_PACIENTE = ?, DATA_NASCIMENTO = ?, TELEFONE_PACIENTE = ?, SEXO_PACIENTE = ? WHERE CPF_PACIENTE = ?");
        stmt.setString(1, paciente.getNome());
        stmt.setDate(2, java.sql.Date.valueOf(paciente.getDataNascimento()));
        stmt.setString(3, paciente.getTelefone());
        stmt.setString(4, paciente.getSexo());
        stmt.setString(5, paciente.getCpf());

        stmt.executeUpdate();
        stmt.close();

        return "Paciente atualizado com sucesso!";
    }

    // Select
    public List<Paciente> select() throws SQLException {
        List<Paciente> listPacientes = new ArrayList<>();

        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT * FROM PACIENTE");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Paciente paciente = new Paciente();
            paciente.setIdPaciente(rs.getInt("ID_PACIENTE"));
            paciente.setNome(rs.getString("NOME_PACIENTE"));
            paciente.setCpf(rs.getString("CPF_PACIENTE"));
            paciente.setDataNascimento(rs.getDate("DATA_NASCIMENTO").toLocalDate());
            paciente.setTelefone(rs.getString("TELEFONE_PACIENTE"));
            paciente.setSexo(rs.getString("SEXO_PACIENTE"));

            listPacientes.add(paciente);
        }

        rs.close();
        stmt.close();

        return listPacientes;
    }

    public Paciente buscarLogin(String cpf) throws SQLException {
        Paciente paciente = null;

        String sql = "SELECT * FROM PACIENTE WHERE CPF_PACIENTE = ?";
        PreparedStatement stmt = minhaConexao.prepareStatement(sql);
        stmt.setString(1, cpf);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            paciente = new Paciente(rs.getInt("ID_PACIENTE"), rs.getString("NOME_PACIENTE"));
            paciente.setIdPaciente(rs.getInt("ID_PACIENTE"));
            paciente.setNome(rs.getString("NOME_PACIENTE"));
            paciente.setCpf(rs.getString("CPF_PACIENTE"));
            paciente.setDataNascimento(rs.getDate("DATA_NASCIMENTO").toLocalDate());
            paciente.setTelefone(rs.getString("TELEFONE_PACIENTE"));
            paciente.setSexo(rs.getString("SEXO_PACIENTE"));
        }

        rs.close();
        stmt.close();

        return paciente;
    }
}
