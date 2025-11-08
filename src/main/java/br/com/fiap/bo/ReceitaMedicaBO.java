package br.com.fiap.bo;

import br.com.fiap.beans.ReceitaMedica;
import br.com.fiap.dao.ReceitaMedicaDAO;

import java.sql.SQLException;
import java.util.List;

public class ReceitaMedicaBO {

    private ReceitaMedicaDAO receitaDAO;

    public ReceitaMedicaBO() throws SQLException, ClassNotFoundException {
        this.receitaDAO = new ReceitaMedicaDAO();
    }

    public String cadastrar(ReceitaMedica receita) throws SQLException {
        return receitaDAO.insert(receita);
    }

    public List<ReceitaMedica> listarPorPaciente(int idPaciente) throws SQLException {
        return receitaDAO.receitasPaciente(idPaciente);
    }

    public String atualizar(ReceitaMedica receita) throws SQLException {
        return receitaDAO.update(receita);
    }

    public String deletar(int idReceita) throws SQLException {
        return receitaDAO.delete(idReceita);
    }
}
