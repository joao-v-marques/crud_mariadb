package com.joao_v_marques.crud_mariadb.service;

import com.joao_v_marques.crud_mariadb.model.Aluno;
import com.joao_v_marques.crud_mariadb.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    // listar todos os alunos
    public List<Aluno> listarTodos() {
        return repository.findAll();
    }

    // cadastrar novo aluno
    public Aluno cadastrar(Aluno aluno) {
        return repository.save(aluno);
    }

    // excluir aluno
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado com o id: " + id);
        }

        repository.deleteById(id);
    }
}
