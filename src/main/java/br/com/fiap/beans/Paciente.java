package br.com.fiap.beans;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Paciente {

    private int idPaciente;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String sexo;

    public Paciente(String nome, String cpf, LocalDate dataNascimento, String telefone, String sexo) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.sexo = sexo;
    }

    public Paciente(int idPaciente, String nomePaciente) {

    }

    public Paciente() {

    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "\n--- Paciente ---" +
                "\nNome do Paciente: " + nome +
                "\nCPF do Paciente: " + cpf +
                "\nData de nascimento do Paciente: " + getDataNascimentoFormatada() +
                "\nTelefone do Paciente: " + telefone +
                "\nSexo do Paciente: " + sexo;
    }

    public String getDataNascimentoFormatada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNascimento.format(formato);
        }


    public void mostrarDados() {
        JOptionPane.showMessageDialog(null,
                "\n--- Paciente ---" +
                        "\nId do Paciente: " + idPaciente +
                        "\nNome do Paciente: " + nome +
                        "\nCPF do Paciente: " + cpf +
                        "\nData de nascimento do Paciente: " + getDataNascimentoFormatada() +
                        "\nTelefone do Paciente: " + telefone +
                        "\nSexo do Paciente: " + sexo);
    }
}