package br.com.fiap.main;

import br.com.fiap.beans.*;
import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.dao.ConsultaDAO;
import br.com.fiap.dao.ExameDAO;
import br.com.fiap.dao.PacienteDAO;
import br.com.fiap.dao.ReceitaMedicaDAO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    static String texto (String s) {
        return JOptionPane.showInputDialog(s);
    }
    static int inteiro (String i) {
        return Integer.parseInt(JOptionPane.showInputDialog(i));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Connection cn = new ConexaoFactory().conexao();

        System.out.println("Conectado com o banco de dados");

        PacienteDAO pacienteDAO = new PacienteDAO();
        ConsultaDAO consultaDAO = new ConsultaDAO();
        ExameDAO exameDAO = new ExameDAO();
        ReceitaMedicaDAO receitaMedicaDAO = new ReceitaMedicaDAO();

        Paciente objPaciente = new Paciente();
        CadastroManager cadastro = new CadastroManager();

        Consulta objConsulta = new Consulta();
        Exame objExame = new Exame();
        AgendamentoManager agendamento = new AgendamentoManager();

        ReceitaMedica objReceitaMedica = new ReceitaMedica();
        ReceitaMedicaManager receita = new ReceitaMedicaManager();

        int resposta = JOptionPane.showConfirmDialog(null,
                "Você já tem cadastro?",

                "Cadastro/Login",
                JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.NO_OPTION) {
            objPaciente = cadastro.cadastrar();
            pacienteDAO.insert(objPaciente);
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso! Faça o login.");
        }

        boolean logado = false;
        int tentativas = 5;

        while (!logado && tentativas > 0) {
            String cpf = texto("Digite seu CPF:");

            // Agora busca no banco
            objPaciente = pacienteDAO.buscarLogin(cpf);

            if (objPaciente != null) {
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
                logado = true;
            } else {
                tentativas--;
                JOptionPane.showMessageDialog(null, "Nome ou CPF incorretos! Restam " + tentativas + " tentativas.");
            }
        }

        if (!logado) {
            JOptionPane.showMessageDialog(null, "Número máximo de tentativas atingido. Programa encerrado.");
            System.exit(0);
        }

        boolean rodando = true;
        while (rodando) {
                // Menu principal
                int opcao = Integer.parseInt(JOptionPane.showInputDialog("""
                Menu Principal:
                1 - Menu do Paciente
                2 - Menu da Consulta
                3 - Menu do Exame
                4 - Menu da Receita Médica 
                0 - Sair
                """));

                switch (opcao) {
                    case 1 -> {
                        int opcaoPaciente = Integer.parseInt(JOptionPane.showInputDialog("""
                                Menu do Paciente:
                                1 - Atualizar Cadastro
                                2 - Deletar Cadastro
                                3 - Mostrar dados do Paciente
                                0 - Voltar
                                """));

                        switch (opcaoPaciente) {
                            case 1 -> {
                                objPaciente = cadastro.atualizarCadastro();
                                pacienteDAO.update(objPaciente);
                            }
                            case 2 ->  {
                                int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Tem certeza que deseja deletar seu cadastro?\nEssa ação não pode ser desfeita.",
                                        "Confirmação de Exclusão",
                                        JOptionPane.YES_NO_OPTION
                                );

                                if (confirm == JOptionPane.YES_OPTION) {
                                    String msg = pacienteDAO.delete(objPaciente.getCpf());
                                    JOptionPane.showMessageDialog(null, msg);
                                    rodando = false; // encerra o sistema após exclusão
                                } else {
                                    JOptionPane.showMessageDialog(null, "Exclusão cancelada.");
                                }
                            }
                            case 3 -> objPaciente.mostrarDados();
                            case 0 -> JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
                            default -> JOptionPane.showMessageDialog(null, "Opção inválida no menu Paciente");
                        }
                    }

                    case 2 -> {
                        int opcaoConsulta = Integer.parseInt(JOptionPane.showInputDialog("""
                        Menu da Consulta:
                            1 - Cadastrar Consulta
                            2 - Listar Consultas
                            3 - Deletar Consulta
                            4 - Atualizar Consulta
                            0 - Voltar
                        """));

                        switch (opcaoConsulta) {
                            case 1 -> {
                                objConsulta = agendamento.agendarConsulta(objPaciente);
                                objConsulta.setPaciente(objPaciente);
                                consultaDAO.insert(objConsulta);
                                JOptionPane.showMessageDialog(null, "Consulta cadastrada com sucesso!");
                            }
                            case 2 -> {
                                List<Consulta> consultas = consultaDAO.consultasPaciente(objPaciente.getIdPaciente());

                                if (consultas.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Você não possui consultas cadastradas.");
                                } else {
                                    StringBuilder sb = new StringBuilder("Suas Consultas:\n");
                                    for (Consulta c : consultas) {
                                        c.setPaciente(objPaciente);
                                        sb.append(c).append("\n");
                                    }
                                    JOptionPane.showMessageDialog(null, sb.toString());
                                }
                            }
                            case 3 -> {
                                int consultaId = inteiro("Informe o ID da Consulta que você deseja deletar:");
                                objConsulta.setIdConsulta(consultaId);

                                int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Tem certeza que deseja deletar a consulta de id: " + consultaId + "?",
                                        "Confirmação de Exclusão",
                                        JOptionPane.YES_NO_OPTION
                                );

                                if (confirm == JOptionPane.YES_OPTION) {
                                    String msg = consultaDAO.delete(objConsulta.getIdConsulta());
                                    JOptionPane.showMessageDialog(null, msg);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Exclusão cancelada.");
                                }
                            }
                            case 4 -> {
                                int consultaId = inteiro("Informe o ID da Consulta que você deseja atualizar:");
                                objConsulta.setIdConsulta(consultaId);

                                int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Tem certeza que deseja atualizar a consulta de id: " + consultaId + "?",
                                        "Confirmação de Atualização",
                                        JOptionPane.YES_NO_OPTION
                                );

                                if (confirm == JOptionPane.YES_OPTION) {
                                    Consulta novaConsulta = agendamento.agendarConsulta(objPaciente);
                                    novaConsulta.setPaciente(objPaciente);
                                    novaConsulta.setIdConsulta(consultaId); // importante: manter o mesmo ID para atualizar no banco
                                    String msg = consultaDAO.update(novaConsulta);
                                    JOptionPane.showMessageDialog(null, msg);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Atualização cancelada.");
                                }
                            }
                            case 0 -> JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
                            default -> JOptionPane.showMessageDialog(null, "Opção inválida no submenu Consulta");
                        }
                    }

                    case 3 -> {
                        int opcaoExame = Integer.parseInt(JOptionPane.showInputDialog("""
                        Menu do Exame:
                            1 - Cadastrar Exame
                            2 - Listar Exames
                            3 - Deletar Exames
                            4 - Atualizar Exame
                            0 - Voltar
                        """));

                        switch (opcaoExame) {
                            case 1 -> {
                                objExame = agendamento.agendarExame(objPaciente);
                                objExame.setPaciente(objPaciente);
                                exameDAO.insert(objExame);
                                JOptionPane.showMessageDialog(null, "Exame cadastrado com sucesso!");
                            }
                            case 2 -> {
                                List<Exame> exames = exameDAO.examesPaciente(objPaciente.getIdPaciente());

                                if (exames.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Você não possui exames cadastrados.");
                                } else {
                                    StringBuilder sb = new StringBuilder("Seus Exames:\n");
                                    for (Exame e : exames) {
                                        e.setPaciente(objPaciente);
                                        sb.append(e).append("\n");
                                    }
                                    JOptionPane.showMessageDialog(null, sb.toString());
                                }
                            }
                            case 3 -> {
                                int exameId = inteiro("Informe o ID do Exame que você deseja deletar:");
                                objExame.setIdExame(exameId);

                                int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Tem certeza que deseja deletar o exame de id: " + exameId + "?",
                                        "Confirmação de Exclusão",
                                        JOptionPane.YES_NO_OPTION
                                );

                                if (confirm == JOptionPane.YES_OPTION) {
                                    String msg = exameDAO.delete(objExame.getIdExame());
                                    JOptionPane.showMessageDialog(null, msg);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Exclusão cancelada.");
                                }
                            }
                            case 4 -> {
                                int exameId = inteiro("Informe o ID do Exame que você deseja atualizar:");
                                objExame.setIdExame(exameId);

                                int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Tem certeza que deseja atualizar o Exame de id: " + exameId + "?",
                                        "Confirmação de Atualização",
                                        JOptionPane.YES_NO_OPTION
                                );

                                if (confirm == JOptionPane.YES_OPTION) {
                                    Exame novoExame = agendamento.agendarExame(objPaciente);
                                    novoExame.setPaciente(objPaciente);
                                    novoExame.setIdExame(exameId); // importante: manter o mesmo ID para atualizar no banco
                                    String msg = exameDAO.update(novoExame);
                                    JOptionPane.showMessageDialog(null, msg);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Atualização cancelada.");
                                }
                            }
                            case 0 -> JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
                            default -> JOptionPane.showMessageDialog(null, "Opção inválida no submenu Consulta");
                        }
                    }

                    case 4 -> {
                        int opcaoReceita = Integer.parseInt(JOptionPane.showInputDialog("""
                            Menu da Receita Médica:
                            1 - Cadastrar Receita Médica
                            2 - Listar Receitas Médicas
                            3 - Deletar Receita Médica
                            4 - Atualizar Receita Médica
                            0 - Voltar
                        """));

                        switch (opcaoReceita) {
                            case 1 -> {
                                objReceitaMedica = receita.adicionarReceita(objPaciente);
                                objReceitaMedica.setPaciente(objPaciente);
                                receitaMedicaDAO.insert(objReceitaMedica);
                                JOptionPane.showMessageDialog(null, "Receita Médica cadastrada com sucesso!");
                            }
                            case 2 -> {
                                List<ReceitaMedica> receitasMedicas = receitaMedicaDAO.receitasPaciente(objPaciente.getIdPaciente());

                                if (receitasMedicas.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Você não possui Receitas Médicas cadastradas.");
                                } else {
                                    StringBuilder sb = new StringBuilder("Suas Receitas Médicas:\n");
                                    for (ReceitaMedica r : receitasMedicas) {
                                        r.setPaciente(objPaciente);
                                        sb.append(r).append("\n");
                                    }
                                    JOptionPane.showMessageDialog(null, sb.toString());
                                }
                            }
                            case 3 -> {
                                int receitaMedicaId = inteiro("Informe o ID da Receita Médica que você deseja deletar:");
                                objReceitaMedica.setIdReceita(receitaMedicaId);

                                int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Tem certeza que deseja deletar a Receita Médica de id: " + receitaMedicaId + "?",
                                        "Confirmação de Exclusão",
                                        JOptionPane.YES_NO_OPTION
                                );

                                if (confirm == JOptionPane.YES_OPTION) {
                                    String msg = receitaMedicaDAO.delete(objReceitaMedica.getIdReceita());
                                    JOptionPane.showMessageDialog(null, msg);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Exclusão cancelada.");
                                }
                            }
                            case 4 -> {
                                int receitaMedicaId = inteiro("Informe o ID da Receita Médica que você deseja atualizar:");
                                objReceitaMedica.setIdReceita(receitaMedicaId);

                                int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Tem certeza que deseja atualizar a Receita de id: " + receitaMedicaId + "?",
                                        "Confirmação de Atualização",
                                        JOptionPane.YES_NO_OPTION
                                );

                                if (confirm == JOptionPane.YES_OPTION) {
                                    ReceitaMedica novaReceita = receita.adicionarReceita(objPaciente);
                                    novaReceita.setPaciente(objPaciente);
                                    novaReceita.setIdReceita(receitaMedicaId);
                                    String msg = receitaMedicaDAO.update(novaReceita);
                                    JOptionPane.showMessageDialog(null, msg);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Atualização cancelada.");
                                }
                            }
                            case 0 -> JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
                            default -> JOptionPane.showMessageDialog(null, "Opção inválida no submenu Consulta");
                        }
                    }

                    case 0 -> {
                        JOptionPane.showMessageDialog(null, "Saindo do sistema...");
                        rodando = false;
                    }

                    default -> JOptionPane.showMessageDialog(null, "Opção inválida no menu principal");




                }
    }
}
}
