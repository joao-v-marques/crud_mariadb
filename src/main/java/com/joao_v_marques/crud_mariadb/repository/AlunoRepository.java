package com.joao_v_marques.crud_mariadb.repository;

import com.joao_v_marques.crud_mariadb.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
