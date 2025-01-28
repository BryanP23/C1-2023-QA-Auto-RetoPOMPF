package com.sofkau.stepdefinitions;

import com.sofkau.models.Facturacion_Envio;
import com.sofkau.pages.FormPage;
import com.sofkau.setup.WebUI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Assertions;
import com.sofkau.pages.FormPage.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.sofkau.setup.ConstantSetup.ZonaFitUrl;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlujoDeCompraStepDefinition extends WebUI {
    Facturacion_Envio facturacion_envio = new Facturacion_Envio();
    public static Logger LOGGER = Logger.getLogger(IniciarSesionOutlineStepDefinition.class);
    @Given("estoy en la pagina principal de ZonaFit usando {string}")
    public void estoyEnLaPaginaPrincipalDeZonaFitUsando(String navegador) {
        try {
            if (navegador.equals("chrome")){
                LOGGER.info("Iniciando navegador Chrome...");
                generalSetUpChrome();
                LOGGER.info("Navegador Chrome iniciado.");
            } else if (navegador.equals("edge")) {
                LOGGER.info("Iniciando navegador Edge...");
                generalSetUpEdge();
                LOGGER.info("Navegador Edge iniciado.");
            }
        } catch (Exception exception){
            quiteDriver();
            LOGGER.error("Error al inicializar el navegador: " + exception.getMessage());
            Assertions.fail("Error en la inicialización del navegador: " + exception.getMessage());
        }

        FormPage formPage = new FormPage(super.driver, facturacion_envio);
        LOGGER.info("Formulario inicializado correctamente.");
    }
    private void setUpWebdriverGoogle() {
        try {
            ChromeOptions co = new ChromeOptions();
            co.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(co);
            driver.get(ZonaFitUrl);
            maximize();
            LOGGER.info("Navegador Chrome inicializado correctamente.");
        } catch (Exception e) {
            LOGGER.error("Error al inicializar el navegador Chrome: " + e.getMessage());
            throw e;  // Re-lanzar la excepción para que se maneje en el bloque catch
        }
    }
    protected void quiteDriver() {
        try {
            if (driver != null) {
                driver.quit();
                LOGGER.info("Navegador cerrado correctamente.");
            } else {
                LOGGER.warn("El driver es null, no se puede cerrar.");
            }
        } catch (Exception e) {
            LOGGER.error("Error al intentar cerrar el navegador: " + e.getMessage());
        }
    }

    @When("selecciono y anado productos al carrito de compras")
    public void seleccionoYAnadoProductosAlCarritoDeCompras() {
        FormPage formPage = new FormPage(super.driver,facturacion_envio);
        formPage.DatosFacturacionn();
        formPage.agregar();
        formPage.facturacion();

    }
    @When("ingreso mis datos personales y el metodo de pago")
    public void ingresoMisDatosPersonalesYElMetodoDePago() {


    }
    @Then("debo ver un mensaje de confirmacion de compra exitosa.")
    public void deboVerUnMensajeDeConfirmacionDeCompraExitosa() {
        try {

            String MensajeEsperado="Gracias. Tu pedido ha sido recibido.";
            FormPage formPage = new FormPage(super.driver,facturacion_envio);
            String Mensajeactual= formPage.getMensajeFinal(driver).getText();

            assertEquals(MensajeEsperado,Mensajeactual);
            LOGGER.info("mensaje esperado:"+MensajeEsperado);
            LOGGER.info("mensaje actual:"+Mensajeactual);


        }
        catch (Exception exception) {
            quiteDriver();
            Assertions.fail(exception.getMessage(), exception);
            LOGGER.warn(exception.getMessage(), exception);
            LOGGER.error("la asercion no cumple");
        }

    }

}