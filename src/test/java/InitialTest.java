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

public class InitialTest {
    @BeforeEach
    public void beforeTest(){
        open("http://localhost:8887/projector/");
    }
    @Test
    public void initialTest() {
        WebElement ele = $(byXpath("//body"));

        String code = """
                object KotlinObject {

                    @JvmStatic
                    fun main(vararg args: String) {
                        println("projector: Hello, Kotlin!")
                    }
                }
                """;

        Actions action = new Actions(getWebDriver());

        action.moveToElement(ele, -180, 43).click().build().perform(); // кликаем на окно kotlin файла
        action.moveToElement(ele, 50, 100).click().build().perform(); // кликаем в область кода

        action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).build().perform();
        action.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).build().perform();

        String result = GetTextFromBuffer.get();

        assertEquals(result, code);
    }
}
