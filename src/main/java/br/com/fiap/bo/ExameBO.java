package br.com.fiap.bo;

import br.com.fiap.beans.Exame;
import br.com.fiap.dao.ExameDAO;

import java.sql.SQLException;
import java.util.List;

public class ExameBO {

    private ExameDAO exameDAO;

    public ExameBO() throws SQLException, ClassNotFoundException {
        this.exameDAO = new ExameDAO();
    }

    // Listar exames de um paciente
    public List<Exame> selecionarBo(int idPaciente) throws SQLException {
        return exameDAO.examesPaciente(idPaciente);
    }

    // Inserir novo exame
    public void inserirBo(Exame exame, int idPaciente) throws SQLException {
        exame.getPaciente().setIdPaciente(idPaciente);
        exameDAO.insert(exame);
    }

    // Atualizar exame
    public void atualizarBo(Exame exame) throws SQLException {
        exameDAO.update(exame);
    }

    // Deletar exame (verifica se pertence ao paciente)
    public void deletarBo(int idExame, int idPaciente) throws SQLException {
        List<Exame> exames = selecionarBo(idPaciente);
        boolean pertence = exames.stream().anyMatch(e -> e.getIdExame() == idExame);

        if (pertence) {
            exameDAO.delete(idExame);
        } else {
            throw new SQLException("Exame não pertence ao paciente logado.");
        }
    }

    // Buscar exame por ID
    public Exame buscarPorId(int idExame) throws SQLException {
        return exameDAO.buscarPorId(idExame);
    }
}
