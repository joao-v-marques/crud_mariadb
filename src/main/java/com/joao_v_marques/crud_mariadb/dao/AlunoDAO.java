package com.joao_v_marques.crud_mariadb.dao;

import com.joao_v_marques.crud_mariadb.infra.Conexao;
import com.joao_v_marques.crud_mariadb.model.Aluno;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AlunoDAO {

    private final Conexao conexao;

    public AlunoDAO(Conexao conexao) {
        this.conexao = conexao;
    }

    // retornar todos os alunos
    public List<Aluno> findAll() {
        String sql_query = "SELECT id_aluno, nome, data_nascimento, email, telefone FROM alunos";
        List<Aluno> listaAlunos = new ArrayList<>();
        Conexao conexao = new Conexao();

        try {
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql_query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                listaAlunos.add(mapearAluno(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar();
        }

        return listaAlunos;
    }


    // retornar aluno pelo id
    public Optional<Aluno> findById(Long id) {
        String sql_query = "SELECT id_aluno, nome, data_nascimento, email, telefone FROM alunos WHERE id_aluno = ?";
        Conexao conexao = new Conexao();

        try {
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql_query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapearAluno(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar();
        }

        return Optional.empty();
    }

    // verificar se aluno existe, coloquei esse para usar depois no service do delete para garantir que o usuário existe
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    // cadastrar novo aluno
    public Aluno save(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, data_nascimento, email, telefone) VALUES (?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try {
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, aluno.getNome());
            stmt.setDate(2, Date.valueOf(LocalDate.parse(aluno.getDataNascimento())));
            stmt.setString(3, aluno.getEmail());
            stmt.setString(4, aluno.getTelefone());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                aluno.setId(rs.getLong(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar();
        }

        return aluno;
    }

    // atualizar aluno
    public Aluno update(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, data_nascimento = ?, email = ?, telefone = ? WHERE id_aluno = ?";
        Conexao conexao = new Conexao();

        try {
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setDate(2, Date.valueOf(LocalDate.parse(aluno.getDataNascimento())));
            stmt.setString(3, aluno.getEmail());
            stmt.setString(4, aluno.getTelefone());
            stmt.setLong(5, aluno.getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar();
        }

        return aluno;
    }

    // excluir aluno
    public void deleteById(Long id) {
        String sql = "DELETE FROM alunos WHERE id_aluno = ?";
        Conexao conexao = new Conexao();

        try {
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexao.desconectar();
        }
    }

    // converte uma linha do ResultSet em um objeto Aluno
    private Aluno mapearAluno(ResultSet rs) throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getLong("id_aluno"));
        aluno.setNome(rs.getString("nome"));
        aluno.setDataNascimento(rs.getString("data_nascimento"));
        aluno.setEmail(rs.getString("email"));
        aluno.setTelefone(rs.getString("telefone"));
        return aluno;
    }
}
