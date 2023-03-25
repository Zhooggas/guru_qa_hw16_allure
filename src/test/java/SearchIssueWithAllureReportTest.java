import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class SearchIssueWithAllureReportTest {

    private static final String REPOSITORY = "eleonoraart/guru_qa_hw16_allure";
    private static final String ISSUE = "Issue for hw16. Allure report";
    Steps step = new Steps();

    @Test
    public void searchIssueWithSelenideOnlyTest(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");

        $(".header-search-input").click();
        $(".header-search-input").sendKeys("eleonoraart/guru_qa_hw16_allure");
        $(".header-search-input").submit();

        $(linkText("eleonoraart/guru_qa_hw16_allure")).click();
        $("#issues-tab").click();
        $(withText("Issue for hw16. Allure report")).should(Condition.exist);
    }

    @Test
    public void searchIssueWithLambdaStep(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY);
            $(".header-search-input").submit();
        });
        step("Кликаем по ссылке репозитория " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Открываем таб Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем наличие Issue с названием " + ISSUE, () -> {
            $(withText(ISSUE)).should(Condition.exist);
        });
    }

    @Test
    public void searchIssueWithStepAnnotationTest(){

        SelenideLogger.addListener("allure", new AllureSelenide());

        step.openMainPage()
                .searchForRepository(REPOSITORY)
                .clickOnRepositoryLink(REPOSITORY)
                .openIssuesTab()
                .shouldSeeIssueWithName(ISSUE);

    }
}
