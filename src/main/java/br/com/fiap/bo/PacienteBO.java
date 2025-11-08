package br.com.fiap.bo;

import br.com.fiap.beans.Paciente;
import br.com.fiap.dao.PacienteDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class PacienteBO {

    private PacienteDAO pacienteDAO;

    public PacienteBO() throws ClassNotFoundException, SQLException {
        this.pacienteDAO = new PacienteDAO(); // inicializa o DAO corretamente
    }

    // Selecionar todos
    public ArrayList<Paciente> selecionarBo() throws ClassNotFoundException, SQLException {
        return (ArrayList<Paciente>) pacienteDAO.select();
    }

    // Inserir
    public void inserirBo(Paciente paciente) throws ClassNotFoundException, SQLException {
        pacienteDAO.insert(paciente);
    }

    // Atualizar
    public void atualizarBo(Paciente paciente) throws ClassNotFoundException, SQLException {
        pacienteDAO.update(paciente);
    }

    // Deletar
    public void deletarBo(String cpf) throws ClassNotFoundException, SQLException {
        pacienteDAO.delete(cpf);
    }

    // Buscar login pelo CPF
    public Paciente buscarLogin(String cpf) throws SQLException, ClassNotFoundException {
        return pacienteDAO.buscarLogin(cpf);
    }
}
