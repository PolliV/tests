import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
//import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyles.modifiers;


public class Bebebe {
    private static Playwright play1;
    private static Browser br1;
    private static Page page1;

    @BeforeEach
    public void enterTo() {
        play1 = Playwright.create();
        br1 = play1.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page1 = br1.newPage();
        page1.navigate("https://nnov.kamni-furnitura.ru/");
    }

    @AfterEach
    public void exitUp() {
        page1.close();
        br1.close();
        play1.close();
    }

    @Test
    @DisplayName("Вход в аккаунт")
    public void entry_acc(){
        //видимость кнопки Войти и клик по ней
        assertThat(page1.getByTitle("Войти")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(500));
        page1.getByTitle("Войти").click();

        //видимость и редактируемость поля ввода email или номера телефона
        assertThat(page1.getByPlaceholder("Email или номер телефона")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(500));
        assertThat(page1.getByPlaceholder("Email или номер телефона")).isEditable();

        //ввод номера телефона
        page1.getByPlaceholder("Email или номер телефона").fill("89101046073");

        //видимость и редактируемость поля ввода пароля
        assertThat(page1.getByPlaceholder("Пароль")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(500));
        assertThat(page1.getByPlaceholder("Пароль")).isEditable();

        //ввод пароля
        page1.getByPlaceholder("Пароль").fill("qwerty");

        //видимость конпки войти и клик по ней
        assertThat(page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Войти"))).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(500));
        page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Войти")).click();

        //видимость названия аккаунта
        assertThat(page1.getByText("Polli")).isVisible();
    }

    @Test
    @DisplayName("Просмотр каталога")
    public void apr_catalog(){
        //видимость и нажатие на кнопку каталога
        assertThat(page1.locator("#pages-nav").getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Каталог"))).isVisible();
        page1.locator("#pages-nav").getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Каталог")).click();

        //видимость и нажатие на категорию каталога
        assertThat(page1.getByTitle("Фурнитура для украшений")).isVisible();
        page1.getByTitle("Фурнитура для украшений").click();

        //видимость товара
        assertThat(page1.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Подвеска Луна размер 18,7")).first()).isVisible();
        //page1.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Подвеска Луна размер 18,7")).first().click();
    }

    @Test
    @DisplayName("Добавление товара в корзину")
    public void add_basket(){
        //просмотр каталога
        apr_catalog();

        //фокусировка и нажатие на кнопку купить у товара
        page1.getByRole(AriaRole.ARTICLE).filter(new Locator.FilterOptions().setHasText("Новинка Подвеска Луна размер 18,7мм цвет золото 180")).getByLabel("Купить").focus();
        page1.getByRole(AriaRole.ARTICLE).filter(new Locator.FilterOptions().setHasText("Новинка Подвеска Луна размер 18,7мм цвет золото 180")).getByLabel("Купить").click();

        //видимость и нажатие на корзину
        assertThat(page1.getByTitle("Корзина")).isVisible();
        page1.getByTitle("Корзина").click();

        //наличие товара в корзине
        assertThat(page1.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("Подвеска Луна размер 18,7мм цвет золото Золотой x1 180 руб")).getByRole(AriaRole.LINK)).isVisible();
    }

    @Test
    @DisplayName("Поиск по товарам")
    public void filter_cost(){
        //видимость поля поиска товара
        assertThat(page1.getByPlaceholder("Поиск по товарам...")).isVisible();

        //ввод названия товара в поле поиска и нажатие Enter
        page1.getByPlaceholder("Поиск по товарам...").fill("Подвеска Луна размер 18,7");
        page1.getByPlaceholder("Поиск по товарам...").press("Enter");

        //видимость искомого товара
        assertThat(page1.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Подвеска Луна размер 18,7")).first()).isVisible();
    }

    @Test
    @DisplayName("Выход из аккаунта")
    public void exit_acc(){
        //вход в аккаунт
        entry_acc();

        //видимость и нажатие на кнопку выйти
        assertThat(page1.getByTitle("Выйти")).isVisible();
        page1.getByTitle("Выйти").click();

        //проверка на успешный выход (наличие кнопки вход)
        assertThat(page1.getByTitle("Войти")).isVisible();
    }

}

