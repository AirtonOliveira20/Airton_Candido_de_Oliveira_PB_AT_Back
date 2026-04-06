package org.example.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CadastroAtivoPage extends BasePage {

    private final By campoStatus = By.name("statusItem");
    private final By campoNumeroPatrimonio = By.name("numeroPatrimonio");
    private final By campoNomeItem = By.name("nomeItem");
    private final By campoModelo = By.name("modelo");
    private final By campoNumeroSerie = By.name("numeroSerie");
    private final By campoVoltagem = By.name("voltagem");
    private final By campoNumRI = By.name("numRI");
    private final By campoFornecedor = By.name("idFornecedor");
    private final By campoDescricao = By.name("descricao");

    private final By botaoSalvar = By.xpath("//button[@type='submit' and contains(text(),'Salvar')]");
    private final By botaoLimpar = By.xpath("//button[@type='button' and contains(text(),'Limpar')]");

    public CadastroAtivoPage(WebDriver driver) {
        super(driver);
    }

    public CadastroAtivoPage abrir() {
        driver.get("http://localhost:3000/#/theme/CadastroAtivos");
        return this;
    }

    public CadastroAtivoPage selecionarStatus(String status) {
        Select select = new Select($(campoStatus));
        select.selectByVisibleText(status);
        return this;
    }

    public CadastroAtivoPage preencherNumeroPatrimonio(String numeroPatrimonio) {
        type(campoNumeroPatrimonio, numeroPatrimonio);
        return this;
    }

    public CadastroAtivoPage preencherNomeItem(String nomeItem) {
        type(campoNomeItem, nomeItem);
        return this;
    }

    public CadastroAtivoPage preencherModelo(String modelo) {
        type(campoModelo, modelo);
        return this;
    }

    public CadastroAtivoPage preencherNumeroSerie(String numeroSerie) {
        type(campoNumeroSerie, numeroSerie);
        return this;
    }

    public CadastroAtivoPage selecionarVoltagem(String voltagem) {
        Select select = new Select($(campoVoltagem));
        select.selectByVisibleText(voltagem);
        return this;
    }

    public CadastroAtivoPage preencherNumRI(String numRI) {
        type(campoNumRI, numRI);
        return this;
    }

    public CadastroAtivoPage selecionarFornecedor(String fornecedor) {
        Select select = new Select($(campoFornecedor));
        select.selectByVisibleText(fornecedor);
        return this;
    }

    public CadastroAtivoPage preencherDescricao(String descricao) {
        type(campoDescricao, descricao);
        return this;
    }

    public CadastroAtivoPage clicarSalvar() {
        click(botaoSalvar);
        return this;
    }

    public CadastroAtivoPage clicarLimpar() {
        click(botaoLimpar);
        return this;
    }

    public String obterValorNumeroPatrimonio() {
        return $(campoNumeroPatrimonio).getAttribute("value");
    }

    public String obterValorNomeItem() {
        return $(campoNomeItem).getAttribute("value");
    }

    public String obterValorModelo() {
        return $(campoModelo).getAttribute("value");
    }

    public String obterValorNumeroSerie() {
        return $(campoNumeroSerie).getAttribute("value");
    }

    public String obterValorNumRI() {
        return $(campoNumRI).getAttribute("value");
    }

    public String obterValorDescricao() {
        return $(campoDescricao).getAttribute("value");
    }

    public String obterStatusSelecionado() {
        Select select = new Select($(campoStatus));
        return select.getFirstSelectedOption().getText();
    }

    public String obterVoltagemSelecionada() {
        Select select = new Select($(campoVoltagem));
        return select.getFirstSelectedOption().getText();
    }

    public String obterFornecedorSelecionado() {
        Select select = new Select($(campoFornecedor));
        return select.getFirstSelectedOption().getText();
    }
}