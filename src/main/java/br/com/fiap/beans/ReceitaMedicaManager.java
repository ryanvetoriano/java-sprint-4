package br.com.fiap.beans;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReceitaMedicaManager {

    static String texto (String s) {
        return JOptionPane.showInputDialog(s);
    }

    public ReceitaMedica adicionarReceita(Paciente paciente){
        String data = texto("Digite a data na qual foi passada a receita médica:");
        String medicamento = texto("Digite qual medicamento o médico lhe passou:");
        String dosagem = texto("Qual a dosagem do medicamento:");
        String frequencia = texto("Com qual frequência deve ser tomada a medicação:");
        String duracao = texto("Por quanto tempo deverá ser tomada a medicação:");

        LocalDate dataformatada = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return new ReceitaMedica(dataformatada, medicamento, dosagem, frequencia, duracao, paciente);
    }

}
