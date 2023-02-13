package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

public class ByWithCreditCard {
    private final SelenideElement heading = $(byText("Кредит по данным карты"));
    private final SelenideElement cardNumber = $(".input__control[placeholder=\"0000 0000 0000 0000\"]");
    private final SelenideElement month = $(".input__control[placeholder=\"08\"]");
    private final SelenideElement year = $(".input__control[placeholder=\"22\"]");
    private final SelenideElement cardholder = $$(".input").find(exactText("Владелец")).$(".input__control");
    private final SelenideElement CVC = $(".input__control[placeholder=\"999\"]");
    private final SelenideElement button = $$("button").find(exactText("Продолжить"));
    private final SelenideElement cardNumberError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    private final SelenideElement monthError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    private final SelenideElement yearError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    private final SelenideElement cardholderError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    private final SelenideElement CVCError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");
    private final SelenideElement notificationOK = $(".notification_status_ok");
    private final SelenideElement notificationERROR = $(".notification_status_error");

    public ByWithCreditCard() {
        heading.shouldBe(visible);
    }

    public void shouldFillFieldsAndSendRequest(String String1, String String2, String String3, String String4, String String5) {
        cardNumber.setValue(String1);
        month.setValue(String2);
        year.setValue(String3);
        cardholder.setValue(String4);
        CVC.setValue(String5);
        button.click();
    }

    public void shouldFillFields(String String1, String String2, String String3, String String4, String String5) {
        cardNumber.setValue(String1);
        month.setValue(String2);
        year.setValue(String3);
        cardholder.setValue(String4);
        CVC.setValue(String5);
    }

    public String getCardNumberError() {
        cardNumberError.shouldBe(visible);
        return cardNumberError.getText().trim();
    }

    public void findCardNumberError() {
        cardNumberError.shouldNotBe(visible);
    }

    public String getMonthError() {
        monthError.shouldBe(visible);
        return monthError.getText().trim();
    }

    public void findMonthError() {
        monthError.shouldNotBe(visible);
    }

    public String getYearError() {
        yearError.shouldBe(visible);
        return yearError.getText().trim();
    }

    public void findYearError() {
        yearError.shouldNotBe(visible);
    }

    public String getCardholderError() {
        cardholderError.shouldBe(visible);
        return cardholderError.getText().trim();
    }

    public void findCardHolderError() {
        cardholderError.shouldNotBe(visible);
    }

    public String getCVCError() {
        CVCError.shouldBe(visible);
        return CVCError.getText().trim();
    }

    public void findCVCError() {
        CVCError.shouldNotBe(visible);
    }

    public String getValueFromCardNumber() {
        return cardNumber.getValue();
    }

    public String getValueFromMonth() {
        return month.getValue();
    }

    public String getValueFromYear() {
        return year.getValue();
    }

    public String getValueFromCVC() {
        return CVC.getValue();
    }

    public void waitForNotificationOK() {
        notificationOK.shouldBe(visible, Duration.ofSeconds(10));
    }

    public void waitForNotificationERROR() {
        notificationERROR.shouldBe(visible, Duration.ofSeconds(10));
    }
}

