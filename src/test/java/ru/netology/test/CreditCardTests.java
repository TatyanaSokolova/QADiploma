package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataBaseHelper;
import org.junit.jupiter.api.Test;
import ru.netology.page.ByWithCreditCard;
import ru.netology.page.OfferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class CreditCardTests {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @AfterEach
    void teardown() {
        DataBaseHelper.cleanTables();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Order(1)
    @Test
    public void shouldAcceptPurchaseWithApprovedCard() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithApprovedCardNumber();
        buy.waitForNotificationOK();
        assertEquals("APPROVED", DataBaseHelper.getCreditStatus());
    }

    @Order(2)
    @Test
    public void shouldDenyPurchaseWithDeclinedCard() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithDeclinedCardNumber();
        buy.waitForNotificationERROR();
        assertEquals("DECLINED", DataBaseHelper.getCreditStatus());
    }

    @Order(3)
    @Test
    public void shouldBeErrorsAfterSendingEmptyRequest() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendEmptyRequest();
        assertEquals("Неверный формат", buy.getCardNumberError());
        assertEquals("Неверный формат", buy.getMonthError());
        assertEquals("Неверный формат", buy.getYearError());
        assertEquals("Поле обязательно для заполнения", buy.getCardholderError());
        assertEquals("Неверный формат", buy.getCVCError());
    }

    @Order(4)
    @Test
    public void shouldBeErrorAfterSendingRequestWithoutCardNumber() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithoutCardNumber();
        buy.findMonthError();
        buy.findYearError();
        buy.findCardHolderError();
        buy.findCVCError();
        assertEquals("Неверный формат", buy.getCardNumberError());
    }

    @Order(5)
    @Test
    public void shouldBeErrorAfterSendingRequestWithoutMonth() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithoutMonth();
        buy.findCardNumberError();
        buy.findYearError();
        buy.findCardHolderError();
        buy.findCVCError();
        assertEquals("Неверный формат", buy.getMonthError());
    }

    @Order(6)
    @Test
    public void shouldBeErrorAfterSendingRequestWithoutYear() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithoutYear();
        buy.findCardNumberError();
        buy.findMonthError();
        buy.findCardHolderError();
        buy.findCVCError();
        assertEquals("Неверный формат", buy.getYearError());
    }

    @Order(7)
    @Test
    public void shouldBeErrorAfterSendingRequestWithoutCardHolder() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithoutCardholder();
        buy.findCardNumberError();
        buy.findMonthError();
        buy.findYearError();
        buy.findCVCError();
        assertEquals("Поле обязательно для заполнения", buy.getCardholderError());
    }

    @Order(8)
    @Test
    public void shouldBeErrorAfterSendingRequestWithoutCVC() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithoutCVC();
        buy.findCardNumberError();
        buy.findMonthError();
        buy.findYearError();
        buy.findCardHolderError();
        assertEquals("Неверный формат", buy.getCVCError());
    }

    @Order(9)
    @Test
    public void shouldNotAcceptLettersInCardNumberField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.fillCardNumberWithText();
        assertEquals("", buy.getValueFromCardNumber());
    }

    @Order(10)
    @Test
    public void shouldNotAcceptSymbolsInCardNumberField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.fillCardNumberWithSymbols();
        assertEquals("", buy.getValueFromCardNumber());
    }

    @Order(11)
    @Test
    public void shouldBeErrorAfterSendingRequestWithShortCardNumber() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithShortCardNumber();
        buy.findMonthError();
        buy.findYearError();
        buy.findCardHolderError();
        buy.findCVCError();
        assertEquals("Неверный формат", buy.getCardNumberError());
    }

    @Order(12)
    @Test
    public void shouldFillFieldCardNumberMax16Digits() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.fillCardNumberWithLongCardNumber();
        assertEquals("4444 4444 4444 4441", buy.getValueFromCardNumber());
    }

    @Order(13)
    @Test
    public void shouldBeErrorNotificationAfterSendingRequestWithRandomCardNumber() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithRandomCardNumber();
        buy.waitForNotificationERROR();
    }

    @Order(14)
    @Test
    public void shouldBeErrorAfterSendingRequestWithInvalidCardNumber() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithInvalidCardNumber();
        buy.findMonthError();
        buy.findYearError();
        buy.findCardHolderError();
        buy.findCVCError();
        assertEquals("Неверный формат", buy.getCardNumberError());
    }

    @Order(15)
    @Test
    public void shouldNotAcceptLettersInMonthField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.fillMonthWithText();
        assertEquals("", buy.getValueFromMonth());
    }

    @Order(16)
    @Test
    public void shouldNotAcceptSymbolsInMonthField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.fillMonthWithSymbols();
        assertEquals("", buy.getValueFromMonth());
    }

    @Order(17)
    @Test
    public void shouldBeErrorAfterSendingRequestWithInvalidMothBelow() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithInvalidMonthBelow();
        assertEquals("Неверно указан срок действия карты", buy.getMonthError());
    }

    @Order(18)
    @Test
    public void shouldBeErrorAfterSendingRequestWithInvalidMothAbove() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithInvalidMonthAbove();
        assertEquals("Неверно указан срок действия карты", buy.getMonthError());
    }

    @Order(19)
    @Test
    public void shouldBeErrorAfterSendingRequestWithInvalidMothOf1Digit() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithInvalidMonthOf1Symbol();
        assertEquals("Неверный формат", buy.getMonthError());
    }

    @Order(20)
    @Test
    public void shouldBeErrorAfterSendingRequestWithPreviousDate() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithPreviousDate();
        assertEquals("Неверно указан срок действия карты", buy.getMonthError());
    }

    @Order(21)
    @Test
    public void shouldNotAcceptLettersInYearField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.fillYearWithText();
        assertEquals("", buy.getValueFromYear());
    }

    @Order(22)
    @Test
    public void shouldNotAcceptSymbolsInYearField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.fillYearWithSymbols();
        assertEquals("", buy.getValueFromYear());
    }

    @Order(23)
    @Test
    public void shouldBeErrorAfterSendingRequestWithPreviousYear() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithPreviousYear();
        assertEquals("Истёк срок действия карты", buy.getYearError());
    }

    @Order(24)
    @Test
    public void shouldBeErrorAfterSendingRequestWithNextYear() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithNextYear();
        assertEquals("Неверно указан срок действия карты", buy.getYearError());
    }

    @Order(25)
    @Test
    public void shouldBeErrorAfterSendingRequestWithDigitsInCardholderField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithDigitsInCardholder();
        assertEquals("Поле должно содержать латинские буквы, допустимы дефис и пробел", buy.getCardholderError());
    }

    @Order(26)
    @Test
    public void shouldBeErrorAfterSendingRequestWithSymbolsInCardholderField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithSymbolsInCardholder();
        assertEquals("Поле должно содержать латинские буквы, допустимы дефис и пробел", buy.getCardholderError());
    }

    @Order(27)
    @Test
    public void shouldBeErrorAfterSendingRequestWithTextInRussianInCardholderField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithTextInRussianInCardholder();
        assertEquals("Поле должно содержать латинские буквы, допустимы дефис и пробел", buy.getCardholderError());
    }

    @Order(28)
    @Test
    public void shouldBeErrorAfterSendingRequestWithShortNameInCardholderField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithShortNameInCardholder();
        assertEquals("Введите данные в диапазоне от 4 до 60 символов", buy.getCardholderError());
    }

    @Order(29)
    @Test
    public void shouldBeErrorAfterSendingRequestWithLongNameInCardholderField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithLongNameInCardholder();
        assertEquals("Введите данные в диапазоне от 4 до 60 символов", buy.getCardholderError());
    }

    @Order(30)
    @Test
    public void shouldNotAcceptLettersInCVCField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.fillCVCWithText();
        assertEquals("", buy.getValueFromCVC());
    }

    @Order(31)
    @Test
    public void shouldNotAcceptSymbolsInCVCField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.fillCVCWithSymbols();
        assertEquals("", buy.getValueFromCVC());
    }

    @Order(32)
    @Test
    public void shouldBeErrorAfterSendingRequestWithCVCOf1Digit() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithCVCof1Digit();
        assertEquals("Неверный формат", buy.getCVCError());
    }

    @Order(33)
    @Test
    public void shouldBeErrorAfterSendingRequestWithCVCOf2Digits() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.sendValidDataWithCVCof2Digits();
        assertEquals("Неверный формат", buy.getCVCError());
    }
}
