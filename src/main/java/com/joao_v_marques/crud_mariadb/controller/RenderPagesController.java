package com.joao_v_marques.crud_mariadb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RenderPagesController {

    // renderizar pagina home
    @GetMapping("/home")
    public String paginaInicial() {
        return "home";
    }
}
