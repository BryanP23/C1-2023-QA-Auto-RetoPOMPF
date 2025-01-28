package com.sofkau.setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.core.config.Property;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import static com.google.common.base.StandardSystemProperty.USER_DIR;
import static com.sofkau.setup.ConstantSetup.*;
import static com.sofkau.util.Log4j.LOG4J_PROPERTIES_FILE_PATH;

public class WebUI {

    protected WebDriver driver;

    // Configura WebDriverManager para Chrome y Edge
    private void setUpWebDriver() {
        try {
            WebDriverManager.chromedriver().setup();
            WebDriverManager.edgedriver().setup();
        } catch (Exception e) {
            System.out.println("Error al configurar WebDriverManager: " + e.getMessage());
            throw e;  // Lanza el error para que se pueda manejar en el lugar correspondiente
        }
    }

    // Configura el WebDriver para Chrome
    private void setUpWebdriverGoogle() {
        try {
            ChromeOptions co = new ChromeOptions();
            co.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(co);
            driver.get(ZonaFitUrl);
            maximize();
        } catch (Exception e) {
            System.out.println("Error al inicializar el WebDriver de Chrome: " + e.getMessage());
            throw e;
        }
    }

    // Configura el WebDriver para Edge
    private void setUpWebdriverEdge() {
        try {
            EdgeOptions co = new EdgeOptions();
            co.addArguments("--remote-allow-origins=*");
            driver = new EdgeDriver(co);
            driver.get(ZonaFitUrl);
            maximize();
        } catch (Exception e) {
            System.out.println("Error al inicializar el WebDriver de Edge: " + e.getMessage());
            throw e;
        }
    }

    // Configuración general para Chrome
    protected void generalSetUpChrome() {
        setUplog4j();
        setUpWebDriver();  // Configura los controladores
        setUpWebdriverGoogle();  // Inicializa el driver de Chrome
    }

    // Configuración general para Edge
    protected void generalSetUpEdge() {
        setUplog4j();
        setUpWebDriver();  // Configura los controladores
        setUpWebdriverEdge();  // Inicializa el driver de Edge
    }

    // Cierra el driver
    protected void quiteDriver() {
        if (driver != null) {
            driver.quit();
        } else {
            System.out.println("El driver no está inicializado.");
        }
    }

    // Maximiza la ventana del navegador
    protected void maximize() {
        if (driver != null) {
            driver.manage().window().maximize();
        }
    }

    // Configura Log4j
    private void setUplog4j() {
        PropertyConfigurator.configure(USER_DIR.value() + LOG4J_PROPERTIES_FILE_PATH.getValue());
    }
}