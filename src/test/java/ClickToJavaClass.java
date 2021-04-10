import hooks.GetTextFromBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClickToJavaClass {
    @BeforeEach
    public void beforeTest(){
        open("http://localhost:8887/projector/");
    }
    @Test
    public void clickToJavaClass() {
        WebElement elementBody = $(byXpath("//body"));

        String answerConsole = """
                public class JavaClass {

                    public static void main(String[] args) {
                        System.out.println("projector: Hello, Java!");
                    }
                }
                """;

        Actions action = new Actions(getWebDriver());

        action.moveToElement(elementBody, -300, 43).click().build().perform(); // кликаем на окно java файла
        action.moveToElement(elementBody, 50, 100).click().build().perform(); // кликаем в область кода

        action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).build().perform();
        action.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).build().perform();

        String result = GetTextFromBuffer.get();

        assertEquals(result, answerConsole);
    }
}
