package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful schedule and reschedule meeting")
    void shouldSuccessfulScheduleAndRescheduleMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysShiftForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysShiftForFirstMeeting);
        var daysShiftForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysShiftForSecondMeeting);

        SelenideElement form = $("form");
        form.$("span[data-test-id=city] input.input__control")
                .setValue(validUser.getCity());
        form.$("span[data-test-id=date] input.input__control")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("span[data-test-id=date] input.input__control")
                .setValue(firstMeetingDate);
        form.$("span[data-test-id=name] input.input__control")
                .setValue(validUser.getName());
        form.$("span[data-test-id=phone] input.input__control")
                .setValue(validUser.getPhone());
        form.$("label[data-test-id=agreement]")
                .click();
        form.$$("button").find(exactText("Запланировать")).click();

        $("div[data-test-id=success-notification] div.notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

        form.$("span[data-test-id=date] input.input__control")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        form.$("span[data-test-id=date] input.input__control")
                .setValue(secondMeetingDate);
        form.$$("button").find(exactText("Запланировать")).click();

        $("div[data-test-id=replan-notification] div.notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15));

        $$("button")
                .find(exactText("Перепланировать")).click();

        $("div[data-test-id=success-notification] div.notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
