package com.joao_v_marques.crud_mariadb.service;

import com.joao_v_marques.crud_mariadb.dao.AlunoDAO;
import com.joao_v_marques.crud_mariadb.model.Aluno;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoDAO dao;

    public AlunoService(AlunoDAO dao) {
        this.dao = dao;
    }

    // listar todos os alunos
    public List<Aluno> listarTodos() {
        return dao.findAll();
    }

    public Aluno cadastrar(Aluno aluno) {
        return dao.save(aluno);
    }

    public void deletar(Long id) {
        if (!dao.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado com o id: " + id);
        }
        dao.deleteById(id);
    }

    public Aluno atualizar(Long id, Aluno alunoAtualizado) {
        Aluno alunoExistente = dao.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + id));

        alunoExistente.setNome(alunoAtualizado.getNome());
        alunoExistente.setDataNascimento(alunoAtualizado.getDataNascimento());
        alunoExistente.setEmail(alunoAtualizado.getEmail());
        alunoExistente.setTelefone(alunoAtualizado.getTelefone());

        return dao.update(alunoExistente);
    }
}
