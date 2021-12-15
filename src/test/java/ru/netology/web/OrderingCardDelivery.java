package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.*;

public class OrderingCardDelivery {

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldActiveUserLogin() {
        DataGenerator.RegistrationDto registeredUser = getRegisteredUser("active");
        $("[data-test-id='login']  input").setValue(registeredUser.getLogin());
        $("[data-test-id='password']  input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldBe(visible).shouldHave(exactText("Личный кабинет"));
    }

    @Test
    void shouldBlockedUserLogin() {
        DataGenerator.RegistrationDto blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text(
                "Пользователь заблокирован"));
    }

    @Test
    void shouldNotRegisteredUserLogin() {
        DataGenerator.RegistrationDto newUser = getNewUser("active");
        $("[data-test-id='login'] input").setValue(newUser.getLogin());
        $("[data-test-id='password'] input").setValue(newUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text(
                "Неверно указан логин или пароль"));
    }

    @Test
    void shouldWrongPassword() {
        DataGenerator.RegistrationDto registeredUser = getRegisteredUser("active");
        String wrongPassword = DataGenerator.randomPass();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text(
                "Неверно указан логин или пароль"));
    }

    @Test
    void shouldLoginWithWrongLogin() {
        DataGenerator.RegistrationDto registeredUser = getRegisteredUser("active");
        String wrongLogin = DataGenerator.randomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldBe(visible).shouldHave(text(
                "Неверно указан логин или пароль"));
    }

    @Test
    void shouldWithoutLogin() {
        DataGenerator.RegistrationDto registeredUser = getRegisteredUser("active");
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='login'] .input__sub").shouldBe(visible).shouldHave(text(
                "Поле обязательно для заполнения"));
    }

    @Test
    void shouldWithoutPassword() {
        DataGenerator.RegistrationDto registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='password'] .input__sub").shouldBe(visible).shouldHave(text(
                "Поле обязательно для заполнения"));
    }
}

