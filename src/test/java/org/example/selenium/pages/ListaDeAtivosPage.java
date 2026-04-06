package org.example.selenium.pages;

import org.example.selenium.core.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

public class ListaDeAtivosPage extends BasePage{

    private final By titulo = By.xpath("//*[contains(text(),'Itens cadastrados')]");
    private final By botaoEditar = By.xpath("//button[contains(text(),'Editar')]");
    private final By botaoExcluir = By.xpath("//button[contains(text(),'Excluir')]");


    public ListaDeAtivosPage(WebDriver driver) {
        super(driver);
    }

    public ListaDeAtivosPage abrir() {
        driver.get("http://localhost:3000/#/theme/ativos");
        wait.until(ExpectedConditions.visibilityOfElementLocated(titulo));
        return this;
    }

    public String obterTitulo() {
        return $(titulo).getText();
    }

    public boolean temBotaoEditar() {
        return !driver.findElements(botaoEditar).isEmpty();
    }

    public boolean temBotaoExcluir() {
        return !driver.findElements(botaoExcluir).isEmpty();
    }

    public void clicarPrimeiroEditar() {
        click(botaoEditar);
    }

    public void clicarPrimeiroExcluir() {
        click(botaoExcluir);
    }

    public String obterTextoDoAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    public void cancelarExclusao() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }
}