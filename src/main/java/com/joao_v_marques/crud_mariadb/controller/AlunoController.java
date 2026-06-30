package com.joao_v_marques.crud_mariadb.controller;

import com.joao_v_marques.crud_mariadb.model.Aluno;
import com.joao_v_marques.crud_mariadb.service.AlunoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    // retornar alunos cadastrados
    @GetMapping
    public List<Aluno> listar() {
        return service.listarTodos();
    }

    // cadastrar novo aluno
    @PostMapping
    public Aluno cadastrar(@RequestBody Aluno aluno) {
        return service.cadastrar(aluno);
    }

    // excluir aluno
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    // atualizar aluno
    @PutMapping("/{id}")
    public Aluno atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
        return service.atualizar(id, aluno);
    }

}
