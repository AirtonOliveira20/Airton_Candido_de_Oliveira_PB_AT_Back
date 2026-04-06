package org.example.selenium.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CadastroAtivoPage extends BasePage {

    private final By campoItem = By.name("item");
    private final By campoFabricante = By.name("fabricante");
    private final By campoModelo = By.name("modelo");
    private final By campoSerial = By.name("serial");
    private final By campoNotaFiscal = By.name("notaFiscal");
    private final By campoValor = By.name("valor");
    private final By campoFornecedor = By.name("fornecedor");
    private final By campoDataEmissao = By.name("dataEmissao");

    private final By botaoSalvar = By.cssSelector("button[type='submit']");
    private final By mensagemSucesso = By.className("alert");
    private final By tabelaAtivos = By.tagName("table");

    public CadastroAtivoPage(WebDriver driver) {
        super(driver);
    }

    public CadastroAtivoPage abrir() {
        driver.get("http://localhost:3000/#/theme/CadastroAtivos");
        return this;
    }

    public CadastroAtivoPage preencherItem(String item) {
        type(campoItem, item);
        return this;
    }

    public CadastroAtivoPage preencherFabricante(String fabricante) {
        type(campoFabricante, fabricante);
        return this;
    }

    public CadastroAtivoPage preencherModelo(String modelo) {
        type(campoModelo, modelo);
        return this;
    }

    public CadastroAtivoPage preencherSerial(String serial) {
        type(campoSerial, serial);
        return this;
    }

    public CadastroAtivoPage preencherNotaFiscal(String notaFiscal) {
        type(campoNotaFiscal, notaFiscal);
        return this;
    }

    public CadastroAtivoPage preencherValor(String valor) {
        type(campoValor, valor);
        return this;
    }

    public CadastroAtivoPage preencherFornecedor(String fornecedor) {
        type(campoFornecedor, fornecedor);
        return this;
    }

    public CadastroAtivoPage preencherDataEmissao(String dataEmissao) {
        type(campoDataEmissao, dataEmissao);
        return this;
    }

    public CadastroAtivoPage clicarSalvar() {
        click(botaoSalvar);
        return this;
    }

    public boolean mensagemSucessoVisivel() {
        return $(mensagemSucesso).isDisplayed();
    }

    public String obterMensagemSucesso() {
        return $(mensagemSucesso).getText();
    }

    public boolean foiParaListaDeAtivos() {
        return driver.getCurrentUrl().contains("/theme/ativos");
    }

    public boolean tabelaVisivel() {
        return $(tabelaAtivos).isDisplayed();
    }
}