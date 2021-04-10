import hooks.GetTextFromBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilationTest {
    @BeforeEach
    public void beforeTest(){
        open("http://localhost:8887/projector/");

    }
    @Test
    public void compilationTest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        WebElement ele = $(byXpath("//body"));

        String consoleOut = """
                /projector/ide/jbr/bin/java -javaagent:/projector/ide/lib/idea_rt.jar=41267:/projector/ide/bin -Dfile.encoding=UTF-8 -classpath /home/projector-user/DemoProject/out/production/DemoProject:/projector/ide/plugins/Kotlin/kotlinc/lib/kotlin-stdlib.jar:/projector/ide/plugins/Kotlin/kotlinc/lib/kotlin-reflect.jar:/projector/ide/plugins/Kotlin/kotlinc/lib/kotlin-test.jar:/projector/ide/plugins/Kotlin/kotlinc/lib/kotlin-stdlib-jdk7.jar:/projector/ide/plugins/Kotlin/kotlinc/lib/kotlin-stdlib-jdk8.jar KotlinObject
                projector: Hello, Kotlin!

                Process finished with exit code 0
                """;

        Actions action = new Actions(getWebDriver());
        action.moveToElement(ele, -180, 43).click().build().perform();

        action.keyDown(Keys.SHIFT).sendKeys(Keys.F10).keyUp(Keys.SHIFT).build().perform();
        TimeUnit.SECONDS.sleep(3);

        action.moveToElement(ele, 400, 600).click().build().perform();

        action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).build().perform();
        action.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).build().perform();

        String result = GetTextFromBuffer.get();

        assertEquals(consoleOut.substring(consoleOut.indexOf("\n")), result.substring(result.indexOf("\n")));
    }

}
