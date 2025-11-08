package br.com.fiap.beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReceitaMedica {

    private int idReceita;
    private Paciente paciente;
    private LocalDate dataEmissao;
    private String medicamento;
    private String dosagem;
    private String frequencia;
    private String duracao;

    public ReceitaMedica(LocalDate dataEmissao, String medicamento, String dosagem, String frquencia, String duracao, Paciente paciente) {
        this.dataEmissao = dataEmissao;
        this.medicamento = medicamento;
        this.dosagem = dosagem;
        this.frequencia = frquencia;
        this.duracao = duracao;
    }

    public ReceitaMedica(int idReceita, LocalDate dataEmissao, String medicamento, String dosagem, String frquencia, String duracao, Paciente paciente) {
        this.idReceita = idReceita;
        this.dataEmissao = dataEmissao;
        this.medicamento = medicamento;
        this.dosagem = dosagem;
        this.frequencia = frquencia;
        this.duracao = duracao;
    }

    public ReceitaMedica() {

    }

    public int getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(int idReceita) {
        this.idReceita = idReceita;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getDataNascimentoFormatada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataEmissao.format(formato);
    }



}
