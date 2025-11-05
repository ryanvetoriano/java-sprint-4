package br.com.fiap.bo;

import br.com.fiap.beans.Consulta;
import br.com.fiap.dao.ConsultaDAO;

import java.sql.SQLException;
import java.util.List;

public class ConsultaBO {

    private ConsultaDAO consultaDAO;

    public ConsultaBO() throws SQLException, ClassNotFoundException {
        this.consultaDAO = new ConsultaDAO();
    }

    // Listar consultas de um paciente
    public List<Consulta> selecionarBo(int idPaciente) throws SQLException {
        return consultaDAO.consultasPaciente(idPaciente);
    }

    // Inserir nova consulta
    public void inserirBo(Consulta consulta, int idPaciente) throws SQLException {
        // Garante que a consulta pertence ao paciente logado
        consulta.getPaciente().setIdPaciente(idPaciente);
        consultaDAO.insert(consulta);
    }

    // Atualizar consulta
    public void atualizarBo(Consulta consulta) throws SQLException {
        consultaDAO.update(consulta);
    }

    // Deletar consulta, verificando se pertence ao paciente
    public void deletarBo(int idConsulta, int idPaciente) throws SQLException {
        List<Consulta> consultas = selecionarBo(idPaciente);
        boolean pertence = consultas.stream().anyMatch(c -> c.getIdConsulta() == idConsulta);

        if (pertence) {
            consultaDAO.delete(idConsulta);
        } else {
            throw new SQLException("Consulta não pertence ao paciente logado.");
        }
    }
}
