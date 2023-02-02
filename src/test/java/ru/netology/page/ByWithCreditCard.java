package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

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

    public void sendValidDataWithApprovedCardNumber() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithDeclinedCardNumber() {
        cardNumber.setValue(DataHelper.getDeclinedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendEmptyRequest() {
        button.click();
    }

    public void sendValidDataWithoutCardNumber() {
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithoutMonth() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithoutYear() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithoutCardholder() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithoutCVC() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        button.click();
    }

    public void fillCardNumberWithText() {
        cardNumber.setValue(DataHelper.getNameOfCardholder());
    }

    public void fillCardNumberWithSymbols() {
        cardNumber.setValue(DataHelper.getTextOfSymbols());
    }

    public void sendValidDataWithShortCardNumber() {
        cardNumber.setValue(DataHelper.getShortCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void fillCardNumberWithLongCardNumber() {
        cardNumber.setValue(DataHelper.getLongCardNumber());
    }

    public void sendValidDataWithRandomCardNumber() {
        cardNumber.setValue(DataHelper.getRandomCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithInvalidCardNumber() {
        cardNumber.setValue(DataHelper.getInvalidCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void fillMonthWithText() {
        month.setValue(DataHelper.getNameOfCardholder());
    }

    public void fillMonthWithSymbols() {
        month.setValue(DataHelper.getTextOfSymbols());
    }

    public void sendValidDataWithInvalidMonthBelow() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getInvalidMonthBelow());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithInvalidMonthAbove() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getInvalidMonthAbove());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithInvalidMonthOf1Symbol() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getInvalidMonthOf1Symbol());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithPreviousDate() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonthFromPreviousDate());
        year.setValue(DataHelper.getYearFromPreviousDate());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void fillYearWithText() {
        year.setValue(DataHelper.getNameOfCardholder());
    }

    public void fillYearWithSymbols() {
        year.setValue(DataHelper.getTextOfSymbols());
    }

    public void sendValidDataWithPreviousYear() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getPreviousYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithNextYear() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getNextYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithDigitsInCardholder() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getCVC());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithSymbolsInCardholder() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getTextOfSymbols());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithTextInRussianInCardholder() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getTextInRussian());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithShortNameInCardholder() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getShortName());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void sendValidDataWithLongNameInCardholder() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getLongName());
        CVC.setValue(DataHelper.getCVC());
        button.click();
    }

    public void fillCVCWithText() {
        CVC.setValue(DataHelper.getNameOfCardholder());
    }

    public void fillCVCWithSymbols() {
        CVC.setValue(DataHelper.getTextOfSymbols());
    }

    public void sendValidDataWithCVCof1Digit() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVCOf1Digit());
        button.click();
    }

    public void sendValidDataWithCVCof2Digits() {
        cardNumber.setValue(DataHelper.getApprovedCardNumber());
        month.setValue(DataHelper.getMonth());
        year.setValue(DataHelper.getYear());
        cardholder.setValue(DataHelper.getNameOfCardholder());
        CVC.setValue(DataHelper.getCVCOf2Digits());
        button.click();
    }

    public String getCardNumberError() {
        return cardNumberError.getText().trim();
    }

    public void findCardNumberError() {
        cardNumberError.shouldNotBe(visible);
    }

    public String getMonthError() {
        return monthError.getText().trim();
    }

    public void findMonthError() {
        monthError.shouldNotBe(visible);
    }

    public String getYearError() {
        return yearError.getText().trim();
    }

    public void findYearError() {
        yearError.shouldNotBe(visible);
    }

    public String getCardholderError() {
        return cardholderError.getText().trim();
    }

    public void findCardHolderError() {
        cardholderError.shouldNotBe(visible);
    }

    public String getCVCError() {
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

