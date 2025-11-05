package br.com.fiap.beans;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CadastroManager {

    static String texto (String s) {
        return JOptionPane.showInputDialog(s);
    }

    public Paciente cadastrar() {
        String nome = texto("Digite seu Nome Completo:");
        String  cpf = texto("Digite seu CPF: (Apenas números)");
        String data_nascimento = texto("Digite sua data de nascimento DD/MM/AAAA");
        String telefone = texto("Digite seu telefone: (Apenas números, incluindo DDD)");
        String[] opcoesSexo = {"Masculino", "Feminino", "Outros"};

        int escolha = JOptionPane.showOptionDialog(
                null,
                "Selecione o seu Sexo:",
                "Sexo do Paciente",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesSexo,
                opcoesSexo[0]
        );
        String sexo = opcoesSexo[escolha];

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimento = LocalDate.parse(data_nascimento, formato);

        return new Paciente(nome, cpf, nascimento, telefone, sexo);
    }

    public Paciente atualizarCadastro() {
        String nome = texto("Digite seu Nome Completo:");
        String  cpf = texto("Digite seu CPF: (Apenas números)");
        String data_nascimento = texto("Digite sua data de nascimento DD/MM/AAAA");
        String telefone = texto("Digite seu telefone: (Apenas números, incluindo DDD)");
        String[] opcoesSexo = {"Masculino", "Feminino", "Outros"};

        int escolha = JOptionPane.showOptionDialog(
                null,
                "Selecione o seu Sexo:",
                "Sexo do Paciente",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesSexo,
                opcoesSexo[0]
        );
        String sexo = opcoesSexo[escolha];

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nascimento = LocalDate.parse(data_nascimento, formato);

        return new Paciente(nome, cpf, nascimento, telefone, sexo);
    }

}
