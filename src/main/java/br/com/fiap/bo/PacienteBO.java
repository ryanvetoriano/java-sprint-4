package br.com.fiap.bo;

import br.com.fiap.beans.Paciente;
import br.com.fiap.dao.PacienteDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class PacienteBO {

    PacienteDAO pacienteDAO;

    // Selecionar
    public ArrayList<Paciente> selecionarBo() throws ClassNotFoundException, SQLException {
        pacienteDAO = new PacienteDAO();
        // Regra de negocios

        return (ArrayList<Paciente>) pacienteDAO.select();
    }

    // Inserir
    public void inserirBo(Paciente paciente) throws ClassNotFoundException, SQLException {
        PacienteDAO pacienteDAO = new PacienteDAO();
        // Regra de negocios
        pacienteDAO.insert(paciente);
    }

    // Atualizar
    public void atualizarBo (Paciente paciente) throws ClassNotFoundException, SQLException {
        PacienteDAO pacienteDAO = new PacienteDAO();
        // Regra de negocios
        pacienteDAO.update(paciente);
    }

    // Deletar
    public void deletarBo (String cpf) throws ClassNotFoundException, SQLException {
        PacienteDAO pacienteDAO = new PacienteDAO();
        // Regra de negocios
        pacienteDAO.delete(cpf);
    }


}