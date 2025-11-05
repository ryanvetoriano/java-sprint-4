package br.com.fiap.beans;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static javax.swing.UIManager.getInt;

public class AgendamentoManager {

    static String texto (String s) {
        return JOptionPane.showInputDialog(s);
    }

    public Consulta agendarConsulta(Paciente paciente){
        String dataStr = texto("Digite a data da consulta (DD/MM/AAAA):");
        String horaStr = texto("Digite a hora da consulta (HH:MM):");
        String[] opcoesStatus = {"Agendado", "Realizado", "Cancelado"};
        String motivo = texto("Digite o motivo pelo qual você marcou está consulta:");

        int escolha = JOptionPane.showOptionDialog(
                null,
                "Selecione o status da Consulta:",
                "Status da Consulta",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesStatus,
                opcoesStatus[0]
        );
        String status = opcoesStatus[escolha];

        LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));

        return new Consulta(data, hora, status, motivo, paciente);
    }

    public Exame agendarExame(Paciente paciente){

        String data = texto("Digite a data do Exame (DD/MM/AAAA):");
        String hora = texto("Digite a hora do Exame (HH:mm):");
        String[] opcoesStatus = {"Agendado", "Realizado", "Cancelado"};
        String exame = texto("Digite o tipo de exame que será cadastrado:");
        String local = texto("Digite o local onde será feito o exame:");

        int escolha = JOptionPane.showOptionDialog(
                null,
                "Selecione o status do Exame:",
                "Status do Exame",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesStatus,
                opcoesStatus[0]
        );
        String status = opcoesStatus[escolha];


        LocalDate dataformatada = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime horaFormatada = LocalTime.parse(hora);

        return new Exame(dataformatada, horaFormatada, status, exame, local, paciente);
    }

}
