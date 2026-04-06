package org.example.selenium.test;

import org.example.selenium.core.BaseTest;
import org.example.selenium.pages.ListaDeAtivosPage;
import org.example.selenium.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

    public class ListarAtivosPageTest extends BaseTest {

        @BeforeEach
        void fazerLoginAntesDeCadaTeste() {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.fazerLogin("admin@sistema.com", "admin123");
        }

        @Test
        @DisplayName("Deve exibir tabela ou mensagem de nenhum item")
        void deveExibirTabela() {

            ListaDeAtivosPage listaDeAtivosPage = new ListaDeAtivosPage(driver);
            listaDeAtivosPage.abrir();

            String tabelaVisivel = listaDeAtivosPage.obterTitulo();

            assertEquals("Itens cadastrados", tabelaVisivel);
        }

        @Test
        @DisplayName("Deve abrir confirmação ao clicar em excluir")
        void deveAbrirConfirmacaoAoExcluir() {
            ListaDeAtivosPage listaDeAtivosPage = new ListaDeAtivosPage(driver);

            listaDeAtivosPage.abrir();

            assertTrue(listaDeAtivosPage.temBotaoExcluir(),
                    "O botão Excluir não foi encontrado na página.");

            listaDeAtivosPage.clicarPrimeiroExcluir();

            String texto = listaDeAtivosPage.obterTextoDoAlert();

            assertEquals("Tem certeza que deseja excluir este ativo?", texto);
            listaDeAtivosPage.cancelarExclusao();
        }

        @Test
        @DisplayName("Deve abrir a página de editar ao clicar no botão Editar")
        void deveVerificarPresencaDoBotaoEditar(){
            ListaDeAtivosPage listaDeAtivosPage = new ListaDeAtivosPage(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            listaDeAtivosPage.abrir();
            assertTrue(listaDeAtivosPage.temBotaoEditar(), "O botão Editar não foi encontrado na página.");

            listaDeAtivosPage.clicarPrimeiroEditar();

            wait.until(ExpectedConditions.urlContains("/theme/EditarAtivos/"));

            assertTrue(driver.getCurrentUrl().contains("/theme/EditarAtivos/"));
        }

    }

