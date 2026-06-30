package com.joao_v_marques.crud_mariadb.model;

import java.time.LocalDate;

public class Aluno {

    // atributos
    private Long id;
    private String nome;
    private String dataNascimento;
    private String email;
    private String telefone;

    // construtores
    public Aluno() {

    }

    // gets e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
