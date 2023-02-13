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
import static ru.netology.data.DataBaseHelper.getCreditStatus;
import static ru.netology.data.DataHelper.*;

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

    @Test
    public void shouldAcceptPurchaseWithApprovedCard() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getYear(), getNameOfCardholder(), getCVC());
        buy.waitForNotificationOK();
        assertEquals("APPROVED", getCreditStatus());
    }

    @Test
    public void shouldDenyPurchaseWithDeclinedCard() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getDeclinedCardNumber(), getMonth(), getYear(), getNameOfCardholder(), getCVC());
        buy.waitForNotificationERROR();
        assertEquals("DECLINED", getCreditStatus());
    }

    @Test
    public void shouldBeErrorsAfterSendingEmptyRequest() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(null, null, null, null, null);
        assertEquals("Неверный формат", buy.getCardNumberError());
        assertEquals("Неверный формат", buy.getMonthError());
        assertEquals("Неверный формат", buy.getYearError());
        assertEquals("Поле обязательно для заполнения", buy.getCardholderError());
        assertEquals("Неверный формат", buy.getCVCError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithoutCardNumber() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(null, getMonth(), getYear(), getNameOfCardholder(), getCVC());
        buy.findMonthError();
        buy.findYearError();
        buy.findCardHolderError();
        buy.findCVCError();
        assertEquals("Неверный формат", buy.getCardNumberError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithoutMonth() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), null, getYear(), getNameOfCardholder(), getCVC());
        buy.findCardNumberError();
        buy.findYearError();
        buy.findCardHolderError();
        buy.findCVCError();
        assertEquals("Неверный формат", buy.getMonthError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithoutYear() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), null, getNameOfCardholder(), getCVC());
        buy.findCardNumberError();
        buy.findMonthError();
        buy.findCardHolderError();
        buy.findCVCError();
        assertEquals("Неверный формат", buy.getYearError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithoutCardHolder() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getYear(), null, getCVC());
        buy.findCardNumberError();
        buy.findMonthError();
        buy.findYearError();
        buy.findCVCError();
        assertEquals("Поле обязательно для заполнения", buy.getCardholderError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithoutCVC() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getYear(), getNameOfCardholder(), null);
        buy.findCardNumberError();
        buy.findMonthError();
        buy.findYearError();
        buy.findCardHolderError();
        assertEquals("Неверный формат", buy.getCVCError());
    }

    @Test
    public void shouldNotAcceptLettersInCardNumberField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFields(getNameOfCardholder(), null, null, null, null);
        assertEquals("", buy.getValueFromCardNumber());
    }

    @Test
    public void shouldNotAcceptSymbolsInCardNumberField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFields(getTextOfSymbols(), null, null, null, null);
        assertEquals("", buy.getValueFromCardNumber());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithShortCardNumber() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getShortCardNumber(), getMonth(), getYear(), getNameOfCardholder(), getCVC());
        buy.findMonthError();
        buy.findYearError();
        buy.findCardHolderError();
        buy.findCVCError();
        assertEquals("Неверный формат", buy.getCardNumberError());
    }

    @Test
    public void shouldFillFieldCardNumberMax16Digits() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFields(getLongCardNumber(), null, null, null, null);
        assertEquals("4444 4444 4444 4441", buy.getValueFromCardNumber());
    }

    @Test
    public void shouldBeErrorNotificationAfterSendingRequestWithRandomCardNumber() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getRandomCardNumber(), getMonth(), getYear(), getNameOfCardholder(), getCVC());
        buy.waitForNotificationERROR();
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithInvalidCardNumber() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getInvalidCardNumber(), getMonth(), getYear(), getNameOfCardholder(), getCVC());
        buy.findMonthError();
        buy.findYearError();
        buy.findCardHolderError();
        buy.findCVCError();
        assertEquals("Неверный формат", buy.getCardNumberError());
    }

    @Test
    public void shouldNotAcceptLettersInMonthField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFields(null, getNameOfCardholder(), null, null, null);
        assertEquals("", buy.getValueFromMonth());
    }

    @Test
    public void shouldNotAcceptSymbolsInMonthField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFields(null, getTextOfSymbols(), null, null, null);
        assertEquals("", buy.getValueFromMonth());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithInvalidMothBelow() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getInvalidMonthBelow(), getYear(), getNameOfCardholder(), getCVC());
        assertEquals("Неверно указан срок действия карты", buy.getMonthError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithInvalidMothAbove() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getInvalidMonthAbove(), getYear(), getNameOfCardholder(), getCVC());
        assertEquals("Неверно указан срок действия карты", buy.getMonthError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithInvalidMothOf1Digit() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getInvalidMonthOf1Symbol(), getYear(), getNameOfCardholder(), getCVC());
        assertEquals("Неверный формат", buy.getMonthError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithPreviousDate() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonthFromPreviousDate(), getYearFromPreviousDate(), getNameOfCardholder(), getCVC());
        assertEquals("Неверно указан срок действия карты", buy.getMonthError());
    }

    @Test
    public void shouldNotAcceptLettersInYearField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFields(null, null, getNameOfCardholder(), null, null);
        assertEquals("", buy.getValueFromYear());
    }

    @Test
    public void shouldNotAcceptSymbolsInYearField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFields(null, null, getTextOfSymbols(), null, null);
        assertEquals("", buy.getValueFromYear());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithPreviousYear() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getPreviousYear(), getNameOfCardholder(), getCVC());
        assertEquals("Истёк срок действия карты", buy.getYearError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithNextYear() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getNextYear(), getNameOfCardholder(), getCVC());
        assertEquals("Неверно указан срок действия карты", buy.getYearError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithDigitsInCardholderField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getYear(), getCVC(), getCVC());
        assertEquals("Поле должно содержать латинские буквы, допустимы дефис и пробел", buy.getCardholderError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithSymbolsInCardholderField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getYear(), getTextOfSymbols(), getCVC());
        assertEquals("Поле должно содержать латинские буквы, допустимы дефис и пробел", buy.getCardholderError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithTextInRussianInCardholderField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getYear(), getTextInRussian(), getCVC());
        assertEquals("Поле должно содержать латинские буквы, допустимы дефис и пробел", buy.getCardholderError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithShortNameInCardholderField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getYear(), getShortName(), getCVC());
        assertEquals("Введите данные в диапазоне от 4 до 60 символов", buy.getCardholderError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithLongNameInCardholderField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getYear(), getLongName(), getCVC());
        assertEquals("Введите данные в диапазоне от 4 до 60 символов", buy.getCardholderError());
    }

    @Test
    public void shouldNotAcceptLettersInCVCField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFields(null, null, null, null, getNameOfCardholder());
        assertEquals("", buy.getValueFromCVC());
    }

    @Test
    public void shouldNotAcceptSymbolsInCVCField() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFields(null, null, null, null, getTextOfSymbols());
        assertEquals("", buy.getValueFromCVC());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithCVCOf1Digit() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getYear(), getNameOfCardholder(), getCVCOf1Digit());
        assertEquals("Неверный формат", buy.getCVCError());
    }

    @Test
    public void shouldBeErrorAfterSendingRequestWithCVCOf2Digits() {
        OfferPage offerPage = new OfferPage();
        offerPage.openByWithCreditCard();
        var buy = new ByWithCreditCard();
        buy.shouldFillFieldsAndSendRequest(getApprovedCardNumber(), getMonth(), getYear(), getNameOfCardholder(), getCVCOf2Digits());
        assertEquals("Неверный формат", buy.getCVCError());
    }
}