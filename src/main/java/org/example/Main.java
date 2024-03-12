package org.example;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Main {
    private AndroidDriver<AndroidElement> androidDriver;
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    private static final String SETTINGS_BUTTON = "settings_button";
    private static final String MAIN = "__1346498178_container";

    @When( "browserstack test 1" )
    public void browserstackTest1() {
        startBrowserstack( getTest1Capabilities() );
        openApp();
        login();

        webDriver.findElement( By.id( "__2093277894" ) ).click();
    }

    @When( "browserstack test 2" )
    public void browserstackTest2() {
        startBrowserstack( getTest2Capabilities() );
        openApp();
        login();

        webDriver.findElement( By.id( "__2093277894" ) ).click();
    }

    public DesiredCapabilities getDefaultCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();

        // browserstack user
        caps.setCapability( "browserstack.user", "username" );
        caps.setCapability( "browserstack.key", "password" );

        // android app
        caps.setCapability("app", "bs://025be4fdcaeeba6a98c044cdee5207793844a396");

        return caps;
    }

    public DesiredCapabilities getTest1Capabilities() {
        DesiredCapabilities caps = getDefaultCapabilities();

        // device
        caps.setCapability( "device", "Samsung Galaxy S23" );
        caps.setCapability( "os_version", "13" );

        // Set other BrowserStack capabilities
        caps.setCapability("project", "browserstack test");
        caps.setCapability("build", "test1");
        caps.setCapability("name", "test1");
        caps.setCapability("browserstack.networkLogs", "true");

        caps.setCapability("browserstack.appium_version", "1.22.0");
        caps.setCapability("browserstack.debug", "true");
        caps.setCapability("appium.ensureWebviewsHavePages", "true");

        return caps;
    }

    public DesiredCapabilities getTest2Capabilities() {
        DesiredCapabilities caps = getDefaultCapabilities();

        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("projectName", "browserstack test");
        browserstackOptions.put("buildName", "Web view build");
        browserstackOptions.put("sessionName", "test2");
        browserstackOptions.put("appiumVersion", "2.4.1");

        caps.setCapability("bstack:options", browserstackOptions);
        caps.setCapability("platformName", "android");
        caps.setCapability("platformVersion", "13.0");
        caps.setCapability("deviceName", "Samsung Galaxy S23");

        return caps;
    }

    protected void startBrowserstack( DesiredCapabilities caps ) {
        // Initialise the remote Webdriver using BrowserStack remote URL
        // and desired capabilities defined above
        try
        {
            // proxy is needed internally
            initProxy();
            androidDriver = new AndroidDriver<>( new URL("http://hub.browserstack.com/wd/hub"), caps );
            webDriver = androidDriver;
            webDriverWait = new WebDriverWait( webDriver, 10 );
            removeProxy();
        }
        catch( MalformedURLException e )
        {
            e.printStackTrace();
        }
    }

    protected void initProxy()
    {
        System.setProperty("https.proxyHost", "62.192.194.114");
        System.setProperty("https.proxyPort", "8080");
        System.setProperty("http.proxyHost", "62.192.194.114");
        System.setProperty("http.proxyPort", "8080");
    }

    protected void removeProxy()
    {
        System.setProperty("https.proxyHost", "");
        System.setProperty("https.proxyPort", "");
        System.setProperty("http.proxyHost", "");
        System.setProperty("http.proxyPort", "");
    }

    protected void openApp() {
        setTestSystem();
    }

    protected void setTestSystem() {
        waitAnimation( 1000 );
        webDriver.findElement(By.xpath("//*[@text='flatex Deutschland']")).click();
        waitAnimation();

        waitUntilPresent( By.id( SETTINGS_BUTTON ) );
        androidSetUat();

        waitUntilPresent( By.xpath( "//*[@text='Login' or @name='Login']" ) );
        waitAnimation();
        webDriver.findElement( By.xpath( "//*[@text='Login']" ) ).click();
        waitAnimation();

        waitUntilPresent( By.xpath( new Node().id( "customer_input" ).toString() ) );
        waitAnimation();

        WebElement env = webDriver.findElement(By.id( "env" ));
        Assert.assertEquals( "UAT", env.getText() );
    }

    private void androidSetUat() {
        webDriver.findElement( By.id( SETTINGS_BUTTON ) ).click();
        waitAnimation();

        webDriver.findElement( By.xpath( "//*[@resource-id='de.xcom.flatexde:id/action_bar']" ) );

        waitAnimation();
        waitUntilPresent( By.xpath("//*[@text='Anwendungscache einmalig leeren']") );
        waitAnimation();
        webDriver.findElement(By.xpath("//*[@text='Fehlerdiagnose']")).click();
        waitAnimation();
        waitUntilPresent( By.id("custom") );
        webDriver.findElement( By.xpath("//*[@text='Bitte einen Wert zur Diagnose eingeben']") );

        var action = new Actions( webDriver );
        action.sendKeys( "test:uat" );
        action.perform();

        waitAnimation();
        webDriver.findElement(By.xpath("//*[@text='OK']")).click();
        waitAnimation();
        webDriver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up' or @content-desc='Nach oben']")).click();
        waitAnimation();

        waitUntilPresent( By.id(SETTINGS_BUTTON) );
    }

    private void login() {
        webDriver.findElement(By.xpath( new Node().id( "customer_input" ).toString())).sendKeys( "10000559" );
        webDriver.findElement(By.xpath( new Node().id( "password_input" ).toString())).sendKeys( "toyed-vHUE5gJ" );
        androidDriver.hideKeyboard();
        waitAnimation();

        webDriver.findElement(By.xpath( new Node().id( "loginButton" ).toString())).click();

        waitUntilPresent( By.xpath(new Node().id( MAIN ).toString() ), 60 );
        waitAnimation( 1000 );

        System.out.println( androidDriver.getContextHandles() );
        androidDriver.context( "WEBVIEW_de.xcom.flatexde" );

        androidDriver.switchTo().window( androidDriver.getWindowHandles().toArray()[1].toString() );
    }

    public void waitAnimation() {
        waitAnimation( 500 );
    }

    public void waitAnimation(int miliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep( miliseconds );
        } catch( InterruptedException e ) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void waitUntilPresent( By by ) {
        waitUntilPresent( by, 10, true );
    }

    public void waitUntilPresent( By by, int seconds, boolean withException ) {
        if( webDriverWait == null )
            throw new IllegalArgumentException("webDriverWait not initialized");
        int polling = 100;
        if( seconds > 30 )
            polling = 500;
        try {
            webDriverWait
                    .withTimeout(Duration.ofSeconds(seconds))
                    .pollingEvery(Duration.ofMillis(polling))
                    .until(ExpectedConditions.presenceOfElementLocated(by));
        } catch ( TimeoutException te ) {
            if( withException )
                throw te;
        }
    }

    public void waitUntilPresent(By by, int seconds ) {
        waitUntilPresent( by, seconds, true );
    }
}