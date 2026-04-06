package org.example.selenium.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By campoEmail = By.cssSelector("input[type='email']");
    private final By campoSenha = By.cssSelector("input[type='password']");
    private final By botaoLogin = By.cssSelector("button[type='submit']");
    private final By mensagemErro = By.className("alert");
    private final By dashboard = By.xpath("//*[contains(text(),'Dashboard')]");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public LoginPage abrir(){
        driver.get("http://localhost:3000/#/login");
        return this;
    }

    public LoginPage preencherEmail(String email){
        type(campoEmail, email);
        return this;
    }

    public LoginPage preencherSenha(String senha){
        type(campoSenha, senha);
        return this;
    }

    public LoginPage clicarLogin(){
        click(botaoLogin);
        return this;
    }

    public boolean loginComSucesso(){
        return $(dashboard).isDisplayed();
    }

    public boolean mensagemErroVisivel(){
        return $(mensagemErro).isDisplayed();
    }
}