import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FacebookTest {

    private static final String URL = "https://www.newegg.com/NVIDIA/BrandStore/ID-1441";

    public static void main(String[] args) {
        WebDriver driver;
        String projectPath = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", projectPath + "/src/test/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(URL);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

        WebElement centerPopUp = driver.findElement(By.xpath("//*[@class='centerPopup-body']"));
        System.out.println(centerPopUp.isDisplayed());

        driver.findElement(By.xpath("//*[text()='North America']")).click();
        driver.findElement(By.xpath("//*[text()='United States']")).click();
        driver.findElement(By.xpath("//button[text()='Stay at United States']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.id("tb_id109")).click();
        int countOfProducts = driver.findElements(By.xpath("//*[@class='pageItemContainer']")).size();
        System.out.println(countOfProducts);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,350)", "");

       for(int i=1;i<5;i++){
            try
            {

                String productName =driver.findElement(By.xpath("(//*[@class=\"itemTitle\"])["+i+"]")).getText();

                System.out.println("Product Name in the first row: " + productName);

                String price =driver.findElement(By.xpath("(//*[@class=\"itemPrice\"])["+i+"]")).getText();
                System.out.println("Unit price of the items in the first row" + price);

            }

            catch(NoSuchElementException e)
            {
                e.printStackTrace();
            }

        }
        String firstPrice = driver.findElement(By.xpath("(//*[@class=\"itemPrice\"])[1]")).getText();

        System.out.println("Price in main Window" + firstPrice);

        driver.findElement(By.xpath("(//*[@class=\"buton btnBrand\"])[1]")).click();
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

        Set<String> allWindowHandles = driver.getWindowHandles();
        System.out.println("Handles " + allWindowHandles);
        for (String handle : allWindowHandles) {
            System.out.println("Switching to window - > " + handle);
            try {

                driver.switchTo().window(handle);
            }
            catch(NoSuchWindowException e) {
                e.printStackTrace();
            }
        }
        WebElement text = driver.findElement(By.xpath("(//*[@class=\"price-current\"])[1]"));

        String detailedPrice = text.getText();
        System.out.println("Price in detailed session " + detailedPrice);
        if (detailedPrice.equalsIgnoreCase(firstPrice)) {
            System.out.println("Price in child window is matching with product list ");
        } else {
            System.out.println("Price in child window is not matching with product list ");
        }
        driver.close();
        driver.quit();
    }

}







