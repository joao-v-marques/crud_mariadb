package com.joao_v_marques.crud_mariadb.infra;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class Conexao {

    private Connection conexao;

    public Conexao() {
        try {
            String url = "jdbc:mariadb://localhost:3306/crud_mariadb" ;
            String usuario = "root";
            String senha = "1234";
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desconectar() {
        try {
            conexao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
}
