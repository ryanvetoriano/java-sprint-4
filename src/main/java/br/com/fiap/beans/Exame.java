package br.com.fiap.beans;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Exame extends Agendamento{

    private int idExame;
    private String tipoExame;
    private String local;

    public Exame(LocalDate data, LocalTime hora, String status, String tipoExame, String local, Paciente paciente) {
        super(data, hora, status);
        this.tipoExame = tipoExame;
        this.local = local;
    }

    public Exame() {
    }

    public Exame(int idExame, LocalDate data, LocalTime hora, String status, String tipoExame, String local, Paciente p) {
        super(data, hora, status);
        this.idExame = idExame;
        this.tipoExame = tipoExame;
        this.local = local;
    }

    public int getIdExame() {
        return idExame;
    }

    public void setIdExame(int idExame) {
        this.idExame = idExame;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "\n\n--- Exame ---" +
                "\nExame marcado: " + tipoExame +
                "\nLocal do Exame: " + local + super.toString();
    }

    public void mostrarDados() {
        JOptionPane.showMessageDialog(null,
                "\n\n--- Exame ---" +
                        "\nExame marcado: " + tipoExame +
                        "\nLocal do Exame: " + local + super.toString());
    }
}
