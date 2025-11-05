package br.com.fiap.beans;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta extends Agendamento {

    private int idConsulta;
    private String motivoConsulta;

    // Construtor completo
    public Consulta(LocalDate data, LocalTime hora, String status, String motivoConsulta, Paciente paciente) {
        super(data, hora, status);
        this.motivoConsulta = motivoConsulta;
    }

    // Construtor alternativo
    public Consulta(int idConsulta, LocalDate data, LocalTime hora, String status, String motivoConsulta, Paciente paciente) {
        super(data, hora, status);
        this.idConsulta = idConsulta;
        this.motivoConsulta = motivoConsulta;;
    }

    // Construtor vazio
    public Consulta() {
        super();
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }


    @Override
    public String toString() {
        return "\n\n--- Consulta ---" +
                "\nID da Consulta: " + idConsulta +
                "\nMotivo da Consulta: " + motivoConsulta +
                super.toString();
    }

    public void mostrarDados() {
        JOptionPane.showMessageDialog(null,
                "\n\n--- Consulta ---" +
                        "\nID da Consulta: " + idConsulta +
                        "\nMotivo da Consulta: " + motivoConsulta +
                        super.toString()
        );
    }
}
