package org.example.selenium.core.test;

import org.example.selenium.core.BaseTest;
import org.example.selenium.core.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginValido() {

        LoginPage login = new LoginPage(driver);

        login
                .abrir()
                .preencherEmail("admin@sistema.com")
                .preencherSenha("admin123")
                .clicarLogin();

        assertTrue(login.loginComSucesso());
    }

    @Test
    public void loginInvalido(){
        LoginPage login = new LoginPage(driver);

        login
                .abrir()
                .preencherEmail("usuario@invalido.com")
                .preencherSenha("senha")
                .clicarLogin();

        assertTrue(login.mensagemErroVisivel());
    }

    @Test public void cadastrarAtivo(){

    }

}