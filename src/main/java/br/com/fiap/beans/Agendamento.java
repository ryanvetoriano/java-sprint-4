package br.com.fiap.beans;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Agendamento {

    private LocalDate data;
    private LocalTime hora;
    private Paciente paciente;
    private String status;

    public Agendamento() {
    }

    public Agendamento(LocalDate data, LocalTime hora, String status) {
        this.data = data;
        this.hora = hora;
        this.status = status;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String dataFormatada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formato);
    }

    public String horaFormatada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
        return hora.format(formato);
    }

    @Override
    public String toString() {
        return "\nData: " + dataFormatada() +
                "\nHora: " + horaFormatada() +
                "\nPaciente: " + paciente.getNome() +
                "\nStatus: " + status;
    }

    public void mostrarDados(){
        JOptionPane.showMessageDialog(null,"\nData: " + dataFormatada() +
                "\nHora: " + horaFormatada() +
                "\nPaciente: " + paciente.getNome() +
                "\nStatus: " + status);
    }
}
